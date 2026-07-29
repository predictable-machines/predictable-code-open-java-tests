# Binary-only dependency fixture (`moneyguard`)

> Part of the internal testing scaffolding described in the [repository
> README](../README.md). Not an example of anything.

This directory holds a **dependency that exists here only as a compiled JAR**,
with no source. That is the whole point: a tool that wants to say anything about
the calls in `src/main/java/payments/PaymentProcessor.java` has to work from the
`.class` bytes, because there is no source to read.

## Contents

- `moneyguard.jar` — the compiled dependency. There is deliberately **no source**
  in this repository.

`MoneyGuard` exposes three static, all-`int` guard helpers, one per return shape
we wanted covered. `javap -p -classpath lib/moneyguard.jar moneyguard.MoneyGuard`
shows the signatures; the conditions under which each one throws are:

| method | descriptor | throws unless |
|---|---|---|
| `requirePositiveAmount(int)` | `(I)I` single return | `amount > 0` |
| `requireSufficientFunds(int, int)` | `(II)V` void guard | `amount > 0 && amount <= balance` |
| `feeTier(int)` | `(I)I` piecewise | `amount >= 0` |

`src/main/java/payments/PaymentProcessor.java` calls each one.

## The dependency's source (reference only)

Reproduced here for a human reader; it is **not** in the repository tree. If you
rebuild the jar from this, keep the method bodies identical, since they are what
determines the conditions above.

```java
package moneyguard;

public final class MoneyGuard {
    private MoneyGuard() {}

    // Single-return guard. Throws unless amount > 0.
    public static int requirePositiveAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        return amount;
    }

    // Void guard; the whole contract is the throw condition.
    // Throws unless amount > 0 && amount <= balance.
    public static void requireSufficientFunds(int amount, int balance) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be positive");
        }
        if (amount > balance) {
            throw new IllegalStateException("insufficient funds");
        }
    }

    // Piecewise return: throws unless amount >= 0, and returns 0 for 0,
    // 1 for anything positive.
    public static int feeTier(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must not be negative");
        }
        if (amount == 0) {
            return 0;
        }
        return 1;
    }
}
```

## Classpath

`moneyguard.jar` is declared as a **system-scoped dependency** in the project's
[`pom.xml`](../pom.xml) (`systemPath` → `lib/moneyguard.jar`), so anything that
recovers the classpath through Maven (`mvn dependency:build-classpath`) picks it
up with no install or deploy step and no manual classpath.

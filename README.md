# predictable-code-open-java-tests — branch `demo/faulty-vanilla-banking`

> [!WARNING]
> **Internal testing scaffolding. Not an example, not a demo, not documentation.**
>
> This repository is public so that the GitHub integrations we are testing (code
> scanning alerts, checks, pull request annotations) are available on it. It
> exists for no purpose beyond exercising those integrations against a throwaway
> project.
>
> It is **not** meant to illustrate anything:
>
> - It is **not** a tutorial, sample project, or reference implementation.
> - It does **not** show how to use Predictable Code, and the commands and file
>   layouts here are not a guide to anything.
> - It does **not** represent Predictable Code's capabilities, output quality, or
>   roadmap. Nothing here should be read as a product claim.
> - **The code on this branch is deliberately wrong**, in the specific ways
>   listed below. Do **not** copy it.
> - Nothing here is supported. It may be rewritten, force-pushed, or deleted
>   without notice, and issues and pull requests are not monitored.
>
> If you are looking for information about Predictable Code, this repository is
> not it.

## What is on this branch

The same small vanilla Java banking application as `main`, with three
intentional behavioral bugs added, plus a couple of extra classes that exist
only to exhibit specific runtime error shapes. Everything still compiles and
runs; the bugs are wrong *behavior*, not compile errors. They are here so that a
checking tool has something to find.

### Bug 1: `Account.withdraw` can leave a negative balance

`withdraw` applies a fixed fee of 2 *after* the solvency check, so a withdrawal
whose amount equals the balance succeeds and then leaves the balance at `-2`.
Two things are wrong: the balance check ignores the fee, and the amount actually
deducted is `amount + 2` rather than `amount`.

### Bug 2: `Bank.transfer` does not conserve money

`transfer` debits the sender by `amount + 1` (a transfer fee) and the inner
`Account.withdraw` then applies its own fee of 2, so the sender loses
`amount + 3` while the recipient receives only `amount`. The fee is not recorded
in the transaction log either. Starting from Alice 11 / Bob 50, `transfer(10)`
leaves Alice at -2 and Bob at 60, so the total across accounts drops from 61
to 58.

### Bug 3: `Account.deposit` accepts non-positive amounts

The `amount <= 0` validation was removed, so `deposit(-5)` silently subtracts 5
from the balance instead of being rejected.

`src/main/java/bank/MainTest.java` walks through all three and prints the
resulting balances.

## Extra classes

- `src/main/java/showcase/` — one method per runtime error shape (guard throw,
  division by zero, null dereference, list index out of bounds, negative array
  size), each written so the failure is reachable from a caller. Deliberately
  contrived; not code anyone should imitate.
- `src/main/java/payments/PaymentProcessor.java` — calls `moneyguard.MoneyGuard`,
  a helper this project has only as a compiled JAR (`lib/moneyguard.jar`, no
  source, on purpose), so that a checking tool has to work from bytecode. See
  [`lib/README.md`](lib/README.md).

## Requirements

- Java 17+ (the `pom.xml` targets 21)

## How to run

On this branch `Main` reads an account name and an initial balance from
`args`, without validating either (that is also on purpose), so it needs two
arguments and throws `ArrayIndexOutOfBoundsException` without them:

```bash
javac src/main/java/bank/*.java
java -cp src/main/java bank.Main carol 100
```

`MainTest` takes no arguments and prints the three bugs:

```bash
java -cp src/main/java bank.MainTest
```

Compiling the `payments` package with plain `javac` needs the JAR on the
classpath:

```bash
javac -cp lib/moneyguard.jar -d /tmp/out src/main/java/payments/PaymentProcessor.java
```

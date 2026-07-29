package payments;

import moneyguard.MoneyGuard;

/**
 * Uses the {@link MoneyGuard} dependency, which reaches this project only as a
 * compiled JAR (lib/moneyguard.jar) with no source anywhere in the tree, so the
 * conditions under which its methods throw are recoverable only from bytecode.
 * The call sites below differ in whether those conditions hold:
 *
 *   - chargeUnchecked forwards an unconstrained parameter, so nothing
 *     establishes `amount > 0`: it can throw IllegalArgumentException.
 *   - chargeMinimum passes a literal that satisfies the guard, so it cannot.
 *   - settle / classify likewise forward unconstrained inputs.
 *
 * Deliberately contrived; see the repository README.
 */
public class PaymentProcessor {

    /** Unconstrained amount: nothing establishes `amount > 0`. */
    public int chargeUnchecked(int amount) {
        return MoneyGuard.requirePositiveAmount(amount);
    }

    /** Literal amount that satisfies the guard, so this one cannot throw. */
    public int chargeMinimum() {
        return MoneyGuard.requirePositiveAmount(1);
    }

    /** Void guard over two unconstrained ints (needs amount>0 && amount<=balance). */
    public int settle(int amount, int balance) {
        MoneyGuard.requireSufficientFunds(amount, balance);
        return amount;
    }

    /** Piecewise guard: throws for a negative amount. */
    public int classify(int amount) {
        return MoneyGuard.feeTier(amount);
    }
}

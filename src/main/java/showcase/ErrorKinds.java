package showcase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Deliberately contrived: one method per shape of runtime failure we wanted a
// checking tool to have to deal with. Nobody should write code like this.
//
// Each guard throw carries a unique message so a test can identify it by a
// substring match.
//
// `driver` calls the failing methods with non-literal arguments, so those
// failures are reachable from existing code; `futurePotentialGuard` is never
// called, so its throw is only reachable from a hypothetical future caller.
public class ErrorKinds {

    private final Map<String, Holder> holders = new HashMap<>();
    private final List<Integer> items = new ArrayList<>();

    // A guard throw that a caller below can actually reach.
    public int reachableGuard(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("ErrorKinds: guard rejects non-positive amount");
        }
        return amount;
    }

    // Division by zero: nothing rules out b == 0, so this can throw
    // ArithmeticException.
    public int divByZero(int a, int b) {
        return a / b;
    }

    // Null dereference: a `Map.get` result is used without a null check, so a
    // missing key throws NullPointerException.
    public int npeFromMap(String key, int amount) {
        Holder h = holders.get(key);
        return h.consume(amount);
    }

    // Out-of-range list index: nothing constrains i to the list's bounds.
    public int indexOutOfBounds(int i) {
        return items.get(i);
    }

    // Negative array size at allocation.
    public int negativeArray(int n) {
        int[] xs = new int[n];
        return xs.length;
    }

    // Never called below, so this guard throw is reachable only by a
    // hypothetical future caller, not by any current code.
    public int futurePotentialGuard(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("ErrorKinds: future-only guard rejects non-positive");
        }
        return amount;
    }

    // The existing caller. Every argument is non-literal, so nothing here rules
    // out the failures above.
    public int driver(int x, String key) {
        int g = reachableGuard(x);
        int d = divByZero(x, g);
        int m = npeFromMap(key, x);
        int idx = indexOutOfBounds(x);
        int len = negativeArray(x);
        return g + d + m + idx + len;
    }
}

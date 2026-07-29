package showcase;

// Companion for ErrorKinds.npeFromMap: a plain project class, so that the
// `Holder`-typed map value there is a nullable reference to a class defined in
// this project rather than to a library type.
public final class Holder {

    private final int stock;

    public Holder(int stock) {
        this.stock = stock;
    }

    public int consume(int amount) {
        return stock - amount;
    }
}

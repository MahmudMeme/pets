package mk.ukim.finki.emt.ptes.model.exceptions;

public class PetsNotFound extends RuntimeException {
    public PetsNotFound() {
        super("There are no pets in your database");
    }
}

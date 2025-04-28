package mk.ukim.finki.emt.ptes.model.exceptions;

public class UsersNotFound extends RuntimeException {
    public UsersNotFound() {
        super("There are 0 users in the database");
    }
}

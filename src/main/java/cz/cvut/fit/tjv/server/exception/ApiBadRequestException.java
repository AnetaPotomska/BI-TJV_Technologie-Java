package cz.cvut.fit.tjv.server.exception;

public class ApiBadRequestException extends RuntimeException {
    public ApiBadRequestException(String message) {
        super(message); // super here means it will call superclass (here RunTimeException)
    }
}

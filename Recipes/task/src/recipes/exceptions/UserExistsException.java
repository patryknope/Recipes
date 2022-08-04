package recipes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "User already found.")
public class UserExistsException extends RuntimeException {
    public UserExistsException() {
        super();
    }
}
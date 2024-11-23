package banquemisr.challenge05.task.management.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    private String message;
    private HttpStatus httpStatus;

}


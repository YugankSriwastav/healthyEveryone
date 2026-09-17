package shiva_care.healthify.exception.ExceptionHandling;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    @Id
    long id;
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    public ExceptionResponse(
            LocalDateTime now,
            int value,
            String userIsAlreadyCreated,
            String message,
            String requestURI
    ) {
        this.timestamp = now;
        this.status = value;
        this.error = userIsAlreadyCreated;
        this.message = message;
        this.path = requestURI;
    }
}

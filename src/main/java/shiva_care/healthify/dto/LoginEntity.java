package shiva_care.healthify.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LoginEntity {
    @Id
    long id;
    @NotBlank
    String userName;
    @NotBlank
    String password;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$",
            message = "Only Gmail address is allowed")
    @NotBlank
    String gmail;
    String role;
}

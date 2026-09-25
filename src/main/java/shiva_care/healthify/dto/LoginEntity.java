package shiva_care.healthify.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class LoginEntity {
    @NotBlank
    String userName;
    @NotBlank
    String password;
    String role;

}

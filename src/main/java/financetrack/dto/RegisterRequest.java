package financetrack.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "El nombre completo es obligatorio")
    @Size(
            min = 3,
            max = 150,
            message = "El nombre debe tener entre 3 y 150 caracteres"
    )
    private String nombreCompleto;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(
            min = 8,
            message = "La contraseña debe tener al menos 8 caracteres"
    )
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[0-9]).+$",
            message = "La contraseña debe contener al menos una mayúscula y un número"
    )
    private String password;

    @NotBlank(message = "El rol es obligatorio")
    private String rol;
}
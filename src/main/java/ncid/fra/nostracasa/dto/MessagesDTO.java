package ncid.fra.nostracasa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagesDTO {

    @NotBlank(message = "Introduce un nombre.")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres.")
    private String name;

    @NotBlank(message = "Introduce un email.")
    @Email(message = "El email debe ser válido.")
    private String email;

    @NotBlank(message = "Introduce un asunto.")
    @Size(min = 2, max = 50, message = "El asunto debe tener entre 2 y 50 caracteres.")
    private String subject;

    @NotBlank(message = "Introduce un número.")
    @Pattern(
            regexp = "^\\+?[0-9\\s-]{7,15}$",
            message = "Introduce un número de teléfono válido."
    )
    private String phone;

    @Size(min = 2, max = 3000, message = "El mensaje debe tener entre 2 y 3000 caracteres.")
    private String messageText;
}

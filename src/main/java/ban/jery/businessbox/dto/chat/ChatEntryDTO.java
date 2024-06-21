package ban.jery.businessbox.dto.chat;

import ban.jery.businessbox.model.Business;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatEntryDTO {

    @NotBlank
    private String body;

    @NotNull
    private String businessName;

    @NotNull
    @Email
    private String sender;

    private String createdAt = null;
}

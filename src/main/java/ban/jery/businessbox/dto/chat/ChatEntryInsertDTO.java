package ban.jery.businessbox.dto.chat;

import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatEntryInsertDTO {

    @NotBlank
    private String body;

    @NotNull
    private Business business;

    @NotNull
    private User sender;
}

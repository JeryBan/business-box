package ban.jery.businessbox.dto.chat;

import ban.jery.businessbox.dto.BaseDTO;
import ban.jery.businessbox.model.Business;
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
public class ChatEntryRoDTO extends BaseDTO {

    @NotBlank
    private String body;

    @NotNull
    private Business business;

    public ChatEntryRoDTO(Long id, String body, Business business) {
        setId(id);
        this.body = body;
        this.business = business;
    }
}

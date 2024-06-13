package ban.jery.businessbox.dto.user;

import ban.jery.businessbox.dto.BaseDTO;
import ban.jery.businessbox.model.AccountSettings;
import ban.jery.businessbox.model.Business;
import ban.jery.businessbox.model.ChatEntry;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRoDTO extends BaseDTO {

    @NotBlank(message = "username is required.")
    @Email(message = "Invalid email address.")
    private String email;

    @NotBlank(message = "password is required.")
    private String password;

    private Set<Business> businesses;

    private List<ChatEntry> chatEntryList;

    private AccountSettings accountSettings;

    public UserRoDTO(Long id, String email, String password, Set<Business> businesses,
                     List<ChatEntry> chatEntryList, AccountSettings accountSettings) {
        this.email = email;
        this.password = password;
        this.businesses = businesses;
        this.chatEntryList = chatEntryList;
        this.accountSettings = accountSettings;
        setId(id);
    }
}

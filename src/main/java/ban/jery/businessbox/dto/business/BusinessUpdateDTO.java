package ban.jery.businessbox.dto.business;

import ban.jery.businessbox.dto.BaseDTO;
import ban.jery.businessbox.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BusinessUpdateDTO extends BaseDTO {

    @NotNull
    @Column(nullable = false)
    private String name;


}

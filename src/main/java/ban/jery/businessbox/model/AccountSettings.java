package ban.jery.businessbox.model;

import ban.jery.businessbox.config.Theme;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account_settings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Theme theme = Theme.LIGHT;

    private boolean isVisibleDashboard = true;

    private boolean notificationsEnabled = true;

}

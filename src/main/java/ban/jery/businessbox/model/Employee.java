package ban.jery.businessbox.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "employees")
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    @NotNull
    @Column(nullable = false)
    private String lastname;

    @Length(min = 10, max = 10)
    @Column(length = 10)
    private String phoneNumber;

    @Email
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String filesPath;
}

package banquemisr.challenge05.task.management.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

import static banquemisr.challenge05.task.management.constant.ErrorMessages.BLANK_PASSWORD;
import static banquemisr.challenge05.task.management.constant.ErrorMessages.BLANK_USERNAME;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@Table(name = "USER_ACCOUNT")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = BLANK_USERNAME)
    private String username;
    @NotBlank(message = BLANK_PASSWORD)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

}





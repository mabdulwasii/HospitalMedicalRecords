package guru.oze.hospitalmedicalrecords.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name = "user",
        uniqueConstraints = {
                @UniqueConstraint(name = "uc_user_username", columnNames = "username"),
                @UniqueConstraint(name = "uc_user_uuid", columnNames = {"uuid"})
        },
        indexes = {
                @Index(name = "idx_staff_uuid", columnList = "uuid")
        }
)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Size(min = 5, max = 200)
    @Column(length = 200, unique = true, nullable = false)
    private String username;

    @JsonIgnore
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "password", length = 256, nullable = false)
    private String password;

    @NotNull
    @Column(nullable = false)
    @Builder.Default
    private boolean activated = true;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    @ToString.Exclude
    @Builder.Default
    private Set<Authority> authorities = new HashSet<>();

    public User(@NotNull @Size(min = 5, max = 200) String username, @NotNull @Size(min = 1, max = 256) String password) {
        this.username = username;
        this.password = password;
    }

    public User(Integer id, @NotNull @Size(min = 5, max = 200) String username,
                @NotNull @Size(min = 1, max = 256) String password, @NotNull boolean activated) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.activated = activated;
    }
}

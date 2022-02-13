package guru.oze.hospitalmedicalrecords.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Table(name = "staff", indexes = {
        @Index(name = "idx_staff_uuid", columnList = "uuid")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uc_staff_uuid", columnNames = {"uuid"})
})
@NaturalIdCache
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @NaturalId
    @Builder.Default
    @Column(name = "uuid", nullable = false, unique = true)
    private String uuid = UUID.randomUUID().toString();

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Staff staff = (Staff) o;
        return id != null && Objects.equals(id, staff.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
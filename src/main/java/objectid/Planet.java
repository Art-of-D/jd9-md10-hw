package objectid;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "planet")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Planet {
    @Id
    private String id;

    @Column(name = "name", length = 500)
    private String name;
}

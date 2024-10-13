package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class ItemEntity {
    @Id
    private String id;

    private String name;

    private String description;

    private String category;

    private Double qty ;

    private Integer price;
}

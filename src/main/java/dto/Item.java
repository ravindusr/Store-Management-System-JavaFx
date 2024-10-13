package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item {
    private String id;

    private String name;

    private String description;

    private String category;

    private Integer qty ;

    private Double price;
}

package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartTm {

    private  String itemCode;

    private String itemName;

    private Double unitPrice;

    private Integer qty;

    private Double total;

}

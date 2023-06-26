package lk.ijse.restaurant.dto.Tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ItemTm {
    private String IID;
    private String itemName;
//    private String MatirialType;
//    private String Size;
//    private String color;
    private Integer UnitPrize;
    private Integer QtyOnHand;

}

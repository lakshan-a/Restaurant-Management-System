package lk.ijse.restaurant.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ItemDto {
    private String IID;
    private String itemName;
    private int UnitPrize;
    private int QtyOnHand;


}


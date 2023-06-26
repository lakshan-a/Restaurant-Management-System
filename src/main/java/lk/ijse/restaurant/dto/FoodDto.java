package lk.ijse.restaurant.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class FoodDto {
    private String foodNumber;
    private String description;
    private double Price;
    private int qtyOnHand;
    private String itemId;

}

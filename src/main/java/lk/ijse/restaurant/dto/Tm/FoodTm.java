package lk.ijse.restaurant.dto.Tm;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class FoodTm {
    private String foodNumber;
    private String description;
    private double Price;
    private int Qty;
    private String itemId;

}

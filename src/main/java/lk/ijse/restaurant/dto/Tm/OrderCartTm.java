package lk.ijse.restaurant.dto.Tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderCartTm {
    private String code;
    private String name;
    private Integer qty;
    private Double unitPrice;
    private Double total;
    private String delivery;
    private Button btnAction;

}

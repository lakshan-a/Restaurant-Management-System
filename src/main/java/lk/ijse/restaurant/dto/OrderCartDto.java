package lk.ijse.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class OrderCartDto {
    private String code;
    private Integer qty;
    private String delivery;
}

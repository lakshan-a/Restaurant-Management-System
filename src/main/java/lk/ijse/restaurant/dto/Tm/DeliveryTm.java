package lk.ijse.restaurant.dto.Tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeliveryTm {
    private String deliveryId;
    private String empId;
    private String orderId;
    private String location;
    private String deliveryDate;
    private String dueDate;
    private String deliveryStaus;
}

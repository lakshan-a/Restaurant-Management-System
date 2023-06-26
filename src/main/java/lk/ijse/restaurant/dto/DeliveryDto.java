package lk.ijse.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DeliveryDto {
    private String deliveryId;
    private String empId;
    private String orderId;
    private String location;
    private String deliveryDate;
    private String dueDate;
    private String deliveryStaus;
}

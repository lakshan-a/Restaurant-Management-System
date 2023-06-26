package lk.ijse.restaurant.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class OrderDto {
    private String orderId;
    private String custId;
    private double payment;
    private String time;
    private String date;
    private String deliveryStatus;

}

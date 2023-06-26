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

public class OrderTm {
    private String orderId;
    private String custId;
    private double payment;
    private String time;
    private String date;
    private String deliveryStatus;

}

package lk.ijse.restaurant.dto.Tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SalaryTm {
    private String salaryId;
    private String empId;
    private String paymentMethod;
    private Double payment;
    private Double overTime;


}

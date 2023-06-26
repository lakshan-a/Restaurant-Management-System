package lk.ijse.restaurant.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class SalaryDto {
    private String salaryId;
    private String empId;
    private String paymentMethod;
    private Double payment;
    private Double overTime;

}

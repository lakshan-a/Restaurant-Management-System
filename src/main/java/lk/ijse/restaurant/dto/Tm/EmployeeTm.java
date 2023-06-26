package lk.ijse.restaurant.dto.Tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeTm {
    private String empId;
    private String empName;
    private String address;
    private String dob;
    private String contactNo;
    private String email;
    private String nic;

}
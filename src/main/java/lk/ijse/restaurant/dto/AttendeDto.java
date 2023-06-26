package lk.ijse.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttendeDto {

    private String AID;
    private String EID;
    private String attendance;
    private Integer workingHourse;
    private String date;

}

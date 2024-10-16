package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {

    private String empId;
    private String empName;
    private String gender;
    private String address;
    private String contact;
    private String nic ;
    private Double salary;
}

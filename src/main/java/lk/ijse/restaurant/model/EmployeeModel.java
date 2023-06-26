package lk.ijse.restaurant.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.restaurant.dto.EmployeeDto;
import lk.ijse.restaurant.dto.Tm.EmployeeTm;
import lk.ijse.restaurant.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {

    public static List<String> loadEmpIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT empId FROM Employee";
        List<String> allEmpIds = new ArrayList<>();

        ResultSet resultSet = CrudUtil.crudUtil(sql);
        while (resultSet.next()){
            allEmpIds.add(resultSet.getString(1));
        }
        return allEmpIds;
    }

    public static ObservableList<EmployeeTm> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM employee";

        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.crudUtil(sql);
        while (resultSet.next()) {
            obList.add(new EmployeeTm(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return obList;
    }

    public static boolean save(EmployeeDto employee) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Employee(empId, empName, address, dob, contactNo, email, nic)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return CrudUtil.crudUtil(
                sql,
                employee.getEmpId(),
                employee.getEmpName(),
                employee.getAddress(),
                employee.getDob(),
                employee.getContactNo(),
                employee.getEmail(),
                employee.getNic()
        );
    }

    public static boolean update(EmployeeDto employee) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Employee SET empName =?, address =?, dob =?, contactNo =?, " +
                "email =?, nic =? WHERE empId =?";
        return CrudUtil.crudUtil(
                sql,
                employee.getEmpName(),
                employee.getAddress(),
                employee.getDob(),
                employee.getContactNo(),
                employee.getEmail(),
                employee.getNic(),
                employee.getEmpId()
        );
    }

    public static boolean delete(String empId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Employee WHERE empId = ?";
        return CrudUtil.crudUtil(sql, empId);
    }

    public static EmployeeDto search(String empId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Employee WHERE empId =?";
        ResultSet resultSet = CrudUtil.crudUtil(sql, empId);

        if(resultSet.next()){
            return new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
        }
        return null;
    }
}

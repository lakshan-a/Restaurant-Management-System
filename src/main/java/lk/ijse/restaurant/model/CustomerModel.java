package lk.ijse.restaurant.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.restaurant.dto.CustomerDto;
import lk.ijse.restaurant.dto.Tm.CustomerTm;
import lk.ijse.restaurant.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel {

    public static boolean save(CustomerDto customer) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Customer(custId, custName, contactNo , address,email)" +
                "VALUES(?, ?, ?, ?,?)";
        return CrudUtil.crudUtil(sql,
                customer.getId(),
                customer.getName(),
                customer.getContact(),
                customer.getAddress(),
                customer.getEmail());
    }

    public static ObservableList<CustomerTm> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Customer";

        ObservableList<CustomerTm> allData = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.crudUtil(sql);
        while (resultSet.next()){
            allData.add(new CustomerTm(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)

            ));
        }
        return allData;
    }

    public static boolean update(CustomerDto customer) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Customer SET custName = ?,contactNo=?, address = ?, email = ?  WHERE custId = ?";

        return CrudUtil.crudUtil(
                sql,
                customer.getName(),
                customer.getContact(),
                customer.getAddress(),
                customer.getEmail(),
                customer.getId()
        );
    }

    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Customer WHERE custId = ?";

        return CrudUtil.crudUtil(sql,id);
    }

    public static CustomerDto searchById(String text) throws ClassNotFoundException {
        String sql = "SELECT * FROM customer WHERE custId=?";
        ResultSet resultSet = null;
        try {
            resultSet = CrudUtil.crudUtil(sql, text);
            if (resultSet.next()) {
                return new CustomerDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return null;
    }

    public static List<String> loadCustomerIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT custId FROM customer";
        List<String> allCustIds = new ArrayList<>();

        ResultSet resultSet = CrudUtil.crudUtil(sql);
        while (resultSet.next()){
            allCustIds.add(resultSet.getString(1));
        }
        return allCustIds;
    }

    public static String getCustCount() throws SQLException, ClassNotFoundException {
        String sql = "select count(empId) from employee";

        ResultSet resultSet = CrudUtil.crudUtil(sql);

        String count;
        if(resultSet.next()) {
            return count = String.valueOf(resultSet.getInt(1));
        }
        return String.valueOf(0);
    }

}

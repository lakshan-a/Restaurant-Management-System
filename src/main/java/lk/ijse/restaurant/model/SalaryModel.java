package lk.ijse.restaurant.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.restaurant.dto.SalaryDto;
import lk.ijse.restaurant.dto.Tm.SalaryTm;
import lk.ijse.restaurant.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SalaryModel {
    public static boolean save(SalaryDto salary) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO salary(salaryId,EmpId,salaryPaymentMethod,salaryPayment,salaryOt)" +
                "VALUES(?,?,?,?,?)";

        return CrudUtil.crudUtil(sql,
                salary.getSalaryId(),
                salary.getEmpId(),
                salary.getPaymentMethod(),
                salary.getPayment(),
                salary.getOverTime());
    }

    public static ObservableList<SalaryTm> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM salary";

        ObservableList<SalaryTm> salaryData = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.crudUtil(sql);

        while (resultSet.next()) {
            salaryData.add(new SalaryTm(
                    resultSet.getString(2),
                    resultSet.getString(1),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5)

            ));

        }
        return salaryData;


    }


    public static boolean update(SalaryDto salary) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE salary SET empId=?,salaryPaymentMethod=?,salaryPayment=?,salaryOt=? WHERE" +
                " salaryId=?";

        return CrudUtil.crudUtil(sql,

                salary.getEmpId(),
                salary.getPaymentMethod(),
                salary.getPayment(),
                salary.getOverTime(),
                salary.getSalaryId());
    }

    public static boolean delete(String value) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM salary WHERE salaryId=?";
        return CrudUtil.crudUtil(sql, value);
    }

    public static SalaryDto searchById(String text) throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM salary WHERE salaryId=?";

         ResultSet resultSet=CrudUtil.crudUtil(sql,text);
         if (resultSet.next()){
             return new SalaryDto(
                     resultSet.getString(1),
                     resultSet.getString(2),
                     resultSet.getString(3),
                     resultSet.getDouble(4),
                     resultSet.getDouble(5)
             );
         }
            return null;
    }
}

  
  
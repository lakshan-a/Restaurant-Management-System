package lk.ijse.restaurant.model;

import lk.ijse.restaurant.dto.AdminDto;
import lk.ijse.restaurant.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminModel {

    public static boolean save(AdminDto user) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO user(Password,Username,Email)VALUES(?,?,?)";

        return CrudUtil.crudUtil(sql,
                user.getPassword(),
                user.getUsername(),
                user.getEmial());


    }

    public static AdminDto SearchById(String username) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM User WHERE username=?";
        ResultSet resultSet = null;

        resultSet = CrudUtil.crudUtil(sql,
                username);
        if (resultSet.next()) {
            return new AdminDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)

            );
        }


        return null;
    }
}

package lk.ijse.restaurant.util;

import lk.ijse.restaurant.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    public static <T> T crudUtil(String sql, Object... arg) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        for (int i = 0; i < arg.length; i++) {
            statement.setObject((i + 1), arg[i]);
        }
        if (sql.startsWith("SELECT")|sql.startsWith("select")|sql.startsWith("Select")) {
            return (T) statement.executeQuery();
        } else {
            return (T) (Boolean) (statement.executeUpdate() > 0);
        }
    }
}
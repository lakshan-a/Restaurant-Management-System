package lk.ijse.restaurant.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.restaurant.dto.AttendeDto;
import lk.ijse.restaurant.dto.Tm.AttendTm;
import lk.ijse.restaurant.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class

AttendanceModel {
    public static ObservableList<AttendTm> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM employeeattendance";

        ObservableList<AttendTm> obList = FXCollections.observableArrayList();

        ResultSet resultSet = CrudUtil.crudUtil(sql);
        while (resultSet.next()) {
            obList.add(new AttendTm(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)
            ));
        }
        return obList;
    }

    public static boolean save(AttendeDto attend) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO employeeattendance VALUES(?, ?, ?, ?, ?)";
        return CrudUtil.crudUtil(
                sql,
                attend.getAID(),
                attend.getEID(),
                attend.getAttendance(),
                attend.getWorkingHourse(),
                attend.getDate()
        );
    }

    public static boolean delete(String attendId) throws SQLException, ClassNotFoundException {
        return CrudUtil.crudUtil("DELETE FROM  employeeattendance WHERE addendanceId=?", attendId);
    }


    public static boolean update(AttendeDto attend) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE employeeattendance set empId=?,workingHourse=?,attendance=?,date=? where addendanceId=?";
        return CrudUtil.crudUtil(sql,
                attend.getEID(),
                attend.getWorkingHourse(),
                attend.getAttendance(),
                attend.getDate(),
                attend.getAID()
        );

    }


//    public AttendeDto search(String id) throws SQLException, ClassNotFoundException {
//        ResultSet rst= CrudUtil.crudUtil("SELECT*FROM attend where AID=?", id);
//        if (rst.next()){
//            return new AttendeDto(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getInt(3),
//                    rst.getString(4),
//                    rst.getString(5)
//            );
//        }
//        return null;
//    }



}

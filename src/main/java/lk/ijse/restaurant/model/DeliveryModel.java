package lk.ijse.restaurant.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.restaurant.dto.DeliveryDto;
import lk.ijse.restaurant.dto.Tm.DeliveryTm;
import lk.ijse.restaurant.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliveryModel {
    public static String getNextDeliveryId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT deliveryId FROM Delivery ORDER BY deliveryId DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.crudUtil(sql);

        if (resultSet.next()) {
            return splitRepairId(resultSet.getString(1));
        }
        return splitRepairId(null);
    }

    private static String splitRepairId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("DE-");
            int id = Integer.parseInt(strings[1]);
            ++id;
            String digit=String.format("%03d", id);
            return "DE-" + digit;
        }
        return "DE-001";
    }

    public static boolean save(DeliveryDto delivery) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Delivery(deliveryId, empId, orderId, location, deliveryDate, dueDate)" +
                "VALUE(?, ?, ?, ?, ?, ?)";
        return CrudUtil.crudUtil(
                sql,
                delivery.getDeliveryId(),
                delivery.getEmpId(),
                delivery.getOrderId(),
                delivery.getLocation(),
                delivery.getDeliveryDate(),
                delivery.getDueDate()
        );
    }

    public static ObservableList<DeliveryTm> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Delivery";
        ResultSet resultSet = CrudUtil.crudUtil(sql);

        ObservableList<DeliveryTm> allData = FXCollections.observableArrayList();

        while (resultSet.next()){
            allData.add(new DeliveryTm(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            ));
        }
        return allData;
    }

    public static boolean update(DeliveryDto delivery) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Delivery SET empid =?, location =?, deliverydate =?, deliveryStatus =? WHERE deliveryId =?";
        return CrudUtil.crudUtil(
                sql,
                delivery.getEmpId(),
                delivery.getLocation(),
                delivery.getDeliveryDate(),
                delivery.getDeliveryStaus(),
                delivery.getDeliveryId()
        );
    }

    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Delivery WHERE deliveryId =?";
        return CrudUtil.crudUtil(sql, id);
    }

    public static DeliveryDto search(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM Delivery WHERE deliveryId = ?";
        ResultSet resultSet = CrudUtil.crudUtil(sql, id);

        if(resultSet.next()){
            return new DeliveryDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7)
            );
        }
        return  null;
    }
}

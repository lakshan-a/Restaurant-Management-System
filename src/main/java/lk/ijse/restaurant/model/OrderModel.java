package lk.ijse.restaurant.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lk.ijse.restaurant.dto.OrderCartDto;
import lk.ijse.restaurant.dto.OrderDto;
import lk.ijse.restaurant.dto.Tm.OrderTm;
import lk.ijse.restaurant.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class OrderModel {

    public static String getNextOrderId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT orderId FROM Orders ORDER BY orderId DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.crudUtil(sql);

        if (resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentId) {
        if(currentId != null) {
            String[] strings = currentId.split("OD-");
            int id = Integer.parseInt(strings[1]);
            ++id;
            String digit=String.format("%03d", id);
            return "OD-" + digit;
        }
        return "OD-001";
    }

    public static boolean save(String ordrId, String customerId, double netTotal, LocalTime time, LocalDate date, List<OrderCartDto> cartDTOList) throws SQLException, ClassNotFoundException {
        for (OrderCartDto orderCartDto: cartDTOList) {
            if(!save(ordrId, customerId, netTotal, time, date, orderCartDto)){
                return false;
            }
            break;
        }
        return true;
    }

    private static boolean save(String ordrId, String customerId, double netTotal, LocalTime time, LocalDate date, OrderCartDto orderCartDto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Orders(orderId, custId, payment, time, date, deliveryStatus)" +
                "VALUES(?, ?, ?, ?, ?, ?)";
        return CrudUtil.crudUtil(
                sql,
                ordrId,
                customerId,
                netTotal,
                time,
                date,
                orderCartDto.getDelivery()
        );
    }

    public static Integer getTodayIncome() throws SQLException, ClassNotFoundException {
        String sql = "select sum(payment) from orders where date= curdate()";

        ResultSet resultSet = CrudUtil.crudUtil(sql);

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public static Integer getMonthlyIncome() throws SQLException, ClassNotFoundException {
        String sql = "select sum(payment) from orders where MONTH(date)= MONTH(curdate())";

        ResultSet resultSet = CrudUtil.crudUtil(sql);

        if(resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public static OrderDto searchById(String text) throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM orders WHERE orderId=?";
        ResultSet resultSet= CrudUtil.crudUtil(sql,text);
        if (resultSet.next()){
            return new OrderDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)

            );
        }
        return null;
    }

    public static OrderDto searchByFoodCode(String code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM orders WHERE orderId= ?";
        ResultSet resultSet = CrudUtil.crudUtil(sql,code);
        if(resultSet.next()){
            return new OrderDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
        }
        return null;
    }

    public static OrderDto search(String num) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM orders WHERE orderId =?";
        ResultSet resultSet = CrudUtil.crudUtil(sql, num);

        if(resultSet.next()){
            return new OrderDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            );
        }
        return null;
    }

    public static ObservableList<OrderTm> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM orders ";

        ObservableList<OrderTm> orderData = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.crudUtil(sql);
        while (resultSet.next()) {
            orderData.add(new OrderTm(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));

        }
        return orderData;
    }

    public static boolean delete(String text) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM orders WHERE orderId=?";
        return CrudUtil.crudUtil(sql, text);
    }

}

package lk.ijse.restaurant.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import lk.ijse.restaurant.dto.ItemDto;
import lk.ijse.restaurant.dto.Tm.ItemTm;
import lk.ijse.restaurant.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {

    public static boolean save(ItemDto item) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO item(itemId, itemName, unitPrize ,qtyOnHand)" +
                "VALUES(?, ?, ?, ?)";
        return CrudUtil.crudUtil(sql,
                item.getIID(),
                item.getItemName(),
                item.getUnitPrize(),
                item.getQtyOnHand());
    }

    public static ObservableList<ItemTm> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM item";

        ObservableList<ItemTm> allData = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.crudUtil(sql);
        while (resultSet.next()){
            allData.add(new ItemTm(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getInt(4)

            ));
        }
        return allData;
    }

    public static boolean update(ItemDto item) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Item SET  itemName= ?, unitPrize=?, qtyOnHand = ?  WHERE  itemId= ?";

        return CrudUtil.crudUtil(
                sql,
                item.getIID(),
                item.getItemName(),
                item.getUnitPrize(),
                item.getQtyOnHand()
        );
    }

    public static boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM item WHERE itemId = ?";

        return CrudUtil.crudUtil(sql,id);
    }

    public static ItemDto searchById(String text) throws ClassNotFoundException {
        String sql = "SELECT * FROM item WHERE itemId=?";
        ResultSet resultSet = null;
        try {
            resultSet = CrudUtil.crudUtil(sql, text);
            if (resultSet.next()) {
                return new ItemDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4)
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        return null;
    }

    public static List<String> loadItemCodes() throws SQLException, ClassNotFoundException {
        String sql = "SELECT itemId FROM item";
        List<String> allCodes = new ArrayList<>();
        ResultSet resultSet = CrudUtil.crudUtil(sql);

        while (resultSet.next()){
            allCodes.add(resultSet.getString(1));
        }
        return allCodes;
    }

    public static List<String> loadItemIds() throws SQLException, ClassNotFoundException {
        String sql = "SELECT itemId FROM item";
        List<String> allItemIds = new ArrayList<>();

        ResultSet resultSet = CrudUtil.crudUtil(sql);
        while (resultSet.next()){
            allItemIds.add(resultSet.getString(1));
        }
        return allItemIds;
    }

    public static String getItemCount() throws SQLException, ClassNotFoundException {
        String sql = "select count(empId) from employee";

        ResultSet resultSet = CrudUtil.crudUtil(sql);

        String count;
        if(resultSet.next()) {
            return count = String.valueOf(resultSet.getInt(1));
        }
        return String.valueOf(0);
    }

}

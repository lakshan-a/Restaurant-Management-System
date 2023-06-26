package lk.ijse.restaurant.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import javafx.scene.chart.XYChart;
import lk.ijse.restaurant.dto.FoodDto;
import lk.ijse.restaurant.dto.OrderCartDto;
import lk.ijse.restaurant.dto.Tm.FoodTm;
import lk.ijse.restaurant.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FoodModel {

    public static boolean save(FoodDto food) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO food(Food_num,Description,Price,qtyOnHand,itemId)" +
                "VALUE(?,?,?,?,?)";

        return CrudUtil.crudUtil(sql,
                food.getFoodNumber(),
                food.getDescription(),
                food.getPrice(),
                food.getQtyOnHand(),
                food.getItemId());


    }

    public static FoodDto search(String num) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM food WHERE Food_num =?";
        ResultSet resultSet = CrudUtil.crudUtil(sql, num);

        if(resultSet.next()){
            return new FoodDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }

    public static ObservableList<FoodTm> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM food";

        ObservableList<FoodTm> foodData = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.crudUtil(sql);
        while (resultSet.next()) {
            foodData.add(new FoodTm(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)
            ));

        }
        return foodData;
    }

    public static boolean update(FoodDto food) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE food SET Description=?,Price=?,qtyOnHand=?,itemId=? " +
                "WHERE Food_num=? ";
        return CrudUtil.crudUtil(sql,
                food.getDescription(),
                food.getPrice(),
                food.getQtyOnHand(),
                food.getItemId(),
                food.getFoodNumber()
        );
    }


//        public ArrayList<FoodDto> getAlll() throws SQLException, ClassNotFoundException {
//        ArrayList<FoodDto> all = new ArrayList<>();
//        ResultSet rst = CrudUtil.crudUtil("SELECT*from food");
//        while (rst.next()) {
//            all.add(
//                    new FoodDto(
//                            rst.getString(1),
//                            rst.getString(2),
//                            rst.getDouble(3),
//                            rst.getInt(4),
//                            rst.getString(5)
//
//                    ));
//        }
//        return all;
//    }

    public static boolean delete(String text) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM food WHERE Food_num=?";
        return CrudUtil.crudUtil(sql, text);
    }

    public static List<String> loadFoodCodes() throws SQLException, ClassNotFoundException {
        String sql = "SELECT Food_num FROM food";
        List<String> allCodes = new ArrayList<>();
        ResultSet resultSet = CrudUtil.crudUtil(sql);

        while (resultSet.next()){
            allCodes.add(resultSet.getString(1));
        }
        return allCodes;
    }

    public static FoodDto searchByFoodCode(String code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM food WHERE Food_num= ?";
        ResultSet resultSet = CrudUtil.crudUtil(sql,code);
        if(resultSet.next()){
            return new FoodDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)
            );
        }
        return null;
    }

    public static ObservableList<XYChart.Series<String, Integer>> getDataToBarChart() throws SQLException, ClassNotFoundException {
        String sql="SELECT Description,qtyOnHand FROM food WHERE qtyOnHand<=100 ";

        ObservableList<XYChart.Series<String, Integer>> datalist = FXCollections.observableArrayList();
        ResultSet resultSet = CrudUtil.crudUtil(sql);

        // Creating a new series object
        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        while(resultSet.next()){
            String Description = resultSet.getString("Description");
            int FoodQty = resultSet.getInt("qtyOnHand");
            series.getData().add(new XYChart.Data<>(Description, FoodQty));
        }

        datalist.add(series);
        return datalist;
    }

    public static boolean updateQty(List<OrderCartDto> cartDTOList) throws SQLException, ClassNotFoundException {
        for (OrderCartDto dto : cartDTOList) {
            if(!updateQty(dto)){
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(OrderCartDto dto) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE food SET qtyOnHand = (qtyOnHand - ?) WHERE Food_num = ?";
        return CrudUtil.crudUtil(sql, dto.getQty(), dto.getCode());
    }

    public static FoodDto searchById(String text) throws SQLException, ClassNotFoundException {
        String sql="SELECT * FROM food WHERE Food_num=?";
        ResultSet resultSet= CrudUtil.crudUtil(sql,text);
        if (resultSet.next()){
            return new FoodDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(2),
                    resultSet.getInt(3),
                    resultSet.getString(4)

            );
        }
        return null;
    }

//    public static boolean delete(String text) {
//    }
}

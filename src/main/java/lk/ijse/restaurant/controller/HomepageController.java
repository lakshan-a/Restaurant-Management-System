package lk.ijse.restaurant.controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import lk.ijse.restaurant.model.CustomerModel;
import lk.ijse.restaurant.model.FoodModel;
import lk.ijse.restaurant.model.OrderModel;
import lk.ijse.restaurant.util.Navigation;

import java.io.IOException;
import java.sql.SQLException;

public class HomepageController {
    @FXML
    private Label lblcustcount;

    @FXML
    private Label lblmonthlyincome;

    @FXML
    private Label lbltodayincome;

    @FXML
    private Text lblAdminName;

    @FXML
    private BarChart<String, Integer> lowstockitemsBarChart;

    static String usernamehere;

    public static void getUsername(String username){
        usernamehere=username;
       // System.out.println(usernamehere);
    }

    @FXML
    void initialize() {
        setDataToNoOfCustomers();
        setDataToTodayIncome();
        setDataToMonthlyIncome();
        setDataToBarChart();

        lblAdminName.setText(usernamehere);
    }

    private void setDataToBarChart() {
        ObservableList<XYChart.Series<String, Integer>> barChartData = null;
        try {
            barChartData = FoodModel.getDataToBarChart();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        lowstockitemsBarChart.setData(barChartData);
    }

    private void setDataToNoOfCustomers() {
        try {
            String value = CustomerModel.getCustCount();
            lblcustcount.setText(value);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setDataToTodayIncome(){
        Integer value= null;
        try {
            value = OrderModel.getTodayIncome();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        lbltodayincome.setText(String.valueOf(value));
    }

    public void setDataToMonthlyIncome(){
        Integer value= null;
        try {
            value = OrderModel.getMonthlyIncome();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        lblmonthlyincome.setText(String.valueOf(value));
    }

}

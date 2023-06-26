package lk.ijse.restaurant.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class FoodMenuMainDishes {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnNext;

    @FXML
    private AnchorPane mainDishes;

    @FXML
    void btnNextOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FoodMenu/FoodMenuStarter_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            mainDishes.getChildren().clear();
            mainDishes.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void btnBackOnAction(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/HomePage/HomePage1_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            mainDishes.getChildren().clear();
            mainDishes.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public void btnBackToFoodFormOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FoodForm_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            mainDishes.getChildren().clear();
            mainDishes.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package lk.ijse.restaurant.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class FoodMenuDesserts {

    @FXML
    private Button btnNext;

    @FXML
    private Button btnBack;

    @FXML
    private AnchorPane desserts;

    @FXML
    void btnNextOnAction(ActionEvent event)throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FoodMenu/FoodMenuDrinks_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            desserts.getChildren().clear();
            desserts.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void btnBackOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FoodMenu/FoodMenuSeaFood_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            desserts.getChildren().clear();
            desserts.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

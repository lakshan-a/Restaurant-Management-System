package lk.ijse.restaurant.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class FoodMenuSeaFood {

    @FXML
    private Button btnNext;

    @FXML
    private Button btnBack;

    @FXML
    private AnchorPane seaFood;

    @FXML
    void btnNextOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FoodMenu/FoodMenuDesserts_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            seaFood.getChildren().clear();
            seaFood.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void btnBackOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FoodMenu/FoodMenuStarter_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            seaFood.getChildren().clear();
            seaFood.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

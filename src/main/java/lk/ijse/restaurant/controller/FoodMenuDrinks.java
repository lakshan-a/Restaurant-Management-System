package lk.ijse.restaurant.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class FoodMenuDrinks {

    @FXML
    private Button btnBack;

    @FXML
    private AnchorPane drinks;

    public void btnBackOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FoodMenu/FoodMenuDesserts_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            drinks.getChildren().clear();
            drinks.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

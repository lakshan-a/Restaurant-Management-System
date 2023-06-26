package lk.ijse.restaurant.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomePage1 {
    public Label lblTime;

    public void initialize(){
        RunningTime();

    }

    private void RunningTime() {
        final Thread thread = new Thread(()->{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                final String time=simpleDateFormat .format(new Date());
                Platform.runLater(()->{
                    lblTime.setText(time);
                });
            }
        });
        thread.start();
    }

    @FXML
    private Button btnHomePage1;

    @FXML
    public AnchorPane ancoRoot;

    @FXML
    private AnchorPane Homepage1;

    @FXML
    private Button btnCheckMenu;


    @FXML
    private Button btnhome1;

    @FXML
    private Button btnhome2;

    @FXML
    private Button btnhome3;

    @FXML
    void btnCheckManuOnAction(ActionEvent event) throws IOException{

    }

    @FXML
    void btnhome1OnAction(ActionEvent event) throws IOException {

    }

    @FXML
    void btnhome2OnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/HomePage/HomePage2_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            Homepage1.getChildren().clear();
            Homepage1.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnhome3OnAction(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/HomePage/HomePage3_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            Homepage1.getChildren().clear();
            Homepage1.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void btnHomePage1OnAction(ActionEvent event) {

    }

    public void btnCheckMenuOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FoodMenu/FoodMenuMainDishes_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            Homepage1.getChildren().clear();
            Homepage1.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

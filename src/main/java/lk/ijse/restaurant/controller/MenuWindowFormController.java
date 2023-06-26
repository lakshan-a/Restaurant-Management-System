package lk.ijse.restaurant.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class MenuWindowFormController  {

    public AnchorPane ancoRoot;

    public Label lblTime;

    @FXML
    private Button btnCheckMenu;

    @FXML
    private Button btnCustomer;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnEmployee;

    @FXML
    private Button btnFood;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnItem;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnOrder;

    @FXML
    private Button btnPayment;

    @FXML
    private Button btnSalary;

    @FXML
    private Button btnUser;

    @FXML
    private Button btnhome1;

    @FXML
    private Button btnhome2;

    @FXML
    private Button btnhome3;


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
    void btnCustomerOnAction(ActionEvent event) throws IOException  {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CustomerPage_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            ancoRoot.getChildren().clear();
            ancoRoot.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnFoodOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FoodForm_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            ancoRoot.getChildren().clear();
            ancoRoot.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/HomePage/HomePage1_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            ancoRoot.getChildren().clear();
            ancoRoot.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnItemOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ItemPage_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            ancoRoot.getChildren().clear();
            ancoRoot.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Stage satge1=new Stage();
        satge1.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Login_form.fxml")))));
        satge1.setTitle("Restaurant Management System");
        satge1.show();
        Stage stage2 = (Stage) btnLogout.getScene().getWindow();
        stage2.close();

    }

    @FXML
    void btnOrderOnAction(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/OrderPageForm.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            ancoRoot.getChildren().clear();
            ancoRoot.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Payment_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            ancoRoot.getChildren().clear();
            ancoRoot.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnSalaryOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/SalaryPageForm.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            ancoRoot.getChildren().clear();
            ancoRoot.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void btnhome2OnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/HomePage/HomePage2_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            ancoRoot.getChildren().clear();
            ancoRoot.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void btnhome3OnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/HomePage/HomePage3_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            ancoRoot.getChildren().clear();
            ancoRoot.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void btnhome1OnAction(ActionEvent event) {


    }

    public void btnCheckMenuOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FoodMenu/FoodMenuMainDishes_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            ancoRoot.getChildren().clear();
            ancoRoot.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void btnEmployeeOnAction(ActionEvent event)  throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/EmployeePage_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            ancoRoot.getChildren().clear();
            ancoRoot.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void btnDashbpoardOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/HomePage_form.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            ancoRoot.getChildren().clear();
            ancoRoot.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void btnDeliveryOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/DeliveryPageForm.fxml"));
        Pane registerPane = fxmlLoader.load();

        try {
            ancoRoot.getChildren().clear();
            ancoRoot.getChildren().setAll(registerPane);
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

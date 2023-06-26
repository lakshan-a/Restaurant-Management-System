package lk.ijse.restaurant.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.restaurant.dto.OrderDto;
import lk.ijse.restaurant.dto.Tm.AttendTm;
import lk.ijse.restaurant.dto.Tm.OrderTm;
import lk.ijse.restaurant.model.*;
import lk.ijse.restaurant.util.AlertController;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class PaymentFormController  implements Initializable {

    @FXML
    private Button btnBill;

    @FXML
    private Button btnDelete;

    @FXML
    private Label lblOrderId;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colDeliveryStatus;

    @FXML
    private TableColumn<?, ?> colOrderDate;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colOrderTime;

    @FXML
    private TableColumn<?, ?> colPayment;

    @FXML
    private JFXComboBox<?> comOrderId;

    @FXML
    private Label lblCustomerId;

    @FXML
    private Label lblDeliveryStatus;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderTime;

    @FXML
    private Label lblPayment;

    @FXML
    private TableView<OrderTm> tblPayment;

    @FXML
    private TextField txtSearch;

    @FXML
    private JFXButton deleteBtn;


    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {
        setCellValueFactory();
        getAll();
    }

    private void setCellValueFactory() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("custId"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        colOrderTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDeliveryStatus.setCellValueFactory(new PropertyValueFactory<>("deliveryStatus"));
    }

    private void getAll(){
        try {
            ObservableList<OrderTm> obList = OrderModel.getAll();

            tblPayment.setItems(obList);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void clearTxtField() {
        lblOrderId.setText("");
        lblCustomerId.setText("");
        lblPayment.setText("");
        lblOrderTime.setText("");
        lblOrderDate.setText("");
        lblDeliveryStatus.setText("");
    }



    @FXML
    void btnBillOnAction(ActionEvent event) {

    }


    @FXML
    void empIdSearchBarOnAction(ActionEvent event) {
        String empId = txtSearch.getText();

        try {
            OrderDto employee = OrderModel.search(empId);

            if(employee != null){
                lblOrderId.setText(employee.getOrderId());
                lblCustomerId.setText(employee.getCustId());
                lblPayment.setText(String.valueOf(employee.getPayment()));
                lblOrderTime.setText(employee.getTime());
                lblOrderDate.setText(employee.getDate());
                lblDeliveryStatus.setText(employee.getDeliveryStatus());
            }else{
                AlertController.errormessage("Invalid Id");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            AlertController.errormessage("Something went wrong");
        }

    }

    @FXML
    void searchIconOnMouseClickedAction(MouseEvent event) {
        String empId = txtSearch.getText();

        try {
            OrderDto employee = OrderModel.search(empId);

            if(employee != null){
                lblOrderId.setText(employee.getOrderId());
                lblCustomerId.setText(employee.getCustId());
                lblPayment.setText(String.valueOf(employee.getPayment()));
                lblOrderTime.setText(employee.getTime());
                lblOrderDate.setText(employee.getDate());
                lblDeliveryStatus.setText(employee.getDeliveryStatus());
            }else{
                AlertController.errormessage("Invalid Id");
            }

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            AlertController.errormessage("Something went wrong");
        }

    }

    @FXML
    void txtSearchOnKeyTypedAction(KeyEvent event) throws SQLException, ClassNotFoundException {
        String searchValue = txtSearch.getText().trim();
        ObservableList<OrderTm> obList = OrderModel.getAll();

        if (!searchValue.isEmpty()) {
            ObservableList<OrderTm> filteredData = obList.filtered(new Predicate<OrderTm>(){
                @Override
                public boolean test(OrderTm employeetm) {
                    return String.valueOf(employeetm.getOrderId()).toLowerCase().contains(searchValue.toLowerCase());
                }
            });
            tblPayment.setItems(filteredData);
        } else {
            tblPayment.setItems(obList);
        }

    }

    public void tableOnMouseClicked(MouseEvent mouseEvent) {

        deleteBtn.setDisable(false);

        TablePosition pos=tblPayment.getSelectionModel().getSelectedCells().get(0);
        int row=pos.getRow();

        ObservableList<TableColumn<OrderTm,?>> columns=tblPayment.getColumns();

        lblOrderId.setText(columns.get(0).getCellData(row).toString());
        lblCustomerId.setText(columns.get(1).getCellData(row).toString());
        lblPayment.setText(columns.get(2).getCellData(row).toString());
        lblOrderTime.setText(columns.get(3).getCellData(row).toString());
        lblOrderDate.setText(columns.get(4).getCellData(row).toString());
        lblDeliveryStatus.setText(columns.get(5).getCellData(row).toString());
    }


    public void btnDeleteOnAction(ActionEvent event) {
        boolean result = AlertController.okconfirmmessage("Are you sure you want to delete ?");
        if(result) {
            try {
                String empId = lblOrderId.getText();

                boolean isDeleted = OrderModel.delete(empId);
                if (isDeleted) {
                    deleteBtn.setDisable(true);
                    setCellValueFactory();
                    getAll();
                    clearTxtField();
                } else {
                    AlertController.errormessage("Not Deleted");
                }
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
                AlertController.errormessage("Something went wrong");
            }
        }

    }
}

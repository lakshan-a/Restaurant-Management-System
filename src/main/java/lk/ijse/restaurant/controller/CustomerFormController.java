package lk.ijse.restaurant.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.restaurant.dto.CustomerDto;
import lk.ijse.restaurant.dto.Tm.CustomerTm;
import lk.ijse.restaurant.model.CustomerModel;
import lk.ijse.restaurant.util.AlertController;
import lk.ijse.restaurant.util.DataValidateController;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class CustomerFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton addBtn;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colContactNo;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private JFXButton deleteBtn;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSearch;


    @FXML
    private JFXButton updateBtn;


    @FXML
    void btnAddOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();

        CustomerDto customer = new CustomerDto(id, name, contact, address, email);


        try {
            boolean isSaved = CustomerModel.save(customer);
            if (isSaved) {
                AlertController.confirmmessage("Customer saved sucessfully");
                defaultTxt();
                addBtn.setDisable(true);
                updateBtn.setDisable(true);
                deleteBtn.setDisable(true);
                txtName.setText("");
                txtId.setText("");
                txtAddress.setText("");
                txtEmail.setText("");
                txtContact.setText("");
                getAll();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void updateBtnOnAction(ActionEvent event) {
        String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String email = txtEmail.getText();
        String contact = txtContact.getText();
        CustomerDto customer = new CustomerDto(id, name,contact, address, email );


        try {
            boolean isUpdated = CustomerModel.update(customer);
            if (isUpdated) {
                AlertController.confirmmessage("Customer updated sucessfully");
                defaultTxt();
                addBtn.setDisable(true);
                updateBtn.setDisable(true);
                deleteBtn.setDisable(true);
                txtName.setText("");
                txtId.setText("");
                txtAddress.setText("");
                txtEmail.setText("");
                txtContact.setText("");
                getAll();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void deleteBtnOnAction(ActionEvent event) {
        String id = txtId.getText();


        try {
            boolean isDelete = CustomerModel.delete(id);
            if (isDelete) {
                AlertController.confirmmessage("Customer deleted sucessfully");

                addBtn.setDisable(true);
                updateBtn.setDisable(true);
                deleteBtn.setDisable(true);
                txtName.setText("");
                txtId.setText("");
                txtAddress.setText("");
                txtEmail.setText("");
                txtContact.setText("");

                getAll();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void empIdSearchOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            CustomerDto customer = CustomerModel.searchById(id);

            if(customer != null){
                txtId.setText(customer.getId());
                txtName.setText(customer.getName());
                txtContact.setText(customer.getContact());
                txtAddress.setText(customer.getAddress());
                txtEmail.setText(customer.getEmail());


            }else{
                AlertController.errormessage("Invalid Id");
            }

        } catch (ClassNotFoundException throwables) {
            throwables.printStackTrace();
            AlertController.errormessage("Something went wrong");
        }

    }

    @FXML
    void tableOnMouseClicked(MouseEvent event) {
        addBtn.setDisable(false);
        updateBtn.setDisable(false);
        deleteBtn.setDisable(false);
        TablePosition pos = tblCustomer.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();

        ObservableList<TableColumn<CustomerTm, ?>> columns = tblCustomer.getColumns();

        txtId.setText(columns.get(0).getCellData(row).toString());
        txtName.setText(columns.get(1).getCellData(row).toString());
        txtContact.setText(columns.get(3).getCellData(row).toString());
        txtAddress.setText(columns.get(2).getCellData(row).toString());
        txtEmail.setText(columns.get(4).getCellData(row).toString());
    }

    @FXML
    void txtContactNoOnMouseClickedAction(MouseEvent event) {

    }

    @FXML
    void txtSalaryOnMouseClickedAction(MouseEvent event) {

    }


    void getAll() {

        ObservableList<CustomerTm> obList = null;
        try {
            obList = CustomerModel.getAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        tblCustomer.setItems(obList);


    }

    void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contact"));

    }

    @FXML
    void empIdSearchBarOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            CustomerDto customer = CustomerModel.searchById(txtSearch.getText());
            if (customer != null) {
                txtId.setText(customer.getId());
                txtName.setText(customer.getName());
                txtContact.setText(customer.getContact());
                txtEmail.setText(customer.getEmail());
                txtAddress.setText(customer.getAddress());
            } else {
//          NotificationController.ErrorMasseage("Event ID Not Found");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchIconOnMouseClickedAction(MouseEvent event) {
        try {
            CustomerDto customer = CustomerModel.searchById(txtSearch.getText());
            if (customer != null) {
                txtId.setText(customer.getId());
                txtName.setText(customer.getName());
                txtContact.setText(customer.getContact());
                txtEmail.setText(customer.getEmail());
                txtAddress.setText(customer.getAddress());
            } else {
//            NotificationController.ErrorMasseage("Event ID Not Found");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void txtSearchOnKeyTypedAction(KeyEvent keyEvent) throws SQLException, ClassNotFoundException {
        String searchValue = txtSearch.getText().trim();
        ObservableList<CustomerTm> obList = CustomerModel.getAll();

        if (!searchValue.isEmpty()) {
            ObservableList<CustomerTm> filteredData = obList.filtered(new Predicate<CustomerTm>() {
                @Override
                public boolean test(CustomerTm customerTM) {
                    return String.valueOf(customerTM.getId()).toLowerCase().contains(searchValue.toLowerCase());
                }
            });
            tblCustomer.setItems(filteredData);
        } else {
            tblCustomer.setItems(obList);
        }

    }
    @FXML
    void addressKeyTyped(KeyEvent event) {
        boolean isValid=DataValidateController.customerNameValidate(txtAddress.getText());
        addBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtEmail.getText().isEmpty()|txtName.getText().isEmpty()|txtContact.getText().isEmpty());
        updateBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtEmail.getText().isEmpty()|txtName.getText().isEmpty()|txtContact.getText().isEmpty());
        deleteBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtEmail.getText().isEmpty()|txtName.getText().isEmpty()|txtContact.getText().isEmpty());

        if (isValid){
            txtAddress.setStyle("-fx-border-color: green");

        }else{
            txtAddress.setStyle("-fx-border-color: red");

        }

    }

    @FXML
    void contactKeyTyped(KeyEvent event) {
        boolean isValid=DataValidateController.contactCheck(txtContact.getText());

        addBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtEmail.getText().isEmpty()|txtName.getText().isEmpty()|txtAddress.getText().isEmpty());
        updateBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtEmail.getText().isEmpty()|txtName.getText().isEmpty()|txtAddress.getText().isEmpty());
        deleteBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtEmail.getText().isEmpty()|txtName.getText().isEmpty()|txtAddress.getText().isEmpty());

        if (isValid){
            txtContact.setStyle("-fx-border-color: green");

        }else{
            txtContact.setStyle("-fx-border-color: red");

        }


    }
    @FXML
    void emailKeyTyped(KeyEvent event) {
        boolean isValid=DataValidateController.emailCheck(txtEmail.getText());
        addBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtContact.getText().isEmpty()|txtName.getText().isEmpty()|txtAddress.getText().isEmpty());
        updateBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtContact.getText().isEmpty()|txtName.getText().isEmpty()|txtAddress.getText().isEmpty());
        deleteBtn.setDisable(!isValid|txtId.getText().isEmpty()|txtContact.getText().isEmpty()|txtName.getText().isEmpty()|txtAddress.getText().isEmpty());

        if (isValid){
            txtEmail.setStyle("-fx-border-color: green");

        }else{
            txtEmail.setStyle("-fx-border-color: red");

        }

    }
    @FXML
    void idKeyTyped(KeyEvent event) {
        boolean isValid= DataValidateController.customerIdValidate(txtId.getText());
        addBtn.setDisable(!isValid|txtContact.getText().isEmpty()|txtEmail.getText().isEmpty()|txtName.getText().isEmpty()|txtAddress.getText().isEmpty());
        updateBtn.setDisable(!isValid|txtContact.getText().isEmpty()|txtEmail.getText().isEmpty()|txtName.getText().isEmpty()|txtAddress.getText().isEmpty());
        deleteBtn.setDisable(!isValid|txtContact.getText().isEmpty()|txtEmail.getText().isEmpty()|txtName.getText().isEmpty()|txtAddress.getText().isEmpty());

        if (isValid){
            txtId.setStyle("-fx-border-color: green");

        }else{
            txtId.setStyle("-fx-border-color: red");

        }

    }

    @FXML
    void nameKeyTyped(KeyEvent event) {
        boolean isValid=DataValidateController.customerNameValidate(txtName.getText());
        addBtn.setDisable(!isValid|txtContact.getText().isEmpty()|txtEmail.getText().isEmpty()|txtId.getText().isEmpty()|txtAddress.getText().isEmpty());
        updateBtn.setDisable(!isValid|txtContact.getText().isEmpty()|txtEmail.getText().isEmpty()|txtId.getText().isEmpty()|txtAddress.getText().isEmpty());
        deleteBtn.setDisable(!isValid|txtContact.getText().isEmpty()|txtEmail.getText().isEmpty()|txtId.getText().isEmpty()|txtAddress.getText().isEmpty());

        if (isValid){
            txtName.setStyle("-fx-border-color: green");

        }else{
            txtName.setStyle("-fx-border-color: red");

        }
    }

    void defaultTxt(){
        txtId.setStyle("-fx-border-color: transparent");
        txtName.setStyle("-fx-border-color: transparent");
        txtContact.setStyle("-fx-border-color: transparent");
        txtEmail.setStyle("-fx-border-color: transparent");
        txtAddress.setStyle("-fx-border-color: transparent");
    }


    @FXML
    void initialize() {
        addBtn.setDisable(true);
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);
        getAll();
        setCellValueFactory();

    }



    public void btn(ActionEvent event) {

    }
}

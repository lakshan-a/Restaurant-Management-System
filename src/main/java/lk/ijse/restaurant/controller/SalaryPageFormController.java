package lk.ijse.restaurant.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.restaurant.dto.SalaryDto;
import lk.ijse.restaurant.dto.Tm.SalaryTm;
import lk.ijse.restaurant.model.EmployeeModel;
import lk.ijse.restaurant.model.SalaryModel;
import lk.ijse.restaurant.util.AlertController;
import lk.ijse.restaurant.util.DataValidateController;
import lk.ijse.restaurant.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class SalaryPageFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox MethodTxt;

    @FXML
    private TableColumn<?, ?> colEID;

    @FXML
    private TableColumn<?, ?> colMethod;

    @FXML
    private TableColumn<?, ?> colOt;

    @FXML
    private TableColumn<?, ?> colSID;

    @FXML
    private ComboBox<String> empIdTxt;

    @FXML
    private TextField otTxt;

    @FXML
    private TextField paymentTxt;

    @FXML
    private TextField salaryIdTxt;
    @FXML
    private TextField txtSearch;
    @FXML
    private JFXButton addBtn;
    @FXML
    private JFXButton deleteBtn;
    @FXML
    private JFXButton updateBtn;



    @FXML
    private TableView<SalaryTm> tblSalary;

    @FXML
    private TableColumn<?, ?> colPayment;


    @FXML
    void updateBtnOnAction(ActionEvent event) {
        Double overTyime;
        String id = empIdTxt.getValue();
        String salaryId = salaryIdTxt.getText();
        String method = String.valueOf(MethodTxt.getValue());
        Double payment = Double.valueOf(paymentTxt.getText());
        if (otTxt.getText().isEmpty()) {
            overTyime = 0.0;
        } else {
            overTyime = Double.valueOf(otTxt.getText());
        }
        SalaryDto salary = new SalaryDto(salaryId, id, method, payment, overTyime);

        try {
            boolean isUpdated = SalaryModel.update(salary);
            if (isUpdated) {
                AlertController.confirmmessage("Salary updated sucessfully");
                defaultTxt();
                addBtn.setDisable(true);
                updateBtn.setDisable(true);
                deleteBtn.setDisable(true);
                empIdTxt.setValue(null);
                salaryIdTxt.setText("");
                MethodTxt.setValue(null);
                paymentTxt.setText("");
                otTxt.setText("");

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

        try {
            boolean isDeleted = SalaryModel.delete(salaryIdTxt.getText());
            if (isDeleted) {
                AlertController.confirmmessage("Salary deleted sucessfully");
                addBtn.setDisable(true);
                updateBtn.setDisable(true);
                deleteBtn.setDisable(true);
                empIdTxt.setValue(null);
                salaryIdTxt.setText("");
                MethodTxt.setValue(null);
                paymentTxt.setText("");
                otTxt.setText("");

                getAll();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (empIdTxt.getSelectionModel().isEmpty() & MethodTxt.getSelectionModel().isEmpty()) {
            AlertController.errormessage("Employee Id and payment method can't be empty");
        } else if (empIdTxt.getSelectionModel().isEmpty()) {
            AlertController.errormessage("Employee Id can't be empty");

        } else if (MethodTxt.getSelectionModel().isEmpty()) {
            AlertController.errormessage("Payment method can't be empty");
        }else {
            Double overTyime;
            String id = String.valueOf(empIdTxt.getValue());
            String salaryId = salaryIdTxt.getText();
            String method = String.valueOf(MethodTxt.getValue());
            Double payment = Double.valueOf(paymentTxt.getText());
            if (otTxt.getText().isEmpty()) {
                overTyime = 0.0;
            } else {
                overTyime = Double.valueOf(otTxt.getText());
            }


            SalaryDto salary = new SalaryDto(salaryId, id, method, payment, overTyime);


            try {
                boolean isSaved = SalaryModel.save(salary);
                if (isSaved) {
                    AlertController.confirmmessage("Salary saved sucessfully");
                    defaultTxt();
                    addBtn.setDisable(true);
                    updateBtn.setDisable(true);
                    deleteBtn.setDisable(true);
                    empIdTxt.setValue(null);
                    salaryIdTxt.setText("");
                    MethodTxt.setValue(null);
                    paymentTxt.setText("");
                    otTxt.setText("");
                    System.out.println("saved");
                      getAll();

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }


    @FXML
    void txtSearchOnKeyTypedAction(KeyEvent event) throws SQLException, ClassNotFoundException {
        String searchValue =txtSearch.getText().trim();
        ObservableList<SalaryTm> obList = SalaryModel.getAll();

        if (!searchValue.isEmpty()) {
            ObservableList<SalaryTm> filteredData = obList.filtered(new Predicate<SalaryTm>() {
                @Override
                public boolean test(SalaryTm salarytm) {
                    return String.valueOf(salarytm.getEmpId()).toLowerCase().contains(searchValue.toLowerCase());
                }
            });
            tblSalary.setItems(filteredData);
        } else {
            tblSalary.setItems(obList);
        }


    }

    @FXML
    void searchIconOnMouseClickedAction(MouseEvent event) {



        try {
            SalaryDto salaryDto = SalaryModel.searchById(txtSearch.getText());
            if (salaryDto != null) {
                empIdTxt.setValue(salaryDto.getEmpId());
                salaryIdTxt.setText(salaryDto.getSalaryId());
                MethodTxt.setValue(salaryDto.getPaymentMethod());
                paymentTxt.setText(String.valueOf(salaryDto.getPayment()));
                otTxt.setText(String.valueOf(salaryDto.getOverTime()));
            } else {

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }




    @FXML
    void empIdSearchOnAction(ActionEvent event) {

    }

    @FXML
    void idKeyTyped(KeyEvent event) {
        boolean isValidate= DataValidateController.salaryIdValidate(salaryIdTxt.getText());
        addBtn.setDisable(!isValidate|paymentTxt.getText().isEmpty()|otTxt.getText().isEmpty());
        updateBtn.setDisable(!isValidate|paymentTxt.getText().isEmpty()|otTxt.getText().isEmpty());
        deleteBtn.setDisable(!isValidate|paymentTxt.getText().isEmpty()|otTxt.getText().isEmpty());
        if (isValidate){
            salaryIdTxt.setStyle("-fx-border-color: green");
        }else {
            salaryIdTxt.setStyle("-fx-border-color: red");
        }

    }

    @FXML
    void otKeyTyped(KeyEvent event) {
        boolean isValidate= DataValidateController.priceValidate(otTxt.getText());
        addBtn.setDisable(!isValidate|paymentTxt.getText().isEmpty()|salaryIdTxt.getText().isEmpty());
        updateBtn.setDisable(!isValidate|paymentTxt.getText().isEmpty()|salaryIdTxt.getText().isEmpty());
        deleteBtn.setDisable(!isValidate|paymentTxt.getText().isEmpty()|salaryIdTxt.getText().isEmpty());
        if (isValidate){
            otTxt.setStyle("-fx-border-color: green");
        }else {
            otTxt.setStyle("-fx-border-color: red");
        }

    }

    @FXML
    void paymentKeyTyped(KeyEvent event) {
        boolean isValidate= DataValidateController.priceValidate(paymentTxt.getText());
        addBtn.setDisable(!isValidate|otTxt.getText().isEmpty()|salaryIdTxt.getText().isEmpty());
        updateBtn.setDisable(!isValidate|otTxt.getText().isEmpty()|salaryIdTxt.getText().isEmpty());
        deleteBtn.setDisable(!isValidate|otTxt.getText().isEmpty()|salaryIdTxt.getText().isEmpty());
        if (isValidate){
            paymentTxt.setStyle("-fx-border-color: green");
        }else {
            paymentTxt.setStyle("-fx-border-color: red");
        }

    }

    @FXML
    void txtContactNoOnMouseClickedAction(MouseEvent event) {

    }

    void loadEmployeeId() {

        ObservableList<String> empIds = FXCollections.observableArrayList();
        try {
            List<String> ids = EmployeeModel.loadEmpIds();

            for (String id : ids) {
                empIds.add(id);
            }
            empIdTxt.setItems(empIds);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    void getAll() {


        try {
            ObservableList<SalaryTm> salaryData = SalaryModel.getAll();
            tblSalary.setItems(salaryData);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    void getCellValueFactory() {
        colSID.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colEID.setCellValueFactory(new PropertyValueFactory<>("salaryId"));
        colMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colPayment.setCellValueFactory(new PropertyValueFactory<>("payment"));
        colOt.setCellValueFactory(new PropertyValueFactory<>("overTime"));
    }

    public void tblSalaryOnClick(MouseEvent mouseEvent) {
        addBtn.setDisable(false);
        updateBtn.setDisable(false);
        deleteBtn.setDisable(false);
        TablePosition pos = tblSalary.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        // Get the data from the selected row
        ObservableList<TableColumn<SalaryTm, ?>> columns = tblSalary.getColumns();

        salaryIdTxt.setText(columns.get(0).getCellData(row).toString());
        empIdTxt.setValue(columns.get(1).getCellData(row).toString());
        MethodTxt.setValue(columns.get(2).getCellData(row).toString());
        otTxt.setText(columns.get(3).getCellData(row).toString());
        paymentTxt.setText(columns.get(4).getCellData(row).toString());
    }

    void defaultTxt(){
        salaryIdTxt.setStyle("-fx-border-color: transparent");
        paymentTxt.setStyle("-fx-border-color: transparent");
        otTxt.setStyle("-fx-border-color: transparent");
    }


    @FXML
    void initialize() {
        addBtn.setDisable(true);
        updateBtn.setDisable(true);
        deleteBtn.setDisable(true);
        getCellValueFactory();
        getAll();
        loadEmployeeId();
        MethodTxt.getItems().addAll("Card", "Cash");

    }

    public void empIdSearchBarOnAction(ActionEvent actionEvent) {
    }


}

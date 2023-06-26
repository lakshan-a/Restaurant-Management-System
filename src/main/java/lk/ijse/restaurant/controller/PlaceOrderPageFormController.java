package lk.ijse.restaurant.controller;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.restaurant.db.DBConnection;
import lk.ijse.restaurant.dto.CustomerDto;
import lk.ijse.restaurant.dto.FoodDto;
import lk.ijse.restaurant.dto.ItemDto;
import lk.ijse.restaurant.dto.OrderCartDto;
import lk.ijse.restaurant.dto.Tm.OrderCartTm;
import lk.ijse.restaurant.model.*;
import lk.ijse.restaurant.util.AlertController;
import lk.ijse.restaurant.util.DateAndTimeConntroller;
import lk.ijse.restaurant.util.ValidateField;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class PlaceOrderPageFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnPlaceOrder;

    @FXML
    private ComboBox<String> cmbCustId;

    @FXML
    private ComboBox<String> cmbFoodID;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colDelivery;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblEmptyPaidAmount;

    @FXML
    private Label lblEmptyQuantity;

    @FXML
    private Label lblInvalidQuantity;


    @FXML
    private Label lblBalance;

    @FXML
    private Label lblCustName;

    @FXML
    private Label lblFoodName;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Label lblorderTime;

    @FXML
    private RadioButton radioBtnYes;

    @FXML
    private TableView<OrderCartTm> tblPlaceOrder;

    @FXML
    private TextField txtPaidAmount;

    @FXML
    private TextField txtQty;

    private ObservableList<OrderCartTm> obList = FXCollections.observableArrayList();
    private ItemDto itemDto;
    private FoodDto foodDto;
    private String delivery = "No";
    private Object FoodDto;

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String code = cmbFoodID.getValue();
        String name = lblFoodName.getText();
        String unitprice = lblUnitPrice.getText();
        Integer qty = 0;
        try {
            qty = Integer.valueOf(txtQty.getText());
        }catch(Exception e){
            System.out.println("@1"+e);
        }
        Double total = Double.parseDouble(lblUnitPrice.getText()) * qty;
        Button btnAction = new Button("Remove");
        btnAction.setCursor(Cursor.HAND);

        if (ValidateField.numberCheck(txtQty.getText())) {
            if (qty > Integer.parseInt(lblQtyOnHand.getText())) {
                AlertController.errormessage("Item " + name + " out of stock or not enough stock");
            } else {
                setRemoveBtnOnAction(btnAction);

                if (!obList.isEmpty()) {
                    int newval= Integer.parseInt(lblQtyOnHand.getText())-Integer.parseInt(txtQty.getText());
                    lblQtyOnHand.setText(String.valueOf(newval));
                    txtQty.setText("");////////////////
                    for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
                        if (colId.getCellData(i).equals(code)) {
                            qty += (int) colQty.getCellData(i);
                            total = qty * Double.parseDouble(unitprice);

                            obList.get(i).setQty(qty);
                            obList.get(i).setTotal(total);

                            tblPlaceOrder.refresh();
                            calculateNetTotal();
                            return;
                        }
                    }
                }else{
                    int newval= Integer.parseInt(lblQtyOnHand.getText())-Integer.parseInt(txtQty.getText());
                    lblQtyOnHand.setText(String.valueOf(newval));
                }

                OrderCartTm tm = new OrderCartTm(code, name, qty,Double.parseDouble(unitprice), total, delivery, btnAction);

                obList.add(tm);
                tblPlaceOrder.setItems(obList);
                calculateNetTotal();
                txtQty.setText("");
            }
        } else {
            AlertController.errormessage("Wrong input format for item quantity field");
        }
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        if(txtPaidAmount.getText().isEmpty()){
            lblEmptyPaidAmount.setVisible(true);
            AlertController.errormessage("Please enter Amount");
        }else {
            String ordrId = lblOrderId.getText();
            String customerId = cmbCustId.getValue();
            boolean delivery = radioBtnYes.isSelected();

            double netTotal = Double.parseDouble(lblNetTotal.getText());
            double paidAmount = Double.parseDouble(txtPaidAmount.getText());

            if(paidAmount >= netTotal){

                List<OrderCartDto> cartDTOList = new ArrayList<>();

                for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
                    OrderCartTm cartTM = obList.get(i);

                    OrderCartDto dto = new OrderCartDto(
                            cartTM.getCode(),
                            cartTM.getQty(),
                            cartTM.getDelivery()
                    );
                    cartDTOList.add(dto);
                }

                try {
                    boolean isPlaced = PlaceOrderModel.placeOrder(ordrId, customerId,netTotal, delivery, cartDTOList);
                    if(isPlaced){
                        generateNextOrderId();
                        AlertController.okconfirmmessage("Order Placed");

                        boolean result = AlertController.okconfirmmessage("Do you want the bill ?");

                        if (result) {
                            double printcash = Double.parseDouble(txtPaidAmount.getText());
                            double balance = Double.parseDouble(lblBalance.getText());

                            Map<String, Object> parameters = new HashMap<>();
                            parameters.put("Param1", printcash);
                            parameters.put("param2", balance);

                            InputStream resource = this.getClass().getResourceAsStream("/report/FurnitureShopOrderBill.jrxml");
                            try {
                                JasperReport jasperReport = JasperCompileManager.compileReport(resource);
                                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, DBConnection.getInstance().getConnection());
                                JasperViewer.viewReport(jasperPrint, false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        clearTxtFields();
                        tblPlaceOrder.getItems().clear();
                        lblCustName.setText("");
                        cmbCustId.setValue(null);
                        radioBtnYes.setSelected(false);
                        lblNetTotal.setText("");
                        txtPaidAmount.setText("");
                        lblBalance.setText("");
                    }
                }  catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e);
                    AlertController.errormessage("Order Not Placed");
                }
            }else {
                AlertController.errormessage("Amount not enough");
            }
        }
    }

    private void setRemoveBtnOnAction(Button btnRemove) {
        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {

                int index = tblPlaceOrder.getSelectionModel().getSelectedIndex();
                obList.remove(index);

                tblPlaceOrder.refresh();
                calculateNetTotal();
            }

        });
    }

    private void generateNextOrderId() {
        try {
            String id = OrderModel.getNextOrderId();
            lblOrderId.setText(id);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tblPlaceOrder.getItems().size(); i++) {
            double total = (double) colTotal.getCellData(i);
            netTotal += total;
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }

    private void clearTxtFields() {
        cmbFoodID.setValue(null);
        lblFoodName.setText("");
        lblUnitPrice.setText("");
        lblQtyOnHand.setText("");
    }

    @FXML
    void cmbCustIdOnAction(ActionEvent event) {
        String id = cmbCustId.getValue();
        try {
            CustomerDto customerDto = CustomerModel.searchById(id);

            lblCustName.setText(customerDto.getName());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("custId "+e);
        }
    }


    FoodDto food;
    @FXML
    void cmbcmbFoodIDOnAction(ActionEvent event) {
        String itemcode = cmbFoodID.getValue();

        try {
            food = FoodModel.searchByFoodCode(itemcode);
            lblFoodName.setText(food.getDescription());
            lblUnitPrice.setText(String.valueOf(food.getPrice()));

            if (food.getQtyOnHand() > 0) {
                lblQtyOnHand.setText(String.valueOf(food.getQtyOnHand()));
            } else {
                lblQtyOnHand.setText("Out Of Stock");
                AlertController.errormessage("item " + food.getDescription() + " out of stock");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @FXML
    void cmbItemCodeOnAction(ActionEvent event) {

    }

    @FXML
    void radioBtnYesOnAction(ActionEvent event) {
        delivery = "Yes";

        if(radioBtnYes.isSelected()){
            Stage stage = new Stage();
            stage.resizableProperty().setValue(false);
            try {
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DeliveryPopUp_form.fxml"))));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e);
            }
            stage.centerOnScreen();
            stage.show();
        }
    }

    @FXML
    void tableOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void txtPaidAmountOnKeyTypedAction(KeyEvent event) {
        lblEmptyPaidAmount.setVisible(false);
        if(!txtPaidAmount.getText().isEmpty() && !lblNetTotal.getText().isEmpty()){
            double netTotal = Double.parseDouble(lblNetTotal.getText());
            double paidAmount = Double.parseDouble(txtPaidAmount.getText());

            if(netTotal <= paidAmount){
                lblBalance.setVisible(true);
                lblBalance.setText(String.valueOf(paidAmount - netTotal));
            }else {
                lblBalance.setVisible(false);
            }
        }
    }

    @FXML
    void txtQuantityOnMouseClickedAction(MouseEvent event) {
        lblEmptyQuantity.setVisible(false);
        lblInvalidQuantity.setVisible(false);
    }

    private void loadCustomerIds() {
        try {
            ObservableList<String> custIds = FXCollections.observableArrayList();
            List<String> ids = CustomerModel.loadCustomerIds();

            for(String id : ids){
                custIds.add(id);
            }

            cmbCustId.setItems(custIds);
        } catch (SQLException | ClassNotFoundException throwables) {
            System.out.println(throwables);
            AlertController.errormessage("Something went wrong");
        }
    }

    private void loadFoodCodes() throws SQLException, ClassNotFoundException {
        ObservableList<String> foodCodes = FXCollections.observableArrayList();
        List<String> cods = FoodModel.loadFoodCodes();

        for(String cod : cods){
            foodCodes.add(cod);
        }
        cmbFoodID.setItems(foodCodes);
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("code"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colDelivery.setCellValueFactory(new PropertyValueFactory<>("delivery"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnAction"));
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        loadCustomerIds();
        loadFoodCodes();
        generateNextOrderId();

        DateAndTimeConntroller dateTime = new DateAndTimeConntroller();
        dateTime.Timenow(lblorderTime, lblOrderDate);

        lblInvalidQuantity.setVisible(false);
        lblEmptyPaidAmount.setVisible(false);
        lblEmptyQuantity.setVisible(false);
    }



}

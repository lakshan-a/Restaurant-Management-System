package lk.ijse.restaurant.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import lk.ijse.restaurant.db.DBConnection;
import lk.ijse.restaurant.util.Navigation;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;

public class ReportFormController {

    public JFXButton btnCusReport;

    public void btnCustomerReport(ActionEvent event) {
        InputStream resource = this.getClass().getResourceAsStream("/report/customer_report.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();

  }
    }

    public  void btnDeliveryOnAction(ActionEvent event) {
        try {
            Navigation.switchNavigation("DeliveryPageForm.fxml",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void btnEmployeeOnAction(ActionEvent event) {
        try {
            Navigation.popupNavigation("EmployeeAddForm.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnSalaryOnAction(ActionEvent event) {
        try {
            Navigation.switchNavigation("SalaryPageForm.fxml",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnCustomerOnAction(ActionEvent event) {
        try {
            Navigation.switchNavigation("CustomerPage_form.fxml",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnItemOnAction(ActionEvent event) {
        try {
            Navigation.switchNavigation("ItemPage_form.fxml",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnOrderOnAction(ActionEvent event) {
        try {
            Navigation.switchNavigation("OrderPageForm.fxml",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnPaymentOnAction(ActionEvent event) {
        try {
            Navigation.switchNavigation("ReportFrom.fxml",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void backHomeOnAction(ActionEvent event) {
        try {
            Navigation.switchNavigation("HomePage_form.fxml",event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

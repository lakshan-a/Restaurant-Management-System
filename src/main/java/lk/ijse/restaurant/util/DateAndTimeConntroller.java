package lk.ijse.restaurant.util;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAndTimeConntroller {
    public void Timenow(Label lblTime, Label lblDate){
        Thread thread =new Thread(() ->{
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
            SimpleDateFormat sdf1 = new SimpleDateFormat("MMMM,  dd, yyyy");
            while (true){
                try{
                    Thread.sleep(1000);

                }catch (Exception e){
                    System.out.println(e);
                }
                final String timenow = sdf.format(new Date());
                String timenow1 = sdf1.format(new Date());

                Platform.runLater(() ->{
                    lblTime.setText(timenow);
                    lblDate.setText(timenow1);
                });
            }
        });
        thread.start();
    }
}

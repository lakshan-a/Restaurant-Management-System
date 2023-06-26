package lk.ijse.restaurant.model;

import lk.ijse.restaurant.db.DBConnection;
import lk.ijse.restaurant.dto.DeliveryDto;
import lk.ijse.restaurant.dto.OrderCartDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class PlaceOrderModel {
    static DeliveryDto delivery;

    public static boolean placeOrder(String ordrId, String customerId, double netTotal, boolean deliverystatus, List<OrderCartDto> cartDTOList) throws SQLException {
        Connection con = null;

        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            boolean isSaved = OrderModel.save(ordrId, customerId, netTotal, LocalTime.now(), LocalDate.now(), cartDTOList);
            if(isSaved){
                boolean isUpdate = FoodModel.updateQty(cartDTOList);
                if(isUpdate){
                    boolean isOrdered = OrderDetailModel.save(ordrId, cartDTOList);
                    if(isOrdered){
                        if(deliverystatus){
                            boolean isDeliverySave = DeliveryModel.save(delivery);
                            if (isDeliverySave) {
                                con.commit();
                                return true;
                            }
                        }else {
                            con.commit();
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            con.rollback();
            return false;
        } finally {
            con.setAutoCommit(true);
        }
    }

    public static void saveDelivery(DeliveryDto newDelivery) {
        delivery = newDelivery;
    }
}

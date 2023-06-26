package lk.ijse.restaurant.model;

import lk.ijse.restaurant.dto.OrderCartDto;
import lk.ijse.restaurant.util.CrudUtil;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailModel {

    public static boolean save(String ordrId, List<OrderCartDto> cartDTOList) throws SQLException, ClassNotFoundException {
        for(OrderCartDto dto : cartDTOList){
            if(!save(ordrId, dto)){
                return false;
            }
        }
        return true;
    }

    private static boolean save(String ordrId, OrderCartDto dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO orderDetail(orderId,Food_num , qty)" +
                "VALUES(?, ?, ?)";
        return CrudUtil.crudUtil(sql, ordrId, dto.getCode(), dto.getQty());
    }


}

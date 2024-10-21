package controller.Home;

import db.DbConnection;
import dto.Order;
import javafx.scene.control.Alert;
import service.Custom.Impl.ItemServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class homeOrderController {

    public boolean placeOrder(Order order) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            String SQL = "INSERT INTO orders VALUES(?,?,?)";
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, order.getOrderId());
            psTm.setObject(2, order.getDate());
            psTm.setObject(3, order.getCustomerId());
            boolean isOrderAdd = psTm.executeUpdate() > 0;
            if (isOrderAdd) {
                boolean isOrderDetailAdd = new homeOrderDetailController().addOrderDetail(order.getOrderDetails());
                if (isOrderDetailAdd) {
                    boolean isUpdateStock = ItemServiceImpl.getInstance().updateStock(order.getOrderDetails());
                    if (isUpdateStock) {
                        connection.commit();
                        new Alert(Alert.AlertType.INFORMATION, "Order Placed!!").show();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }

    }
}

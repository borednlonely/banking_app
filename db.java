package Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class db {

    private static final String URL = "jdbc:postgresql://localhost:5432/bank";
    private static final String USER = "postgres";
    private static final String PASSWORD = "YOUR PASSWORD HERE";

    public static Connection connect() throws SQLException{
        return DriverManager.getConnection(URL,USER,PASSWORD);

    }

    public static void updateBalance(String accountNumber, double balance) {
        String sql = "UPDATE accounts SET balance = ? WHERE account_number = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, balance);
            ps.setString(2, accountNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("COULDN'T SAVE BALANCE :[");
            e.printStackTrace();
        }
    }


}

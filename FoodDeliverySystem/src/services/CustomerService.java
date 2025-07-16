package services;

import models.Customer;
import models.User;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    public boolean registerCustomer(Customer customer) {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false);

            // Insert into users table
            String userQuery = "INSERT INTO users (username, email, password, phone, user_type) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement userStmt = conn.prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
            userStmt.setString(1, customer.getUsername());
            userStmt.setString(2, customer.getEmail());
            userStmt.setString(3, customer.getPassword());
            userStmt.setString(4, customer.getPhone());
            userStmt.setString(5, "CUSTOMER");

            int userResult = userStmt.executeUpdate();

            if (userResult > 0) {
                ResultSet keys = userStmt.getGeneratedKeys();
                if (keys.next()) {
                    int userId = keys.getInt(1);
                    customer.setUserId(userId);

                    // Insert into customers table
                    String customerQuery = "INSERT INTO customers (customer_id, address) VALUES (?, ?)";
                    PreparedStatement customerStmt = conn.prepareStatement(customerQuery);
                    customerStmt.setInt(1, userId);
                    customerStmt.setString(2, customer.getAddress());

                    int customerResult = customerStmt.executeUpdate();

                    if (customerResult > 0) {
                        conn.commit();
                        return true;
                    }
                }
            }

            conn.rollback();
            return false;

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Customer authenticateCustomer(String username, String password) {
        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT u.*, c.address FROM users u JOIN customers c ON u.user_id = c.customer_id WHERE u.username = ? AND u.password = ? AND u.user_type = 'CUSTOMER'";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Customer customer = new Customer();
                customer.setUserId(rs.getInt("user_id"));
                customer.setUsername(rs.getString("username"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddress(rs.getString("address"));
                customer.setUserType(User.UserType.CUSTOMER);

                return customer;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();
        String query = "SELECT u.*, c.address FROM users u JOIN customers c ON u.user_id = c.customer_id";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setUserId(rs.getInt("user_id"));
                customer.setUsername(rs.getString("username"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddress(rs.getString("address"));
                customer.setUserType(User.UserType.CUSTOMER);

                customers.add(customer);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }
}
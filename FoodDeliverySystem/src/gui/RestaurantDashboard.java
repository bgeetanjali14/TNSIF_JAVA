package gui;

import models.Restaurant;
import models.Order;
import services.OrderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RestaurantDashboard extends JFrame {
    private Restaurant restaurant;
    private OrderService orderService;
    private JTable ordersTable;
    private DefaultTableModel tableModel;

    public RestaurantDashboard(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.orderService = new OrderService();
        setTitle("Restaurant Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        loadOrders();
    }

    private void initComponents() {
        tableModel = new DefaultTableModel(new String[]{"Order ID", "Customer ID", "Status", "Total Amount", "Order Date"}, 0);
        ordersTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(ordersTable);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(e -> loadOrders());

        JPanel btnPanel = new JPanel();
        btnPanel.add(refreshBtn);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
    }

    private void loadOrders() {
        tableModel.setRowCount(0);
        List<Order> orders = orderService.getOrdersByRestaurant(restaurant.getUserId());
        for (Order order : orders) {
            tableModel.addRow(new Object[]{
                    order.getOrderId(),
                    order.getCustomerId(),
                    order.getStatus().name(),
                    order.getTotalAmount(),
                    order.getOrderDate()
            });
        }
    }
}
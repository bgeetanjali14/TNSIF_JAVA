CREATE DATABASE food_delivery_db;
USE food_delivery_db;

CREATE TABLE users (
                       user_id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       phone VARCHAR(15),
                       user_type ENUM('CUSTOMER', 'RESTAURANT', 'DELIVERY') NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE customers (
                           customer_id INT PRIMARY KEY,
                           address TEXT,
                           FOREIGN KEY (customer_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE restaurants (
                             restaurant_id INT PRIMARY KEY,
                             name VARCHAR(100) NOT NULL,
                             address TEXT,
                             cuisine_type VARCHAR(50),
                             FOREIGN KEY (restaurant_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE delivery_persons (
                                  delivery_id INT PRIMARY KEY,
                                  vehicle_type VARCHAR(50),
                                  license_number VARCHAR(50),
                                  is_available BOOLEAN DEFAULT TRUE,
                                  FOREIGN KEY (delivery_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE food_items (
                            food_id INT PRIMARY KEY AUTO_INCREMENT,
                            restaurant_id INT,
                            name VARCHAR(100) NOT NULL,
                            description TEXT,
                            price DECIMAL(10,2) NOT NULL,
                            category VARCHAR(50),
                            is_available BOOLEAN DEFAULT TRUE,
                            FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id) ON DELETE CASCADE
);

CREATE TABLE orders (
                        order_id INT PRIMARY KEY AUTO_INCREMENT,
                        customer_id INT,
                        restaurant_id INT,
                        delivery_id INT,
                        total_amount DECIMAL(10,2) NOT NULL,
                        status ENUM('PENDING', 'CONFIRMED', 'PREPARING', 'OUT_FOR_DELIVERY', 'DELIVERED', 'CANCELLED') DEFAULT 'PENDING',
                        delivery_address TEXT,
                        order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
                        FOREIGN KEY (restaurant_id) REFERENCES restaurants(restaurant_id),
                        FOREIGN KEY (delivery_id) REFERENCES delivery_persons(delivery_id)
);

CREATE TABLE order_items (
                             order_item_id INT PRIMARY KEY AUTO_INCREMENT,
                             order_id INT,
                             food_id INT,
                             quantity INT NOT NULL,
                             price DECIMAL(10,2) NOT NULL,
                             FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
                             FOREIGN KEY (food_id) REFERENCES food_items(food_id)
);

CREATE TABLE cart (
                      cart_id INT PRIMARY KEY AUTO_INCREMENT,
                      customer_id INT,
                      food_id INT,
                      quantity INT NOT NULL DEFAULT 1,
                      FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE,
                      FOREIGN KEY (food_id) REFERENCES food_items(food_id) ON DELETE CASCADE
);
package com.cs157afall21.productmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.Console;
import java.io.IOException;
import java.sql.*;

public class DBUtils {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String username) {
        Parent root = null;

        if (username != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LoginController loginController = loader.getController();
                // loginController.setCustomerInformation(username);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600,400));
        stage.show();
    }

    public static void signUpUser(ActionEvent event,
                                  String username,
                                  String password,
                                  String firstName,
                                  String lastName,
                                  String phone,
                                  String address,
                                  String city,
                                  String state,
                                  String postal
    ){
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Product",
                    "root", "mikkhail");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM Customer WHERE username = ?");
            psCheckUserExists.setString(1,username);
            resultSet = psCheckUserExists.executeQuery();

            // Check if the username exists
            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username because it is already taken!");
            } else {
                psInsert = connection.prepareStatement
                        ("INSERT INTO Customer (username, password, firstName, lastName, phone, address, city, state, postal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setString(3, firstName);
                psInsert.setString(4, lastName);
                psInsert.setString(5, phone);
                psInsert.setString(6, address);
                psInsert.setString(7, city);
                psInsert.setString(8, state);
                psInsert.setString(9, postal);
                psInsert.executeUpdate();

                changeScene(event, "log-in.fxml", "Welcome!", null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try{
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Product",
                    "root", "mikkhail");
            preparedStatement = connection.prepareStatement("SELECT password FROM Customer WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the Database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Incorrect username or password!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");

                    // Check for the right password
                    if (retrievedPassword.equals(password)) {
                        changeScene(event, "log-in.fxml", "Welcome!", null);
                    } else {
                        System.out.println("Password is incorrect!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Password is incorrect!");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

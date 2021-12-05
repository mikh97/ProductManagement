package com.cs157afall21.productmanagement;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignupController implements Initializable {

    @FXML
    private Button button_signup;
    @FXML
    private Button button_login;

    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_password;
    @FXML
    private TextField tf_firstName;
    @FXML
    private TextField tf_Lastname;
    @FXML
    private TextField tf_phoneNumber;
    @FXML
    private TextField tf_address;
    @FXML
    private TextField tf_city;
    @FXML
    private TextField tf_state;
    @FXML
    private TextField tf_postal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        button_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (!tf_username.getText().isEmpty() &&
                        !tf_password.getText().isEmpty()) {
                    DBUtils.signUpUser(event,
                            tf_firstName.getText(),
                            tf_Lastname.getText(),
                            tf_phoneNumber.getText(),
                            tf_address.getText(),
                            tf_city.getText(),
                            tf_state.getText(),
                            tf_postal.getText(),
                            tf_username.getText(),
                            tf_password.getText());
                } else {
                    System.out.println("Please fill in all information!");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all information on the sign up!");
                    alert.show();
                }

            }
        });


        button_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "log-in.fxml", "Logged in!", null);
            }
        });
    }
}

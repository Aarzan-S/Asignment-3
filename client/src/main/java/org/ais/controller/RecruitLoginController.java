package org.ais.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.ais.model.IModel;
import org.ais.model.Recruit;
import org.ais.model.RecruitModel;
import org.ais.presenter.RecruitLoginPresenter;
import org.ais.util.routing.NavigationHelper;
import org.ais.view.IView;

import java.net.URL;
import java.util.ResourceBundle;

public class RecruitLoginController implements Controller, IView<Recruit> {
    private RecruitLoginPresenter presenter;
    @FXML
    private TextField loginUserName;
    @FXML
    private TextField loginUserPassword;
    @FXML
    private TextField otpValue;
    @FXML
    private Label messageLabel;
    @FXML
    private Button loginBtn;
    @FXML
    private Button requestOTP;
   
    @FXML
    private Label otpLabel;
     @FXML

    /**
     * This is empty implementation of setUp method.
     * Can be updated as per need.
     */
    @Override
    public void setUp(String userName, String userRole) {
    }

    /**
     * Binds loginBtn to binding which will disable button
     * if the username and password fields are empty.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        IModel<Recruit> model = new RecruitModel();
        presenter = new RecruitLoginPresenter(model, this);
        model.connect();
        loginBtn.disableProperty().bind(
                Bindings.createBooleanBinding(() -> loginUserName.getText().trim().isEmpty() ||
                                loginUserPassword.getText().trim().isEmpty() || otpValue.getText().trim().isEmpty(),
                        loginUserName.textProperty(), loginUserPassword.textProperty(), otpValue.textProperty())
        );
    }
 
    @FXML
    private void requestOTP(){
        presenter.getOTP(loginUserName.getText().trim());
    }
    /**
     * Validates username and password, if credential match, it will redirect the user to respective page
     * based on user type.
     * If credential does not match, error message is shown.
     */
    @FXML
    private void onLoginClicked() {
        Recruit recruit = new Recruit(loginUserName.getText().trim(),
                loginUserPassword.getText().trim());
        if (presenter.login(recruit, otpValue.getText().trim())) {
            NavigationHelper.navigate("Recruit.fxml", recruit.getUsername(), "Recruit");
        }
    }
    @FXML
    private void goBack(){
        NavigationHelper.navigate("Login.fxml", null, null);
    }

    @Override
    public void display(String message, String type) {
           if ("ERROR".equals(type)) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText(message);
        } else if ("OTP".equals(type)){
            messageLabel.setStyle("-fx-text-fill: green;");
            otpLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText("Your OTP is " +message);
            otpLabel.setText(message);
        }

    }

    @Override
    public void clearInputs() {
    }
}
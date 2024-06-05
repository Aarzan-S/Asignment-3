package org.ais.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.ais.model.IModel;
import org.ais.model.Staff;
import org.ais.model.StaffModel;
import org.ais.presenter.LoginPresenter;
import org.ais.util.routing.NavigationHelper;
import org.ais.view.IView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class handles authentication and authorization
 */
public class LoginController implements Controller, IView<Staff> {
    private LoginPresenter presenter;
    @FXML
    private TextField loginUserName;
    @FXML
    private TextField loginUserPassword;
    @FXML
    private Label messageLabel;
    @FXML
    private Button loginBtn;

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
        IModel<Staff> model = new StaffModel();
        presenter = new LoginPresenter(model, this);
        model.connect();
        loginBtn.disableProperty().bind(
                Bindings.createBooleanBinding(() -> loginUserName.getText().trim().isEmpty() ||
                        loginUserPassword.getText().trim().isEmpty(), loginUserName.textProperty(), loginUserPassword.textProperty())
        );
    }

    /**
     * Validates username and password, if credential match, it will redirect the user to respective page
     * based on user type.
     * If credential does not match, error message is shown.
     */
    @FXML
    void onLoginClicked() {
        Staff staff = new Staff(loginUserName.getText().trim(),
                loginUserPassword.getText().trim(),
                "");
        if (presenter.login(staff)) {
            NavigationHelper.navigate(staff.getStaffId() + ".fxml", staff.getUsername(), staff.getStaffId());
        }
    }
    @FXML
    void onRegisterClicked() {
        NavigationHelper.navigate("Registration.fxml", null, null);
    }

    @FXML
    void recruitLogin() {
        NavigationHelper.navigate("RecruitLogin.fxml", null, null);
    }

    @Override
    public void display(String message, String type) {
        if ("INFO".equals(type)) {
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText(message);
        } else {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText(message);
        }    }

    @Override
    public void clearInputs() {

    }
}
package org.ais.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.ais.model.AdminStaff;
import org.ais.model.AdminUpdateModel;
import org.ais.model.IModel;
import org.ais.presenter.AdminUpdatePresenter;
import org.ais.util.NavigationHelper;
import org.ais.view.IView;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminUpdateController implements Controller, IView<AdminStaff> {
    private AdminUpdatePresenter presenter;
    AdminStaff adminStaff = null;
    private String userName;
    private String userRole;
    @FXML
    private TextField adminStaffFullName;
    @FXML
    private TextField adminStaffAddress;
    @FXML
    private TextField adminStaffPhone;
    @FXML
    private TextField adminStaffEmail;
    @FXML
    private TextField adminStaffUsername;
    @FXML
    private TextField adminStaffPassword;
    @FXML
    private ChoiceBox<String> adminStaffPositionType = new ChoiceBox<>();
    @FXML
    private Label messageLabel;


    /**
     * Initializes userName and userRole for this class and initializes welcomeLabel for UI
     *
     * @param userName name of the logged-in user
     * @param userRole
     */
    @Override
    public void setUp(String userName, String userRole) {
        this.userName = userName;
        this.userRole = userRole;
        adminStaff = presenter.getAdminDetails(userName);
        loadDetails();
    }

    /**
     * This is empty implementation, which can be implemented as per need
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        IModel<AdminStaff> model = new AdminUpdateModel();
        presenter = new AdminUpdatePresenter(model, this);
        model.connect();
        adminStaffPositionType.getItems().addAll("Full-time", "Part-time", "Volunteer");
    }
    @FXML
    public void updateAdmin(ActionEvent event){
        AdminStaff admin = new AdminStaff(
                0,
                 adminStaffFullName.getText().trim()
                , adminStaffAddress.getText().trim()
                , Long.parseLong(adminStaffPhone.getText())
                , adminStaffEmail.getText().trim()
                , adminStaffUsername.getText().trim()
                , adminStaffPassword.getText().trim()
                , ""
                , adminStaffPositionType.getValue().trim()
        );
        presenter.updateAdminDetails(admin, adminStaff.getId());
    }

    /**
     * Navigates to login page.
     */
    @FXML
    private void goBack() {
        NavigationHelper.navigate("Admin.fxml", userName, userRole);
    }

    @Override
    public void display(AdminStaff object) {

    }

    @Override
    public void display(String message, String type) {
        if ("INFO".equals(type)) {
            messageLabel.setStyle("-fx-text-fill: green;");
            messageLabel.setText(message);
        } else {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText(message);
        }
    }

    @Override
    public void clearInputs() {
        messageLabel.setText("");
        adminStaffFullName.clear();
        adminStaffAddress.clear();
        adminStaffPhone.clear();
        adminStaffEmail.clear();
        adminStaffUsername.clear();
        adminStaffPassword.clear();
        adminStaffPositionType.setValue(null);
    }

    private void loadDetails() {
        if (adminStaff != null) {
            adminStaffFullName.setText(this.adminStaff.getFullName());
            adminStaffAddress.setText(this.adminStaff.getAddress());
            adminStaffPhone.setText(this.adminStaff.getPhoneNumber().toString());
            adminStaffEmail.setText(this.adminStaff.getEmail());
            adminStaffUsername.setText(this.adminStaff.getUsername());
            adminStaffPassword.setText(this.adminStaff.getPassword());
            adminStaffPositionType.setValue(this.adminStaff.getPositionType());
        }
    }
}

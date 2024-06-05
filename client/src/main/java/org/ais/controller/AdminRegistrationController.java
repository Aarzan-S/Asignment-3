package org.ais.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.ais.Main;
import org.ais.model.AdminStaff;
import org.ais.model.AdminStaffModel;
import org.ais.model.IModel;
import org.ais.presenter.AdminRegistrationPresenter;
import org.ais.util.routing.NavigationHelper;
import org.ais.util.validators.NumberFieldValidator;
import org.ais.view.IView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class handles all the tasks related to Admin Registration
 */
public class AdminRegistrationController implements Controller, IView<AdminStaff> {
    private AdminRegistrationPresenter presenter;
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
    @FXML
    private Button addAdminDetails;

    /**
     * initialize userName and userRole for this class
     *
     * @param userName name of the logged-in user
     * @param userRole role of the logged-in user e.g. Admin, Management
     */
    @Override
    public void setUp(String userName, String userRole) {
        this.userName = userName;
        this.userRole = userRole;
    }

    /**
     * Initializes necessary fields for the class such as headerLabel, adminStaffPositionType
     * adds validation to phone number fields
     * binds disable property to add button if the input fields are empty
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IModel<AdminStaff> model = new AdminStaffModel();
        presenter = new AdminRegistrationPresenter(model, this);
        model.connect();
        adminStaffPositionType.getItems().addAll("Full-time", "Part-time", "Volunteer");
        adminStaffPhone.setTextFormatter(new NumberFieldValidator());
        addAdminDetails.disableProperty().bind(
                Bindings.isEmpty(adminStaffFullName.textProperty())
                        .or(Bindings.isEmpty(adminStaffAddress.textProperty()))
                        .or(Bindings.isEmpty(adminStaffPhone.textProperty()))
                        .or(Bindings.isEmpty(adminStaffEmail.textProperty()))
                        .or(Bindings.isEmpty(adminStaffUsername.textProperty()))
                        .or(Bindings.isEmpty(adminStaffPassword.textProperty()))
                        .or(Bindings.isNull(adminStaffPositionType.valueProperty()))

        );
    }

    /**
     * Navigates to Staff page
     */
    @FXML
    void goBack() {
        String path = userName != null ? "Admin.fxml" : "Registration.fxml";
        NavigationHelper.navigate( path, userName, userRole);
    }

    /**
     * Handles the adding of management staff details
     * Handles white spaces, email and phone number validation.
     * Validates uniqueness of username,email and phone so that there is no duplicate/redundant entry.
     * AdminStaff Object is created and added to adminList upon successful validation/
     */
    @FXML
    private void addAdminDetails() {
        AdminStaff adminDetails = new AdminStaff(
                0,
                adminStaffFullName.getText().trim()
                , adminStaffAddress.getText().trim()
                , Long.parseLong(adminStaffPhone.getText())
                , adminStaffEmail.getText().trim()
                , adminStaffUsername.getText().trim()
                , adminStaffPassword.getText().trim()
                , "Admin-" + Main.noOfEntries
                , adminStaffPositionType.getValue().trim()
        );
        presenter.add(adminDetails);
    }
    /**
     * Saves Admin information in the file
     *
     * @throws Exception
     */
    @FXML
    void registerAdminStaff() throws Exception {
        presenter.register();
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

    /**
     * Clears all the input fields.
     */
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

}

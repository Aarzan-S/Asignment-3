package org.ais.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.ais.Main;
import org.ais.model.IModel;
import org.ais.model.ManagementStaff;
import org.ais.model.ManagementStaffModel;
import org.ais.presenter.ManagementRegistrationPresenter;
import org.ais.util.NavigationHelper;
import org.ais.util.NumberFieldValidator;
import org.ais.view.IView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class handles management staff registration mechanism
 */
public class ManagementRegistrationController implements Controller, IView<ManagementStaff> {
    private ManagementRegistrationPresenter presenter;
    private String userName;
    private String userRole;
    @FXML
    private TextField managementStaffFullname;
    @FXML
    private TextField managementStaffAddress;
    @FXML
    private TextField managementStaffPhone;
    @FXML
    private TextField managementStaffEmail;
    @FXML
    private TextField managementStaffUsername;
    @FXML
    private TextField managementStaffPassword;
    @FXML
    private ChoiceBox<String> managementStaffManagementLevel = new ChoiceBox<>();
    @FXML
    private ChoiceBox<String> managementStaffBranch = new ChoiceBox<>();
    @FXML
    private Label messageLabel;
    @FXML
    private Button addManagementDetails;

    /**
     * Initialize userName and userRole for this class
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
     * Initializes necessary fields for the class such as headerLabel, managementStaffManagementLevel
     * and managementStaffBranch.I
     * Adds validation to phone number field to accept only number and 10 digits only.
     * Binds disable property to add button if the input fields are empty
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IModel<ManagementStaff> model = new ManagementStaffModel();
        presenter = new ManagementRegistrationPresenter(model, this);
        model.connect();
        managementStaffManagementLevel.getItems().addAll("Senior Manager", "Mid-level Manager", "Supervisor");
        managementStaffBranch.getItems().addAll("Brisbane", "Adelaide", "Sydney", "Melbourne");
        managementStaffPhone.setTextFormatter(new NumberFieldValidator());
        addManagementDetails.disableProperty().bind(
                Bindings.isEmpty(managementStaffFullname.textProperty())
                        .or(Bindings.isEmpty(managementStaffAddress.textProperty()))
                        .or(Bindings.isEmpty(managementStaffPhone.textProperty()))
                        .or(Bindings.isEmpty(managementStaffEmail.textProperty()))
                        .or(Bindings.isEmpty(managementStaffUsername.textProperty()))
                        .or(Bindings.isEmpty(managementStaffPassword.textProperty()))
                        .or(Bindings.isNull(managementStaffManagementLevel.valueProperty()))
                        .or(Bindings.isNull(managementStaffBranch.valueProperty()))
        );
    }

    /**
     * Handles the adding of management staff details
     * Handles white spaces, email and phone number validation.
     * Validates uniqueness of username,email and phone so that there is no duplicate/redundant entry..
     * Creates ManagementStaff Object and adds to managementList .
     */
    @FXML
    private void addManagementDetails() {
        ManagementStaff managementDetails = new ManagementStaff(
                0,
                managementStaffFullname.getText(),
                managementStaffAddress.getText(),
                Long.parseLong(managementStaffPhone.getText()),
                managementStaffEmail.getText(),
                managementStaffUsername.getText(),
                managementStaffPassword.getText(),
                "Management-" + Main.noOfEntries,
                managementStaffManagementLevel.getValue(),
                managementStaffBranch.getValue()
        );
        presenter.add(managementDetails);
    }

    /**
     * Navigates to Staff page.
     */
    @FXML
    void goBack() {
        NavigationHelper.navigate( "Login.fxml", userName, userRole);
    }

    /**
     * Saves ManagementStaff information in the file.
     * @throws Exception
     */
    @FXML
    private void registerManagementStaff(ActionEvent event) throws Exception {
        presenter.register();
    }

    @Override
    public void display(ManagementStaff object) {

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
     * It will clear all the input fields.
     */
    @Override
    public void clearInputs() {
        messageLabel.setText("");
        managementStaffFullname.clear();
        managementStaffAddress.clear();
        managementStaffPhone.clear();
        managementStaffEmail.clear();
        managementStaffUsername.clear();
        managementStaffPassword.clear();
        managementStaffManagementLevel.setValue(null);
        managementStaffBranch.setValue(null);
    }

}

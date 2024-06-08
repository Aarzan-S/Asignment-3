package org.ais.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.ais.model.IModel;
import org.ais.model.ManagementStaff;
import org.ais.model.ManagementUpdateModel;
import org.ais.presenter.ManagementUpdatePresenter;
import org.ais.util.routing.NavigationHelper;
import org.ais.util.validators.NumberFieldValidator;
import org.ais.view.IView;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagementUpdateController implements Controller, IView<ManagementStaff> {
    private ManagementUpdatePresenter presenter;
    ManagementStaff managementStaff = null;
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
        managementStaff = presenter.getManagementDetails(userName);
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
        IModel<ManagementStaff> model = new ManagementUpdateModel();
        presenter = new ManagementUpdatePresenter(model, this);
        model.connect();
        managementStaffManagementLevel.getItems().addAll("Senior Manager", "Mid-level Manager", "Supervisor");
        managementStaffBranch.getItems().addAll("Brisbane", "Adelaide", "Sydney", "Melbourne");
        managementStaffPhone.setTextFormatter(new NumberFieldValidator());
    }
    @FXML
    public void updateManagement(ActionEvent event){
        ManagementStaff mngStaff = new ManagementStaff(
                0,
                managementStaffFullname.getText(),
                managementStaffAddress.getText(),
                Long.parseLong(managementStaffPhone.getText()),
                managementStaffEmail.getText(),
                managementStaffUsername.getText(),
                managementStaffPassword.getText(),
                "",
                managementStaffManagementLevel.getValue(),
                managementStaffBranch.getValue()
        );
        if (presenter.updateManagementDetails(mngStaff, managementStaff.getId()))
            this.userName = mngStaff.getUsername();
    }

    /**
     * Navigates to login page.
     */
    @FXML
    private void goBack() {
        NavigationHelper.navigate("Management.fxml", userName, userRole);
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
        managementStaffFullname.clear();
        managementStaffAddress.clear();
        managementStaffPhone.clear();
        managementStaffEmail.clear();
        managementStaffUsername.clear();
        managementStaffPassword.clear();
        managementStaffManagementLevel.setValue(null);
        managementStaffBranch.setValue(null);
    }

    private void loadDetails() {
        if (managementStaff != null) {
            managementStaffFullname.setText(this.managementStaff.getFullName());
            managementStaffAddress.setText(this.managementStaff.getAddress());
            managementStaffPhone.setText(this.managementStaff.getPhoneNumber().toString());
            managementStaffEmail.setText(this.managementStaff.getEmail());
            managementStaffUsername.setText(this.managementStaff.getUsername());
            managementStaffPassword.setText(this.managementStaff.getPassword());
            managementStaffManagementLevel.setValue(this.managementStaff.getLevel());
            managementStaffBranch.setValue(this.managementStaff.getBranch());
        }
    }
}


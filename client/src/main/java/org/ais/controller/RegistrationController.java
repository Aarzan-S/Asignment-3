package org.ais.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.ais.model.IModel;
import org.ais.model.Staff;
import org.ais.model.StaffModel;
import org.ais.presenter.StaffPresenter;
import org.ais.util.NavigationHelper;
import org.ais.view.IView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class handles mainly Management and Admin registration
 */
public class RegistrationController implements Controller, IView<Staff> {
    private static final String LOGIN_PAGE = "Login.fxml";
    private static final String RECRUIT_REGISTRATION = "RecruitRegistration.fxml";
    private static final String ADMIN_REGISTRATION = "AdminRegistration.fxml";
    private static final String MANAGEMENT_REGISTRATION = "ManagementRegistration.fxml";
    private StaffPresenter presenter;

    /**
     * Initializes userName, userRole,
     * @param userName name of the logged-in user
     * @param userRole role of the logged-in user
     */
    @Override
    public void setUp(String userName, String userRole) {
    }

    /**
     * This is empty implementation of setUp method.
     * Can be updated as per need
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IModel<Staff> model = new StaffModel();
        presenter = new StaffPresenter(model, this);
        model.connect();
    }

    /**
     * Navigates to management registration page
     */
    @FXML
    private void showManagementRegistration() {
        NavigationHelper.navigate( MANAGEMENT_REGISTRATION, null, null);
    }

    /**
     * Navigates to admin registration page
     */
    @FXML
    private void showAdminRegistration() {
        NavigationHelper.navigate( ADMIN_REGISTRATION, null, null);
    }

    /**
     * This method is bind to 'View History' button, and it navigates to recruit history page.
     */
    @FXML
    private void showRecruitRegistration() {
        NavigationHelper.navigate( RECRUIT_REGISTRATION, null, null);
    }

    /**
     * Navigates to login page.
     */
    @FXML
    private void goBack() {
        NavigationHelper.navigate(LOGIN_PAGE, "", "");
    }


    @Override
    public void display(Staff object) {

    }

    @Override
    public void display(String message, String type) {
    }

    @Override
    public void clearInputs() {

    }
}

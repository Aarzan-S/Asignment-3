package org.ais.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.ais.model.IModel;
import org.ais.model.ManagementStaff;
import org.ais.model.ManagementStaffModel;
import org.ais.presenter.ManagementPresenter;
import org.ais.util.routing.NavigationHelper;
import org.ais.view.IView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class handles all the functionality of Management page.
 */
public class ManagementController implements Controller, IView<ManagementStaff> {
    private static final String VIEW_RECRUIT_PAGE = "ViewRecruit.fxml";
    private static final String LOGIN_PAGE = "Login.fxml";

    private ManagementPresenter presenter;

    private String userName;

    private String userRole;

    @FXML
    private Label welcomeLabel;

    /**
     * Initializes userName,userRole and initialize welcomeLabel.
     * @param userName name of the logged-in user
     * @param userRole
     */
    @Override
    public void setUp(String userName, String userRole) {
        this.userName = userName;
        this.userRole = userRole;
        this.welcomeLabel.setText("Welcome, " + userName);
    }

    /**
     * This is empty implementation of setUp method
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        IModel<ManagementStaff> model = new ManagementStaffModel();
        presenter = new ManagementPresenter(model, this);
        model.connect();
    }

    @FXML
    private void editManagement() {
        NavigationHelper.navigate( "ManagementUpdate.fxml", userName, userRole);
    }
    /**
     * Navigates to recruit details page.
     */
    @FXML
    private void viewRecruitDetails() {
        NavigationHelper.navigate( VIEW_RECRUIT_PAGE, userName, userRole);
    }

    /**
     * Navigates to login page.
     */
    @FXML
    private void logout() {
        NavigationHelper.navigate( LOGIN_PAGE, "","");
    }

    @Override
    public void display(String message, String type) {

    }

    @Override
    public void clearInputs() {

    }
}

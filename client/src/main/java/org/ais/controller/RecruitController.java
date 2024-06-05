package org.ais.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.ais.model.IModel;
import org.ais.model.Recruit;
import org.ais.model.RecruitModel;
import org.ais.presenter.RecruitPresenter;
import org.ais.util.routing.NavigationHelper;
import org.ais.view.IView;

import java.net.URL;
import java.util.ResourceBundle;

public class RecruitController implements Controller, IView<Recruit> {
    private RecruitPresenter presenter;

    private String userName;

    private String userRole;

    @FXML
    private Label welcomeLabel;

    /**
     * Initializes userName,userRole and initialize welcomeLabel.
     *
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
        IModel<Recruit> model = new RecruitModel();
        presenter = new RecruitPresenter(model, this);
        model.connect();
    }

    @FXML
    private void editRecruit() {
        NavigationHelper.navigate("RecruitUpdate.fxml", userName, userRole);
    }

    /**
     * Navigates to recruit details page.
     */
    @FXML
    private void logout() {
        NavigationHelper.navigate("Login.fxml", "","");
    }

    @Override
    public void display(String message, String type) {
    }

    @Override
    public void clearInputs() {
    }
}

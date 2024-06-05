package org.ais.controller;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.ais.model.IModel;
import org.ais.model.Recruit;
import org.ais.model.RecruitModel;
import org.ais.presenter.RecruitRegistrationPresenter;
import org.ais.util.routing.NavigationHelper;
import org.ais.util.validators.NumberFieldValidator;
import org.ais.view.IView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * THis class handles recruit registration mechanism
 */
public class RecruitRegistrationController implements Controller, IView<Recruit> {
    private RecruitRegistrationPresenter presenter;
    private String userName;
    private  String userRole;
    @FXML
    private TextField recruitFullname;
    @FXML
    private TextField recruitAddress;
    @FXML
    private TextField recruitPhone;
    @FXML
    private TextField recruitEmail;
    @FXML
    private TextField recruitUsername;
    @FXML
    private TextField recruitPassword;
    @FXML
    private DatePicker recruitInterviewDate;
    @FXML
    private ChoiceBox<String> recruitQualificationLevel = new ChoiceBox<>();
    @FXML
    private Label messageLabel;
    @FXML
    private Button addRecruitDetails;

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
     * It initializes necessary fields for the class such as headerLabel, recruitQualificationLevel
     * It adds validation to phone number field to accept only number and 10 digits only.
     * It also binds validation to date picker to disable past dates and set today's date as default date.
     * It will binds disable property to add button if the input fields are empty
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        IModel<Recruit> model = new RecruitModel();
        presenter = new RecruitRegistrationPresenter(model, this);
        model.connect();
        recruitQualificationLevel.getItems().addAll("Bachelors", "Masters", "PhD");
        recruitPhone.setTextFormatter(new NumberFieldValidator());
        recruitInterviewDate.setDayCellFactory(dataPicker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
            }
        });
        recruitInterviewDate.setValue(LocalDate.now());
        addRecruitDetails.disableProperty().bind(
                Bindings.isEmpty(recruitFullname.textProperty())
                        .or(Bindings.isEmpty(recruitAddress.textProperty()))
                        .or(Bindings.isEmpty(recruitPhone.textProperty()))
                        .or(Bindings.isEmpty(recruitEmail.textProperty()))
                        .or(Bindings.isEmpty(recruitUsername.textProperty()))
                        .or(Bindings.isEmpty(recruitPassword.textProperty()))
                        .or(Bindings.isNull(recruitInterviewDate.valueProperty()))
                        .or(Bindings.isNull(recruitQualificationLevel.valueProperty()))
        );

    }
    /**
     * Handles the adding of recruit details
     * Handles white spaces, email and phone number validation.
     * Validates uniqueness of username,email and phone so that there is no duplicate/redundant entry.
     * Recruit Object is created and added to recruitList upon successful validation/
     */
    @FXML
    private void addRecruitDetails() {
        Recruit recruit = new Recruit(
                0,
                recruitFullname.getText(),
                recruitAddress.getText(),
                Long.parseLong(recruitPhone.getText()),
                recruitEmail.getText(),
                recruitUsername.getText(),
                recruitPassword.getText(),
                recruitInterviewDate.getValue(),
                recruitQualificationLevel.getValue(),
                "N/A",
                "N/A",
                this.userName,
                LocalDate.now());
        presenter.add(recruit);
    }

    /**
     * Navigates to Admin page.
     */
    @FXML
    private void navigateBack() {
        String path = userName != null ? "Admin.fxml" : "Registration.fxml";
        NavigationHelper.navigate( path, userName, userRole);
    }

    /**
     * Saves Recruit information in the file
     *
     * @throws Exception
     */
    @FXML
    private void registerRecruit(ActionEvent event) throws Exception {
        presenter.register(userRole, userName);
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
        recruitFullname.clear();
        recruitAddress.clear();
        recruitPhone.clear();
        recruitEmail.clear();
        recruitUsername.clear();
        recruitPassword.clear();
        recruitInterviewDate.setValue(null);
        recruitQualificationLevel.setValue(null);
    }
}

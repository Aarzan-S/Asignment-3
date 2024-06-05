package org.ais.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.ais.model.IModel;
import org.ais.model.Recruit;
import org.ais.model.RecruitUpdateModel;
import org.ais.presenter.RecruitUpdatePresenter;
import org.ais.util.routing.NavigationHelper;
import org.ais.util.validators.NumberFieldValidator;
import org.ais.view.IView;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RecruitUpdateController implements Controller, IView<Recruit> {
    private RecruitUpdatePresenter presenter;
    private Recruit recruit;
    private String userName;
    private String userRole;
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
        recruit = presenter.getRecruitDetails(userName);
        loadDetails();
    }

    /**
     * It initializes necessary fields for the class such as headerLabel, recruitQualificationLevel
     * It adds validation to phone number field to accept only number and 10 digits only.
     * It also binds validation to date picker to disable past dates and set today's date as default date.
     * It will binds disable property to add button if the input fields are empty
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        IModel<Recruit> model = new RecruitUpdateModel();
        presenter = new RecruitUpdatePresenter(model, this);
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
    }

    /**
     * Navigates to Admin page.
     */
    @FXML
    private void goBack() {
        NavigationHelper.navigate("Recruit.fxml", userName, userRole);
    }

    /**
     * Saves Recruit information in the file
     *
     */
    @FXML
    private void updateRecruit(ActionEvent event) {
        Recruit recruitDetail = new Recruit(
                0,
                recruitFullname.getText(),
                recruitAddress.getText(),
                Long.parseLong(recruitPhone.getText()),
                recruitEmail.getText(),
                recruitUsername.getText(),
                recruitPassword.getText(),
                recruitInterviewDate.getValue(),
                recruitQualificationLevel.getValue(),
                "",
                "",
                recruitUsername.getText(),
                LocalDate.now());
        presenter.updateDetails(recruitDetail, recruit.getId());
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
    }
    private void loadDetails() {
        if (recruit != null) {
            recruitFullname.setText(this.recruit.getFullName());
            recruitAddress.setText(this.recruit.getAddress());
            recruitPhone.setText(this.recruit.getPhoneNumber().toString());
            recruitEmail.setText(this.recruit.getEmail());
            recruitUsername.setText(this.recruit.getUsername());
            recruitPassword.setText(this.recruit.getPassword());
            recruitInterviewDate.setValue(this.recruit.getInterviewDate());
            recruitQualificationLevel.setValue(this.recruit.getHighestQualification());
        }
    }
}
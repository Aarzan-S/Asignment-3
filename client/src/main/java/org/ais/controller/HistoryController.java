package org.ais.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.ais.model.IModel;
import org.ais.model.Recruit;
import org.ais.model.RecruitModel;
import org.ais.presenter.RecruitHistoryPresenter;
import org.ais.util.routing.NavigationHelper;
import org.ais.view.IView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class handles all the history related functionality
 */
public class HistoryController implements Controller, IView<Recruit> {
    public static final String STAFF_PAGE = "Staff.fxml";
    public static final String ADMIN_PAGE = "Admin.fxml";

    private RecruitHistoryPresenter presenter;
    private String userName;
    private String userRole;
    ObservableList<Recruit> recruits = FXCollections.observableArrayList();
    FilteredList<Recruit> filteredList = new FilteredList<>(recruits);
    @FXML
    TableView<Recruit> tableView = new TableView<>();
    @FXML
    Label infoLabel;
    @FXML
    TextField searchBox;

    /**
     * Initializes userName and userRole for this class
     * Loads recruit data from file
     *
     * @param userName name of the logged-in user
     * @param userRole role of the logged-in user e.g. Admin, Management
     */
    @Override
    public void setUp(String userName, String userRole) {
        this.userName = userName;
        this.userRole = userRole;
        loadRecruitData();
    }

    /**
     * Loads data from file, create column definitions and set the columns along with their data.
     */
    public void loadRecruitData() {
        recruits.addAll(presenter.loadData());
        presenter.generateColumnDefinition(tableView, userRole);
        tableView.setItems(filteredList);
    }

    /**
     * Binds the searchBox property to an event listener which hand;les searching
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        IModel<Recruit> model = new RecruitModel();
        presenter = new RecruitHistoryPresenter(model, this);
        model.connect();
        searchBox.textProperty().addListener((obs, oldVal, newVal) -> filteredList.setPredicate(recruit -> {
            if (newVal == null || newVal.isEmpty()) {
                return true;
            }
            return recruit.getFullName().toLowerCase().contains(newVal.toLowerCase().trim());
        }));
    }

    /**
     * Navigates to Admin page or Staff page based on the logged-in user
     */
    @FXML
    private void navigateBack() {
        String page = userRole.equals("Admin") ? ADMIN_PAGE : STAFF_PAGE;
        NavigationHelper.navigate( page, userName, userRole);
    }

    @Override
    public void display(String message, String type) {

    }

    @Override
    public void clearInputs() {

    }
}

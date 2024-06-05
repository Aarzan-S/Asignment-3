package org.ais.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.ais.model.IModel;
import org.ais.model.ManagementStaff;
import org.ais.model.ManagementStaffModel;
import org.ais.presenter.ManagementViewPresenter;
import org.ais.util.routing.NavigationHelper;
import org.ais.view.IView;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewManagementController implements Controller, IView<ManagementStaff> {
    private ManagementViewPresenter presenter;
    ObservableList<ManagementStaff> mngStaffs = FXCollections.observableArrayList();
    FilteredList<ManagementStaff> filteredList = new FilteredList<>(mngStaffs);

    private String userName;
    private String userRole;
    @FXML
    TableView<ManagementStaff> tableView = new TableView<>();
    @FXML
    TextField searchBox;
    @Override
    public void setUp(String userName, String userRole) {
        this.userName = userName;
        this.userRole = userRole;
        loadMngData();
    }

    /**
     * Loads data from file, creates column definitions and set the columns along with their data.
     */
    public void loadMngData() {
        mngStaffs.addAll(presenter.loadData());
        presenter.generateColumnDefinition(tableView);
        tableView.setItems(filteredList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        IModel<ManagementStaff> model = new ManagementStaffModel();
        presenter = new ManagementViewPresenter(model, this);
        model.connect();
        searchBox.textProperty().addListener((obs, oldVal, newVal) -> filteredList.setPredicate(mng -> {
            if (newVal == null || newVal.isEmpty()) {
                return true;
            }
            return mng.getFullName().toLowerCase().contains(newVal.toLowerCase().trim());
        }));
    }

    @Override
    public void display(String message, String type) {

    }

    @Override
    public void clearInputs() {

    }

    /**
     * Navigates to Admin page
     */
    @FXML
    private void navigateBack() {
        NavigationHelper.navigate("Admin.fxml", userName, userRole);
    }
}

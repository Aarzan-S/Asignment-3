package org.ais.presenter;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ais.model.IModel;
import org.ais.model.ManagementStaff;
import org.ais.view.IView;

import java.util.List;
/**
 * Represents the logic for the Management page
 */
public class ManagementViewPresenter {
    private IModel<ManagementStaff> mngModel;
    private IView<ManagementStaff> view;

    public ManagementViewPresenter(IModel<ManagementStaff> mngModel, IView<ManagementStaff> view) {
        this.mngModel = mngModel;
        this.view = view;
    }

    /**
     * Fetches management staff deadbeats
     * @return
     */
    public List<ManagementStaff> loadData() {
        return mngModel.loadData();
    }

    /**
     * Generates column definitions
     * @param tableView
     */
    public void generateColumnDefinition(TableView<ManagementStaff> tableView) {
        tableView.setEditable(false);
        TableColumn<ManagementStaff, String> nameColumn = new TableColumn<>("Full Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        TableColumn<ManagementStaff, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<ManagementStaff, String> phoneNumberColumn = new TableColumn<>("Phone Number");
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        TableColumn<ManagementStaff, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<ManagementStaff, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<ManagementStaff, String> levelColumn = new TableColumn<>("Level");
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));

        TableColumn<ManagementStaff, String> branchColumn = new TableColumn<>("Branch");
        branchColumn.setCellValueFactory(new PropertyValueFactory<>("branch"));

        tableView.getColumns().addAll(nameColumn, addressColumn, phoneNumberColumn, emailColumn, usernameColumn,
                levelColumn, branchColumn);
    }

}

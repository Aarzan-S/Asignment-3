package org.ais.presenter;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.ais.model.IModel;
import org.ais.model.Recruit;
import org.ais.restHandler.AdminLogHandler;
import org.ais.view.IView;

import java.time.LocalDate;
import java.util.List;

public class RecruitViewPresenter {
    private IModel<Recruit> recruitModel;
    private IView<Recruit> view;

    private String userName;
    private String userRole;

    public RecruitViewPresenter(IModel<Recruit> recruitModel, IView<Recruit> view) {
        this.recruitModel = recruitModel;
        this.view = view;
    }

    public List<Recruit> loadData(String userName, String userRole) {
        this.userName = userName;
        this.userRole = userRole;
        return recruitModel.loadData();
    }

    public void generateColumnDefinition(TableView<Recruit> tableView, String userRole) {
        tableView.setEditable(userRole.equals("Admin"));
        TableColumn<Recruit, String> nameColumn = new TableColumn<>("Full Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(event -> {
            event.getTableView().getItems().get(event.getTablePosition().getRow()).setFullName(event.getNewValue());
        });

        TableColumn<Recruit, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        addressColumn.setOnEditCommit(event -> event.getRowValue().setFullName(event.getNewValue()));

        TableColumn<Recruit, String> phoneNumberColumn = new TableColumn<>("Phone Number");
        phoneNumberColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPhoneNumber().toString()));
        phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        phoneNumberColumn.setOnEditCommit(event -> event.getRowValue().setPhoneNumber(Long.valueOf(event.getNewValue())));

        TableColumn<Recruit, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit(event -> event.getRowValue().setFullName(event.getNewValue()));

        TableColumn<Recruit, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        usernameColumn.setOnEditCommit(event -> event.getRowValue().setFullName(event.getNewValue()));

        TableColumn<Recruit, LocalDate> interviewDateColumn = new TableColumn<>("Interview Date");
        interviewDateColumn.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getInterviewDate()));
        interviewDateColumn.setCellFactory(col -> {
            TableCell<Recruit, LocalDate> cell = new TableCell<>() {
                private final DatePicker datePicker = new DatePicker();

                {
                    datePicker.setOnAction(event -> {
                        commitEdit(datePicker.getValue());
                    });
                }

                @Override
                public void startEdit() {
                    super.startEdit();
                    if (isEmpty()) {
                        return;
                    }
                    setGraphic(datePicker);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                }

                @Override
                public void cancelEdit() {
                    super.cancelEdit();
                    setContentDisplay(ContentDisplay.TEXT_ONLY);
                }

                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        if (isEditing()) {
                            setGraphic(datePicker);
                            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        } else {
                            setText(item.toString());
                            setContentDisplay(ContentDisplay.TEXT_ONLY);
                        }
                    }
                }
            };

            cell.setEditable(true);
            return cell;
        });

        interviewDateColumn.setOnEditCommit(event -> {
            event.getRowValue().setInterviewDate(event.getNewValue());
        });

        TableColumn<Recruit, String> highestQualificationColumn = new TableColumn<>("Highest Qualification");
        highestQualificationColumn.setCellValueFactory(new PropertyValueFactory<>("highestQualification"));
        highestQualificationColumn.setPrefWidth(100);
        highestQualificationColumn.setCellFactory(column -> new ComboBoxCell(FXCollections.observableArrayList("None",
                "Bachelors", "Masters", "PhD"), "highestQualificationColumn"));
        highestQualificationColumn.setEditable(true);

        TableColumn<Recruit, String> departmentColumn = new TableColumn<>("Department");
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        departmentColumn.setPrefWidth(100);
        departmentColumn.setCellFactory(column -> new ComboBoxCell(FXCollections.observableArrayList("None",
                "Software", "Aerospace", "Mechanical", "Electronics"), "department"));
        departmentColumn.setEditable(true);

        TableColumn<Recruit, String> locationColumn = new TableColumn<>("Location");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        locationColumn.setPrefWidth(100);
        locationColumn.setCellFactory(col -> new ComboBoxCell(FXCollections.observableArrayList("None", "Brisbane",
                "Adelaide", "Sydney", "Melbourne"), "location"));
        locationColumn.setEditable(true);

        TableColumn<Recruit, Void> buttonColumn = new TableColumn<>("Action");
        buttonColumn.setPrefWidth(100);
        buttonColumn.setSortable(false);
        buttonColumn.setCellFactory(param -> new ButtonCell(userRole));
        tableView.getColumns().addAll(nameColumn, addressColumn, phoneNumberColumn, emailColumn, usernameColumn,
                interviewDateColumn, highestQualificationColumn, departmentColumn, locationColumn, buttonColumn);
    }
    /**
     * This class defines the property of dropdown column in table namely
     * location and department.
     */
    private class ComboBoxCell extends TableCell<Recruit, String> {
        private final ComboBox<String> comboBox;

        public ComboBoxCell(ObservableList<String> items, String columnName) {
            this.comboBox = new ComboBox<>(items);
            comboBox.setOnAction(event -> {
                Recruit recruit = getTableRow().getItem();
                if (recruit != null) {
                    if (columnName.equals("department"))
                        recruit.setDepartment(comboBox.getValue());
                    else
                        recruit.setLocation(comboBox.getValue());
                }
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || getTableRow() == null) {
                setGraphic(null);
            } else {
                comboBox.setValue(item);
                setGraphic(comboBox);
            }
        }
    }

    /**
     * This class defines button property for 'Update' button in table.
     * updateCSV method is bind to the click event of the button
     */
    private class ButtonCell extends TableCell<Recruit, Void> {
        private final Button updateButton;
        private final String userRole;

        public ButtonCell(String userRole) {
            updateButton = new Button("Update");
            this.userRole = userRole;
            updateButton.setOnAction(event -> {
                Recruit recruit = getTableView().getItems().get(getIndex());
                updateCSV(recruit, getIndex() + 1);
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                updateButton.setDisable(false);
                setGraphic(updateButton);
            }
        }
    }

    /**
     * Updates the table row, and is  bind to 'Update' button in table row
     *
     * @param recruit Recruit Object representing updated row
     * @param index   Index of row in the table
     */
    private void updateCSV(Recruit recruit, int index) {
        recruitModel.update(recruit, recruit.getId());
        if ("Admin".equals(userRole) && null != userName)
            AdminLogHandler.addLog(userName, recruit.getUsername(), "Update Recruit");
        view.display("Recruit Updated Successfully", "INFO");
    }

}

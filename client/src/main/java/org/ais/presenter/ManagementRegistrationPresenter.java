package org.ais.presenter;

import org.ais.model.IModel;
import org.ais.model.ManagementStaff;
import org.ais.util.validators.Validator;
import org.ais.view.IView;

import java.io.IOException;
/**
 * Represents the logic for the management registration page
 */
public class ManagementRegistrationPresenter {
    private IModel<ManagementStaff> managementStaffModel;
    private IView<ManagementStaff> view;

    public ManagementRegistrationPresenter(IModel<ManagementStaff> managementStaffModel, IView<ManagementStaff> view) {
        this.managementStaffModel = managementStaffModel;
        this.view = view;
    }

    /**
     * Adds details to the list which is later saved to database
     * @param managementStaff
     */
    public void add(ManagementStaff managementStaff) {
        if (!Validator.validatePhoneNumber(managementStaff.getPhoneNumber().toString())) {
            view.display("Phone number is not valid", "ERROR");
            return;
        } else if (!Validator.validateEmail(managementStaff.getEmail())) {
            view.display("Email address is not valid", "ERROR");
            return;
        }
        managementStaffModel.add(managementStaff);
//        view.clearInputs();
        view.display(managementStaff.getUsername() + " is saved", "INFO");
    }

    /**
     * Saves management staff data to database
     * @throws IOException
     */
    public void register() throws IOException {
        if (managementStaffModel.getAll().isEmpty()) {
            view.display("Nothing to register", "ERROR");
            return;
        }
        String errMsg = managementStaffModel.register();
        if (errMsg != null) {
            view.display(errMsg.split(":")[1], "ERROR");
            return;
        }
        view.clearInputs();
        view.display("Management Staff registered", "INFO");
    }
}

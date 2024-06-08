package org.ais.presenter;

import org.ais.model.AdminStaff;
import org.ais.model.IModel;
import org.ais.util.validators.Validator;
import org.ais.view.IView;

import java.io.IOException;
/**
 * Represents the logic for the admin registration page
 */
public class AdminRegistrationPresenter {
    private IModel<AdminStaff> adminStaffModel;
    private IView<AdminStaff> view;

    public AdminRegistrationPresenter(IModel<AdminStaff> adminStaffModel, IView<AdminStaff> view) {
        this.adminStaffModel = adminStaffModel;
        this.view = view;
    }
    /**
     * Adds admin details to list which is later used to save
     * @param adminStaff
     */
    public void add(AdminStaff adminStaff) {
        if (!Validator.validatePhoneNumber(adminStaff.getPhoneNumber().toString())) {
            view.display("Phone number is not valid", "ERROR");
            return;
        } else if (!Validator.validateEmail(adminStaff.getEmail())) {
            view.display("Email address is not valid", "ERROR");
            return;
        }
        adminStaffModel.add(adminStaff);
        view.clearInputs();
        view.display("Admin details added", "INFO");
    }

    /**
     * Saves admin staff to database
     * @throws IOException
     */
    public void register() throws IOException {
        if (adminStaffModel.getAll().isEmpty()) {
            view.display("Nothing to register", "ERROR");
            return;
        }
        String errMsg = adminStaffModel.register();
        if (errMsg != null){
            view.display(errMsg.split(":")[1], "ERROR");
            return;
        }
        view.display("Admin Staff registered", "INFO");
    }
}

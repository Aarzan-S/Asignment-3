package org.ais.presenter;

import org.ais.model.AdminStaff;
import org.ais.model.IModel;
import org.ais.util.validators.Validator;
import org.ais.view.IView;

import java.io.IOException;

public class AdminRegistrationPresenter {
    private IModel<AdminStaff> adminStaffModel;
    private IView<AdminStaff> view;

    public AdminRegistrationPresenter(IModel<AdminStaff> adminStaffModel, IView<AdminStaff> view) {
        this.adminStaffModel = adminStaffModel;
        this.view = view;
    }
    private boolean isRegistered(String name) {
        return adminStaffModel.get(name) != null;
    }

    public void add(AdminStaff adminStaff) {
        if (!Validator.validatePhoneNumber(adminStaff.getPhoneNumber().toString())) {
            view.display("Phone number is not valid", "ERROR");
            return;
        } else if (!Validator.validateEmail(adminStaff.getEmail())) {
            view.display("Email address is not valid", "ERROR");
            return;
        }
//        if (isRegistered(adminStaff.getUsername())) {
//            view.display(adminStaff.getUsername() + " is already registered", "ERROR");
//            return;
//        }
        adminStaffModel.add(adminStaff);
        view.clearInputs();
        view.display("Admin details added", "INFO");
    }

//    public void register(AdminStaff staff) throws IOException {
//        String errMsg = adminStaffModel.register(staff);
//        if (errMsg != null){
//            view.display(errMsg.split(":")[1], "ERROR");
//            return;
//        }
//        view.display("Admin Staff registered", "INFO");
//    }

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

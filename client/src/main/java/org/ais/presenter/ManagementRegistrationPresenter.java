package org.ais.presenter;

import org.ais.model.IModel;
import org.ais.model.ManagementStaff;
import org.ais.util.validators.Validator;
import org.ais.view.IView;

import java.io.IOException;

public class ManagementRegistrationPresenter {
    private IModel<ManagementStaff> managementStaffModel;
    private IView<ManagementStaff> view;

    public ManagementRegistrationPresenter(IModel<ManagementStaff> managementStaffModel, IView<ManagementStaff> view) {
        this.managementStaffModel = managementStaffModel;
        this.view = view;
    }

//    public void register(ManagementStaff staff) throws IOException {
//        String errMsg = managementStaffModel.register(staff);
//        if (errMsg != null){
//            view.display(errMsg.split(":")[1], "ERROR");
//            return;
//        }
//        view.display("Management Staff registered", "INFO");
//    }

    public void add(ManagementStaff managementStaff) {
        if (!Validator.validatePhoneNumber(managementStaff.getPhoneNumber().toString())) {
            view.display("Phone number is not valid", "ERROR");
            return;
        } else if (!Validator.validateEmail(managementStaff.getEmail())) {
            view.display("Email address is not valid", "ERROR");
            return;
        }
        managementStaffModel.add(managementStaff);
        view.clearInputs();
        view.display(managementStaff.getUsername() + " is saved", "INFO");
    }

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
        view.display("Management Staff registered", "INFO");
    }
}

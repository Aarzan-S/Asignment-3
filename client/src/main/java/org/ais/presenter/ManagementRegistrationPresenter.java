package org.ais.presenter;

import org.ais.model.IModel;
import org.ais.model.ManagementStaff;
import org.ais.restHandler.AdminHandler;
import org.ais.restHandler.ManagementHandler;
import org.ais.util.Validator;
import org.ais.view.IView;

import java.io.IOException;

public class ManagementRegistrationPresenter {
    private IModel<ManagementStaff> managementStaffList;
    private IView<ManagementStaff> view;

    public ManagementRegistrationPresenter(IModel<ManagementStaff> managementStaffList, IView<ManagementStaff> view) {
        this.managementStaffList = managementStaffList;
        this.view = view;
    }

//    public void register(ManagementStaff staff) throws IOException {
//        String errMsg = managementStaffList.register(staff);
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
        managementStaffList.add(managementStaff);
        view.clearInputs();
        view.display(managementStaff.getUsername() + " is saved", "INFO");
    }

    public void register() throws IOException {
        if (managementStaffList.getAll().isEmpty()) {
            view.display("Nothing to register", "ERROR");
            return;
        }
        String errMsg = managementStaffList.register();
        if (errMsg != null) {
            view.display(errMsg.split(":")[1], "ERROR");
            return;
        }
        view.display("Management Staff registered", "INFO");
    }
}

package org.ais.presenter;

import org.ais.model.IModel;
import org.ais.model.Staff;
import org.ais.restHandler.LoginHandler;
import org.ais.view.IView;

public class LoginPresenter {
    private IModel<Staff> staffIModel;
    private IView<Staff> view;

    public LoginPresenter(IModel<Staff> staffIModel, IView<Staff> view) {
        this.staffIModel = staffIModel;
        this.view = view;
    }

    public boolean login(Staff staff) {
        String msg = LoginHandler.authenticateUser(staff);
        if (msg.startsWith("ERROR")) {
            view.display(msg.split(":")[1], "ERROR");
            return false;
        }
        staff.setStaffId(msg);
        return true;
    }
}



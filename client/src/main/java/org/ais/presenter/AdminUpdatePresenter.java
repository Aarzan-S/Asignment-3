package org.ais.presenter;

import org.ais.model.AdminStaff;
import org.ais.model.IModel;
import org.ais.restHandler.AdminHandler;
import org.ais.view.IView;
/**
 * Represents the logic for the admin update page
 */
public class AdminUpdatePresenter {
    private IModel<AdminStaff> adminStaffModel;
    private IView<AdminStaff> view;

    public AdminUpdatePresenter(IModel<AdminStaff> adminStaffModel, IView<AdminStaff> view) {
        this.adminStaffModel = adminStaffModel;
        this.view = view;
    }

    /**
     * Fetches admin details from database
     * @param username
     * @return
     */

    public AdminStaff getAdminDetails(String username) {
        AdminStaff adminStaff = adminStaffModel.get(username);
        if (adminStaff == null) {
            this.view.display("Could not load ", "ERROR");
            return null;
        }
        return adminStaff;
    }

    /**
     * Sends update request to server
     * @param staff
     * @param id
     * @return
     */
    public boolean updateAdminDetails(AdminStaff staff, int id) {
        String errMsg = AdminHandler.updateDetails(staff, id);
        if (errMsg != null) {
            this.view.display(errMsg.split(":")[1], "ERROR");
            return false;
        }
        this.view.display("AdminDetails update successfully", "INFO");
        return true;
    }
}
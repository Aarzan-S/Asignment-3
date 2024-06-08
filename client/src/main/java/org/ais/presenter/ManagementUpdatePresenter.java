package org.ais.presenter;

import org.ais.model.IModel;
import org.ais.model.ManagementStaff;
import org.ais.restHandler.ManagementHandler;
import org.ais.view.IView;
/**
 * Represents the logic for the management update page
 */
public class ManagementUpdatePresenter {
    private IModel<ManagementStaff> mngStaffModel;
    private IView<ManagementStaff> view;

    public ManagementUpdatePresenter(IModel<ManagementStaff> mngStaffModel, IView<ManagementStaff> view) {
        this.mngStaffModel = mngStaffModel;
        this.view = view;
    }

    /**
     * Fetches management staff detail
     * @param username
     * @return
     */
    public ManagementStaff getManagementDetails(String username) {
        ManagementStaff adminStaff = mngStaffModel.get(username);
        if (adminStaff == null) {
            this.view.display("Could not load ", "ERROR");
            return null;
        }
        return adminStaff;
    }

    /**
     * Sends update request to server to update
     * @param staff
     * @param id
     * @return
     */

    public boolean updateManagementDetails(ManagementStaff staff, int id) {
        String errMsg = ManagementHandler.updateDetails(staff, id);
        if (errMsg != null) {
            this.view.display(errMsg.split(":")[1], "ERROR");
            return false;
        }
        this.view.display("Management Details updated successfully", "INFO");
        return true;
    }

}
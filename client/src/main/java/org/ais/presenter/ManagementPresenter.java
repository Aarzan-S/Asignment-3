package org.ais.presenter;

import org.ais.model.IModel;
import org.ais.model.ManagementStaff;
import org.ais.view.IView;
/**
 * Represents the logic for the management page
 */
public class ManagementPresenter {
    private IModel<ManagementStaff> managementStaffList;
    private IView<ManagementStaff> view;

    public ManagementPresenter(IModel<ManagementStaff> managementStaffList, IView<ManagementStaff> view) {
        this.managementStaffList = managementStaffList;
        this.view = view;
    }
}

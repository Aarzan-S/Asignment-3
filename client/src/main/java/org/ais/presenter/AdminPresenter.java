package org.ais.presenter;


import org.ais.model.AdminStaff;
import org.ais.model.IModel;
import org.ais.view.IView;
/**
 * Represents the logic for the admin page
 */
public class AdminPresenter {
    private IModel<AdminStaff> adminStaffIModel;
    private IView<AdminStaff> view;

    public AdminPresenter(IModel<AdminStaff> adminStaffIModel, IView<AdminStaff> view) {
        this.adminStaffIModel = adminStaffIModel;
        this.view = view;
    }

}

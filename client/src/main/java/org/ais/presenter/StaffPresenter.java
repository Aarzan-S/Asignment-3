package org.ais.presenter;

import org.ais.model.IModel;
import org.ais.model.Staff;
import org.ais.view.IView;

/**
 * Represents the logic for the staff page which includes Admin and Management
 */
public class StaffPresenter {
    private IModel<Staff> staffModel;
    private IView<Staff> view;

    public StaffPresenter(IModel<Staff> staffModel, IView<Staff> view) {
        this.staffModel = staffModel;
        this.view = view;
    }
}

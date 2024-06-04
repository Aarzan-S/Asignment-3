package org.ais.presenter;

import org.ais.model.IModel;
import org.ais.model.Recruit;
import org.ais.restHandler.RecruitHandler;
import org.ais.view.IView;

public class RecruitUpdatePresenter {
    private IModel<Recruit> recruitModel;
    private IView<Recruit> view;

    public RecruitUpdatePresenter(IModel<Recruit> recruitModel, IView<Recruit> view) {
        this.recruitModel = recruitModel;
        this.view = view;
    }

    public Recruit getRecruitDetails(String username) {
        Recruit adminStaff = recruitModel.get(username);
        if (adminStaff == null) {
            this.view.display("Could not load ", "ERROR");
            return null;
        }
        return adminStaff;
    }

    public boolean updateDetails(Recruit staff, int id) {
        String errMsg = RecruitHandler.updateDetails(staff, id);
        if (errMsg != null) {
            this.view.display(errMsg.split(":")[1], "ERROR");
            return false;
        }
        this.view.display("Recruit Details update successfully", "INFO");
        return true;
    }
}

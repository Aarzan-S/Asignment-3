package org.ais.presenter;

import org.ais.model.IModel;
import org.ais.model.Recruit;
import org.ais.restHandler.RecruitHandler;
import org.ais.view.IView;
/**
 * This class represents the logic for the recruit update page
 */
public class RecruitUpdatePresenter {
    private IModel<Recruit> recruitModel;
    private IView<Recruit> view;

    public RecruitUpdatePresenter(IModel<Recruit> recruitModel, IView<Recruit> view) {
        this.recruitModel = recruitModel;
        this.view = view;
    }

    /**
     * fetches recruit deatils from server
     * @param username
     * @return
     */

    public Recruit getRecruitDetails(String username) {
        Recruit adminStaff = recruitModel.get(username);
        if (adminStaff == null) {
            this.view.display("Could not load ", "ERROR");
            return null;
        }
        return adminStaff;
    }

    /**
     * update recruit details
     * @param recruit
     * @param id
     * @return
     */

    public boolean updateDetails(Recruit recruit, int id) {
        String errMsg = RecruitHandler.updateDetails(recruit, id);
        if (errMsg != null) {
            this.view.display(errMsg.split(":")[1], "ERROR");
            return false;
        }
        this.view.display("Recruit Details update successfully", "INFO");
        return true;
    }
}

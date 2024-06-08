package org.ais.presenter;

import org.ais.model.IModel;
import org.ais.model.Recruit;
import org.ais.restHandler.RecruitHandler;
import org.ais.view.IView;
/**
 * Represents the logic for the recruit login page
 */
public class RecruitLoginPresenter {
    private IModel<Recruit> recruitModel;
    private IView<Recruit> view;

    public RecruitLoginPresenter(IModel<Recruit> recruitModel, IView<Recruit> view) {
        this.recruitModel = recruitModel;
        this.view = view;
    }

    /**
     * Sends login request to server to authenticate
     * @param recruit
     * @param otp
     * @return
     */
    public boolean login(Recruit recruit, String otp) {
        String errMsg = RecruitHandler.authenticateUser(recruit, otp);
        if (errMsg != null) {
            view.display(errMsg, "ERROR");
            return false;
        }
        return true;
    }
}

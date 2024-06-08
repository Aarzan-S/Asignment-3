package org.ais.presenter;

import org.ais.model.IModel;
import org.ais.model.Recruit;
import org.ais.restHandler.RecruitHandler;
import org.ais.view.IView;
/**
 * Represents the logic for the recruit page
 */
public class RecruitPresenter {
    private IModel<Recruit> recruitModel;
    private IView<Recruit> view;

    public RecruitPresenter(IModel<Recruit> recruitModel, IView<Recruit> view) {
        this.recruitModel = recruitModel;
        this.view = view;
    }
}

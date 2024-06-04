package org.ais.presenter;

import org.ais.model.IModel;
import org.ais.model.Recruit;
import org.ais.util.Validator;
import org.ais.view.IView;

import java.io.IOException;

public class RecruitRegistrationPresenter {
    private IModel<Recruit> recruitModel;
    private IView<Recruit> view;

    public RecruitRegistrationPresenter(IModel<Recruit> recruitModel, IView<Recruit> view) {
        this.recruitModel = recruitModel;
        this.view = view;
    }

    //    public void register(Recruit recruit) throws IOException {
//        String errMsg = recruitModel.register(recruit);
//        if (errMsg != null){
//            view.display(errMsg.split(":")[1], "ERROR");
//            return;
//        }
//        view.display("Recruit registered", "INFO");
//        view.clearInputs();
//    }
    public void add(Recruit recruit) {
        if (!Validator.validatePhoneNumber(recruit.getPhoneNumber().toString())) {
            view.display("Phone number is not valid", "ERROR");
            return;
        } else if (!Validator.validateEmail(recruit.getEmail())) {
            view.display("Email address is not valid", "ERROR");
            return;
        }
        recruitModel.add(recruit);
        view.clearInputs();
        view.display("Recruit details added", "INFO");
    }

    public void register() throws IOException {
        if (recruitModel.getAll().isEmpty()) {
            view.display("Nothing to register", "ERROR");
            return;
        }
        recruitModel.register();
        String errMsg = recruitModel.register();
        if (errMsg != null) {
            view.display(errMsg.split(":")[1], "ERROR");
            return;
        }
        view.display("Recruit registered", "INFO");
    }
}

package org.ais.presenter;

import org.ais.model.IModel;
import org.ais.model.Recruit;
import org.ais.restHandler.AdminLogHandler;
import org.ais.util.validators.Validator;
import org.ais.view.IView;

import java.io.IOException;
/**
 * This class Represents the recruit registration logic
 */
public class RecruitRegistrationPresenter {
    private IModel<Recruit> recruitModel;
    private IView<Recruit> view;

    public RecruitRegistrationPresenter(IModel<Recruit> recruitModel, IView<Recruit> view) {
        this.recruitModel = recruitModel;
        this.view = view;
    }


    /**
     * add recruit to recruit list which will then saved to database
     * @param recruit
     */
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

    /**
     * saves recruit details to database
     * @param userRole
     * @param userName
     * @throws IOException
     */

    public void register(String userRole, String userName) throws IOException {
        if (recruitModel.getAll().isEmpty()) {
            view.display("Nothing to register", "ERROR");
            return;
        }
        String errMsg = recruitModel.register();
        if (errMsg != null) {
            view.display(errMsg.split(":")[1], "ERROR");
            return;
        }
        if ("Admin".equals(userRole) && null != userName)
            AdminLogHandler.addLog(userName, recruitModel.getAll().get(0).getUsername(), "Register Recruit");
        view.display("Recruit registered", "INFO");
    }
}

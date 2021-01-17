package controller.action.admin.doctor;

import controller.action.admin.AdministratorAction;
import domain.Doctor;
import exception.PersistentException;
import service.DoctorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DoctorListAction extends AdministratorAction {

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        DoctorService service = serviceFactory.getDoctorService();
        List<Doctor> doctors = service.findAll();
        request.setAttribute("doctors", doctors);
        return null;
    }
}

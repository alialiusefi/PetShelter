package by.training.finaltask.action.staff;

import by.training.finaltask.action.AuthorizedUserAction;
import by.training.finaltask.dao.mysql.DAOEnum;
import by.training.finaltask.entity.Role;
import by.training.finaltask.entity.User;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.service.serviceinterface.PetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeletePetAction extends AuthorizedUserAction {

    private static final Logger LOGGER = LogManager.getLogger(DeletePetAction.class);

    public DeletePetAction() {
        this.allowedRoles.add(Role.STAFF);
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response)
            throws PersistentException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User authUser = (User) session.getAttribute("authorizedUser");
            if (allowedRoles.contains(authUser.getUserRole())) {
                String petIDParam = request.getParameter("petID");
                int petID = Integer.parseInt(petIDParam);
                PetService service = (PetService) factory
                        .createService(DAOEnum.PET);
                service.delete(petID);
                Forward forward = new Forward("/pets/findpet.html?page=1",true);
                forward.getAttributes().put("successMessage","petDeletedSuccessfully");
                return forward;
            }
            LOGGER.info(String.format("%s - attempted to access %s and stopped due to not enough" +
                    "privileges", request.getRemoteAddr(),request.getContextPath()));
        }
        LOGGER.info(String.format("%s - attempted to access %s and failed",
                request.getRemoteAddr(),request.getContextPath()));
        throw new PersistentException("forbiddenAccess");
    }
}

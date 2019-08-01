package by.training.finaltask.action;

import by.training.finaltask.dao.mysql.DAOEnum;
import by.training.finaltask.entity.Role;
import by.training.finaltask.entity.User;
import by.training.finaltask.exception.InvalidFormDataException;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.service.serviceinterface.AdoptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteAdoptionAction extends AuthorizedUserAction {

    private static final Logger LOGGER = LogManager.getLogger(
            DeleteAdoptionAction.class);
    private static final String ADOPTIONID = "adoptionID";
    public DeleteAdoptionAction() {
        this.allowedRoles.add(Role.STAFF);
        this.allowedRoles.add(Role.GUEST);
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        HttpSession session = request.getSession(false);
        if(session != null)
        {
            User authuser = (User) session.getAttribute("authorizedUser");
            if(authuser != null && this.allowedRoles.contains(authuser.getUserRole()))
            {
                String adoptionIDParam = request.getParameter(ADOPTIONID);
                int adoptionID = Integer.parseInt(adoptionIDParam);
                AdoptionService adoptionService = (AdoptionService)
                        factory.createService(DAOEnum.ADOPTION);
                try {
                    adoptionService.delete(adoptionID);
                    Forward forward = new Forward(request.getHeader("referer"));
                    forward.getAttributes().put("message","adoptionDeleted");
                    LOGGER.info(String.format("%s deleted adoption %d",authuser.getUsername(),adoptionID));
                    return forward;
                } catch (InvalidFormDataException e) {
                    Forward forward = new Forward(request.getHeader("referer"));
                    forward.getAttributes().put("message","cannotDeleteExpiredAdoption");
                    return forward;
                }
            }
            LOGGER.info(String.format("%s - attempted to access %s and stopped due to not enough" +
                            "privileges", request.getRemoteAddr(),request.getContextPath()));
        }
        LOGGER.info(String.format("%s - attempted to access %s and failed",
                request.getRemoteAddr(),request.getContextPath()));
        throw new PersistentException("forbiddenAccess");
    }
}

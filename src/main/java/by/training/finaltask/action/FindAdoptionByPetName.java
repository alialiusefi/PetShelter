package by.training.finaltask.action;

import by.training.finaltask.dao.mysql.DAOEnum;
import by.training.finaltask.entity.Adoption;
import by.training.finaltask.entity.Role;
import by.training.finaltask.entity.User;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.service.serviceinterface.AdoptionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class FindAdoptionByPetName extends AuthorizedUserAction {

    private static final Logger LOGGER = LogManager.getLogger(FindAdoptionByPetName.class);
    private static final int ROWCOUNT = 5;
    private static final String PETNAMEATTR = "petName";

    public FindAdoptionByPetName() {
        this.allowedRoles.add(Role.STAFF);
        this.allowedRoles.add(Role.GUEST);
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User authUser = (User) session.getAttribute("authorizedUser");
            if (authUser != null && allowedRoles.contains(authUser.getUserRole())) {
                String petName = request.getParameter(PETNAMEATTR);
                if (petName == null) {
                    petName = (String) session.getAttribute(PETNAMEATTR);
                } else {
                    session.setAttribute(PETNAMEATTR, petName);
                }
                AdoptionService service = (AdoptionService) factory.createService(
                        DAOEnum.ADOPTION);
                Forward forward = new Forward(request.getHeader("referer"));
                List<Adoption> adoptions = new ArrayList<>();
                if (authUser.getUserRole() == Role.GUEST) {
                    Pagination pagination = new Pagination(service.getCountPetNameCurrentUser(
                            authUser.getId(), "%" + petName + "%"),
                            ROWCOUNT, request.getParameter("page"));
                    forward.getAttributes().put("amountOfPages", pagination.getAmountOfPages());
                    adoptions = service.getAllPetNameCurrentUser(authUser.getId(), petName,
                            pagination.getOffset(), ROWCOUNT);
                }
                if (authUser.getUserRole() == Role.STAFF) {
                    Pagination pagination = new Pagination(service.getCountPetName(
                            "%" + petName + "%"), ROWCOUNT, request.getParameter("page"));
                    forward.getAttributes().put("amountOfPages", pagination.getAmountOfPages());
                    adoptions = service.getAllPetName("%" + petName + "%",
                            pagination.getOffset(), ROWCOUNT);
                }
                forward.getAttributes().put("paginationURL", "/adoptions/staff/findadoptionbypetname.html");
                forward.getAttributes().put("adoptionResults", adoptions);
                return forward;
            }
            LOGGER.info(String.format("%s - attempted to access %s and stopped due to not enough" +
                    "privileges", request.getRemoteAddr(), request.getRequestURI()));
        }
        LOGGER.info(String.format("%s - attempted to access %s and failed",
                request.getRemoteAddr(), request.getRequestURI()));
        throw new PersistentException("forbiddenAccess");
    }

}

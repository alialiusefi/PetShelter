package by.training.finaltask.action;

import by.training.finaltask.dao.mysql.DAOEnum;
import by.training.finaltask.entity.Pet;
import by.training.finaltask.entity.PetStatus;
import by.training.finaltask.entity.Role;
import by.training.finaltask.entity.User;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.parser.FormParser;
import by.training.finaltask.service.serviceinterface.PetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class FindPetByPetNameAction extends AuthorizedUserAction {

    private static final Logger LOGGER = LogManager.getLogger(FindPetByPetNameAction.class);
    private static String PETSTATUSATTR = "petStatus";
    private static String PETNAME_ATTRIBUTE = "petName";
    private static int ROWS_PER_PAGE = 6;

    public FindPetByPetNameAction() {
        this.allowedRoles.add(Role.STAFF);
        this.allowedRoles.add(Role.ADMINISTRATOR);
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User authUser = (User) session.getAttribute("authorizedUser");
            String petName = getPetName(request);
            session.setAttribute(PETNAME_ATTRIBUTE, petName);
            PetStatus status;
            if (authUser != null && this.allowedRoles.contains(authUser.getUserRole())) {
                status = FindPetAction.getStatus(request);
            } else {
                status = PetStatus.SHELTERED;
            }
            session.setAttribute(PETSTATUSATTR, status);
            PetService service = (PetService) factory.createService(DAOEnum.PET);
            int amountOfPetsByBreed = service.getAllCountByPetName(status, "%" + petName + "%");
            int amountOfPages = amountOfPetsByBreed % ROWS_PER_PAGE == 0 ?
                    amountOfPetsByBreed / ROWS_PER_PAGE : amountOfPetsByBreed / ROWS_PER_PAGE + 1;
            int pagenumber = FormParser.parsePageNumber(
                    request.getParameter("page"), amountOfPages);
            int offset = (pagenumber - 1) * ROWS_PER_PAGE;
            Forward forward = new Forward("/pets/findpet.html?page=" + pagenumber);
            forward.getAttributes().put("amountOfPages", amountOfPages);
            List<Pet> pets = service.getAllByPetName(status, "%" + petName + "%", offset, ROWS_PER_PAGE);
            forward.getAttributes().put("petResults", pets);
            List<String> images = FindPetAction.getImages(pets);
            forward.getAttributes().put("petPictures", images);
            forward.getAttributes().put("paginationURL", "/pets/findpetbypetname.html");
            return forward;
        }
        LOGGER.info(String.format("%s - attempted to access %s and failed",
                request.getRemoteAddr(), request.getRequestURI()));
        throw new PersistentException("forbiddenAccess");
    }

    private String getPetName(HttpServletRequest request) {
        String petName = request.getParameter(PETNAME_ATTRIBUTE);
        if (petName == null) {
            return (String) request.getSession(false).getAttribute(PETNAME_ATTRIBUTE);
        }
        return petName;
    }

}

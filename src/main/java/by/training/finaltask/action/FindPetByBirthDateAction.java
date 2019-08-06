package by.training.finaltask.action;

import by.training.finaltask.dao.mysql.DAOEnum;
import by.training.finaltask.entity.Pet;
import by.training.finaltask.entity.PetStatus;
import by.training.finaltask.entity.Role;
import by.training.finaltask.entity.User;
import by.training.finaltask.exception.InvalidFormDataException;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.parser.FormParser;
import by.training.finaltask.service.serviceinterface.PetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.GregorianCalendar;
import java.util.List;

public class FindPetByBirthDateAction extends AuthorizedUserAction {

    private static final Logger LOGGER = LogManager.getLogger(
            FindPetByBirthDateAction.class);
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static int ROWSPERPAGE = 6;
    private static String PETSTATUS_ATTRIBUTE = "petStatus";
    private static String BIRTHDATE_ATTRIBUTE = "birthDate";
    private static String RELATION_ATTRIBUTE = "birthDateChoice";

    public FindPetByBirthDateAction() {
        this.allowedRoles.add(Role.STAFF);
        this.allowedRoles.add(Role.ADMINISTRATOR);
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistentException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User authUser = (User) session.getAttribute("authorizedUser");
            String birthDateParam = getBirthDate(request);
            session.setAttribute(BIRTHDATE_ATTRIBUTE, birthDateParam);
            GregorianCalendar gregorianCalendar;
            try {
                gregorianCalendar = FormParser.parseDate(DATE_FORMAT, birthDateParam);
            } catch (InvalidFormDataException e) {
                request.setAttribute("message", e.getMessage());
                return null;
            }
            int relation = getRelation(request);
            session.setAttribute(RELATION_ATTRIBUTE, relation);
            PetStatus status;
            if (authUser != null && this.allowedRoles.contains(authUser.getUserRole())) {
                status = FindPetAction.getStatus(request);
            } else {
                status = PetStatus.SHELTERED;
            }
            session.setAttribute(PETSTATUS_ATTRIBUTE, status);
            Forward forward = new Forward("/pets/findpet.html?page=1");
            PetService service = (PetService) factory.createService(DAOEnum.PET);
            int amountOfPetsByBirthDate = service.getAllCountByBirthDate(relation,
                    status, gregorianCalendar);
            int amountOfPages = amountOfPetsByBirthDate % ROWSPERPAGE == 0 ?
                    amountOfPetsByBirthDate / ROWSPERPAGE : amountOfPetsByBirthDate / ROWSPERPAGE + 1;
            forward.getAttributes().put("amountOfPages", amountOfPages);
            int pageNumber = FormParser.parsePageNumber(
                    request.getParameter("page"), amountOfPages);
            int offset = (pageNumber - 1) * ROWSPERPAGE;
            List<Pet> pets = service.getAllByBirthDate(relation, status,
                    gregorianCalendar, offset, ROWSPERPAGE);
            forward.getAttributes().put("petResults", pets);
            List<String> images = FindPetAction.getImages(pets);
            forward.getAttributes().put("petPictures", images);
            forward.getAttributes().put("paginationURL", "/pets/findpetbybirthdate.html");
            return forward;
        }
        LOGGER.info(String.format("%s - attempted to access %s and failed",
                request.getRemoteAddr(), request.getRequestURI()));
        throw new PersistentException("forbiddenAccess");
    }

    private int getRelation(HttpServletRequest request) {
        String relationStr = request.getParameter(RELATION_ATTRIBUTE);
        if (relationStr == null) {
            return (Integer) request.getSession(false).getAttribute(RELATION_ATTRIBUTE);
        }
        if (relationStr.equals("lessthan")) {
            return -1;
        }
        if (relationStr.equals("greaterthan")) {
            return 1;
        }
        return 1;
    }

    private String getBirthDate(HttpServletRequest request) {
        String dateStr = request.getParameter(BIRTHDATE_ATTRIBUTE);
        if (dateStr == null) {
            return (String) request.getSession(false).getAttribute(BIRTHDATE_ATTRIBUTE);
        }
        return dateStr;
    }


}

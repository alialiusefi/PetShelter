package by.training.finaltask.action.admin;

import by.training.finaltask.action.AuthorizedUserAction;
import by.training.finaltask.dao.mysql.DAOEnum;
import by.training.finaltask.entity.Role;
import by.training.finaltask.entity.User;
import by.training.finaltask.entity.UserInfo;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.parser.FormParser;
import by.training.finaltask.service.serviceinterface.UserInfoService;
import by.training.finaltask.service.serviceinterface.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class FindStaffByFirstNameAction extends AuthorizedUserAction {

    private static Logger LOGGER = LogManager.getLogger(FindStaffByFirstNameAction.class);


    private static int ROWS_PER_PAGE = 5;
    private static String NUMBER_REGEX = "[1-9]+";
    private static String FIRSTNAMEATTR = "firstnameParameter";

    public FindStaffByFirstNameAction() {
        allowedRoles.add(Role.ADMINISTRATOR);
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response)
            throws PersistentException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("authorizedUser");
            if (user != null && allowedRoles.contains(user.getUserRole())) {
                String firstnameParameter = getSearchParameter(request);
                session.setAttribute(FIRSTNAMEATTR, firstnameParameter);
                Forward forward = new Forward("/user/admin/findstaff.html?page=1");
                UserService userService = (UserService) factory.createService(DAOEnum.USER);
                UserInfoService userInfoService = (UserInfoService)
                        factory.createService(DAOEnum.USERINFO);
                firstnameParameter = "%" + firstnameParameter;
                int amountOfAllStaffByFirstName = userService.getAmountOfAllStaffByFirstName(
                        firstnameParameter);
                int amountOfPages = amountOfAllStaffByFirstName % ROWS_PER_PAGE == 0 ?
                        amountOfAllStaffByFirstName / ROWS_PER_PAGE : amountOfAllStaffByFirstName / ROWS_PER_PAGE + 1;
                forward.getAttributes().put("amountOfPages", amountOfPages);
                int pagenumber = FormParser.parsePageNumber(
                        request.getParameter("page"), amountOfPages);
                int offset = (pagenumber - 1) * ROWS_PER_PAGE;
                List<UserInfo> userInfoList = userInfoService.findAllStaffByFirstName(
                        firstnameParameter, offset, ROWS_PER_PAGE);
                List<User> userList = userService.getAllStaffByFirstName(
                        firstnameParameter, offset, ROWS_PER_PAGE);
                forward.getAttributes().put("resultUsers", userList);
                forward.getAttributes().put("resultsUserInfo", userInfoList);
                forward.getAttributes().put("paginationURL", "/user/admin/findstaffbyfirstname.html");
                return forward;
            }
        }
        LOGGER.info(String.format("%s - attempted to access %s and failed",
                request.getRemoteAddr(), request.getRequestURI()));
        throw new PersistentException("forbiddenAccess");
    }

    private String getSearchParameter(HttpServletRequest request) {
        String firstnameParameter = request.getParameter(
                FIRSTNAMEATTR);
        if (firstnameParameter == null) {
            HttpSession session = request.getSession(false);
            firstnameParameter = (String) session.getAttribute(FIRSTNAMEATTR);
        }
        return firstnameParameter;
    }

}

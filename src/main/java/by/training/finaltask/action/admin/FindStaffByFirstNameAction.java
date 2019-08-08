package by.training.finaltask.action.admin;

import by.training.finaltask.action.AuthorizedUserAction;
import by.training.finaltask.action.Pagination;
import by.training.finaltask.dao.mysql.DAOEnum;
import by.training.finaltask.entity.Role;
import by.training.finaltask.entity.User;
import by.training.finaltask.entity.UserInfo;
import by.training.finaltask.exception.PersistentException;
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
                UserService userService = (UserService) factory.createService(DAOEnum.USER);
                UserInfoService userInfoService = (UserInfoService)
                        factory.createService(DAOEnum.USERINFO);
                Pagination pagination = new Pagination(userService.getAmountOfAllStaffByFirstName(
                        "%" + firstnameParameter + "%"), ROWS_PER_PAGE, request.getParameter("page"));
                Forward forward = new Forward("/user/admin/findstaff.html?page=1");
                forward.getAttributes().put("amountOfPages", pagination.getAmountOfPages());
                List<UserInfo> userInfoList = userInfoService.findAllStaffByFirstName(
                        "%" + firstnameParameter + "%", pagination.getOffset(), ROWS_PER_PAGE);
                List<User> userList = userService.getAllStaffByFirstName(
                        "%" + firstnameParameter + "%", pagination.getOffset(), ROWS_PER_PAGE);
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

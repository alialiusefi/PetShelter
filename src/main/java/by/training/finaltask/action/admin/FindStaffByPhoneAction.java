package by.training.finaltask.action.admin;

import by.training.finaltask.action.AuthorizedUserAction;
import by.training.finaltask.action.Pagination;
import by.training.finaltask.dao.mysql.DAOEnum;
import by.training.finaltask.entity.Role;
import by.training.finaltask.entity.User;
import by.training.finaltask.entity.UserInfo;
import by.training.finaltask.exception.InvalidFormDataException;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.service.serviceinterface.UserInfoService;
import by.training.finaltask.service.serviceinterface.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class FindStaffByPhoneAction extends AuthorizedUserAction {

    private static final String CONTACT_REGEX = "^\\+[0-9]{1,15}$";
    private static int ROWS_PER_PAGE = 5;
    private static String NUMBER_REGEX = "[1-9]+";
    private static Logger LOGGER = LogManager.getLogger(FindStaffByPhoneAction.class);
    private static String PHONEATTR = "phoneParameter";

    public FindStaffByPhoneAction() {
        this.allowedRoles.add(Role.ADMINISTRATOR);
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response)
            throws PersistentException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User authUser = (User) session.getAttribute("authorizedUser");
            if (authUser != null && allowedRoles.contains(authUser.getUserRole())) {
                String phoneParameter = getSearchParameter(request);
                session.setAttribute(PHONEATTR, phoneParameter);
                long phone;
                try {
                    phone = validatePhone(phoneParameter);
                } catch (InvalidFormDataException e) {
                    Forward forward = new Forward("/user/admin/findstaff.html?page=1");
                    forward.getAttributes().put("message", "incorrectNumberFormat");
                    return forward;
                }
                UserService userService = (UserService) factory.
                        createService(DAOEnum.USER);
                UserInfoService userInfoService = (UserInfoService)
                        factory.createService(DAOEnum.USERINFO);
                Pagination pagination = new Pagination(userService.getAmountOfAllStaffByPhone(
                        phone), ROWS_PER_PAGE, request.getParameter("page"));
                Forward forward = new Forward("/user/admin/findstaff.html?page="
                        + pagination.getPageNumber());
                forward.getAttributes().put("amountOfPages", pagination.getAmountOfPages());
                List<UserInfo> userInfoList = userInfoService.findAllStaffByPhone(
                        phone, pagination.getOffset(), ROWS_PER_PAGE);
                List<User> userList = userService.getAllStaffByPhone(
                        phone, pagination.getOffset(), ROWS_PER_PAGE);
                forward.getAttributes().put("resultUsers", userList);
                forward.getAttributes().put("resultsUserInfo", userInfoList);
                forward.getAttributes().put("paginationURL", "/user/admin/findstaffbyphone.html");
                return forward;
            } else {
                return new Forward("login.html", true);
            }
        }
        LOGGER.info(String.format("%s - attempted to access %s and failed",
                request.getRemoteAddr(), request.getRequestURI()));
        throw new PersistentException("forbiddenAccess");
    }

    private long validatePhone(String phoneStr) throws InvalidFormDataException {
        if (phoneStr != null && phoneStr.matches(CONTACT_REGEX)) {
            String phonewithoutplus = phoneStr.substring(1);
            return Long.parseLong(phonewithoutplus);
        }
        throw new InvalidFormDataException("incorrectNumberFormat");
    }

    private String getSearchParameter(HttpServletRequest request) {
        String firstnameParameter = request.getParameter(
                PHONEATTR);
        if (firstnameParameter == null) {
            HttpSession session = request.getSession(false);
            firstnameParameter = (String) session.getAttribute(PHONEATTR);
        }
        return firstnameParameter;
    }

}

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

public class FindStaffAction extends AuthorizedUserAction {

    private static Logger logger = LogManager.getLogger(FindStaffByPhoneAction.class);

    private static int ROWCOUNT = 5;
    private static String NUMBER_REGEX = "[1-9]+";

    public FindStaffAction() {
        allowedRoles.add(Role.ADMINISTRATOR);
    }

    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response)
            throws PersistentException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("authorizedUser");
            if (user != null && allowedRoles.contains(user.getUserRole())) {
                UserService userService = (UserService)
                        factory.createService(DAOEnum.USER);
                UserInfoService userInfoService = (UserInfoService)
                        factory.createService(DAOEnum.USERINFO);
                @SuppressWarnings("unchecked")
                List<User> userList = (List<User>) request.getAttribute(
                        "resultUsers");
                @SuppressWarnings("unchecked")
                List<UserInfo> userInfoList = (List<UserInfo>) request.getAttribute(
                        "resultsUserInfo");
                if (userList == null && userInfoList == null) {
                    Pagination pagination = new Pagination(userService.getAmountOfAllStaff(),
                            ROWCOUNT, request.getParameter("page"));
                    request.setAttribute("amountOfPages", pagination.getAmountOfPages());
                    userList = userService.getAllStaff(pagination.getOffset(), ROWCOUNT);
                    request.setAttribute("resultUsers", userList);
                    userInfoList = userInfoService.findAllStaff(pagination.getOffset(), ROWCOUNT);
                    request.setAttribute("resultsUserInfo", userInfoList);
                    request.setAttribute("paginationURL", "/user/admin/findstaff.html");
                }
                return null;
            } else {
                return new Forward("/login.html", true);
            }
        }
        throw new PersistentException("forbiddenAccess");
    }
}

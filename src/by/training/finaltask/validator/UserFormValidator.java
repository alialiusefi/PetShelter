package by.training.finaltask.validator;

import by.training.finaltask.entity.Role;
import by.training.finaltask.entity.User;
import by.training.finaltask.exception.InvalidFormDataException;
import by.training.finaltask.service.UserServiceImpl;

import java.util.List;

public class UserFormValidator implements FormValidator {
    //todo: to accept russian lang too
    private static final int USERNAME = 0;
    private static final int PASSWORD = 1;
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9]{4,16}$";
    private static final String PASSWORD_REGEX = "^[a-zA-Z0-9]{6,32}$";

    @Override
    public User validate(List<String> userParameters) throws InvalidFormDataException {
        if (userParameters != null) {
            String username = userParameters.get(USERNAME);
            String password = userParameters.get(PASSWORD);
            if (username != null) {
                if(password != null)
                {
                    if (!username.isEmpty() && username.matches(USERNAME_REGEX)) {
                        if (!password.isEmpty() && password.matches(PASSWORD_REGEX)) {
                            return new User(
                                    null,
                                    username,
                                    UserServiceImpl.md5(password),
                                    Role.GUEST
                            );
                        } else {
                            throw new InvalidFormDataException("incorrectPasswordFormat");
                        }
                    } else {
                        throw new InvalidFormDataException("incorrectUsernameFormat");
                    }
                } else {
                    throw new InvalidFormDataException("incorrectPasswordFormat");
                }
            } else {
                throw new InvalidFormDataException("incorrectUsernameFormat");
            }
        }
        return null;
    }

}




package by.training.finaltask.parser;

import by.training.finaltask.action.Action;
import by.training.finaltask.entity.UserInfo;
import by.training.finaltask.exception.InvalidFormDataException;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Parses Parameters from form to create UserInfo.
 */
public final class UserInfoFormParser extends FormParser<UserInfo> {

    private static final int EMAIL = 0;
    private static final int FIRSTNAME = 1;
    private static final int LASTNAME = 2;
    private static final int DATEOFBIRTH = 3;
    private static final int ADDRESS = 4;
    private static final int CONTACTNUMBER = 5;
    private static final String EMAIL_REGEX = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)" +
            "(\\.\\w+(\\.\\w+)?[^.\\W])$";
    private static final String NAME_REGEX = "^[a-zA-Z]+$";
    private static final String CONTACT_REGEX = "^\\+[0-9]{1,15}$";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public UserInfo parse(Action action, List<String> userInfoParameters)
            throws InvalidFormDataException {
        if (!userInfoParameters.isEmpty() &&
                !userInfoParameters.contains(null) &&
                !userInfoParameters.contains("")) {
            String email = userInfoParameters.get(EMAIL);
            if (email != null && email.matches(EMAIL_REGEX)) {
                String firstname = userInfoParameters.get(FIRSTNAME);
                if (firstname != null && firstname.matches(NAME_REGEX)) {
                    String lastname = userInfoParameters.get(LASTNAME);
                    if (lastname != null && lastname.matches(NAME_REGEX)) {
                        String dateofbirth = userInfoParameters.get(DATEOFBIRTH);
                        GregorianCalendar dateofbirthgreg =
                                FormParser.parseDate(DATE_FORMAT, dateofbirth);

                        Calendar current = GregorianCalendar.getInstance();
                        if (dateofbirthgreg.compareTo(current) > 0) {
                            throw new InvalidFormDataException("incorrectBirthDateFormat");
                        }
                        String address = userInfoParameters.get(ADDRESS);
                        if (address != null) {
                            String contactnumber = userInfoParameters.get(CONTACTNUMBER);
                            if (contactnumber != null && contactnumber.matches(CONTACT_REGEX)) {
                                String contactNumberNoSymbols = removePhoneFormatSymbols(contactnumber);
                                long number = Long.parseLong(contactNumberNoSymbols);
                                return new UserInfo(
                                        null,
                                        email,
                                        firstname,
                                        lastname,
                                        dateofbirthgreg,
                                        address,
                                        number
                                );
                            } else {
                                throw new InvalidFormDataException("incorrectNumberFormat");
                            }
                        }
                    } else {
                        throw new InvalidFormDataException("incorrectLastNameFormat");
                    }
                } else {
                    throw new InvalidFormDataException("incorrectFirstNameFormat");
                }
            } else {
                throw new InvalidFormDataException("incorrectEmailFormat");
            }
        }
        throw new InvalidFormDataException("fillAllFields");
    }

    private String removePhoneFormatSymbols(String contactNumber) {
        StringBuffer buffer = new StringBuffer(contactNumber);
        int plusPosition = buffer.indexOf("+");
        buffer.deleteCharAt(plusPosition);
        return buffer.toString();
    }
}

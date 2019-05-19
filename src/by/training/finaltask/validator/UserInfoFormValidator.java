package by.training.finaltask.validator;

import by.training.finaltask.entity.UserInfo;
import by.training.finaltask.exception.InvalidFormDataException;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class UserInfoFormValidator implements FormValidator {

    private static final int EMAIL = 0;
    private static final int FIRSTNAME = 1;
    private static final int LASTNAME = 2;
    private static final int DATEOFBIRTH = 3;
    private static final int ADDRESS = 4;
    private static final int CONTACTNUMBER = 5;
    private static final String EMAIL_REGEX = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$";
    private static final String NAME_REGEX = "^[a-zA-Z]+$";
    private static final String CONTACT_REGEX = "^\\+[1-9]{1,3}-[0-9]{1,3}-[0-9]{1,10}$";

    @Override
    public UserInfo validate(List<String> userInfoParameters) throws InvalidFormDataException {
        if (userInfoParameters != null) {
            String email = userInfoParameters.get(EMAIL);
            if (email != null && email.matches(EMAIL_REGEX)) {
                String firstname = userInfoParameters.get(FIRSTNAME);
                if (firstname != null && firstname.matches(NAME_REGEX)) {
                    String lastname = userInfoParameters.get(LASTNAME);
                    if(lastname != null && lastname.matches(NAME_REGEX))
                    {
                        String dateofbirth = userInfoParameters.get(DATEOFBIRTH);
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date;
                        try {
                            date = dateFormat.parse(dateofbirth);
                        } catch (ParseException e) {
                            date = new Date(2000,1,1);
                        }
                        GregorianCalendar dateofbirthgreg = new GregorianCalendar();
                        dateofbirthgreg.setTime(date);
                        Calendar current = GregorianCalendar.getInstance();
                        if(dateofbirthgreg.compareTo(current) > 0)
                        {
                            throw new InvalidFormDataException("incorrectBirthDateFormat");
                        }
                        String address = userInfoParameters.get(ADDRESS);
                        if(address != null)
                        {
                            String contactnumber = userInfoParameters.get(CONTACTNUMBER);
                            if(contactnumber != null && contactnumber.matches(CONTACT_REGEX))
                            {
                                String contactNumberNoSymbols = removePhoneFormatSymbols(contactnumber);
                                Long number = Long.parseLong(contactNumberNoSymbols);
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
                    }
                    else{
                        throw new InvalidFormDataException("incorrectLastNameFormat") ;
                    }
                } else {
                    throw new InvalidFormDataException("incorrectFirstNameFormat");
                }
            } else {
                throw new InvalidFormDataException("incorrectEmailFormat");
            }
        }

        return null;
    }
    private String removePhoneFormatSymbols(String contactNumber)
    {
        StringBuffer buffer = new StringBuffer(contactNumber);
        int plusPosition = buffer.indexOf("+");
        buffer.deleteCharAt(plusPosition);
        int dashPosition1 = buffer.indexOf("-");
        buffer.deleteCharAt(dashPosition1);
        int dashPosition2 = buffer.indexOf("-",dashPosition1 + 1);
        buffer.deleteCharAt(dashPosition2);
        return buffer.toString();
    }
}

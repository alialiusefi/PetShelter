package by.training.finaltask.service;

import by.training.finaltask.dao.AdoptionDAO;
import by.training.finaltask.dao.PetDAO;
import by.training.finaltask.dao.mysql.DAOEnum;
import by.training.finaltask.entity.Adoption;
import by.training.finaltask.entity.Pet;
import by.training.finaltask.entity.PetStatus;
import by.training.finaltask.exception.InvalidFormDataException;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.service.serviceinterface.AdoptionService;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public final class AdoptionServiceImpl extends ServiceImpl implements AdoptionService {

    public AdoptionServiceImpl(Connection aliveConnection) {
        super(aliveConnection);
    }

    private static final String REVERSEDATE = "adoptionDatesStartMoreThanEnd";
    @Override
    public Adoption get(int adoptionID) throws PersistentException {
        try {
            connection.setAutoCommit(false);
            AdoptionDAO dao = (AdoptionDAO) createDao(DAOEnum.ADOPTION);
            Adoption adoption = dao.get(adoptionID);
            commit();
            connection.setAutoCommit(true);
            return adoption;
        } catch (SQLException e) {
            rollback();
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Adoption> getAll(int offset, int rowcount) throws PersistentException {
        try {
            connection.setAutoCommit(false);
            AdoptionDAO dao = (AdoptionDAO) createDao(DAOEnum.ADOPTION);
            List<Adoption> adoptions = dao.getAll(offset, rowcount);
            commit();
            connection.setAutoCommit(true);
            return adoptions;
        } catch (SQLException e) {
            rollback();
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Adoption> getAllBetweenDates(GregorianCalendar start, GregorianCalendar end, int offset, int rowcount) throws PersistentException, InvalidFormDataException {
        if(start.compareTo(end) > 0)
        {
            throw new InvalidFormDataException();
        }
        try {
            connection.setAutoCommit(false);
            AdoptionDAO dao = (AdoptionDAO) createDao(DAOEnum.ADOPTION);
            List<Adoption> adoptions = dao.getAllBetweenDates(start,end, offset, rowcount);
            commit();
            connection.setAutoCommit(true);
            return adoptions;
        } catch (SQLException e) {
            rollback();
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Adoption> getAllPetName(String petName, int offset, int rowcount)
            throws PersistentException {
        try {
            connection.setAutoCommit(false);
            AdoptionDAO dao = (AdoptionDAO) createDao(DAOEnum.ADOPTION);
            List<Adoption> adoptions = dao.getAllPetName(petName, offset, rowcount);
            commit();
            connection.setAutoCommit(true);
            return adoptions;
        } catch (SQLException e) {
            rollback();
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Adoption> getAllCurrentUser(int userID, int offset, int rowcount) throws PersistentException {
        try {
            connection.setAutoCommit(false);
            AdoptionDAO dao = (AdoptionDAO) createDao(DAOEnum.ADOPTION);
            List<Adoption> adoptions = dao.getAllCurrentUser(userID,offset, rowcount);
            commit();
            connection.setAutoCommit(true);
            return adoptions;
        } catch (SQLException e) {
            rollback();
            throw new PersistentException(e);
        }
    }

    @Override
    public int getAllCountCurrentUser(int userID) throws PersistentException {
        try {
            connection.setAutoCommit(false);
            AdoptionDAO dao = (AdoptionDAO) createDao(DAOEnum.ADOPTION);
            int res = dao.getAllCountCurrentUser(userID);
            commit();
            connection.setAutoCommit(true);
            return res;
        } catch (SQLException e) {
            rollback();
            throw new PersistentException(e);
        }
    }

    @Override
    public int getCountPetName(String petName)
            throws PersistentException {
        try {
            connection.setAutoCommit(false);
            AdoptionDAO dao = (AdoptionDAO) createDao(DAOEnum.ADOPTION);
            int res = dao.getCountPetName(petName);
            commit();
            connection.setAutoCommit(true);
            return res;
        } catch (SQLException e) {
            rollback();
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Adoption> getAllPetNameCurrentUser(int userID, String petName, int offset,
                                                   int rowcount) throws PersistentException {
        try {
            connection.setAutoCommit(false);
            AdoptionDAO dao = (AdoptionDAO) createDao(DAOEnum.ADOPTION);
            List<Adoption> adoptions = dao.getAllPetNameCurrentUser(userID,petName,offset, rowcount);
            commit();
            connection.setAutoCommit(true);
            return adoptions;
        } catch (SQLException e) {
            rollback();
            throw new PersistentException(e);
        }
    }

    @Override
    public int getCountPetNameCurrentUser(int userID, String petName)
            throws PersistentException {
        try {
            connection.setAutoCommit(false);
            AdoptionDAO dao = (AdoptionDAO) createDao(DAOEnum.ADOPTION);
            int res = dao.getCountPetNameCurrentUser(petName,userID);
            commit();
            connection.setAutoCommit(true);
            return res;
        } catch (SQLException e) {
            rollback();
            throw new PersistentException(e);
        }
    }

    @Override
    public List<Adoption> getAllBetweenDatesCurrentUser(int userID, GregorianCalendar start, GregorianCalendar end, int offset, int rowcount) throws PersistentException, InvalidFormDataException {
        if(start.compareTo(end) > 0)
        {
            throw new InvalidFormDataException(REVERSEDATE);
        }
        try {
            connection.setAutoCommit(false);
            AdoptionDAO dao = (AdoptionDAO) createDao(DAOEnum.ADOPTION);
            List<Adoption> adoptions = dao.getAllBetweenDatesCurrentUser(userID,start,end,
                    offset,rowcount);
            commit();
            connection.setAutoCommit(true);
            return adoptions;
        } catch (SQLException e) {
            rollback();
            throw new PersistentException(e);
        }
    }

    @Override
    public int getAllCount() throws PersistentException {
        try {
            connection.setAutoCommit(false);
            AdoptionDAO dao = (AdoptionDAO) createDao(DAOEnum.ADOPTION);
            int res = dao.getAllCount();
            commit();
            connection.setAutoCommit(true);
            return res;
        } catch (SQLException e) {
            rollback();
            throw new PersistentException(e);
        }
    }

    @Override
    public int getCountBetweenDates(GregorianCalendar start, GregorianCalendar end)
            throws PersistentException, InvalidFormDataException {
        if(start.compareTo(end) > 0)
        {
            throw new InvalidFormDataException(REVERSEDATE);
        }
        try {
            connection.setAutoCommit(false);
            AdoptionDAO dao = (AdoptionDAO) createDao(DAOEnum.ADOPTION);
            int res = dao.getCountBetweenDates(start,end);
            commit();
            connection.setAutoCommit(true);
            return res;
        } catch (SQLException e) {
            rollback();
            throw new PersistentException(e);
        }
    }

    @Override
    public int getCountBetweenDatesCurrentUser(int userID, GregorianCalendar start, GregorianCalendar end)
            throws PersistentException, InvalidFormDataException {
        if(start.compareTo(end) > 0)
        {
            throw new InvalidFormDataException(REVERSEDATE);
        }
        try {
            connection.setAutoCommit(false);
            AdoptionDAO dao = (AdoptionDAO) createDao(DAOEnum.ADOPTION);
            int res = dao.getCountBetweenDatesCurrentUser(userID,start,end);
            commit();
            connection.setAutoCommit(true);
            return res;
        } catch (SQLException e) {
            rollback();
            throw new PersistentException(e);
        }
    }

    @Override
    public Integer adopt(Adoption adoption) throws PersistentException, InvalidFormDataException {
        try {
            connection.setAutoCommit(false);
            AdoptionDAO dao = (AdoptionDAO) createDao(DAOEnum.ADOPTION);
            validateDate(adoption);
            isOverlapping(adoption);
            int res = dao.add(adoption);
            updatePetToAdopted(adoption);
            commit();
            connection.setAutoCommit(true);
            return res;
        } catch (SQLException e) {
            rollback();
            throw new PersistentException(e);
        }
    }

    @Override
    public void updateAdoption(Adoption adoption) throws PersistentException,InvalidFormDataException {
        try {
            connection.setAutoCommit(false);
            AdoptionDAO dao = (AdoptionDAO) createDao(DAOEnum.ADOPTION);
            validateDate(adoption);
            isOverlappingExceptItself(adoption);
            dao.update(adoption);
            updatePetToAdopted(adoption);
            commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            rollback();
            throw new PersistentException(e);
        }
    }

    @Override
    public void deleteAdoption(int adoptionID) throws InvalidFormDataException, PersistentException {
        try {
            AdoptionDAO dao = (AdoptionDAO) createDao(DAOEnum.ADOPTION);
            isExpired(adoptionID);
            connection.setAutoCommit(false);
            dao.delete(adoptionID);
            commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            rollback();
            throw new PersistentException(e);
        }
    }

    private void validateDate(Adoption adoption) throws InvalidFormDataException {
        Calendar calendar = Calendar.getInstance();
        if (adoption.getAdoptionEnd() != null) {
            if (adoption.getAdoptionStart().compareTo(adoption.getAdoptionEnd()) > 0) {
                throw new InvalidFormDataException("incorrectDateFormat");
            }
            if (adoption.getAdoptionEnd().compareTo(calendar) < 0) {
                throw new InvalidFormDataException("endDateIsLessThanToday");
            }
        }
    }

    private void isOverlapping(Adoption adoption)
            throws InvalidFormDataException, PersistentException {
        AdoptionDAO dao = (AdoptionDAO) createDao(DAOEnum.ADOPTION);
        int count;
        if (adoption.getAdoptionEnd() != null) {
            count = dao.getCountByPetIDandDateNotNull(adoption.getPetID(),
                    adoption.getAdoptionStart(), adoption.getAdoptionEnd());
        } else {
            count = dao.getCountByPetIDandDateNull(adoption.getPetID(),
                    adoption.getAdoptionStart());
        }
        if (count != 0) {
            throw new InvalidFormDataException("petAlreadyAdopted");
        }
    }

    private void isOverlappingExceptItself(Adoption adoption)
            throws InvalidFormDataException, PersistentException
    {
        AdoptionDAO dao = (AdoptionDAO) createDao(DAOEnum.ADOPTION);
        int count;
        if (adoption.getAdoptionEnd() != null) {
            count = dao.getCountByPetIDandDateNotNull(adoption.getPetID(),
                    adoption.getAdoptionStart(), adoption.getAdoptionEnd());
        } else {
            count = dao.getCountByPetIDandDateNull(adoption.getPetID(),
                    adoption.getAdoptionStart());
        }
        if (count > 1) {
            throw new InvalidFormDataException("petAlreadyAdopted");
        }
    }

    private void updatePetToAdopted(Adoption adoption) throws PersistentException {
        LocalDate date = LocalDate.now();
        GregorianCalendar calendar = new GregorianCalendar(
                date.getYear(),
                date.getMonthValue() - 1,
                date.getDayOfMonth());
        if (calendar.compareTo(adoption.getAdoptionStart()) == 0) {
            PetDAO dao = (PetDAO) createDao(DAOEnum.PET);
            Pet pet = dao.get(adoption.getPetID());
            pet.setStatus(PetStatus.ADOPTED);
            dao.update(pet);
        }
    }

    private void isExpired(int adoptionID) throws InvalidFormDataException, PersistentException{
        Adoption adoption = get(adoptionID);
        Calendar calendar = Calendar.getInstance();
        if(adoption.getAdoptionStart().compareTo(calendar) < 0)
        {
            throw new InvalidFormDataException("cannotDeleteExpiredAdoption");
        }
    }

}

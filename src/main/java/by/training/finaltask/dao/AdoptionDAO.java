package by.training.finaltask.dao;

import by.training.finaltask.entity.Adoption;
import by.training.finaltask.exception.PersistentException;

import java.util.GregorianCalendar;
import java.util.List;

/**
 * Data Access Object Interface
 */
public interface AdoptionDAO extends DAO<Adoption> {

    /**
     * Gets adoption from DB using petID column.
     * @param petID petId to search for.
     * @return returns Adoption.
     * @throws PersistentException
     */
    Adoption get(Integer petID) throws PersistentException;

    /**
     * @param offset   rows that'll be skipped.
     * @param rowcount amount of rows.
     * @return
     * @throws PersistentException
     */
    List<Adoption> getAll(int offset, int rowcount) throws PersistentException;

    /**
     * Get Adoptions that has petID.
     *
     * @param petID    petID parameter.
     * @param offset   offset.
     * @param rowcount rowcount.
     * @return returns list of adoptions.
     * @throws PersistentException
     */
    List<Adoption> getAllByPetID(Integer petID, int offset, int rowcount) throws PersistentException;

    /**
     * Gets all adoptions that are between two dates.
     * ---|start|-[ adoptionStart ]-|end|------[ adoptionEnd ]
     * [ adoptionStart ]---|start|-[ adoptionEnd ]-|end|------
     * ---|start|-[ adoptionStart ]------[ adoptionEnd ]---|end|
     *
     * @param start date where adoption starts.
     * @param end date where adoption ends.
     * @param offset offset.
     * @param rowcount rowcount.
     * @return returns all adoptions that are located between two dates given.
     * @throws PersistentException
     */
    List<Adoption> getAllBetweenDates(GregorianCalendar start, GregorianCalendar end,
                                      int offset, int rowcount) throws PersistentException;

    /**
     * Gets all adoptions that are between two dates and that belong to the userID.
     *     ---|start|-[ adoptionStart ]-|end|------[ adoptionEnd ]
     *     [ adoptionStart ]---|start|-[ adoptionEnd ]-|end|------
     *     ---|start|-[ adoptionStart ]------[ adoptionEnd ]---|end|
     *
     * @param userID userID that adoption belongs to.
     * @param start date where adoption starts.
     * @param end date where adoption ends.
     * @param offset offset.
     * @param rowcount rowcount.
     * @return returns all adoptions that are located between two dates given.
     * @throws PersistentException
     */
    List<Adoption> getAllBetweenDatesCurrentUser(int userID, GregorianCalendar start,
                                                 GregorianCalendar end, int offset, int rowcount)
            throws PersistentException;

    /**
     *
     * @param petName petName that adoption belongs to.
     * @param offset offset.
     * @param rowcount rowcount.
     * @return returns all adoptions that belongs to the petname given
     * @throws PersistentException
     */
    List<Adoption> getAllPetName(String petName, int offset, int rowcount)
            throws PersistentException;

    List<Adoption> getAllPetNameCurrentUser(int userID, String petName, int offset, int rowcount)
            throws PersistentException;

    List<Adoption> getAllCurrentUser(int userID, int offset, int rowcount) throws PersistentException;

    int getAllCountCurrentUser(int userID) throws PersistentException;

    int getCountPetName(String petName) throws PersistentException;

    int getCountPetNameCurrentUser(String petName, int userID) throws PersistentException;

    int getCountBetweenDatesCurrentUser(int userID, GregorianCalendar start,
                                        GregorianCalendar end) throws PersistentException;

    int getCountBetweenDates(GregorianCalendar start, GregorianCalendar end)
            throws PersistentException;

    /**
     *
     * @param petID
     * @param start
     * @param end
     * @return
     * @throws PersistentException
     */
    int getCountByPetIDandDateNotNull(int petID, GregorianCalendar start,
                                      GregorianCalendar end) throws PersistentException;

    /**
     *
     * @param petID
     * @param start
     * @return
     * @throws PersistentException
     */
    int getCountByPetIDandDateNull(int petID, GregorianCalendar start) throws PersistentException;

    int getAllCount() throws PersistentException;

    boolean delete(int adoptionID) throws PersistentException;
}

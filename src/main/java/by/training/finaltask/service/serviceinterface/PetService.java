package by.training.finaltask.service.serviceinterface;

import by.training.finaltask.entity.Pet;
import by.training.finaltask.entity.PetStatus;
import by.training.finaltask.exception.PersistentException;

import java.util.GregorianCalendar;
import java.util.List;

public interface PetService extends Service {

    Pet get(int ID) throws PersistentException;

    List<Pet> getAllByPetName(PetStatus status, String petName, int offset, int rowcount) throws PersistentException;

    int getAllCountByPetName(PetStatus status, String petName) throws PersistentException;

    List<Pet> getAllByShelter(PetStatus status, int shelterID, int offset, int rowcount) throws PersistentException;

    int getAllCountByShelter(PetStatus status, int shelterID) throws PersistentException;

    List<Pet> getAllByBreed(PetStatus status, int breedID, int offset, int rowcount) throws PersistentException;

    int getAllCountByBreed(PetStatus status, int breedID) throws PersistentException;

    List<Pet> getAllByBirthDate(int relation, PetStatus status,
                                GregorianCalendar calendar, int offset, int rowcount)
            throws PersistentException;

    int getAllCountByBirthDate(int relation, PetStatus status, GregorianCalendar calendar)
    throws PersistentException;

    List<Pet> getAllByStatus(PetStatus status, int offset, int rowcount) throws PersistentException;

    int getAllCountByStatus(PetStatus status) throws PersistentException;

    Integer add(Pet pet) throws PersistentException;

    void update(Pet pet) throws PersistentException;

    void delete(int ID) throws PersistentException;
}

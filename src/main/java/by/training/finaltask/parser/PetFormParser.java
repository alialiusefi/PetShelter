package by.training.finaltask.parser;

import by.training.finaltask.action.Action;
import by.training.finaltask.dao.mysql.DAOEnum;
import by.training.finaltask.entity.Breed;
import by.training.finaltask.entity.Pet;
import by.training.finaltask.entity.PetStatus;
import by.training.finaltask.entity.Shelter;
import by.training.finaltask.exception.InvalidFormDataException;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.service.serviceinterface.BreedService;
import by.training.finaltask.service.serviceinterface.ShelterService;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Parses Parameters from form to create Pet.
 */
public final class PetFormParser extends FormParser<Pet> {

    private static final int PICTURE_PATH = 0;
    private static final int PET_NAME = 1;
    private static final int PET_WEIGHT = 2;
    private static final int DATE_OF_BIRTH = 3;
    private static final int DATE_SHELTERED = 4;
    private static final int SHELTER = 5;
    private static final int BREED = 6;
    private static final int PET_STATUS = 7;
    private static final String PET_NAME_REGEX = "^[a-zA-Z]+$";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String WEIGHT_REGEX = "^[0-9]+(.[0-9]+)?$";

    @Override
    public Pet parse(Action action, List<String> parameters) throws
            InvalidFormDataException, PersistentException {
        if (!parameters.isEmpty() && !parameters.contains(null)
                && !parameters.contains("")) {
            Blob pictureBlob = getPictureBlob(parameters.get(PICTURE_PATH));
            String petName = parameters.get(PET_NAME);
            if (petName.matches(PET_NAME_REGEX)) {
                String petWeightStr = parameters.get(PET_WEIGHT);
                if (petWeightStr.matches(WEIGHT_REGEX)) {
                    double petWeight = Double.parseDouble(petWeightStr);
                    GregorianCalendar dateOfBirthGreg = FormParser.parseDate(DATE_FORMAT,
                            parameters.get(DATE_OF_BIRTH));
                    GregorianCalendar dateShelteredGreg = FormParser.parseDate(
                            DATE_FORMAT, parameters.get(DATE_SHELTERED));
                    Shelter shelter = validateShelter(action,
                            parameters.get(SHELTER));
                    Breed breed = validateBreed(action, parameters.get(BREED));
                    PetStatus status = PetStatus.valueOf(parameters.get(PET_STATUS));
                    return new Pet(
                            0,
                            petName,
                            pictureBlob,
                            dateOfBirthGreg,
                            petWeight,
                            dateShelteredGreg,
                            shelter.getId(),
                            breed.getId(),
                            status);
                }
                throw new InvalidFormDataException("incorrectWeightFormat");
            }
            throw new InvalidFormDataException("incorrectPetNameFormat");
        }
        throw new InvalidFormDataException("fillAllFields");
    }

    /**
     * @param action    action to get service.
     * @param shelterID shelterID to check if it exists.
     * @return returns shelter object.
     * @throws InvalidFormDataException incorrect shelterID.
     * @throws PersistentException      database error.
     */
    private Shelter validateShelter(Action action, String shelterID) throws
            InvalidFormDataException, PersistentException {
        ShelterService shelterService = (ShelterService)
                action.factory.createService(DAOEnum.SHELTER);
        int id = Integer.parseInt(shelterID);
        Shelter shelter = shelterService.getByID(id);
        if (shelter != null) {
            return shelter;
        } else {
            throw new InvalidFormDataException("incorrectShelter");
        }
    }

    /**
     * @param action  action to get service.
     * @param breedID to check if it exists.
     * @return returns breed object.
     * @throws InvalidFormDataException incorrect breedID.
     * @throws PersistentException      database error.
     */
    private Breed validateBreed(Action action, String breedID)
            throws InvalidFormDataException, PersistentException {
        BreedService breedService = (BreedService) action.factory
                .createService(DAOEnum.BREED);
        int id = Integer.parseInt(breedID);
        Breed breed = breedService.getByID(id);
        if (breed != null) {
            return breed;
        }
        throw new InvalidFormDataException("incorrectBreed");

    }


    private Blob getPictureBlob(String picturePath)
            throws InvalidFormDataException {
        File pictureFile = new File(picturePath);
        try {
            byte[] pictureBytes = Files.readAllBytes(pictureFile.toPath());
            if (pictureBytes.length == 0) {
                return null;
            }
            return new SerialBlob(pictureBytes);
        } catch (IOException | SQLException e) {
            throw new InvalidFormDataException("fileUploadError");
        }
    }


}

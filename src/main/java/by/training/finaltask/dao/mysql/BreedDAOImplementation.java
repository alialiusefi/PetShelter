package by.training.finaltask.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.finaltask.dao.BreedDAO;
import by.training.finaltask.entity.Breed;
import by.training.finaltask.exception.PersistentException;

public final class BreedDAOImplementation extends BaseDAO implements BreedDAO {

	private Logger LOGGER = LogManager.getLogger(PetDAOImplementation.class);
    private static String UNSUPPORTEDOPERATION = "unsupportedOperation";

	public BreedDAOImplementation(Connection connection) {
		super(connection);
	}

	public List<Breed> getAll() throws PersistentException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				resourceBundle.getString("getAllBreeds"))) {
			List<Breed> breeds = new LinkedList<>();
			ResultSet resultset = preparedStatement.executeQuery();
			while (resultset.next()) {
				Integer id = resultset.getInt("id");
				String name = resultset.getNString("name");
				String description = resultset.getNString("description");
				String origin = resultset.getNString("origin");
				breeds.add(new Breed(id, name, description, origin));
			}
			return breeds;
		} catch (SQLException e) {
			LOGGER.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
	}

	@Override
	public Breed getByID(Integer ID) throws PersistentException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				resourceBundle.getString("getBreedByID"))) {
			preparedStatement.setInt(1, ID);
			ResultSet resultset = preparedStatement.executeQuery();
			if (resultset.next()) {
				return getBreed(resultset);
			}
			return null;
		} catch (SQLException e) {
			LOGGER.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
	}

	@Override
	public Breed get() throws PersistentException {
		throw new PersistentException(UNSUPPORTEDOPERATION);
	}

	@Override
	public int add(Breed element) throws PersistentException {
		throw new PersistentException(UNSUPPORTEDOPERATION);
	}

	@Override
	public boolean update(Breed element) throws PersistentException {
		throw new PersistentException(UNSUPPORTEDOPERATION);

	}

	@Override
	public boolean delete(Breed element) throws PersistentException {
		throw new PersistentException(UNSUPPORTEDOPERATION);

	}

	private Breed getBreed(ResultSet resultSet) throws SQLException {
		Integer id = resultSet.getInt("id");
		String breedName = resultSet.getNString("name");
		String description = resultSet.getNString("description");
		String origin = resultSet.getNString("origin");
		return new Breed(id, breedName, description, origin);
	}
}

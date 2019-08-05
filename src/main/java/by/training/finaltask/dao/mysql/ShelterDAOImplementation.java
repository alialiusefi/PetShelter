package by.training.finaltask.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.training.finaltask.dao.ShelterDAO;
import by.training.finaltask.entity.Shelter;
import by.training.finaltask.exception.PersistentException;

public final class ShelterDAOImplementation extends BaseDAO implements ShelterDAO {

	private static final Logger LOGGER = LogManager.getLogger(ShelterDAOImplementation.class);
	private static String UNSUPPORTEDOPERATION = "unsupportedOperation";

	public ShelterDAOImplementation(Connection aliveConnection) {
		super(aliveConnection);
	}

	@Override
	public List<Shelter> getAll() throws PersistentException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				resourceBundle.getString("getAllShelters"))) {
			List<Shelter> shelters = new LinkedList<>();
			ResultSet resultset = preparedStatement.executeQuery();
			while (resultset.next()) {
				int id = resultset.getInt("id");
				String name = resultset.getNString("name");
				String location = resultset.getNString("location");
				shelters.add(new Shelter(id, name, location));
			}
			return shelters;
		} catch (SQLException e) {
			LOGGER.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
	}

	@Override
	public Shelter getByID(Integer id) throws PersistentException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				resourceBundle.getString("getShelterByID"))) {
			preparedStatement.setInt(1, id);
			ResultSet resultset = preparedStatement.executeQuery();
			if (resultset.next()) {
				return getShelter(resultset);
			}
			return null;

		} catch (SQLException e) {
			LOGGER.warn(e.getMessage(), e);
			throw new PersistentException(e.getMessage(), e);
		}
	}

	@Override
	public int add(Shelter element) throws PersistentException {
		throw new PersistentException(UNSUPPORTEDOPERATION);
	}

	@Override
	public Shelter get() throws PersistentException {
		throw new PersistentException(UNSUPPORTEDOPERATION);

	}

	@Override
	public boolean update(Shelter element) throws PersistentException {
		throw new PersistentException(UNSUPPORTEDOPERATION);

	}

	@Override
	public boolean delete(Shelter element) throws PersistentException {
		throw new PersistentException(UNSUPPORTEDOPERATION);

	}

	private Shelter getShelter(ResultSet resultSet) throws SQLException {
		int shelterID = resultSet.getInt("id");
		String name = resultSet.getNString("name");
		String location = resultSet.getNString("location");
		return new Shelter(shelterID, name, location);
	}
}

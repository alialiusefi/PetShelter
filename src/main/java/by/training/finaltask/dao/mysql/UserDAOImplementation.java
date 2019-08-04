package by.training.finaltask.dao.mysql;

import by.training.finaltask.dao.UserDAO;
import by.training.finaltask.entity.Role;
import by.training.finaltask.entity.User;
import by.training.finaltask.exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public final class UserDAOImplementation extends BaseDAO implements UserDAO {

    private static final Logger LOGGER = LogManager.getLogger(UserDAOImplementation.class);
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    public UserDAOImplementation(Connection connection) {
        super(connection);
    }

    @Override
    public User get() throws PersistentException {
        return get(1);
    }

    @Override
    public User get(Integer userID) throws PersistentException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("getUserDAO"))) {
            preparedStatement.setInt(1, userID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getUser(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new PersistentException(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public User get(String username) throws PersistentException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("getUserByUserNameDAO"))) {
            preparedStatement.setNString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getUser(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new PersistentException(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public User get(String user, String pass) throws PersistentException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("getUserByUserNamePassWordDAO"))) {
            preparedStatement.setNString(1, user);
            preparedStatement.setNString(2, pass);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getUser(resultSet);
            }

        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new PersistentException(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<User> getAll(int offset, int rowcount) throws PersistentException {
        List<User> userList = new LinkedList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("getAllUserDAO"))) {
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, rowcount);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(getUser(resultSet));
            }
            return userList;
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new PersistentException(e.getMessage(), e);
        }
    }

    @Override
    public boolean delete(Integer userID) throws PersistentException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("deleteUserDAO"))) {
            preparedStatement.setInt(1, userID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new PersistentException(e.getMessage(), e);

        }
    }

    @Override
    public int add(User element) throws PersistentException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("addUserDAO"), PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setNString(1, element.getUsername());
            preparedStatement.setNString(2, element.getPassword());
            preparedStatement.setInt(3, element.getUserRole().getValue());
            preparedStatement.executeUpdate();
            ResultSet set = preparedStatement.getGeneratedKeys();
            if (set.next()) {
                return set.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new PersistentException("Couldn't add row!\n" + e.getMessage(), e);
        }
    }

    @Override
    public boolean update(User element) throws PersistentException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("updateUserDAO"))) {
            preparedStatement.setNString(1, element.getUsername());
            preparedStatement.setNString(2, element.getPassword());
            preparedStatement.setInt(3, element.getUserRole().getValue());
            preparedStatement.setInt(4, element.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new PersistentException("Couldn't update user!\n" +
                    e.getMessage(), e);
        }

    }

    @Override
    public boolean delete(User element) throws PersistentException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(resourceBundle.getString("deleteUserDAO"))) {
            preparedStatement.setInt(1, element.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new PersistentException(e.getMessage(), e);
        }
    }

    @Override
    public int getAmountOfAllStaff() throws PersistentException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("getAmountAllStaffDAO"))) {
            ResultSet res = preparedStatement.executeQuery();
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new PersistentException(e.getMessage(), e);
        }
    }

    @Override
    public List<User> getAllStaff(int offset, int rowcount) throws PersistentException {
        List<User> userList = new LinkedList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("getAllStaffDAO"))) {
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, rowcount);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(getUser(resultSet));
            }

            return userList;
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new PersistentException(e.getMessage(), e);
        }
    }

    @Override
    public List<User> getAllStaffByFirstName(String firstname, int offset, int rowcount) throws PersistentException {
        List<User> userList = new LinkedList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("getAllStaffByFirstNameDAO"))) {
            preparedStatement.setNString(1, firstname);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, rowcount);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(getUser(resultSet));
            }
            return userList;
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new PersistentException(e.getMessage(), e);
        }
    }

    @Override
    public int getAmountOfAllStaffByFirstName(String firstname) throws PersistentException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("getAmountAllStaffByFirstNameDAO"))) {
            preparedStatement.setNString(1, firstname);
            ResultSet res = preparedStatement.executeQuery();
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new PersistentException(e.getMessage(), e);
        }
    }

    @Override
    public int getAmountOfAllStaffByPhone(long phone) throws PersistentException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("getAmountAllStaffByPhoneDAO"))) {
            String phoneStr = "%" + phone;
            preparedStatement.setNString(1, phoneStr);
            ResultSet res = preparedStatement.executeQuery();
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new PersistentException(e.getMessage(), e);
        }
    }

    @Override
    public List<User> getAllStaffByPhone(long phone, int offset, int rowcount) throws PersistentException {
        List<User> userList = new LinkedList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                resourceBundle.getString("getAllStaffByPhoneDAO"))) {
            String phoneStr = "%" + phone;
            preparedStatement.setNString(1, phoneStr);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, rowcount);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userList.add(getUser(resultSet));
            }
            return userList;
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage(), e);
            throw new PersistentException(e.getMessage(), e);
        }
    }

    private User getUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(1);
        String username = resultSet.getNString(USERNAME);
        String password = resultSet.getNString(PASSWORD);
        Role role = Role.valueOf(resultSet.getInt(4));
        return new User(id, username, password, role);
    }
}

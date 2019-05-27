package by.training.finaltask.service;

import by.training.finaltask.dao.daointerface.UserInfoDAO;
import by.training.finaltask.dao.mysql.DAOEnum;
import by.training.finaltask.entity.User;
import by.training.finaltask.entity.UserInfo;
import by.training.finaltask.exception.PersistentException;
import by.training.finaltask.service.serviceinterface.UserInfoService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserInfoServiceImpl extends ServiceImpl implements UserInfoService {

    public UserInfoServiceImpl(Connection connection) {
        super(connection);
    }


    @Override
    public List<User> findAll() throws PersistentException {
        return null;
    }

    @Override
    public UserInfo findById(Integer id) throws PersistentException {
        try {
            connection.setAutoCommit(false);
            UserInfoDAO dao = (UserInfoDAO) createDao(DAOEnum.USERINFO);
            UserInfo userInfo = dao.get(id);
            commit();
            connection.setAutoCommit(true);
            return userInfo;
        } catch (SQLException e) {
            rollback();
            throw new PersistentException(e);
        }
    }

    @Override
    public Integer add(UserInfo userinfo) throws PersistentException {
        try {
            connection.setAutoCommit(false);
            UserInfoDAO dao = (UserInfoDAO) createDao(DAOEnum.USERINFO);
            int idGenerated = dao.add(userinfo);
            commit();
            connection.setAutoCommit(true);
            return idGenerated;
        } catch (SQLException e) {
            rollback();
            throw new PersistentException(e);
        }
    }

    @Override
    public void update(UserInfo userinfo) throws PersistentException {
        try {
            connection.setAutoCommit(false);
            UserInfoDAO dao = (UserInfoDAO) createDao(DAOEnum.USERINFO);
            dao.update(userinfo);
            commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            rollback();
            throw new PersistentException(e);
        }
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        try {
            connection.setAutoCommit(false);
            UserInfoDAO dao = (UserInfoDAO) createDao(DAOEnum.USERINFO);
            dao.delete(identity);
            commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            rollback();
            throw new PersistentException(e);
        }
    }
}

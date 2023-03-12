package gr.aueb.cf.schoolapp.dao;

import gr.aueb.cf.schoolapp.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.service.util.DBUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements IUserDAO {
    @Override
    public boolean isUserValid(String username, String password) {
        String hashedPassword;
        try {
            hashedPassword = getByUsername(username).get(0).getPassword();
            if (BCrypt.checkpw(password, hashedPassword)) {
                return true;
            }

        } catch (UserDAOException e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public User insert(User user) throws UserDAOException {
        String sql = "INSERT INTO USERS (USERNAME, PASSWORD) VALUES(?,?)";

        try(Connection conn = DBUtil.getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            String username = user.getUsername();
            String password = user.getPassword();

            if (username.equals("") | password.equals("")) {
                return null;
            }

            int workload = 12;
            String salt = BCrypt.gensalt(workload);
            String hashedPassword = BCrypt.hashpw(password, salt);

            p.setString(1,username);
            p.setString(2,hashedPassword);
            p.executeUpdate();
            return user;
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new UserDAOException("User was not inserted");
        }
    }

    @Override
    public User update(User user) throws UserDAOException {
        String sql = "UPDATE USERS SET USERNAME = ? , PASSWORD = ? WHERE ID = ?";

        try(Connection conn = DBUtil.getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            String username = user.getUsername();
            String password = user.getPassword();
            int id = user.getId();

            if (username.equals("") | password.equals("")) {
                return null;
            }

            int workload = 12;
            String salt = BCrypt.gensalt(workload);
            String hashedPassword = BCrypt.hashpw(password,salt);

            p.setString(1, username);
            p.setString(2, hashedPassword);
            p.setInt(3, id);
            p.executeUpdate();
            return user;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new UserDAOException("Issue in user update");
        }

    }

    @Override
    public void delete(int ID) throws UserDAOException {
        String sql = "DELETE FROM USERS WHERE ID = ?";
        ResultSet rs;

        try(Connection conn = DBUtil.getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1, ID);
            p.executeUpdate();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new UserDAOException("User was not deleted");
        }
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public List<User> getByUsername(String username) throws UserDAOException {
        String sql = "SELECT ID, USERNAME, PASSWORD FROM USERS WHERE USERNAME LIKE ?";
        ResultSet rs;
        List<User> users = new ArrayList<>();

        try(Connection conn = DBUtil.getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {

            p.setString(1, username + '%');
            rs = p.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getInt("ID"),
                        rs.getString("USERNAME"),
                        rs.getString("PASSWORD")
                );
                users.add(user);
            }
            return users;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new UserDAOException("User with lastname: " + username + " not found" );
        }

    }

    @Override
    public User getById(int ID) throws UserDAOException {
        String sql = "SELECT ID, USERNAME, PASSWORD FROM USERS WHERE ID =?";
        ResultSet rs;
        User user = null;

        try(Connection conn = DBUtil.getConnection(); PreparedStatement p = conn.prepareStatement(sql)) {
            p.setInt(1,ID);
            rs = p.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("ID"),
                        rs.getString("USERNAME"),
                        rs.getString("PASSWORD")
                );
            }
            return user;
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new UserDAOException("User was not found") ;
        }
    }

}

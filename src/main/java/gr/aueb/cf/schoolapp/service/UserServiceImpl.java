package gr.aueb.cf.schoolapp.service;

import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dao.exceptions.UserDAOException;
//import gr.aueb.cf.schoolapp.dao.UsersDAO.IUserDAO;
import gr.aueb.cf.schoolapp.dto.UserDTO;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.service.exceptions.UserNotFoundException;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements IUserService{
    private final IUserDAO userDAO;

    public UserServiceImpl(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User insertUser(UserDTO userDTO) throws UserDAOException {
        if (userDTO == null) return null;

        try {
            User user = mapUser(userDTO);
            return userDAO.insert(user);
        } catch (UserDAOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public User updateUser(UserDTO userDTO) throws UserNotFoundException, UserDAOException {
        if (userDTO == null) return null;

        try {
            if (userDAO.getById(userDTO.getId()) == null) {
                throw new UserNotFoundException("User not found");
            }
            User user = mapUser(userDTO);
            return userDAO.update(user);
        } catch (UserDAOException | UserNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void deleteUser(int ID) throws UserDAOException, UserNotFoundException {
        try {
            if (userDAO.getById(ID) == null) {
                throw new UserNotFoundException("User not found");
            }
            userDAO.delete(ID);
        } catch (UserDAOException | UserNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<User> getUserByUsername(String username) throws UserDAOException {
        List<User> users = new ArrayList<>();
        if (username == null) return users;
        try {
//            if (userDAO.getByUsername(username) == null) {
//                throw new UserNotFoundException("user not found");
//            }
            users = userDAO.getByUsername(username);
            return users;
        } catch (UserDAOException  e) {
            e.printStackTrace();
            throw e;
        }

    }

   // @Override
//    public boolean userAuthentication(String username, String Password) throws UserDAOException, UserNotFoundException {
//        return false;
//    }


    //   @Override
//    public boolean userAuthentication(String username, String password) throws UserDAOException, UserNotFoundException {
//        try {
//            if (username.equals("") | password.equals("")) throw new UserNotFoundException("User not found");
//            if (userDAO.getByUsername(username) == null) throw new UserNotFoundException("User not found");
//            if (BCrypt.checkpw(password,userDAO.getByUsername(username).getPassword())) {
//                return true;
//            }
//            return false;
//
//        } catch ( UserNotFoundException e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }

    private User mapUser(UserDTO userDTO) {
        return new User(userDTO.getId(),userDTO.getUsername(),userDTO.getPassword());
    }
}

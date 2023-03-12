package gr.aueb.cf.schoolapp.authentication;

import gr.aueb.cf.schoolapp.dao.IUserDAO;
import gr.aueb.cf.schoolapp.dao.UserDAOImpl;
import gr.aueb.cf.schoolapp.dto.UserDTO;
import gr.aueb.cf.schoolapp.model.User;

public class AuthenticationProvider {
    private static final IUserDAO userDao = new UserDAOImpl();
    private AuthenticationProvider() {};

    public static User authenticate(UserDTO userDTO) {

        if (!userDao.isUserValid(userDTO.getUsername(),userDTO.getPassword())) {
            return null;
        }

        return new User(userDTO.getId(), userDTO.getUsername(), userDTO.getPassword());
    }

}

package gr.aueb.cf.schoolapp.validation;

import gr.aueb.cf.schoolapp.dto.TeacherDTO;
import gr.aueb.cf.schoolapp.dto.UserDTO;
import gr.aueb.cf.schoolapp.model.Teacher;

public class Validator {
    private Validator() {};

    public static String validate(TeacherDTO teacherDTO) {
        if (teacherDTO.getFirstname().equals("")) {
            return "Firstname: Empty";
        }

        if (teacherDTO.getFirstname().length() < 3 || (teacherDTO.getFirstname().length() > 32)) {
            return "Firstname: lenght";
        }

        if (teacherDTO.getLastname().equals("")) {
            return "Lastname: Empty";
        }

        if (teacherDTO.getLastname().length() < 3 || (teacherDTO.getLastname().length() > 32)) {
            return "Lastname: lenght";
        }

        return "";
    }

    public static String userValidate(UserDTO userDTO) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!userDTO.getUsername().matches(emailRegex))  {
            return "ivalid email";
        }

        String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$";
        if (!userDTO.getPassword().matches(passwordRegex)) {
            return "invalid password. Password must contains at least 1 capital , 1 lower and one number.";
        }

        return "";
    }



}

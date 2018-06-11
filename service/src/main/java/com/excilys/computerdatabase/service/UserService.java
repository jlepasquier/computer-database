package com.excilys.computerdatabase.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.dao.UserDAO;
import com.excilys.computerdatabase.model.CDBException;
import com.excilys.computerdatabase.model.User;
import com.excilys.computerdatabase.validator.UserNotFoundException;

@Service("userService")
public class UserService {

    private UserDAO userDAO;
    
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    public User findByUsername(String username) throws CDBException {
        Optional<User> user = userDAO.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UserNotFoundException();
        }
    }
    
}

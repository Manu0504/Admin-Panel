package com.manu.adminpannel.serviceImpl;

import com.manu.adminpannel.entity.User;
import com.manu.adminpannel.repository.UserRepository;
import com.manu.adminpannel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user); 
    }

    @Override
    public User update(Long id, User user) {
    	
        Optional<User> existingUserOpt = userRepository.findById(id);
        
        if (existingUserOpt.isPresent()) {
        	
            User existingUser = existingUserOpt.get();
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setRole(user.getRole());
            return userRepository.save(existingUser); 
            
        }
        throw new IllegalArgumentException("User not found with id: " + id);
    }

    @Override
    public void delete(Long id) {
    	
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);  
        } else {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
        
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id); 
    }
}

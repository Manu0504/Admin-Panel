package com.manu.adminpannel.service;

import com.manu.adminpannel.entity.User;
import java.util.Optional;

public interface UserService {
	
    Optional<User> findByEmail(String email);
    User save(User user);  
    User update(Long id, User user);  
    void delete(Long id);  
    Optional<User> findById(Long id); 
    
}

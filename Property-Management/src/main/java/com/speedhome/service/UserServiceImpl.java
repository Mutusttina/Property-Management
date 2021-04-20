package com.speedhome.service;

import com.speedhome.dao.RoleRepository;
import com.speedhome.dao.UserRepository;
import com.speedhome.entity.User;
import com.speedhome.model.AddUsersRequest;
import com.speedhome.security.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository dao;
    @Autowired
    PasswordEncoder pwdEncoder;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public User addUser(AddUsersRequest request) {
        User user=new User();
        user.setEnabled(request.isEnabled());
        user.setPassword(pwdEncoder.encode(request.getPassword()));
        user.getRoles().add(roleRepository.findById(request.getRoleId()).get());
        user.setUsername(request.getUsername());
        return dao.save(user);
    }
}

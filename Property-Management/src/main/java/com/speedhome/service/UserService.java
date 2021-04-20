package com.speedhome.service;

import com.speedhome.entity.User;
import com.speedhome.model.AddUsersRequest;

import java.util.List;

public interface UserService {

    User addUser(AddUsersRequest user);
}
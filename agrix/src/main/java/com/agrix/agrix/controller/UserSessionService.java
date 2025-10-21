
package com.agrix.agrix.controller; // or .service

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class UserSessionService {

    private Set<String> loggedInUsers = new HashSet<>();

    public void userLoggedIn(String username) {
        loggedInUsers.add(username);
    }

    public void userLoggedOut(String username) {
        loggedInUsers.remove(username);
    }

    public Set<String> getAllLoggedInUsers() {
        return loggedInUsers;
    }
}

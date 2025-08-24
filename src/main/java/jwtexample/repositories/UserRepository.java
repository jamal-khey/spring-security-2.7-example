package jwtexample.repositories;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class UserRepository {
    private final List<UserDetails> APPLICATION_USER = Arrays.asList(
            new User("aa","a", Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))),
            new User("user", "password", Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")))
    );

    public UserDetails findUserByUserName(String userName){
        return APPLICATION_USER.stream()
                .filter( userDetails -> userDetails.getUsername().equals(userName))
                .findAny()
                .orElseThrow( () -> new UsernameNotFoundException("no user found"));
    }
}

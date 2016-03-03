package by.it.auth;

import java.util.ArrayList;
import java.util.List;

import by.it.model.enums.State;
import by.it.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import by.it.model.User;
import by.it.model.UserProfile;

@Service("authService")
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        User user = userService.findByUserName(userName);
        System.out.println("User : " + user);
        if (user == null) {
            System.out.println("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                State.ACTIVE.getState().equals(user.getState()), true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (UserProfile userProfile : user.getUserProfiles()) {
            System.out.println("UserProfile : " + userProfile);
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userProfile.getType()));
        }
        System.out.print("authorities :" + authorities);
        return authorities;
    }

}

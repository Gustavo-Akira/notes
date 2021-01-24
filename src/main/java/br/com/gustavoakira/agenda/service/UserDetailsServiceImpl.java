package br.com.gustavoakira.agenda.service;

import br.com.gustavoakira.agenda.exception.UserNotFoundException;
import br.com.gustavoakira.agenda.models.Users;
import br.com.gustavoakira.agenda.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UsersRepository repository;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users user = repository.findUserByEmail(s);
        if(user == null){
            throw new UserNotFoundException("email "+s);
        }

        return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}

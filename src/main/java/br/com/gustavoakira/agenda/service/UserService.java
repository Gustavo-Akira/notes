package br.com.gustavoakira.agenda.service;

import br.com.gustavoakira.agenda.exception.UserNotFoundException;
import br.com.gustavoakira.agenda.models.Role;
import br.com.gustavoakira.agenda.models.Users;
import br.com.gustavoakira.agenda.repository.RoleRepository;
import br.com.gustavoakira.agenda.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UsersRepository repository;

    @Autowired
    private RoleRepository roleRepository;
    @Transactional
    public List<Users> getUsers(){
        return repository.findAll();
    }
    @Transactional
    public Users getUser(Long id){
        return repository.findById(id).orElseThrow(()->new UserNotFoundException("id:"+id.toString()));
    }
    @Transactional
    public Users getUserByEmail(String email){
        return repository.findUserByEmail(email);
    }
    public Users updateUser(Long id, Users user){
        Users old = getUser(id);
        user.setId(id);
        user = repository.save(user);
        return user;
    }
    @Transactional
    public String removeUser(Long id){
        repository.delete(getUser(id));
        return "Removed user with id: " + id;
    }
    @Transactional
    public Users saveUser(Users user){
        Set<Role> roles = new HashSet<Role>();
        roles.add(roleRepository.findById(2L).get());
        user.setRoles(roles);
        user.setPassword( new BCryptPasswordEncoder().encode(user.getPassword()));
        repository.save(user);
        return repository.findById(user.getId()).get();
    }
}

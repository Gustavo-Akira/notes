package br.com.gustavoakira.agenda.controller;

import br.com.gustavoakira.agenda.exception.UserNotAllowed;
import br.com.gustavoakira.agenda.models.Users;
import br.com.gustavoakira.agenda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UsersController {
    @Autowired
    private UserService service;

    private Users getAuthenticated(){
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return service.getUserByEmail(email);
    }

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Users getUser(@PathVariable Long id){
        Users target =  service.getUser(id);
        if(target.getId() == getAuthenticated().getId()){
            return target;
        }
       throw new UserNotAllowed();
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public Users saveUser(@RequestBody @Valid Users user){
        return service.saveUser(user);
    }

    @PutMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Users updateUser(@PathVariable Long id, @RequestBody @Valid Users users){
        if(getAuthenticated().getId() != id){
            throw new UserNotAllowed();
        }
        return service.updateUser(id,users);
    }

    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String removeUser(@PathVariable Long id){
        if(getAuthenticated() == service.getUser(id)){
            throw new UserNotAllowed();
        }
        return service.removeUser(id);
    }
}

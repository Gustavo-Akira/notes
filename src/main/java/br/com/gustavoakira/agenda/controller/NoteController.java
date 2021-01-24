package br.com.gustavoakira.agenda.controller;

import br.com.gustavoakira.agenda.exception.UserNotAllowed;
import br.com.gustavoakira.agenda.models.Note;
import br.com.gustavoakira.agenda.models.Users;
import br.com.gustavoakira.agenda.service.NoteService;
import br.com.gustavoakira.agenda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class NoteController{

    @Autowired
    private NoteService service;

    @Autowired
    private UserService userService;

    private Users getAuthenticated(){
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.getUserByEmail(email);
    }

    @GetMapping("notes")
    @ResponseStatus(HttpStatus.OK)
    public List<Note> getNotes(@AuthenticationPrincipal Users users){
      return service.getNotesByUser(users);
    }

    @PostMapping("note")
    @ResponseStatus(HttpStatus.CREATED)
    public Note saveNote(@RequestBody Note note){
        note.setUser(getAuthenticated());
        return  service.save(note);
    }

    @DeleteMapping("note/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String removeNote(@PathVariable Long id){
        if(service.getNote(id).getUser() != getAuthenticated()){
            throw new UserNotAllowed();
        }
        return service.remove(id);
    }

    @PutMapping("note/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Note updateNote(@PathVariable Long id, @RequestBody Note note){
        if(service.getNote(id).getUser() != getAuthenticated()){
            throw new UserNotAllowed();
        }
        return service.update(id,note);
    }

    @GetMapping("note/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Note getNote(@PathVariable Long id){
        Note note = service.getNote(id);
        if(note.getUser() == getAuthenticated()){
            return note;
        }
        throw new UserNotAllowed();
    }
}

package br.com.gustavoakira.agenda.service;

import br.com.gustavoakira.agenda.exception.NoteNotFoundException;
import br.com.gustavoakira.agenda.models.Note;
import br.com.gustavoakira.agenda.models.Users;
import br.com.gustavoakira.agenda.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteRepository repository;

    public List<Note> getAllNotes(){
        return repository.findAll();
    }
    @Transactional
    public Note getNote(Long id){
        return repository.findById(id).orElseThrow(()->new NoteNotFoundException(id));
    }
    @Transactional
    public Note save(Note note){
        return repository.save(note);
    }
    @Transactional
    public Note update(Long id, Note note){
        Note old = getNote(id);
        note.setId(old.getId());
        return repository.save(note);
    }
    @Transactional
    public String remove(Long id){
        repository.delete(getNote(id));
        return "Removed Note with id: "+id;
    }
    @Transactional
    public List<Note> getNotesByUser(Users user){
        return repository.findByUser(user.getId());
    }
}

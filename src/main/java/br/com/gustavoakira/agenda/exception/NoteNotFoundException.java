package br.com.gustavoakira.agenda.exception;

public class NoteNotFoundException extends RuntimeException{
    public NoteNotFoundException(Long id){
        super("Note not Found with id: " + id);
    }
}

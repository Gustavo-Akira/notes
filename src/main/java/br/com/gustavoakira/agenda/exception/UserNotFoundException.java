package br.com.gustavoakira.agenda.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String id){
        super("User not Found with " + id);
    }
}

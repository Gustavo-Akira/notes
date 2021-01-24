package br.com.gustavoakira.agenda.exception;

public class UserNotAllowed extends RuntimeException{
    public UserNotAllowed(){
        super("User not allowed to do this action ");
    }
}

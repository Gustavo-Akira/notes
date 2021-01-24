package br.com.gustavoakira.agenda.repository;

import br.com.gustavoakira.agenda.models.Note;
import br.com.gustavoakira.agenda.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {
    @Query("SELECT n FROM Note n where n.user=?1")
    List<Note> getNotesByUser(Users users);
}

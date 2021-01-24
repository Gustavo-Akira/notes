package br.com.gustavoakira.agenda.repository;

import br.com.gustavoakira.agenda.models.Note;
import br.com.gustavoakira.agenda.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {
    @Query("SELECT n from Note n where n.user.id=?1")
    List<Note> findByUser(Long id);
}

package br.com.gustavoakira.agenda.repository;

import br.com.gustavoakira.agenda.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    @Query("select u from Users u where u.email=?1")
    Users findUserByEmail(String email);
}

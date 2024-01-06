package ru.alishev.springcourse.FirstSecurityApp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.FirstSecurityApp.models.Theme;


/**
 * @author Neil Alishev
 */
@Repository
public interface ThemeRepository extends JpaRepository<Theme, Integer> {
    //    @Query("select t from Theme t order by t.lastPostDate desc")
    //    Page<Theme> findAll(Pageable pageable);
    Page<Theme> findAll(Pageable pageable);

    //Optional<Person> findByUsername(String username);
}

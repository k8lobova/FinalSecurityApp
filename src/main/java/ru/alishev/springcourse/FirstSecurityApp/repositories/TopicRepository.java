package ru.alishev.springcourse.FirstSecurityApp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.FirstSecurityApp.models.Theme;
import ru.alishev.springcourse.FirstSecurityApp.models.Topic;


/**
 * @author Neil Alishev
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

    //    @Query("select u from Topic u where u.themeId = :themeId order by u.lastPostDate desc")
    //    Page<Topic> findAll(Pageable pageable, @Param ("themeId") int themeId);

    //Page<Topic> findAll(Pageable pageable, @Param("themeId") int themeId);  23.00 28.12

    Page<Topic> findByThemeId(Pageable pageable, int themeId);


    //Optional<Person> findByUsername(String username);
}

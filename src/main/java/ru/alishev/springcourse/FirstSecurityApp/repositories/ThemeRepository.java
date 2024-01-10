package ru.alishev.springcourse.FirstSecurityApp.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alishev.springcourse.FirstSecurityApp.models.Theme;

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Integer> {

    Page<Theme> findByThemeNameStartingWith(Pageable pageable, String themeName);

    Page<Theme> findByThemeNameContaining(Pageable pageable, String themeName);
    Page<Theme> findByThemeNameEquals(Pageable pageable, String themeName);

    Theme findByThemeName(String themeName);

}


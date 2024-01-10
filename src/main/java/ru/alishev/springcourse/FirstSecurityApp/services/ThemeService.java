package ru.alishev.springcourse.FirstSecurityApp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.FirstSecurityApp.models.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.alishev.springcourse.FirstSecurityApp.repositories.ThemeRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ThemeService {

    private final ThemeRepository themeRepository;

    @Autowired
    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;

    }

    public boolean isThemeNameUnique(String themeName) {
        // Проверяем, существует ли тема с указанным именем
        return themeRepository.findByThemeName(themeName) == null;
    }

    @Transactional
    public void save(Theme theme) {
        themeRepository.save(theme);
    }


    @Transactional
    public void delete(int id) {
        themeRepository.deleteById(id);
    }

    public Theme findById(int id) {
        Optional<Theme> foundTheme = themeRepository.findById(id);
        //Optional<Theme> foundTheme = themeRepository.findById(id);
        return foundTheme.orElse(null);
    }
//    public List<Theme> findAll() {  //??????????
//        return themeRepository.findAll();
//    }

    public Page findAll(Pageable pageable) {
        return themeRepository.findAll(pageable);
    }

    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }


    public Page<Theme> searchByThemeName(Pageable pageable, String themeName) {
        return themeRepository.findByThemeNameStartingWith(pageable, themeName);
    }

//    public Page<Theme> searchByThemeName(Pageable pageable, String themeName) {
//        return themeRepository.findByThemeNameContaining(pageable, themeName);
//    }
//    public Page<Theme> searchByThemeName(Pageable pageable, String themeName) {
//        return themeRepository.findByThemeNameEquals(pageable, themeName);
//    }


}


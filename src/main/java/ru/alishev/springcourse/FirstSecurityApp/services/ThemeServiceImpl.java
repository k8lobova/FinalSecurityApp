package ru.alishev.springcourse.FirstSecurityApp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.alishev.springcourse.FirstSecurityApp.models.Theme;
import ru.alishev.springcourse.FirstSecurityApp.repositories.ThemeRepository;

import java.util.List;

@Service
public class ThemeServiceImpl implements ThemeService{

    @Autowired
    private ThemeRepository themeRepository;

    @Override
    public void save(Theme theme) {
        themeRepository.save(theme);
    }

    @Override
    public void delete(long id) {
        themeRepository.deleteById(id);
    }

    @Override
    public Theme findOne(long id) {
        return themeRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Theme> findAllOrderByLastPostDateDesc(Pageable pageable) {
        return themeRepository.findAllOrderByLastPostDateDesc(pageable);
    }

    @Override
    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }
}
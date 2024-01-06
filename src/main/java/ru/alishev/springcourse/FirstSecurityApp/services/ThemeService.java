package ru.alishev.springcourse.FirstSecurityApp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.FirstSecurityApp.models.Book;
import ru.alishev.springcourse.FirstSecurityApp.models.Message;
import ru.alishev.springcourse.FirstSecurityApp.models.Person;
import ru.alishev.springcourse.FirstSecurityApp.models.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.alishev.springcourse.FirstSecurityApp.repositories.MessageRepository;
import ru.alishev.springcourse.FirstSecurityApp.repositories.ThemeRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Ромчи on 25.07.2017.
 */


@Service
@Transactional(readOnly = true)
public class ThemeService {

    private final ThemeRepository themeRepository;

    @Autowired
    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;

    }

    @Transactional
    public void save(Theme theme) {
        themeRepository.save(theme);
    }


    @Transactional
    public void delete(int id) {
        themeRepository.deleteById(id);
    }

    public Theme findOne(int id) {
        Optional<Theme> foundTheme = themeRepository.findById(id);
        return foundTheme.orElse(null);
    }

//    public List<Theme> findAll() {  //??????????
//        return themeRepository.findAll();
//    }


    public Page findAll(Pageable pageable) {
        return themeRepository.findAll(pageable);
    }



    public List<Theme> getAllThemes() {
        return themeRepository.findAll ();
    }


}


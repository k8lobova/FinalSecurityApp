package ru.alishev.springcourse.FirstSecurityApp.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alishev.springcourse.FirstSecurityApp.models.Message;
import ru.alishev.springcourse.FirstSecurityApp.models.Theme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.alishev.springcourse.FirstSecurityApp.repositories.MessageRepository;
import ru.alishev.springcourse.FirstSecurityApp.repositories.ThemeRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Ромчи on 25.07.2017.
 */



public interface ThemeService {

    void save(Theme theme);

    void delete(long id);

    Theme findOne(long id);

    Page<Theme> findAllOrderByLastPostDateDesc(Pageable pageable);

    List<Theme> getAllThemes();
}



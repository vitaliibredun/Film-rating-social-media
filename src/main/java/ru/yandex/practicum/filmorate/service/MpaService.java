package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.mpa.MpaStorage;
import ru.yandex.practicum.filmorate.dao.mpa.impl.MpaDbStorage;
import ru.yandex.practicum.filmorate.exceptions.MpaDoesNotExistException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

@Service
public class MpaService {
    private final MpaStorage mpaStorage;

    @Autowired
    public MpaService(MpaDbStorage mpaDbStorage) {
        this.mpaStorage = mpaDbStorage;
    }

    public Mpa findMpa(Integer id) {
        boolean idExists = findAllMpa().stream()
                .map(Mpa::getId)
                .anyMatch(id::equals);
        if (!idExists) {
            throw new MpaDoesNotExistException("The MPA with the id doesn't exists");
        }
        return mpaStorage.findMpa(id);
    }

    public Collection<Mpa> findAllMpa() {
        return mpaStorage.findAllMpa();
    }
}

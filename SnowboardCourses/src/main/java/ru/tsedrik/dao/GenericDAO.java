package ru.tsedrik.dao;

import ru.tsedrik.model.Identifired;

import java.util.Collection;

public interface GenericDAO <T extends Identifired<I>, I> {

    T create(T el);
    T getById(I id);
    Collection<T> getAll();
    T update(T el);
    T delete(T el);
    T deleteById(I id);

}

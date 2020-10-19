package ru.tsedrik.dao.map;

import ru.tsedrik.dao.GenericDAO;
import ru.tsedrik.model.Identifired;

import java.util.Collection;
import java.util.Map;

public abstract class AbstactDAO<T extends Identifired<I>, I> implements GenericDAO<T, I> {
    protected Map<I, T> elements;

    public AbstactDAO(Map<I, T> map){
        this.elements = map;
    }

    @Override
    public T create(T el) {
        elements.put(el.getId(), el);
        return el;
    }

    @Override
    public T getById(I id) {
        return elements.get(id);
    }

    @Override
    public Collection<T> getAll() {
        return elements.values();
    }

    @Override
    public T delete(T el) {
        return deleteById(el.getId());
    }

    @Override
    public T update(T el) {
        elements.put(el.getId(), el);
        return el;
    }

    @Override
    public T deleteById(I id) {
        return elements.remove(id);
    }
}

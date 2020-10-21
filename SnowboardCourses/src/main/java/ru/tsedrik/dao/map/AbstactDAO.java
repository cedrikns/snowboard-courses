package ru.tsedrik.dao.map;

import ru.tsedrik.dao.GenericDAO;
import ru.tsedrik.model.Identifired;

import java.util.Collection;
import java.util.Map;

/**
 * Абстрактный класс с реализацией унифицированного управления персистентным состоянием объектов.
 *
 * @param <T>   тип объекта персистенции
 * @param <I>   тип идентификатора объекта персистенции
 */
public abstract class AbstactDAO<T extends Identifired<I>, I> implements GenericDAO<T, I> {

    /**
     * Контейнер для хранения записей
     */
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

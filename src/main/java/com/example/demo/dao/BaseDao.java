package com.example.demo.dao;

import com.example.demo.exception.DaoException;

import java.util.List;

public abstract class BaseDao<T> {
    public abstract boolean insert(T t) throws DaoException;

    public abstract boolean delete(T t) throws DaoException;

    public abstract List<T> findAll() throws DaoException;

    public abstract T update(T t) throws DaoException;
}

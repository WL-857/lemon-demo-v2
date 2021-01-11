package com.nhsoft.lemon.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author wanglei
 */
public class BaseDao {
    @PersistenceContext
    public EntityManager entityManager;
}

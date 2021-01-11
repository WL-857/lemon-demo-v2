package com.nhsoft.lemon.repository.impl;

import com.nhsoft.lemon.model.Course;
import com.nhsoft.lemon.model.Teacher;
import com.nhsoft.lemon.repository.BaseDao;
import com.nhsoft.lemon.repository.TeacherDao;
import org.springframework.data.domain.Pageable;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * @author wanglei
 */
public class TeacherDaoImpl extends BaseDao implements TeacherDao {
    @Override
    public List<Teacher> listAllTeacher(int pageNo,int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Teacher> query = criteriaBuilder.createQuery(Teacher.class);
        query.from(Teacher.class);
        TypedQuery<Teacher> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageNo);
        typedQuery.setMaxResults(pageSize);
        return typedQuery.getResultList();
    }

    @Override
    public Teacher readTeacher(Long id) {
        return entityManager.find(Teacher.class,id);
    }

    @Override
    public Teacher saveTeacher(Teacher teacher) {
        entityManager.persist(teacher);
        return teacher;
    }

    @Override
    public List<Teacher> batchSaveTeacher(List<Teacher> teachers) {
        teachers.forEach(teacher -> {
            entityManager.persist(teacher);
        });
        return teachers;
    }

    @Override
    public int deleteTeacher(Long id) {
        String sql = "delete from Teacher where teacherId = :id";
        Query query = entityManager.createQuery(sql);
        query.setParameter("id", id);
        int i = query.executeUpdate();
        return i;
    }

    @Override
    public void batchDeleteTeacher(List<Long> ids) {
        String sql = "delete from Teacher where teacherId = :id";
        Query query = entityManager.createQuery(sql);
        ids.forEach(id -> {
            query.setParameter("id", id);
            query.executeUpdate();
        });
    }

    @Override
    public Teacher updateTeacher(Teacher teacher) {
        Teacher merge = entityManager.merge(teacher);
        return merge;
    }
}

package com.nhsoft.lemon.repository.impl;

import com.nhsoft.lemon.model.Course;
import com.nhsoft.lemon.repository.BaseDao;
import com.nhsoft.lemon.repository.CourseDao;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.zip.CheckedOutputStream;

/**
 * @author wanglei
 */
@Repository
public class CourseDaoImpl extends BaseDao implements CourseDao {

    @Override
    public List<Course> listAllCourse(int pageNo, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Course> query = criteriaBuilder.createQuery(Course.class);
        query.from(Course.class);
        TypedQuery<Course> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageNo);
        typedQuery.setMaxResults(pageSize);
        return typedQuery.getResultList();
    }

    @Override
    public Course readCourse(Long id) {
        return entityManager.find(Course.class, id);
    }

    @Override
    public Course saveCourse(Course course) {
        entityManager.persist(course);
        return course;
    }

    @Override
    public List<Course> batchSaveCourse(List<Course> courses) {
        courses.forEach(course -> {
            entityManager.persist(course);
        });
        return courses;
    }

    @Override
    public int deleteCourse(Long id) {
        String sql = "delete from Course where couId = :id";
        Query query = entityManager.createQuery(sql);
        query.setParameter("id", id);
        int i = query.executeUpdate();
        return i;
    }

    @Override
    public void batchDeleteCourse(List<Long> ids) {
        String sql = "delete from Course where couId = :id";
        Query query = entityManager.createQuery(sql);
        ids.forEach(id -> {
            query.setParameter("id", id);
            query.executeUpdate();
        });
    }

    @Override
    public Course updateCourse(Course course) {
        Course merge = entityManager.merge(course);
        return merge;
    }
}

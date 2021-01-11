package com.nhsoft.lemon.repository.impl;

import com.nhsoft.lemon.model.Course;
import com.nhsoft.lemon.model.Student;
import com.nhsoft.lemon.repository.BaseDao;
import com.nhsoft.lemon.repository.StudentDao;
import com.nhsoft.lemon.service.StudentService;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * @author wanglei
 */
public class StudentDaoImpl extends BaseDao implements StudentDao {
    @Override
    public List<Student> listAllStudent(int pageNo, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> query = criteriaBuilder.createQuery(Student.class);
        query.from(Student.class);
        TypedQuery<Student> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageNo);
        typedQuery.setMaxResults(pageSize);
        return typedQuery.getResultList();
    }

    @Override
    public Student readStudent(Long id) {
        return entityManager.find(Student.class,id);
    }

    @Override
    public Student saveStudent(Student student) {
        entityManager.persist(student);
        return student;
    }

    @Override
    public List<Student> batchSaveStudent(List<Student> students) {
        students.forEach(student -> entityManager.persist(student));
        return students;
    }

    @Override
    public Student updateStudent(Student student) {
        Student merge = entityManager.merge(student);
        return merge;
    }

    @Override
    public int deleteStudent(Long id) {
        String sql = "delete from Student where stuId = :id";
        Query query = entityManager.createQuery(sql);
        query.setParameter("id", id);
        int i = query.executeUpdate();
        return i;
    }

    @Override
    public void batchDeleteStudent(List<Long> ids) {
        String sql = "delete from Student where stuId = :id";
        Query query = entityManager.createQuery(sql);
        ids.forEach(id -> {
            query.setParameter("id", id);
            query.executeUpdate();
        });
    }
}

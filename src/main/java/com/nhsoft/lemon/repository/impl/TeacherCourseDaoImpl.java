package com.nhsoft.lemon.repository.impl;

import com.nhsoft.lemon.model.Score;
import com.nhsoft.lemon.model.Teacher;
import com.nhsoft.lemon.model.TeacherCourse;
import com.nhsoft.lemon.model.extend.TeacherExtend;
import com.nhsoft.lemon.repository.BaseDao;
import com.nhsoft.lemon.repository.TeacherCourseDao;
import com.nhsoft.lemon.service.TeacherCourseService;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * @author wanglei
 */
@Repository
public class TeacherCourseDaoImpl extends BaseDao implements TeacherCourseDao {
    @Override
    public List<TeacherExtend> list(int pageNo, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TeacherExtend> query = criteriaBuilder.createQuery(TeacherExtend.class);
        Root<Teacher> root = query.from(Teacher.class);
        Join<Object, Object> courseJoin = root.join("course", JoinType.LEFT);
        Join<Object, Object> teacherCourseJoin = root.join("teacher_course_mapping", JoinType.LEFT);
        query.multiselect(
                root.get("teachName"),
                courseJoin.get("couName")
        );
        TypedQuery<TeacherExtend> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageNo);
        typedQuery.setMaxResults(pageSize);
        List<TeacherExtend> resultList = typedQuery.getResultList();
        return resultList;
    }

    @Override
    public TeacherCourse save(TeacherCourse teacherCourse) {
        entityManager.persist(teacherCourse);
        return teacherCourse;
    }

    @Override
    public List<TeacherCourse> batchSave(List<TeacherCourse> teacherCourses) {
        teacherCourses.forEach(teacherCourse -> {
            entityManager.persist(teacherCourses);
        });
        return teacherCourses;
    }

    @Override
    public int delete(TeacherCourse teacherCourse) {
        String sql = "delete from TeacherCourse where courseId = :couId and teacherId = :teachId";
        Query query = entityManager.createQuery(sql);
        query.setParameter("couId", teacherCourse.getCourseId());
        query.setParameter("teachId", teacherCourse.getTeacherId());
        int i = query.executeUpdate();
        return i;
    }

    @Override
    public void batchDelete(List<TeacherCourse> teacherCourses) {
        String sql = "delete from TeacherCourse where courseId = :couId and teacherId = :teachId";
        Query query = entityManager.createQuery(sql);
        teacherCourses.forEach(teacherCourse -> {
            query.setParameter("couId", teacherCourse.getCourseId());
            query.setParameter("teachId", teacherCourse.getTeacherId());
            query.executeUpdate();
        });
    }
}

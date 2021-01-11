package com.nhsoft.lemon.repository.impl;

import com.nhsoft.lemon.model.Score;
import com.nhsoft.lemon.model.extend.ScoreExtend;
import com.nhsoft.lemon.model.extend.TeacherExtend;
import com.nhsoft.lemon.repository.BaseDao;
import com.nhsoft.lemon.repository.ScoreDao;
import com.nhsoft.lemon.service.ScoreService;
import org.springframework.data.domain.Pageable;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * @author wanglei
 */
public class ScoreDaoImpl extends BaseDao implements ScoreDao {
    @Override
    public List<ScoreExtend> listStudentAllGrade(String stuNo, String year) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ScoreExtend> query = criteriaBuilder.createQuery(ScoreExtend.class);
        Root<Score> root = query.from(Score.class);
        Join<Object, Object> courseJoin = root.join("course", JoinType.LEFT);
        Join<Object, Object> studentJoin = root.join("student", JoinType.LEFT);
        query.multiselect(
                root.get("grade"),
                courseJoin.get("courseName"),
                root.get("time"),
                studentJoin.get("studentName")
        );
        Predicate studentNo = criteriaBuilder.equal(studentJoin.get("studentNo"), stuNo);
        Predicate time = criteriaBuilder.equal(root.get("time"), year);
        Predicate predicate = criteriaBuilder.and(studentNo, time);
        query.where(predicate);
        List<ScoreExtend> resultList = entityManager.createQuery(query).getResultList();
        return resultList;
    }

    @Override
    public List<TeacherExtend> listMaxMinAvg(Long teachId, String year) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TeacherExtend> query = criteriaBuilder.createQuery(TeacherExtend.class);
        Root<Score> root = query.from(Score.class);
        Join<Object, Object> courseJoin = root.join("course", JoinType.LEFT);
        Join<Object, Object> teacherCourseJoin = root.join("teacher_course_mapping", JoinType.LEFT);
        query.multiselect(
                courseJoin.get("courseName"),
                criteriaBuilder.max(root.get("grade")),
                criteriaBuilder.min(root.get("grade")),
                criteriaBuilder.avg(root.get("grade"))
        );
        Predicate teacherId = criteriaBuilder.equal(teacherCourseJoin.get("teacherId"), teachId);
        Predicate time = criteriaBuilder.equal(root.get("time"), year);
        Predicate predicate = criteriaBuilder.and(teacherId, time);
        query.where(predicate);
        List<TeacherExtend> resultList = entityManager.createQuery(query).getResultList();
        return resultList;
    }

    @Override
    public List<TeacherExtend> listAllMaxMinAvg(String year) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TeacherExtend> query = criteriaBuilder.createQuery(TeacherExtend.class);
        Root<Score> root = query.from(Score.class);
        Join<Object, Object> courseJoin = root.join("course", JoinType.LEFT);
        Join<Object, Object> studentJoin = root.join("student", JoinType.LEFT);
        query.multiselect(
                courseJoin.get("courseName"),
                criteriaBuilder.max(root.get("grade")),
                criteriaBuilder.min(root.get("grade")),
                criteriaBuilder.avg(root.get("grade"))
        );
        Predicate time = criteriaBuilder.equal(root.get("time"), year);
        query.where(time);
        List<TeacherExtend> resultList = entityManager.createQuery(query).getResultList();
        return resultList;
    }

    @Override
    public List<ScoreExtend> listAll(int pageNo, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ScoreExtend> query = criteriaBuilder.createQuery(ScoreExtend.class);
        Root<Score> root = query.from(Score.class);
        Join<Object, Object> courseJoin = root.join("course", JoinType.LEFT);
        Join<Object, Object> studentJoin = root.join("student", JoinType.LEFT);
        query.multiselect(
                courseJoin.get("grade"),
                root.get("courseName"),
                root.get("time"),
                studentJoin.get("studentName")
        );
        TypedQuery<ScoreExtend> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageNo);
        typedQuery.setMaxResults(pageSize);
        List<ScoreExtend> resultList = typedQuery.getResultList();
        return resultList;
    }

    @Override
    public ScoreExtend readScore(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ScoreExtend> query = criteriaBuilder.createQuery(ScoreExtend.class);
        Root<Score> root = query.from(Score.class);
        Join<Object, Object> courseJoin = root.join("course", JoinType.LEFT);
        Join<Object, Object> studentJoin = root.join("student", JoinType.LEFT);
        query.multiselect(
                root.get("grade"),
                courseJoin.get("courseName"),
                root.get("time"),
                studentJoin.get("studentName")
        );
        Predicate scoId = criteriaBuilder.equal(root.get("scoreId"), id);
        query.where(scoId);
        ScoreExtend scoreExtend = entityManager.createQuery(query).getSingleResult();
        return scoreExtend;
    }

    @Override
    public Score saveScore(Score score) {
        entityManager.persist(score);
        return score;
    }

    @Override
    public List<Score> batchSaveScore(List<Score> scores) {
        scores.forEach(course -> {
            entityManager.persist(course);
        });
        return scores;
    }

    @Override
    public int deleteScore(Long id) {
        String sql = "delete from Score where scoreId = :id";
        Query query = entityManager.createQuery(sql);
        query.setParameter("id", id);
        int i = query.executeUpdate();
        return i;
    }

    @Override
    public void batchDeleteScore(List<Long> ids) {
        String sql = "delete from Score where scoreId = :id";
        Query query = entityManager.createQuery(sql);
        ids.forEach(id -> {
            query.setParameter("id", id);
            query.executeUpdate();
        });
    }

    @Override
    public Score updateScore(Score score) {
        Score merge = entityManager.merge(score);
        return merge;
    }
}

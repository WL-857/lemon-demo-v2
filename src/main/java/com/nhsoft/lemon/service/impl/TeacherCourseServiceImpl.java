package com.nhsoft.lemon.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhsoft.lemon.config.RedisKey;
import com.nhsoft.lemon.model.Student;
import com.nhsoft.lemon.model.Teacher;
import com.nhsoft.lemon.model.TeacherCourse;
import com.nhsoft.lemon.model.extend.TeacherExtend;
import com.nhsoft.lemon.repository.TeacherCourseDao;
import com.nhsoft.lemon.service.TeacherCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author wanglei
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TeacherCourseServiceImpl implements TeacherCourseService {

    @Autowired
    private TeacherCourseDao teacherCourseDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<TeacherExtend> listTeacherCourse(int pageNo, int pageSize) {
        if (pageNo <= 0) {
            pageNo = 1;
        }
        if (pageSize <= 0) {
            pageSize = 5;
        }
        PageRequest page = PageRequest.of(pageNo - 1, pageSize);
        List<TeacherExtend> teacherCourses = teacherCourseDao.listTeacherCourse(page);
        return teacherCourses;
    }

    @Override
    public TeacherCourse saveTeacherCourse(TeacherCourse teacherCourse) {
        TeacherCourse save = teacherCourseDao.save(teacherCourse);
        if (ObjectUtils.isEmpty(save)) {
            return save;
        }
        Long couId = save.getCouId();
        Long teachId = save.getTeachId();
        String teacherCourseKey = RedisKey.TEACHER_COURSE_KEY + couId + teachId;
        Object teacherCourseValue = redisTemplate.opsForValue().get(teacherCourseKey);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String teacherCourseJson = objectMapper.writeValueAsString(teacherCourse);
            if (teacherCourseValue == null) {
                redisTemplate.opsForValue().set(teacherCourseKey, teacherCourseJson);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return save;
    }

    @Override
    public List<TeacherCourse> batchSaveTeacherCourse(List<TeacherCourse> teacherCourses) {
        List<TeacherCourse> teacherCourseList = teacherCourseDao.saveAll(teacherCourses);

        teacherCourses.forEach(teacherCourse -> {
            String teacherCourseKey = RedisKey.TEACHER_COURSE_KEY + teacherCourse.getCouId() + teacherCourse.getTeachId();
            Object teacherCourseValue = redisTemplate.opsForValue().get(teacherCourseKey);

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String teacherCourseJson = objectMapper.writeValueAsString(teacherCourse);
                if (teacherCourseValue == null) {
                    redisTemplate.opsForValue().set(teacherCourseKey, teacherCourseJson);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return teacherCourseList;
    }

    @Override
    public void deleteTeacherCourse(TeacherCourse teacherCourse) {
        Long couId = teacherCourse.getCouId();
        Long teachId = teacherCourse.getTeachId();
        String teacherCourseKey = RedisKey.TEACHER_COURSE_KEY + couId + teachId;
        Object teacherCourseValue = redisTemplate.opsForValue().get(teacherCourseKey);
        if (teacherCourseValue != null) {
            redisTemplate.delete(teacherCourseKey);
        }

        teacherCourseDao.delete(teacherCourse);

    }

    @Override
    public void batchDeleteTeacherCourse(List<TeacherCourse> teacherCourses) {
        teacherCourses.forEach(teacherCourse -> {
            Long couId = teacherCourse.getCouId();
            Long teachId = teacherCourse.getTeachId();
            String teacherCourseKey = RedisKey.TEACHER_COURSE_KEY + couId + teachId;
            Object value = redisTemplate.opsForValue().get(teacherCourseKey);
            if (value != null) {
                redisTemplate.delete(teacherCourseKey);
            }
        });
        teacherCourseDao.deleteInBatch(teacherCourses);
    }
}

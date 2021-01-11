package com.nhsoft.lemon.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhsoft.lemon.config.RedisKey;
import com.nhsoft.lemon.exception.GlobalException;
import com.nhsoft.lemon.model.Student;
import com.nhsoft.lemon.model.Teacher;
import com.nhsoft.lemon.model.TeacherCourse;
import com.nhsoft.lemon.model.extend.TeacherExtend;
import com.nhsoft.lemon.repository.TeacherCourseDao;
import com.nhsoft.lemon.service.TeacherCourseService;
import com.nhsoft.lemon.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
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

    @Resource(name = "objectMapper")
    private ObjectMapper objectMapper;
    @Override
    public List<TeacherExtend> list(int pageNo, int pageSize) {
        PageUtil pageUtil = new PageUtil();
        PageUtil check = pageUtil.check(pageNo, pageSize);
        List<TeacherExtend> teacherCourses = teacherCourseDao.list(check.getOffset(),check.getRows());
        return teacherCourses;
    }

    @Override
    public TeacherCourse save(TeacherCourse teacherCourse) {
        TeacherCourse save = teacherCourseDao.save(teacherCourse);
        if (ObjectUtils.isEmpty(save)) {
            throw new GlobalException("数据库中已存在该条记录");
        }
        Long couId = save.getCourseId();
        Long teachId = save.getTeacherId();
        String teacherCourseKey = RedisKey.TEACHER_COURSE_KEY + couId + teachId;
        Object teacherCourseValue = redisTemplate.opsForValue().get(teacherCourseKey);

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
    public List<TeacherCourse> batchSave(List<TeacherCourse> teacherCourses) {
        List<TeacherCourse> teacherCourseList = teacherCourseDao.batchSave(teacherCourses);

        teacherCourses.forEach(teacherCourse -> {
            String teacherCourseKey = RedisKey.TEACHER_COURSE_KEY + teacherCourse.getCourseId() + teacherCourse.getTeacherId();
            Object teacherCourseValue = redisTemplate.opsForValue().get(teacherCourseKey);

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
    public void delete(TeacherCourse teacherCourse) {
        Long couId = teacherCourse.getCourseId();
        Long teachId = teacherCourse.getTeacherId();
        String teacherCourseKey = RedisKey.TEACHER_COURSE_KEY + couId + teachId;
        Object teacherCourseValue = redisTemplate.opsForValue().get(teacherCourseKey);
        if (teacherCourseValue != null) {
            redisTemplate.delete(teacherCourseKey);
        }

        teacherCourseDao.delete(teacherCourse);

    }

    @Override
    public void batchDelete(List<TeacherCourse> teacherCourses) {
        teacherCourses.forEach(teacherCourse -> {
            Long couId = teacherCourse.getCourseId();
            Long teachId = teacherCourse.getTeacherId();
            String teacherCourseKey = RedisKey.TEACHER_COURSE_KEY + couId + teachId;
            Object value = redisTemplate.opsForValue().get(teacherCourseKey);
            if (value != null) {
                redisTemplate.delete(teacherCourseKey);
            }
        });
        teacherCourseDao.batchDelete(teacherCourses);
    }
}

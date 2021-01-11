package com.nhsoft.lemon.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhsoft.lemon.config.RedisKey;
import com.nhsoft.lemon.repository.CourseDao;

import com.nhsoft.lemon.model.Course;
import com.nhsoft.lemon.service.CourseService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author wanglei
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseDao courseDao;

    @Resource
    private RedisTemplate redisTemplate;


    @Override
    public List<Course> listAllCourse(int pageNo, int pageSize) {
        if (pageNo <= 0) {
            pageNo = 1;
        }
        if (pageSize <= 0) {
            pageSize = 5;
        }
        List<Course> courses = courseDao.listAllCourse(pageNo,pageSize);
        return courses;
    }

    @Override
    public Course readCourse(Long id) {
        return courseDao.readCourse(id);
    }

    @Override
    public Course saveCourse(Course course) {
        Course save = courseDao.saveCourse(course);
        if (save == null) {
            return save;
        }
        Long couId = save.getCouId();
        String courseKey = RedisKey.COURSE_KEY + couId;
        Object courseValue = redisTemplate.opsForValue().get(courseKey);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String courseJson = objectMapper.writeValueAsString(course);
            if (courseValue == null) {
                redisTemplate.opsForValue().set(courseKey, courseJson);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return save;
    }

    @Override
    public List<Course> batchSaveCourse(List<Course> courses) {
        List<Course> list = courseDao.batchSaveCourse(courses);
        list.forEach(course -> {
            String courseKey = RedisKey.COURSE_KEY + course.getCouId();
            Object value = redisTemplate.opsForValue().get(courseKey);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String courseJson = objectMapper.writeValueAsString(course);
                if (value == null) {
                    redisTemplate.opsForValue().set(courseKey, courseJson);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        return list;
    }

    @Override
    public void deleteCourse(Long id) {
        String courseKey = RedisKey.COURSE_KEY + id;
        Object value = redisTemplate.opsForValue().get(courseKey);
        if (value != null) {
            redisTemplate.delete(courseKey);
        }
        if (courseDao.readCourse(id) != null) {

            courseDao.deleteCourse(id);
        }
    }

    @Override
    public void batchDeleteCourse(List<Long> ids) {
        ids.forEach(id -> {
            String courseKey = RedisKey.COURSE_KEY + id;
            Object value = redisTemplate.opsForValue().get(courseKey);
            if (value != null) {
                redisTemplate.delete(courseKey);
            }
        });
        courseDao.batchDeleteCourse(ids);
    }

    @Override
    public Course updateCourse(Course course) {
        return courseDao.updateCourse(course);
    }
}

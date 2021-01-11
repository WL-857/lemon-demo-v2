package com.nhsoft.lemon.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhsoft.lemon.config.RedisKey;
import com.nhsoft.lemon.repository.TeacherDao;
import com.nhsoft.lemon.dto.TeacherDTO;

import com.nhsoft.lemon.model.Teacher;
import com.nhsoft.lemon.service.TeacherService;
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
public class TeacherServiceImpl implements TeacherService {
    @Resource
    private TeacherDao teacherDao;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public List<Teacher> listAllTeacher(int pageNo, int pageSize) {
        if (pageNo <= 0) {
            pageNo = 1;
        }
        if (pageSize <= 0) {
            pageSize = 5;
        }
        PageRequest page = PageRequest.of(pageNo-1, pageSize);
        List<Teacher> teachers = teacherDao.listAllTeacher(page);
        return teachers;
    }

    @Override
    public Teacher readTeacher(Long id) {
        return teacherDao.readTeacher(id);
    }

    @Override
    public Teacher saveTeacher(Teacher teacher) {
        Teacher save = teacherDao.save(teacher);
        Long teachId = save.getTeachId();
        String teacherKey = RedisKey.TEACHER_KEY + teachId;
        Object teacherValue = redisTemplate.opsForValue().get(teacherKey);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String teacherJson = objectMapper.writeValueAsString(teacher);
            if(teacherJson == null){
                redisTemplate.opsForValue().set(teacherKey,teacherJson);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return save;
    }

    @Override
    public List<Teacher> batchSaveTeacher(List<Teacher> teachers) {
        List<Teacher> teacherList = teacherDao.saveAll(teachers);

        teachers.forEach(teacher -> {
            String teacherKey = RedisKey.TEACHER_KEY + teacher.getTeachId();
            Object value = redisTemplate.opsForValue().get(teacherKey);

            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String teacherJson = objectMapper.writeValueAsString(teacher);
                if (value == null) {
                    redisTemplate.opsForValue().set(teacherKey, teacherJson);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return teacherList;
    }

    @Override
    public void deleteTeacher(Long id) {
        String  teacherKey = RedisKey.TEACHER_KEY + id;
        Object value = redisTemplate.opsForValue().get(teacherKey);
        if (value != null) {
            redisTemplate.delete(teacherKey);
        }
        if (teacherDao.readTeacher(id) != null) {

            teacherDao.deleteById(id);
        }

    }

    @Override
    public void batchDeleteTeacher(List<Long> ids) {
        ids.forEach(id -> {
            String teacherKey = RedisKey.TEACHER_KEY + id;
            Object value = redisTemplate.opsForValue().get(teacherKey);
            if (value != null) {
                redisTemplate.delete(teacherKey);
            }
            if (teacherDao.readTeacher(id) != null) {
                teacherDao.deleteById(id);
            }
        });
    }
}

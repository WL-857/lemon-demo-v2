package com.nhsoft.lemon.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhsoft.lemon.config.RedisKey;
import com.nhsoft.lemon.exception.GlobalException;
import com.nhsoft.lemon.repository.StudentDao;
import com.nhsoft.lemon.dto.StudentDTO;

import com.nhsoft.lemon.model.Student;
import com.nhsoft.lemon.service.StudentService;
import com.nhsoft.lemon.utils.PageUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wanglei
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentDao studentDao;
    @Resource
    private RedisTemplate redisTemplate;

    @Resource(name = "objectMapper")
    private ObjectMapper objectMapper;

    @Override
    public List<Student> listAllStudent(int pageNo, int pageSize) {
        PageUtil pageUtil = new PageUtil();
        PageUtil check = pageUtil.check(pageNo, pageSize);
        List<Student> students = studentDao.listAllStudent(check.getOffset(),check.getRows());
        return students;
    }

    @Override
    public Student readStudent(Long id) {
        return studentDao.readStudent(id);
    }

    @Override
    public Student saveStudent(Student student) {
        Student save = studentDao.saveStudent(student);
        if (ObjectUtils.isEmpty(save)) {
            throw new GlobalException("数据库中已存在该记录");
        }
        Long stuId = save.getStudentId();
        String studentKey = RedisKey.STUDENT_KEY + stuId;
        Object studentValue = redisTemplate.opsForValue().get(studentKey);

        try {
            String studentJson = objectMapper.writeValueAsString(student);
            if (studentValue == null) {
                redisTemplate.opsForValue().set(studentKey, studentJson);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return save;
    }

    @Override
    public List<Student> batchSaveStudent(List<Student> students) {
        List<Student> studentList = studentDao.batchSaveStudent(students);
        students.forEach(student -> {
            String studentKey = RedisKey.COURSE_KEY + student.getStudentId();
            Object value = redisTemplate.opsForValue().get(studentKey);

            try {
                String studentJson = objectMapper.writeValueAsString(student);
                if (value == null) {
                    redisTemplate.opsForValue().set(studentKey, studentJson);
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return studentList;
    }

    @Override
    public void deleteStudent(Long id) {
        String studentKey = RedisKey.STUDENT_KEY + id;
        Object value = redisTemplate.opsForValue().get(studentKey);
        if (value != null) {
            redisTemplate.delete(studentKey);
        }
        if (studentDao.readStudent(id) != null) {
            studentDao.deleteStudent(id);
        }else{
            throw new GlobalException("数据库中不存在该条记录");
        }
    }

    @Override
    public void batchDeleteStudent(List<Long> ids) {
        ids.forEach(id -> {
            String studentKey = RedisKey.STUDENT_KEY + id;
            Object value = redisTemplate.opsForValue().get(studentKey);
            if (value != null) {
                redisTemplate.delete(studentKey);
            }
            studentDao.batchDeleteStudent(ids);
        });

    }

    @Override
    public Student updateStudent(Student student) {
        String studentKey = RedisKey.STUDENT_KEY + student.getStudentId();
        redisTemplate.opsForValue().set(studentKey,student);
        return studentDao.updateStudent(student);
    }
}

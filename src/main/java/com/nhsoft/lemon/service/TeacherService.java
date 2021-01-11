package com.nhsoft.lemon.service;


import com.nhsoft.lemon.dto.StudentDTO;
import com.nhsoft.lemon.dto.TeacherDTO;
import com.nhsoft.lemon.model.Student;
import com.nhsoft.lemon.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wanglei
 */
public interface TeacherService{
    /**
     *  查询所有教师信息并且分页
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<Teacher> listAllTeacher(int pageNo, int pageSize);

    /**
     * 根据学生id查询教师信息
     * @param id
     * @return
     */
    Teacher readTeacher(Long id);

    /**
     * 添加教师
     * @param teacher
     * @return
     */
    Teacher saveTeacher(Teacher teacher);

    /**
     * 批量添加教师
     * @param teachers
     * @return
     */
    List<Teacher> batchSaveTeacher(List<Teacher> teachers);

    /**
     * 根据教师
     * @param id
     */
    void deleteTeacher(Long id);

    /**
     * 批量删除教师
     * @param ids
     */
    void batchDeleteTeacher(List<Long> ids);
}

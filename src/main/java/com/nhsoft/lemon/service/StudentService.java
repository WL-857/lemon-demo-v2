package com.nhsoft.lemon.service;

import com.nhsoft.lemon.dto.PageDTO;
import com.nhsoft.lemon.dto.StudentDTO;
import com.nhsoft.lemon.model.Student;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wanglei
 */
public interface StudentService{
    /**
     *  查询所有学生信息并且分页
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<Student> listAllStudent(int pageNo,int pageSize);

    /**
     * 根据学生id查询学生信息
      * @param id
     * @return
     */
    Student readStudent(Long id);

    /**
     * 添加学生
     * @param student
     * @return
     */
    Student saveStudent(Student student);

    /**
     * 批量添加学生
     * @param students
     * @return
     */
    List<Student> batchSaveStudent(List<Student> students);

    /**
     * 根据学生id删除学生
     * @param id
     */
    void deleteStudent(Long id);

    /**
     * 批量删除学生
     * @param ids
     */
    void batchDeleteStudent(List<Long> ids);

    /**
     * 修改学生信息
     * @param student
     * @return
     */
    Student updateStudent(Student student);
}

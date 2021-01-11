package com.nhsoft.lemon.repository;

import com.nhsoft.lemon.model.Student;
import lombok.Lombok;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wanglei
 */
public interface StudentDao {

    /**
     * 查询所有学生信息
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
     * 修改学生
     * @param student
     * @return
     */
    Student updateStudent(Student student);

    /**
     * 删除学生
     * @param id
     * @return
     */
    int deleteStudent(Long id);

    /**
     * 批量删除学生
     * @param ids
     */
    void batchDeleteStudent(List<Long> ids);

}

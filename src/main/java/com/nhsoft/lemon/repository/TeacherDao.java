package com.nhsoft.lemon.repository;

import com.nhsoft.lemon.model.Student;
import com.nhsoft.lemon.model.Teacher;
import com.nhsoft.lemon.model.extend.ScoreExtend;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author wanglei
 */
public interface TeacherDao {
    /**
     * 查询所有教师信息并且分页
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<Teacher> listAll(int pageNo,int pageSize);


    /**
     * 根据教师id查询教师
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
     * 删除教师
     * @param id
     * @return
     */
    int deleteTeacher(Long id);

    /**
     * 批量删除教师
     * @param ids
     */
    void batchDeleteTeacher(List<Long> ids);

    /**
     * 修改教师
     * @param teacher
     * @return
     */
    Teacher updateTeacher(Teacher teacher);
}

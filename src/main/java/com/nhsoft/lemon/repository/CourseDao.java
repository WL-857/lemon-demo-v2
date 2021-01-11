package com.nhsoft.lemon.repository;

import com.nhsoft.lemon.model.Course;
import com.nhsoft.lemon.model.Score;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author wanglei
 */

public interface CourseDao {

    /**
     * 查询所有的课程并分页
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<Course> listAll(int pageNo,int pageSize);

    /**
     * 根据id查询课程
     * @param id
     * @return
     */
    Course readCourse(Long id);

    /**
     * 添加课程
     * @param course
     * @return
     */
    Course saveCourse(Course course);

    /**
     * 批量添加课程
     * @param courses
     * @return
     */
    List<Course> batchSaveCourse(List<Course> courses);

    /**
     * 根据id删除课程
     * @param id
     */
    int deleteCourse(Long id);

    /**
     * 批量删除课程
     * @param ids
     */
    void batchDeleteCourse(List<Long> ids);

    /**
     * 修改课程
     * @param course
     * @return
     */
    Course updateCourse(Course course);
}

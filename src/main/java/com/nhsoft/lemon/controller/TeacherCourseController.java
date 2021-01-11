package com.nhsoft.lemon.controller;

import com.nhsoft.lemon.dto.CourseDTO;
import com.nhsoft.lemon.dto.TeacherCourseDTO;
import com.nhsoft.lemon.exception.GlobalException;
import com.nhsoft.lemon.model.Course;
import com.nhsoft.lemon.model.TeacherCourse;
import com.nhsoft.lemon.model.extend.TeacherExtend;
import com.nhsoft.lemon.response.ResponseMessage;
import com.nhsoft.lemon.service.CourseService;
import com.nhsoft.lemon.service.TeacherCourseService;
import com.nhsoft.lemon.utils.CopyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wanglei
 */
@RestController
@Api("教师课程模块")
@Slf4j
@RequestMapping("/teacherCourse")
public class TeacherCourseController {

    @Autowired
    private TeacherCourseService teacherCourseService;

    @ApiOperation("查询所有教师所教授课程并分页")
    @GetMapping("/list/{pageNo}/{pageSize}")
    public ResponseMessage listAllTeacherCourse(@PathVariable(name = "pageNo", required = true) int pageNo,
                                                @PathVariable(name = "pageSize", required = true) int pageSize) {
        List<TeacherExtend> teacherCourses = teacherCourseService.list(pageNo, pageSize);
        if (CollectionUtils.isEmpty(teacherCourses)) {
            throw new GlobalException("数据为空");
        }
        List<TeacherCourseDTO> teacherCourseDTOS = CopyUtil.toList(teacherCourses, TeacherCourseDTO.class);
        return ResponseMessage.success("courseDTOS", teacherCourseDTOS);
    }

    @ApiOperation("添加教师课程")
    @PostMapping("/save")
    public ResponseMessage saveTeacherCourse(@RequestBody TeacherCourseDTO teacherCourseDTO) {
        if (ObjectUtils.isEmpty(teacherCourseDTO)) {
            throw new GlobalException("参数为空");
        }
        TeacherCourse teacherCourse = CopyUtil.to(teacherCourseDTO, TeacherCourse.class);
        TeacherCourse saveTeacherCourse = teacherCourseService.save(teacherCourse);
        if (saveTeacherCourse == null) {
            throw new GlobalException("数据为空");
        }
        return ResponseMessage.success("添加成功");
    }

    @ApiOperation("批量添加教师课程")
    @PostMapping(value = "/batchSave")
    public ResponseMessage batchSaveTeacherCourse(@RequestBody List<TeacherCourseDTO> teacherCourseDTOS) {
        if (CollectionUtils.isEmpty(teacherCourseDTOS)) {
            throw new GlobalException("参数为空");
        }
        List<TeacherCourse> teacherCourses = CopyUtil.toList(teacherCourseDTOS, TeacherCourse.class);
        List<TeacherCourse> saveTeacherCourse = teacherCourseService.batchSave(teacherCourses);
        if (CollectionUtils.isEmpty(saveTeacherCourse)) {
            throw new GlobalException("数据为空");
        }
        return ResponseMessage.success("添加成功");
    }

    @ApiOperation("删除教师课程")
    @DeleteMapping(value = "/delete/")
    public ResponseMessage deleteTeacherCourse(@RequestBody TeacherCourseDTO teacherCourseDTO) {
        if (ObjectUtils.isEmpty(teacherCourseDTO)) {
            throw new GlobalException("参数为空");
        }
        TeacherCourse teacherCourse = CopyUtil.to(teacherCourseDTO, TeacherCourse.class);
        teacherCourseService.delete(teacherCourse);
        return ResponseMessage.success("删除成功");
    }

    @ApiOperation("批量删除教师课程")
    @PostMapping(value = "/delete")
    public ResponseMessage batchDeleteTeacherCourse(List<TeacherCourseDTO> teacherCourseDTOS) {
        if (CollectionUtils.isEmpty(teacherCourseDTOS)) {
            throw new GlobalException("数据为空");
        }
        List<TeacherCourse> teacherCourses = CopyUtil.toList(teacherCourseDTOS, TeacherCourse.class);
        teacherCourseService.batchDelete(teacherCourses);
        return ResponseMessage.success("删除成功");
    }
}

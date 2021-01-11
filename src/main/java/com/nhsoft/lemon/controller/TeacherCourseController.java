package com.nhsoft.lemon.controller;

import com.nhsoft.lemon.dto.CourseDTO;
import com.nhsoft.lemon.dto.TeacherCourseDTO;
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
        log.info("listAllTeacherCourse方法开始执行,参数为：pageNo=" + pageNo + ",pageSize=" + pageSize);

        if (pageNo < 0) {
            pageNo = 1;
        }
        if (pageSize <= 0) {
            pageSize = 5;
        }
        List<TeacherExtend> teacherCourses = teacherCourseService.listTeacherCourse(pageNo, pageSize);
        if (CollectionUtils.isEmpty(teacherCourses)) {
            return ResponseMessage.error("数据为空");
        }
        List<TeacherCourseDTO> teacherCourseDTOS = CopyUtil.toList(teacherCourses, TeacherCourseDTO.class);
        log.info("方法执行结束，方法返回值为：" + teacherCourseDTOS);
        return ResponseMessage.ok().put("courseDTOS", teacherCourseDTOS);
    }

    @ApiOperation("添加教师课程")
    @PostMapping("/save")
    public ResponseMessage saveTeacherCourse(@RequestBody TeacherCourseDTO teacherCourseDTO) {
        log.info("saveTeacherCourse方法开始执行,参数为：teacherCourseDTO=" + teacherCourseDTO);
        if (ObjectUtils.isEmpty(teacherCourseDTO)) {
            return ResponseMessage.error("参数为空，请输入参数");
        }
        TeacherCourse teacherCourse = CopyUtil.to(teacherCourseDTO, TeacherCourse.class);

        TeacherCourse saveTeacherCourse = teacherCourseService.saveTeacherCourse(teacherCourse);
        if (saveTeacherCourse == null) {
            return ResponseMessage.error("添加失败");
        }
        log.info("saveTeacherCourse方法执行结束");
        return ResponseMessage.ok("添加成功");
    }

    @ApiOperation("批量添加教师课程")
    @PostMapping(value = "/batchSave")
    public ResponseMessage batchSaveTeacherCourse(@RequestBody List<TeacherCourseDTO> teacherCourseDTOS) {
        log.info("batchSaveTeacherCourse方法开始执行,参数为：teacherCourseDTOS=" + teacherCourseDTOS);

        if (CollectionUtils.isEmpty(teacherCourseDTOS)) {
            return ResponseMessage.error("参数为空，请输入参数");
        }
        List<TeacherCourse> teacherCourses = CopyUtil.toList(teacherCourseDTOS, TeacherCourse.class);

        List<TeacherCourse> saveTeacherCourse = teacherCourseService.batchSaveTeacherCourse(teacherCourses);
        if (CollectionUtils.isEmpty(saveTeacherCourse)) {
            return ResponseMessage.error("添加失败");
        }
        log.info("batchSaveTeacherCourse方法执行结束");

        return ResponseMessage.ok("添加成功");
    }

    @ApiOperation("删除教师课程")
    @DeleteMapping(value = "/delete/")
    public ResponseMessage deleteTeacherCourse(@RequestBody TeacherCourseDTO teacherCourseDTO) {
        log.info("deleteTeacherCourse方法开始执行,参数为：teacherCourseDTO=" + teacherCourseDTO);

        if (ObjectUtils.isEmpty(teacherCourseDTO)) {
            return ResponseMessage.error("参数为空，请输入参数");
        }
        TeacherCourse teacherCourse = CopyUtil.to(teacherCourseDTO, TeacherCourse.class);
        teacherCourseService.deleteTeacherCourse(teacherCourse);
        log.info("deleteTeacherCourse方法执行结束");

        return ResponseMessage.ok("删除成功");
    }

    @ApiOperation("批量删除教师课程")
    @PostMapping(value = "/delete")
    public ResponseMessage batchDeleteTeacherCourse(List<TeacherCourseDTO> teacherCourseDTOS) {
        log.info("batchDeleteTeacherCourse方法开始执行,参数为：teacherCourseDTOS=" + teacherCourseDTOS);

        if (CollectionUtils.isEmpty(teacherCourseDTOS)) {
            return ResponseMessage.error("参数为空，请输入参数");
        }
        List<TeacherCourse> teacherCourses = CopyUtil.toList(teacherCourseDTOS, TeacherCourse.class);
        teacherCourseService.batchDeleteTeacherCourse(teacherCourses);
        log.info("batchDeleteTeacherCourse方法执行结束");
        return ResponseMessage.ok("删除成功");
    }


}

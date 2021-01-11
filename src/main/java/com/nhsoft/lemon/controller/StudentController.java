package com.nhsoft.lemon.controller;

import com.nhsoft.lemon.dto.CourseDTO;
import com.nhsoft.lemon.dto.ScoreDTO;
import com.nhsoft.lemon.dto.StudentDTO;
import com.nhsoft.lemon.exception.GlobalException;
import com.nhsoft.lemon.model.Course;
import com.nhsoft.lemon.model.Student;
import com.nhsoft.lemon.model.extend.ScoreExtend;
import com.nhsoft.lemon.response.ResponseMessage;
import com.nhsoft.lemon.service.ScoreService;
import com.nhsoft.lemon.service.StudentService;
import com.nhsoft.lemon.utils.CopyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wanglei
 */
@RestController
@Api("学生信息模块")
@Slf4j
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ScoreService scoreService;

    @ApiOperation("查询所有学生信息并分页")
    @GetMapping("/list/{pageNo}/{pageSize}")
    public ResponseMessage listAllStudents(@PathVariable(name = "pageNo", required = true) int pageNo,
                                           @PathVariable(name = "pageSize", required = true) int pageSize) {
        List<Student> students = studentService.listAll(pageNo, pageSize);
        if (CollectionUtils.isEmpty(students)) {
            throw new GlobalException("参数为空");
        }
        List<StudentDTO> studentDTOS = CopyUtil.toList(students, StudentDTO.class);
        return ResponseMessage.success("studentDTOS", studentDTOS);
    }

    @ApiOperation("根据学号查询学生所有的成绩")
    @GetMapping("/grade/{stuNo}/{year}")
    public ResponseMessage listStudentAllGrade(@PathVariable(value = "stuNo", required = true) String stuNo,
                                               @PathVariable(value = "year", required = true) String year) {
        if (StringUtils.isEmpty(stuNo) || StringUtils.isEmpty(year)) {
            throw new GlobalException("参数为空");
        }
        List<ScoreExtend> scoreExtends = scoreService.listStudentAllGrade(stuNo, year);
        List<ScoreDTO> scoreDTOS = CopyUtil.toList(scoreExtends, ScoreDTO.class);
        return ResponseMessage.success("scoreDTOS", scoreDTOS);
    }

    @ApiOperation("根据id查询学生信息")
    @GetMapping("/{id}")
    public ResponseMessage readStudent(@PathVariable(name = "id", required = true) Long id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new GlobalException("参数为空");
        }
        Student student = studentService.readStudent(id);
        if (student == null) {
            throw new GlobalException("数据为空");
        }
        StudentDTO studentDTO = CopyUtil.to(student, StudentDTO.class);
        return ResponseMessage.success("studentDTO", studentDTO);
    }

    @ApiOperation("添加学生")
    @PostMapping("/save")
    public ResponseMessage saveStudent(@RequestBody StudentDTO studentDTO) {
        if (ObjectUtils.isEmpty(studentDTO)) {
            throw new GlobalException("参数为空");
        }
        Student student = CopyUtil.to(studentDTO, Student.class);
        Student saveStudent = studentService.saveStudent(student);
        if (saveStudent == null) {
            throw new GlobalException("添加失败");
        }
        return ResponseMessage.success("添加成功");
    }

    @ApiOperation("批量添加学生")
    @PostMapping(value = "/batchSave")
    public ResponseMessage batchSaveStudent(@RequestBody List<StudentDTO> studentDTOS) {
        if (CollectionUtils.isEmpty(studentDTOS)) {
            throw new GlobalException("参数为空");
        }
        List<Student> students = CopyUtil.toList(studentDTOS, Student.class);
        List<Student> studentList = studentService.batchSaveStudent(students);
        if (CollectionUtils.isEmpty(studentList)) {
            throw new GlobalException("添加失败");
        }
        return ResponseMessage.success("添加成功");
    }

    @ApiOperation("删除学生")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseMessage deleteStudent(@PathVariable(value = "id", required = true) Long id) {
        if (ObjectUtils.isEmpty(id)) {
            throw new GlobalException("参数为空");
        }
        studentService.deleteStudent(id);
        return ResponseMessage.success("删除成功");
    }

    @ApiOperation("批量删除课程")
    @PostMapping(value = "/delete")
    public ResponseMessage batchDeleteCourse(List<Long> ids) {
        if (ObjectUtils.isEmpty(ids)) {
            throw new GlobalException("参数为空");
        }
        studentService.batchDeleteStudent(ids);
        return ResponseMessage.success("删除成功");
    }
}

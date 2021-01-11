package com.nhsoft.lemon.controller;

import com.nhsoft.lemon.dto.CourseDTO;
import com.nhsoft.lemon.dto.ScoreDTO;
import com.nhsoft.lemon.dto.StudentDTO;
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
    public ResponseMessage listAllStudents(@PathVariable(name = "pageNo",required = true) int pageNo,
                                           @PathVariable(name = "pageSize",required = true) int pageSize) {
        log.info("listAllStudents开始执行，参数为：pageNo=" + pageNo + ",pageSize=" + pageSize);

        if (pageNo < 0) {
            pageNo = 1;
        }
        if (pageSize <= 0) {
            pageSize = 5;
        }
        List<Student> students = studentService.listAllStudent(pageNo,pageSize);
        if (CollectionUtils.isEmpty(students)) {
            return ResponseMessage.error("数据为空");
        }
        List<StudentDTO> studentDTOS = CopyUtil.toList(students, StudentDTO.class);
        log.info("方法执行结束，方法返回值为：" + studentDTOS);
        return ResponseMessage.ok().put("studentDTOS", studentDTOS);
    }

    @ApiOperation("根据学号查询学生所有的成绩")
    @GetMapping("/grade/{stuNo}/{year}")
    public ResponseMessage listStudentAllGrade(@PathVariable(value = "stuNo",required = true) String stuNo,
                                               @PathVariable(value = "year",required = true) String year){
        if (StringUtils.isEmpty(stuNo) || StringUtils.isEmpty(year)) {
            return ResponseMessage.error("请输入学号和年份");
        }
        List<ScoreExtend> scoreExtends = scoreService.listStudentAllGrade(stuNo, year);
        List<ScoreDTO> scoreDTOS = CopyUtil.toList(scoreExtends, ScoreDTO.class);

        return ResponseMessage.ok().put("scoreDTOS",scoreDTOS);
    }

    @ApiOperation("根据id查询学生信息")
    @GetMapping("/{id}")
    public ResponseMessage readStudent(@PathVariable(name = "id",required = true) Long id) {
        log.info("readCourse方法开始执行,参数为：id=" + id);
        if(ObjectUtils.isEmpty(id)){
            return ResponseMessage.error("参数为空");
        }
        Student student = studentService.readStudent(id);
        if (student == null) {
            return ResponseMessage.error("数据为空");
        }
        StudentDTO studentDTO = CopyUtil.to(student, StudentDTO.class);
        log.info("readCourse方法执行结束，方法返回值为：" + studentDTO);
        return ResponseMessage.ok().put("studentDTO", studentDTO);
    }

    @ApiOperation("添加学生")
    @PostMapping("/save")
    public ResponseMessage saveStudent(@RequestBody StudentDTO studentDTO){
        log.info("saveStudent方法开始执行,参数为：studentDTO=" + studentDTO);
        if(ObjectUtils.isEmpty(studentDTO)){
            return ResponseMessage.error("参数为空，请输入参数");
        }
        Student student = CopyUtil.to(studentDTO, Student.class);

        Student saveStudent = studentService.saveStudent(student);
        if(saveStudent == null){
            return ResponseMessage.error("添加失败");
        }
        log.info("saveStudent方法执行结束");
        return ResponseMessage.ok("添加成功");
    }

    @ApiOperation("批量添加学生")
    @PostMapping(value = "/batchSave")
    public ResponseMessage batchSaveStudent(@RequestBody List<StudentDTO> studentDTOS){
        log.info("batchSaveCourse方法开始执行,参数为：studentDTOS=" + studentDTOS);

        if(CollectionUtils.isEmpty(studentDTOS)){
            return ResponseMessage.error("参数为空，请输入参数");
        }
        List<Student> students = CopyUtil.toList(studentDTOS, Student.class);

        List<Student>  studentList= studentService.batchSaveStudent(students);
        if(CollectionUtils.isEmpty(studentList)){
            return ResponseMessage.error("添加失败");
        }
        log.info("batchSaveStudent方法执行结束");

        return ResponseMessage.ok("添加成功");
    }

    @ApiOperation("删除学生")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseMessage deleteStudent(@PathVariable(value = "id",required = true) Long id){
        log.info("deleteStudent方法开始执行,参数为：id=" + id);

        if(ObjectUtils.isEmpty(id)){
            return ResponseMessage.error("参数为空，请输入参数");
        }

        studentService.deleteStudent(id);
        log.info("deleteStudent方法执行结束");

        return ResponseMessage.ok("删除成功");
    }

    @ApiOperation("批量删除课程")
    @PostMapping(value = "/delete")
    public ResponseMessage batchDeleteCourse(List<Long> ids){
        log.info("batchDeleteCourse方法开始执行,参数为：ids=" + ids);

        if(ObjectUtils.isEmpty(ids)){
            return ResponseMessage.error("参数为空，请输入参数");
        }

        studentService.batchDeleteStudent(ids);
        log.info("batchDeleteCourse方法执行结束");
        return ResponseMessage.ok("删除成功");
    }
}

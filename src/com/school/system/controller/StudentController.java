package com.school.system.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.school.system.domain.*;
import com.school.system.domain.dto.CourseDto;
import com.school.system.domain.dto.ScoreDto;
import com.school.system.domain.dto.TeacherDto;
import com.school.system.service.*;
import com.school.system.util.ModelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private EvaluateService evaluateService;
    @Autowired
    private ScoreService scoreService;

    @GetMapping("login")
    public String login() {
        return "student/login";
    }

    @GetMapping("index")
    public String index(HttpSession session, Model model) {
        JSONArray courses = studentService.getStudentCourses((Integer) session.getAttribute("id"));
        model.addAttribute("courses", courses);
        return "student/index";
    }

    @GetMapping("select")
    public String select(Model model) {
        List<CourseDto> allCourses = courseService.getAllOptionalCourseDtos();
        model.addAttribute("courses", allCourses);
        return "student/select";
    }

    @GetMapping("profile")
    public String profile(HttpSession session, Model model) {
        Student student = studentService.getStudentBySNum((String) session.getAttribute("studentNum"));
        model.addAttribute("student", student);
        return "student/profile";
    }

    @GetMapping("evaluate")
    public String evaluate(String teacherId, Model model) {
        if (teacherId == null || StringUtils.equals("", teacherId)) {
            List<TeacherDto> allTeacherDtos = teacherService.getAllTeacherDtos();
            model.addAttribute("teachers", allTeacherDtos);
            return "student/teachers";
        } else {
            List<Major> majors = majorService.getAllMajors();
            model.addAttribute("majors", majors);
            Teacher teacher = teacherService.getTeacherById(teacherId);
            model.addAttribute("teacher", teacher);
            return "student/teacher";
        }
    }

    @PostMapping("evaluate")
    public String evaluate(Evaluate evaluate) {
        evaluateService.createEvaluate(evaluate);
        return "redirect:/student/evaluate";
    }

    @PostMapping("login")
    public String login(Student student, HttpSession session) {
        Student checkLogin = studentService.checkLogin(student);
        if (checkLogin != null) {
            session.setAttribute("id", checkLogin.getId());
            session.setAttribute("studentNum", checkLogin.getStudentNum());
            session.setAttribute("studentName", checkLogin.getStudentName());
            session.setAttribute("role", "student");
            return "redirect:/student/index";
        }
        return "redirect:/student/login";
    }

    @PostMapping("update")
    public String update(Student student) {
        studentService.updateStudent(student);
        return "redirect:/student/profile";
    }

    @GetMapping("adminUpdate")
    public String update(String id, Model model, HttpSession session) {
        List<Major> majors = majorService.getAllMajors();
        model.addAttribute("majors", majors);
        List<Clazz> clazzs = clazzService.getAllClazzs();
        model.addAttribute("majors", majors);
        model.addAttribute("clazzs", clazzs);
        if (id != null && !StringUtils.equals(id, "")) {
            Student student = studentService.getStudentById(id);
            model.addAttribute("student", student);
        } else {
            model.addAttribute("student", new Student());
        }
        ModelUtil.setModelValue(session, model);
        return "admin/studentUpdate";
    }

    @PostMapping("adminUpdate")
    public String adminUpdate(Student student, HttpSession session) {
        if (student.getId() != null) {
            studentService.updateStudent(student);
        } else {
            if (!studentService.checkExist(student.getStudentNum())) {
                studentService.insertStudent(student);
            } else {
                session.setAttribute("error", "编号重复！");
                return "redirect:/student/adminUpdate";
            }
        }
        return "redirect:/admin/student";
    }

    @RequestMapping("delete")
    public String delete(String id) {
        if (id != null) {
            String ids[] = id.split(",");
            for (String id1 : ids) {
                studentService.deleteById(Integer.parseInt(id1));
            }
        }
        return "redirect:/admin/student";
    }

    @GetMapping("score")
    public String score(HttpSession session, Model model) {
        List<ScoreDto> scoreDtos = scoreService.getScoreDtosByStudentId((Integer) session.getAttribute("id"));
        model.addAttribute("scores", scoreDtos);
        return "student/scores";
    }
}

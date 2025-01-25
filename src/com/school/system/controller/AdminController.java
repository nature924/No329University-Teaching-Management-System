package com.school.system.controller;

import com.school.system.domain.*;
import com.school.system.domain.dto.ClazzDto;
import com.school.system.domain.dto.CourseDto;
import com.school.system.domain.dto.StudentDto;
import com.school.system.domain.dto.TeacherDto;
import com.school.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private StudentService studentService;

    @GetMapping("login")
    public String login() {
        return "admin/login";
    }

    @GetMapping("index")
    public String index() {
        return "redirect:/admin/teacher";
    }

    @GetMapping("teacher")
    public String teacher(Model model) {
//        List<Teacher> allTeachers = teacherService.getAllTeachers();
        List<TeacherDto> allTeachers = teacherService.getAllTeacherDtos();
        model.addAttribute("teachers", allTeachers);
        return "admin/index";
    }

    @GetMapping("student")
    public String student(Model model) {
//        List<Student> allStudents = studentService.getAllStudents();
        List<StudentDto> allStudents = studentService.getAllStudentDtos();
        model.addAttribute("students", allStudents);
        return "admin/student";
    }

    @GetMapping("clazz")
    public String clazz(Model model) {
//        List<Clazz> allClazzs = clazzService.getAllClazzs();
        List<ClazzDto> allClazzs = clazzService.getAllClazzDtos();
        model.addAttribute("clazzs", allClazzs);
        return "admin/clazz";
    }

    @GetMapping("course")
    public String course(Model model) {
//        List<Course> allCourses = courseService.getAllCourses();
        List<CourseDto> allCourses = courseService.getAllCourseDtos();
        model.addAttribute("courses", allCourses);
        return "admin/course";
    }

    @GetMapping("profile")
    public String profile(HttpSession session, Model model) {
        Admin admin = adminService.getAdminByAdminNum((String) session.getAttribute("adminNum"));
        model.addAttribute("admin", admin);
        return "admin/profile";
    }

    @PostMapping("login")
    public String login(Admin admin, HttpSession session) {
        Admin checkLogin = adminService.checkLogin(admin);
        if (checkLogin != null) {
            session.setAttribute("id", checkLogin.getId());
            session.setAttribute("adminNum", checkLogin.getAdminNum());
            session.setAttribute("adminName", checkLogin.getAdminName());
            session.setAttribute("role", "admin");
            return "redirect:/admin/index";
        }
        return "redirect:/admin/login";
    }

    @PostMapping("update")
    public String update(Admin admin, HttpSession session) {
        adminService.updateAdmin(admin);
        session.setAttribute("adminNum", admin.getAdminNum());
        return "redirect:/admin/profile";
    }
}

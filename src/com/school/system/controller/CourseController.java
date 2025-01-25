package com.school.system.controller;

import com.alibaba.druid.util.StringUtils;
import com.school.system.domain.*;
import com.school.system.domain.dto.CourseDto;
import com.school.system.domain.dto.StudentFileDto;
import com.school.system.service.*;
import com.school.system.util.ModelUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("course")
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private PaperService paperService;
    @Autowired
    private StudentService studentService;

    @GetMapping("info")
    public String info(String id, Model model, HttpSession session) {
        CourseDto course = courseService.getCouseDtoById(Integer.parseInt(id));
        model.addAttribute("course", course);
        Score score = scoreService.getScoreByStudentIdAndCourseId((Integer) session.getAttribute("id"), Integer.parseInt(id));
        model.addAttribute("score", score);
        Paper paper = paperService.getPaperByStudentIdAndCourseId((Integer) session.getAttribute("id"), Integer.parseInt(id));
        model.addAttribute("paper", paper);
        return "student/course";
    }

    @GetMapping("teacherInfo")
    public String teacherInfo(String id, Model model) {
        CourseDto course = courseService.getCouseDtoById(Integer.parseInt(id));
        model.addAttribute("course", course);
        List<StudentFileDto> studentDtos = courseService.getCourseStudentsByCourseId(Integer.parseInt(id));
        model.addAttribute("students", studentDtos);
        return "teacher/course";
    }

    @RequestMapping("removeStudent")
    public String removeStudent(String studentId, String courseId) {
        if (studentId != null) {
            String ids[] = studentId.split(",");
            for (String id1 : ids) {
                studentService.removeStudentFromCourseById(Integer.parseInt(id1), Integer.parseInt(courseId));
            }
        }
        return "redirect:/course/teacherInfo?id=" + courseId;
    }

    @GetMapping("teacherScore")
    public String teacherScore(String studentId, String courseId, Model model, HttpSession session) {
        Student studentById = studentService.getStudentById(studentId);
        CourseDto courseDtoById = courseService.getCouseDtoById(Integer.parseInt(courseId));
        model.addAttribute("student", studentById);
        model.addAttribute("course", courseDtoById);
        Score score = scoreService.getScoreByStudentIdAndCourseId(Integer.parseInt(studentId), Integer.parseInt(courseId));
        model.addAttribute(score == null ? new Score() : score);
        ModelUtil.setModelValue(session, model);
        return "teacher/student";
    }

    @PostMapping("teacherScore")
    public String teacherScore(Score score, HttpSession session) {
        System.out.println(score);
        if (score.getScoreScore() == null || StringUtils.equals(score.getScoreScore(), "") || Integer.valueOf(score.getScoreScore()) < 0 ||  Integer.valueOf(score.getScoreScore()) > 100) {
            session.setAttribute("error", "分值不合理!");
            return "redirect:/course/teacherScore?studentId=" + score.getScoreStudentId() + "&courseId=" + score.getScoreCourseId();
        }
        if (score.getId() != null && score.getId() != 0) {
            scoreService.updateScore(score);
        } else {
            scoreService.createScore(score);
        }
        return "redirect:/course/teacherInfo?id=" + score.getScoreCourseId();
    }

    @PostMapping("paper")
    public String paper(HttpSession session, Paper paper, @RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) throws IOException {
        Paper paperDB = paperService.getPaperByStudentIdAndCourseId((Integer) session.getAttribute("id"), paper.getPaperCourseId());
        if (paperDB != null) {
            return "redirect:/course/info?id=" + paper.getPaperCourseId();
        }
        String newFileName = null;
        if (!file.isEmpty()) {
            String path = request.getServletContext().getRealPath("/resource/uploadFile/");
            File dir = new File(path);
            boolean dirExist = dir.exists() || dir.mkdirs();
            if (dirExist) {
                String originalFileName = file.getOriginalFilename();
                newFileName = UUID.randomUUID().toString().replaceAll("-", "") + originalFileName.substring(originalFileName.lastIndexOf("."));
                File newFile = new File(path + "/" + newFileName);
                file.transferTo(newFile);
            }
        }
        paper.setPaperCreateTime(new Date());
        paper.setPaperFile(newFileName);
        paper.setPaperState(1);
        System.out.println("create -> " + paper.toString());
        paperService.createPaper(paper);
        return "redirect:/course/info?id=" + paper.getPaperCourseId();
    }

    @RequestMapping("download")
    public ResponseEntity<byte[]> download(String file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (file != null) {
            File filet = new File(request.getServletContext().getRealPath("/resource/uploadFile/" + file));
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Disposition", "attchement;filename=" + file.substring(0, 10) + file.substring(file.lastIndexOf(".")));
            HttpStatus statusCode = HttpStatus.OK;
            return new ResponseEntity<>(FileUtils.readFileToByteArray(filet), httpHeaders, statusCode);
        }
		return null;
    }

    @GetMapping("adminUpdate")
    public String update(String id, Model model) {
        List<Teacher> allTeachers = teacherService.getAllTeachers();
        model.addAttribute("teachers", allTeachers);
        if (id != null && !StringUtils.equals(id, "")) {
            Course course = courseService.getCouseById(id);
            model.addAttribute("course", course);
        } else {
            model.addAttribute("course", new Course());
        }
        return "admin/courseUpdate";
    }

    @PostMapping("adminUpdate")
    public String adminUpdate(Course course) {
        if (course.getId() != null) {
            courseService.updateCourse(course);
        } else {
            courseService.insertCourse(course);
        }
        return "redirect:/admin/course";
    }

    @RequestMapping("delete")
    public String delete(String id) {
        if (id != null) {
            String ids[] = id.split(",");
            for (String id1 : ids) {
                courseService.deleteById(Integer.parseInt(id1));
            }
        }
        return "redirect:/admin/course";
    }

    @RequestMapping("select")
    public String select(String id, HttpSession session) {
        courseService.insertCourseStudent(id, (Integer) session.getAttribute("id"));
        return "redirect:/student/index";
    }

    @RequestMapping("unSelect")
    public String unSelect(String id, HttpSession session) {
        courseService.deleteCourseStudent(id, (Integer) session.getAttribute("id"));
        return "redirect:/student/index";
    }
}

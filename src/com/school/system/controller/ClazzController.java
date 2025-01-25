package com.school.system.controller;

import com.alibaba.druid.util.StringUtils;
import com.school.system.domain.Clazz;
import com.school.system.domain.Major;
import com.school.system.service.ClazzService;
import com.school.system.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("clazz")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;
    @Autowired
    private MajorService majorService;

    @GetMapping("adminUpdate")
    public String update(String id, Model model) {
        List<Major> majors = majorService.getAllMajors();
        model.addAttribute("majors", majors);
        if (id != null && !StringUtils.equals(id, "")) {
            Clazz clazz = clazzService.getClazzById(id);
            model.addAttribute("clazz", clazz);
        } else {
            model.addAttribute("clazz", new Clazz());
        }
        return "admin/clazzUpdate";
    }

    @PostMapping("adminUpdate")
    public String adminUpdate(Clazz clazz, HttpSession session) {
        if (clazz.getId() != null) {
            clazzService.updateClazz(clazz);
        } else {
        	if(!clazzService.checkExist(clazz.getClazzName())) {
        		clazzService.insertClazz(clazz);
        	}else {
        		session.setAttribute("error", "名称重复！");
        		/*return "redirect:/clazz/adminUpdate";*/
        		return "redirect:/clazz/adminUpdate";
        	}
        }
        return "redirect:/admin/clazz";
    }

    @RequestMapping("delete")
    public String delete(String id) {
        if (id != null) {
            String ids[] = id.split(",");
            for (String id1 : ids) {
                clazzService.deleteById(Integer.parseInt(id1));
            }
        }
        return "redirect:/admin/clazz";
    }
}

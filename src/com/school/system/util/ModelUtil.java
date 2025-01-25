package com.school.system.util;

import com.alibaba.druid.util.StringUtils;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public class ModelUtil {
    public static void setModelValue(HttpSession session, Model model) {
        String[] types = {"message", "success", "warning", "error"};
        Object valueObj;
        String value;
        for (String type : types) {
            valueObj = session.getAttribute(type);
            if (valueObj != null && !StringUtils.equals(value = (String) valueObj, "")) {
                model.addAttribute(type, value);
                session.removeAttribute(type);
            }
        }
    }
}

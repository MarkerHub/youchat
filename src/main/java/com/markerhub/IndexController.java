package com.markerhub;

import com.markerhub.service.RedisService;
import com.markerhub.vo.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisService redisService;

    @GetMapping({"/login"})
    public String login() {
        return "login";
    }

    @PostMapping({"/login"})
    public String doLogin(String username, HttpSession session) {

        if (!StringUtils.hasLength(username)) {
            return "redirect:/login";
        }

        session.setAttribute("username", username);

        return "redirect:/index";
    }

    @GetMapping({"", "/", "/index"})
    public Object index(HttpServletRequest req, HttpSession session) {

        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }

        req.setAttribute("username", session.getAttribute("username"));

        return "index";
    }

    /**
     * 测试距离
     */
    @ResponseBody
    @PostMapping("/range")
    public List<Location> range(HttpSession session, double lng, double lat) {

        String username = (String) session.getAttribute("username");

        redisService.addLocation(username, lng, lat);

        List<Location> cityDistances = redisService.getCityDistance(username);

        return cityDistances;
    }


}

package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.ZonedDateTime;

@Controller
public class IndexController {

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(ModelMap modelMap) {

        String time = getTime();
        modelMap.put("time", time);

        return "index";
    }

    @Cacheable(cacheNames = "addresses")
    public String getTime() {

        if(cacheManager.getCache("addresses").get("now") != null) {
            return cacheManager.getCache("addresses").get("now").get().toString();
        }

        ZonedDateTime now = ZonedDateTime.now();
        cacheManager.getCache("addresses").put("now", now);

        return now.toString();
    }
}

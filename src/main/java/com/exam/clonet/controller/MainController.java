package com.exam.clonet.controller;

import com.exam.clonet.domain.Message;
import com.exam.clonet.domain.User;
import com.exam.clonet.repos.MessageRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private MessageRepos messageRepos;

    @GetMapping ("/")
    public String greeting(Map<String, Object> model){
        return  "greeting";
    }
    @GetMapping ("/main")
    public String main (Map <String,Object> model){
        Iterable<Message> messages= messageRepos.findAll();
        model.put("messages", messages);
        return "main";
    }
    @PostMapping ("/add")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag, Map <String,Object> model){
        Message message=new Message(text,tag,user);
        messageRepos.save(message);
        Iterable<Message> messages=messageRepos.findAll();
        model.put("messages",messages);
        return "main";
    }
    @PostMapping ("filter")
    public String filter(@RequestParam String filter, Map<String,Object> model){
        Iterable<Message> messages;
        if(filter !=null && !filter.isEmpty()) {
            messages = messageRepos.findByTag(filter);
        }else {
            messages = messageRepos.findAll();
        }
        model.put("messages" , messages);
        return "main";
    }
}

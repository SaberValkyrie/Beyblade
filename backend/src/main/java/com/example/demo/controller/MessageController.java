package com.example.demo.controller;

import com.example.demo.entity.Greeting;
import com.example.demo.entity.ChatGlobal;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(ChatGlobal message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + message.text + "!");
    }
}

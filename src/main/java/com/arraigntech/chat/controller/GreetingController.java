package com.arraigntech.chat.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.arraigntech.chat.model.InputString;
import com.arraigntech.chat.model.Message;

@Controller
public class GreetingController {
	
	Map<String,String> data = new HashMap<>();
	
    @MessageMapping("/hello")
    @SendTo("/topic/message")
    public Message greeting(InputString message) throws Exception {
    	return getMessage(message);
    }
    
    public Message getMessage(InputString message) {
    	Set<String> inputSet = new HashSet<String>(Arrays.asList(message.getMessage().split(" ")));
    	for(Map.Entry<String, String> da : data.entrySet()) {
    		Set<String> keyString = new HashSet<String>(Arrays.asList(da.getKey().split(" ")));
    		if(keyString.containsAll(inputSet)) {
    			return Message.builder().content(da.getValue()).build();
    		}
    	}
    	
    	return Message.builder().content("i couldn't understand, please enter again").build();
    }
    
    @PostConstruct
    public void init() {
    	data.put("Hey Service, can you provide me a question with numbers to add? ", "Here you go, solve the question: “Please sum the numbers 9,5,3”.");
    	data.put("Hello Hey Hi", "Welcome user");
    	data.put("Great. The original question was “Please sum the numbers 9,5,3” and the answer is 15",
    			"That’s wrong. Please try again.");
    	data.put("Sorry, the original question was “Please sum the numbers 9,5,3” and the answer is 17 . ",
    			"That’s great ");
    }
}

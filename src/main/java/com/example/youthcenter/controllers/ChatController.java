package com.example.youthcenter.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.text.SimpleDateFormat;

@Controller
public class ChatController {
//    @Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;
//
//    @MessageMapping("/secured/room")
//    public void sendSpecific(
//            @Payload Message msg,
//            Principal user,
//            @Header("simpSessionId") String sessionId) throws Exception {
//        OutputMessage out = new OutputMessage(
//                msg.getFrom(),
//                msg.getText(),
//                new SimpleDateFormat("HH:mm").format(new Date()));
//        simpMessagingTemplate.convertAndSendToUser(
//                msg.getTo(), "/secured/user/queue/specific-user", out);
//    }
}

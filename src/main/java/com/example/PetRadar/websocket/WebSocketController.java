package com.example.PetRadar.websocket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Controller
public class WebSocketController {
    @EventListener
    public void handleSubscribed(SessionSubscribeEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        //System.out.println(event.getUser());
        //String userId = event.getUser().getName();
        //System.out.println("subscribed : " + userId);
    }
}

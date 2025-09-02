package com.example.PetRadar.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/sse")
public class SseController {
    private final SseRepository sseRepository;

    @GetMapping("/{userId}")
    public SseEmitter connect(@PathVariable Long userId){
        SseEmitter emitter = new SseEmitter();
        sseRepository.save(userId, emitter);
        emitter.onCompletion(() -> sseRepository.delete(userId));
        emitter.onTimeout(() -> sseRepository.delete(userId));
        emitter.onError((error) -> sseRepository.delete(userId));

        // heartbeat 전송
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("heartbeat")
                        .data("ping"));
            } catch (IOException e) {
                emitter.completeWithError(e);
                throw new RuntimeException(e);
            }
        }, 0, 15, TimeUnit.SECONDS);

        return emitter;
    }
}

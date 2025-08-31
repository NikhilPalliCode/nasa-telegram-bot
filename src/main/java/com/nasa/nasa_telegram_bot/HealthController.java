package com.nasa.nasa_telegram_bot;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/")
    public String home() {
        return "🚀 NASA Telegram Bot is running!";
    }
    
    @GetMapping("/health")
    public String health() {
        return "✅ OK - Bot is healthy and running";
    }
    
    @GetMapping("/status")
    public String status() {
        return "🟢 Online - Ready to receive Telegram messages";
    }
}

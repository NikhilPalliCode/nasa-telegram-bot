package com.nasa.nasa_telegram_bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class NasaBot extends TelegramLongPollingBot {
    
    private final String botUsername;
    private final String botToken;
    
    public NasaBot(@Value("${telegram.bot.token}") String botToken,
                  @Value("${telegram.bot.username}") String botUsername) {
        super(botToken);
        this.botToken = botToken;
        this.botUsername = botUsername;
        
        System.out.println("✅ NASA Bot Initialized");
        System.out.println("🤖 Username: " + botUsername);
        
        // Check if using real token
        if (!botToken.equals("local_dummy_token_123") && !botToken.startsWith("local_")) {
            System.out.println("🎉 Ready to receive Telegram messages!");
            System.out.println("🔑 Token: " + botToken.substring(0, 10) + "...");
        } else {
            System.out.println("ℹ️  Local mode - Using dummy token");
        }
    }
    
    @Override
    public String getBotUsername() {
        return botUsername;
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("📨 Received message from Telegram");
        
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            
            System.out.println("💬 Message: " + messageText);
            
            if (messageText.equalsIgnoreCase("/start")) {
                sendMessage(chatId, "🚀 Welcome to NASA APOD Bot! Use /apod");
            } else if (messageText.equalsIgnoreCase("/apod")) {
                sendMessage(chatId, "🌌 NASA Picture: https://apod.nasa.gov/apod/astropix.html");
            }
        }
    }
    
    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        
        try {
            execute(message);
            System.out.println("✅ Message sent to: " + chatId);
        } catch (TelegramApiException e) {
            System.out.println("❌ Error sending message: " + e.getMessage());
        }
    }
}
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
    
    public NasaBot(@Value("${telegram.bot.token}") String botToken,
                  @Value("${telegram.bot.username}") String botUsername) {
        super(botToken);
        this.botUsername = botUsername;
        System.out.println("✅ Bot initialized: " + botUsername);
    }
    
    @Override
    public String getBotUsername() {
        return botUsername;
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            
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
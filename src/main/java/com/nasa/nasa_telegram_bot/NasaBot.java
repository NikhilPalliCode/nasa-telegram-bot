package com.nasa.nasa_telegram_bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import jakarta.annotation.PostConstruct;

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
    
    // ADD THIS METHOD - This registers the bot with Telegram
    @PostConstruct
    public void registerBot() {
        // Skip registration for dummy tokens
        if (botToken.equals("local_dummy_token_123") || botToken.startsWith("local_")) {
            System.out.println("ℹ️  Local mode - Skipping Telegram registration");
            return;
        }
        
        try {
            System.out.println("⏳ Registering bot with Telegram...");
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
            System.out.println("🎉 Bot registered successfully with Telegram!");
            System.out.println("✅ Now listening for messages...");
        } catch (TelegramApiRequestException e) {
            System.err.println("❌ Telegram API error: " + e.getMessage());
            if (e.getErrorCode() == 401) {
                System.err.println("⚠️  Invalid bot token - check your Railway environment variables");
            }
        } catch (Exception e) {
            System.err.println("❌ Failed to register bot: " + e.getMessage());
            e.printStackTrace();
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
            String user = update.getMessage().getFrom().getUserName();
            
            System.out.println("👤 User: @" + user + ", Chat ID: " + chatId + ", Message: " + messageText);
            
            if (messageText.equalsIgnoreCase("/start")) {
                sendMessage(chatId, "🚀 Welcome to NASA APOD Bot! Use /apod to get today's astronomy picture.");
            } else if (messageText.equalsIgnoreCase("/apod")) {
                sendMessage(chatId, "🌌 NASA Astronomy Picture of the Day: https://apod.nasa.gov/apod/astropix.html");
            } else {
                sendMessage(chatId, "🤖 I understand: /start and /apod");
            }
        }
    }
    
    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        
        try {
            execute(message);
            System.out.println("✅ Message sent to chat: " + chatId);
        } catch (TelegramApiException e) {
            System.err.println("❌ Failed to send message: " + e.getMessage());
        }
    }
}
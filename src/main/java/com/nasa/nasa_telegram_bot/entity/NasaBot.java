package com.nasa.nasa_telegram_bot.entity;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.nasa.nasa_telegram_bot.service.NasaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class NasaBot extends TelegramLongPollingBot {
    
    private static final Logger logger = LoggerFactory.getLogger(NasaBot.class);
    
    private final NasaService nasaService;
    private final String botUsername;
    private final String botToken;
    
    public NasaBot(NasaService nasaService,
                  @Value("${telegram.bot.username}") String botUsername,
                  @Value("${telegram.bot.token}") String botToken) {
        super(botToken); // This sets up long polling automatically
        this.nasaService = nasaService;
        this.botUsername = botUsername;
        this.botToken = botToken;
        logger.info("🤖 NASA Bot initialized with long polling");
        logger.info("📝 Bot username: {}", botUsername);
    }
    
    @Override
    public String getBotUsername() {
        return botUsername;
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        logger.info("📨 Received message from Telegram");
        
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            String userName = update.getMessage().getChat().getUserName();
            
            logger.info("👤 User: @{}, Chat ID: {}, Message: {}", userName, chatId, messageText);
            
            if (messageText.equalsIgnoreCase("/start")) {
                sendWelcomeMessage(chatId);
            } else if (messageText.equalsIgnoreCase("/apod") || 
                      messageText.equalsIgnoreCase("nasa") ||
                      messageText.equalsIgnoreCase("picture of the day")) {
                sendAstronomyPicture(chatId);
            } else {
                sendHelpMessage(chatId);
            }
        }
    }
    
    private void sendWelcomeMessage(Long chatId) {
        String welcomeText = "🚀 *Welcome to NASA APOD Bot!*\n\n" +
                           "I can show you NASA's Astronomy Picture of the Day!\n\n" +
                           "Available commands:\n" +
                           "/apod - Get today's astronomy picture\n" +
                           "/start - Show this welcome message\n\n" +
                           "Just type 'nasa' or 'picture of the day' to get started!";
        
        sendMessage(chatId, welcomeText, "Welcome message");
    }
    
    private void sendAstronomyPicture(Long chatId) {
        logger.info("🌌 Fetching NASA APOD for chat: {}", chatId);
        String apodInfo = nasaService.getAstronomyPictureOfTheDay();
        sendMessage(chatId, apodInfo, "APOD data");
    }
    
    private void sendHelpMessage(Long chatId) {
        String helpText = "🤖 *NASA APOD Bot Help*\n\n" +
                        "I understand these commands:\n" +
                        "• /start - Welcome message\n" +
                        "• /apod - Get astronomy picture of the day\n" +
                        "• 'nasa' - Get astronomy picture\n" +
                        "• 'picture of the day' - Get astronomy picture";
        
        sendMessage(chatId, helpText, "Help message");
    }
    
    private void sendMessage(Long chatId, String text, String messageType) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        message.setParseMode("Markdown");
        message.disableWebPagePreview();
        
        try {
            execute(message);
            logger.info("✅ {} sent successfully to chat: {}", messageType, chatId);
        } catch (TelegramApiException e) {
            logger.error("❌ Failed to send {}: {}", messageType, e.getMessage(), e);
        }
    }
}
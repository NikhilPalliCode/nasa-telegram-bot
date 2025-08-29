package com.nasa.nasa_telegram_bot.entity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.nasa.nasa_telegram_bot.service.NasaService;

import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class NasaBot extends org.telegram.telegrambots.bots.TelegramLongPollingBot {
    
    private static final Logger logger = LoggerFactory.getLogger(NasaBot.class);
    
    @Autowired
    private NasaService nasaService;
    
    @Autowired
    private TelegramBotsApi telegramBotsApi;
    
    private final String botUsername;
    private final String botToken;
    
    public NasaBot(@Value("${telegram.bot.username}") String botUsername,
                  @Value("${telegram.bot.token}") String botToken) {
        super(botToken);
        this.botUsername = botUsername;
        this.botToken = botToken;
        logger.info("NasaBot constructor called with username: {}", botUsername);
    }
    
    @PostConstruct
    public void registerBot() {
        try {
            logger.info("Attempting to register Telegram bot: {}", botUsername);
            telegramBotsApi.registerBot(this);
            logger.info("✅ NASA Bot registered successfully!");
            logger.info("🤖 Bot username: {}", botUsername);
            logger.info("🔑 Bot token: {}", botToken.substring(0, 10) + "..."); // Log partial token for security
        } catch (TelegramApiException e) {
            logger.error("❌ Failed to register bot: {}", e.getMessage(), e);
        } catch (Exception e) {
            logger.error("❌ Unexpected error during bot registration: {}", e.getMessage(), e);
        }
    }
    
    @Override
    public String getBotUsername() {
        return botUsername;
    }
    
    @Override
    public void onUpdateReceived(Update update) {
        logger.debug("Received update: {}", update);
        
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            String userName = update.getMessage().getChat().getUserName();
            
            logger.info("📨 Message from @{} ({}): {}", userName, chatId, messageText);
            
            if (messageText.equalsIgnoreCase("/start")) {
                logger.info("Processing /start command");
                sendWelcomeMessage(chatId);
            } else if (messageText.equalsIgnoreCase("/apod") || 
                      messageText.equalsIgnoreCase("nasa") ||
                      messageText.equalsIgnoreCase("picture of the day")) {
                logger.info("Processing APOD request");
                sendAstronomyPicture(chatId);
            } else {
                logger.info("Processing unknown command: {}", messageText);
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
        
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(welcomeText);
        message.setParseMode("Markdown");
        
        try {
            execute(message);
            logger.info("✅ Welcome message sent to chat: {}", chatId);
        } catch (TelegramApiException e) {
            logger.error("❌ Failed to send welcome message: {}", e.getMessage(), e);
        }
    }
    
    private void sendAstronomyPicture(Long chatId) {
        logger.info("Fetching NASA APOD for chat: {}", chatId);
        String apodInfo = nasaService.getAstronomyPictureOfTheDay();
        
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(apodInfo);
        message.setParseMode("Markdown");
        message.disableWebPagePreview();
        
        try {
            execute(message);
            logger.info("✅ APOD sent to chat: {}", chatId);
        } catch (TelegramApiException e) {
            logger.error("❌ Failed to send APOD: {}", e.getMessage(), e);
        }
    }
    
    private void sendHelpMessage(Long chatId) {
        String helpText = "🤖 *NASA APOD Bot Help*\n\n" +
                        "I understand these commands:\n" +
                        "• /start - Welcome message\n" +
                        "• /apod - Get astronomy picture of the day\n" +
                        "• 'nasa' - Get astronomy picture\n" +
                        "• 'picture of the day' - Get astronomy picture";
        
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(helpText);
        message.setParseMode("Markdown");
        
        try {
            execute(message);
            logger.info("✅ Help message sent to chat: {}", chatId);
        } catch (TelegramApiException e) {
            logger.error("❌ Failed to send help message: {}", e.getMessage(), e);
        }
    }
}
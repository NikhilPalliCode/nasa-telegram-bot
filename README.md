# 🚀 NASA Telegram Bot

A Spring Boot Telegram bot that provides NASA's Astronomy Picture of the Day (APOD) on demand. Users can interact with the bot to get daily astronomy pictures directly from NASA's API.

## 🌟 Features

- **/start** - Welcome message and instructions
- **/apod** - Get today's NASA Astronomy Picture of the Day
- **Real-time updates** - Instant responses via Telegram
- **NASA API integration** - Fetches latest astronomy pictures
- **Cloud deployed** - Always available on Railway

## 🛠️ Technology Stack

- **Java 17** - Programming language
- **Spring Boot 3.5.5** - Framework
- **Telegram Bot API** - Bot communication
- **NASA APOD API** - Astronomy pictures
- **Railway** - Cloud deployment
- **Maven** - Dependency management

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Telegram account
- NASA API key (free)

## 🚀 Quick Start

### 1. Clone the Repository
```bash
git clone https://github.com/NikhilPalliCode/nasa-telegram-bot.git
cd nasa-telegram-bot
```

### 2. Configure Environment Variables
Create `application.properties`:
```properties
spring.application.name=nasa-telegram-bot

# NASA API Configuration
nasa.api.key=DEMO_KEY

# Telegram Bot Configuration
telegram.bot.token=YOUR_BOT_TOKEN
telegram.bot.username=YOUR_BOT_USERNAME
```

### 3. Run Locally
```bash
mvn clean spring-boot:run
```

### 4. Test the Bot
1. Open Telegram
2. Search for your bot username
3. Send `/start` or `/apod`

## 🌐 Deployment

### Railway Deployment
1. Fork this repository
2. Connect to [Railway](https://railway.app)
3. Set environment variables:
   - `TELEGRAM_BOT_TOKEN`
   - `TELEGRAM_BOT_USERNAME` 
   - `NASA_API_KEY`
4. Deploy automatically on git push

### Environment Variables
| Variable | Description | Example |
|----------|-------------|---------|
| `TELEGRAM_BOT_TOKEN` | Telegram bot token from @BotFather | `123456789:ABCdefGHIjklMNopqrSTuvwXYZ` |
| `TELEGRAM_BOT_USERNAME` | Telegram bot username | `MyNasaBot` |
| `NASA_API_KEY` | NASA API key from [api.nasa.gov](https://api.nasa.gov) | `DEMO_KEY` (or your key) |

## 🎯 Usage

### Telegram Commands
- `/start` - Show welcome message and instructions
- `/apod` - Get today's astronomy picture
- `nasa` - Alternative trigger for APOD

### Example Interaction
```
User: /start
Bot: 🚀 Welcome to NASA APOD Bot! Use /apod to get today's astronomy picture.

User: /apod  
Bot: 🌌 NASA Astronomy Picture of the Day (2024-01-15)
    📷 Title: Amazing Nebula
    📝 Explanation: This stunning nebula...
    🖼️ [View Image](https://apod.nasa.gov/apod/image/2401/nebula.jpg)
```

## 📁 Project Structure

```
nasa-telegram-bot/
├── src/main/java/com/nasa/nasa_telegram_bot/
│   ├── NasaTelegramBotApplication.java  # Main application
│   ├── NasaBot.java                     # Telegram bot handler
│   └── controller/
│       └── HealthController.java        # Health endpoints
├── src/main/resources/
│   └── application.properties           # Configuration
├── pom.xml                             # Maven dependencies
└── README.md
```

## 🔧 Configuration

### application.properties
```properties
# Server Configuration
server.port=${PORT:8080}

# NASA API
nasa.api.key=${NASA_API_KEY:DEMO_KEY}

# Telegram Bot
telegram.bot.token=${TELEGRAM_BOT_TOKEN}
telegram.bot.username=${TELEGRAM_BOT_USERNAME}

# Logging
logging.level.com.nasa=INFO
```

## 🐛 Troubleshooting

### Common Issues
1. **Bot not responding**: Check Railway logs for registration errors
2. **Invalid token**: Verify Telegram bot token format
3. **NASA API limits**: Use your own API key for higher limits
4. **Port conflicts**: Remove `server.port` for Railway deployment

### Logs Check
Look for these success messages:
```
✅ NASA Bot Initialized
🎉 Bot registered successfully with Telegram!
✅ Now listening for messages...
```

## 📞 Support

If you encounter issues:
1. Check Railway deployment logs
2. Verify environment variables
3. Ensure bot token is valid
4. Test NASA API key separately

## 🤝 Contributing

1. Fork the project
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 🙏 Acknowledgments

- [NASA APOD API](https://api.nasa.gov) for astronomy pictures
- [Telegram Bot API](https://core.telegram.org/bots/api) for bot platform
- [Spring Boot](https://spring.io/projects/spring-boot) for framework
- [Railway](https://railway.app) for deployment platform

---

⭐ **Star this repo if you found it helpful!**

🚀 **Happy stargazing with your NASA Telegram bot!**

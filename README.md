# 🚀 NASA Telegram Bot

A Spring Boot Telegram bot that provides NASA's Astronomy Picture of the Day (APOD) on demand.

## 🌟 Live Demo

**Try the bot now: [Start Chatting](https://t.me/Nasa_TelePOD_Bot)**

## ✨ Features
- `/start` - Welcome message and instructions
- `/apod` - Get today's NASA Astronomy Picture of the Day
- Real-time updates via Telegram
- NASA API integration

## 🛠️ Tech Stack
- Java 17 + Spring Boot 3.5.5
- Telegram Bot API + NASA APOD API
- Maven + Railway Deployment

## 🚀 Quick Start

```bash
git clone https://github.com/NikhilPalliCode/nasa-telegram-bot.git
cd nasa-telegram-bot

# Set environment variables in application.properties
mvn clean spring-boot:run
```

## ⚙️ Environment Variables
```properties
telegram.bot.token=YOUR_BOT_TOKEN
telegram.bot.username=YOUR_BOT_USERNAME  
nasa.api.key=YOUR_NASA_API_KEY
```

## 🎯 Usage
1. **Click: [Start Chatting](https://t.me/YourBotUsername)**
2. Send `/start` for welcome message
3. Send `/apod` to get today's astronomy picture

## 📦 Deployment
Deployed on Railway with automatic deployments on git push.

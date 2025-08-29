package com.nasa.nasa_telegram_bot.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class NasaService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${nasa.api.key}")
    private String nasaApiKey;
    
    private static final String APOD_URL = "https://api.nasa.gov/planetary/apod?api_key=";
    
    public String getAstronomyPictureOfTheDay() {
        try {
            String url = APOD_URL + nasaApiKey;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            
            String title = root.path("title").asText();
            String explanation = root.path("explanation").asText();
            String imageUrl = root.path("url").asText();
            String date = root.path("date").asText();
            
            return String.format("🌌 *NASA Astronomy Picture of the Day* (%s)\n\n" +
                               "📷 *Title:* %s\n\n" +
                               "📝 *Explanation:* %s\n\n" +
                               "🖼️ [View Image](%s)",
                               date, title, explanation, imageUrl);
            
        } catch (Exception e) {
            return "❌ Sorry, I couldn't fetch the astronomy picture of the day. Please try again later.";
        }
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

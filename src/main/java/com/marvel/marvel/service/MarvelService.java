package com.marvel.marvel.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marvel.marvel.model.Character;
import com.marvel.marvel.model.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.marvel.marvel.repository.LogRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MarvelService {

    private static final String MARVEL_API_URL = "https://gateway.marvel.com:443";
    @Value("${marvel.publicKey}")
    private String publicKey;
    @Value("${marvel.privateKey}")
    private String privateKey;

    @Autowired
    private LogService logService;
    @Autowired
    private LogRepository logRepository;

    public List<Character> getCharacters() {
        String timestamp = generateTimestamp();
        String hash = generateHash(timestamp);
        String url = MARVEL_API_URL + "/characters?apikey=" + publicKey + "&ts=" + timestamp + "&hash=" + hash;
        System.out.println(url);
        ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(url, String.class);
        logService.createLogEntry(null, url);
        return parseCharacterResponse(responseEntity.getBody());
    }

    public Character getCharacter(Long id) {
        String timestamp = generateTimestamp();
        String hash = generateHash(timestamp);
        String url = MARVEL_API_URL + "/characters/" + id + "?apikey=" + publicKey + "&ts=" + timestamp + "&hash=" + hash;
        ResponseEntity<String> responseEntity = new RestTemplate().getForEntity(url, String.class);
        logService.createLogEntry(id, url);
        return parseSingleCharacterResponse(responseEntity.getBody());
    }

    private List<Character> parseCharacterResponse(String responseBody) {
        List<Character> characters = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody).path("data").path("results");
            for (JsonNode characterNode : rootNode) {
                Character character = new Character();
                character.setId(characterNode.path("id").asLong());
                character.setName(characterNode.path("name").asText());
                character.setDescription(characterNode.path("description").asText());
                characters.add(character);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return characters;
    }

    private Character parseSingleCharacterResponse(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(responseBody).path("data").path("results").get(0);
            Character character = new Character();
            character.setId(rootNode.path("id").asLong());
            character.setName(rootNode.path("name").asText());
            character.setDescription(rootNode.path("description").asText());
            return character;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String generateTimestamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    private String generateHash(String timestamp) {
        try {
            String toHash = timestamp + privateKey + publicKey;
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(toHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void saveLogEntry(LogEntry logEntry) {

        logRepository.save(logEntry);
    }
}

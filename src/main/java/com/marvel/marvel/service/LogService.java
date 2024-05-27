package com.marvel.marvel.service;

import com.marvel.marvel.model.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.marvel.marvel.repository.LogRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public List<LogEntry> getLogs() {

        return logRepository.findAll();
    }

    public void saveLogEntry(LogEntry logEntry) {
        logRepository.save(logEntry);
    }

    public void createLogEntry(Long characterId, String endpoint) {
        LogEntry logEntry = new LogEntry();
        logEntry.setCharacterId(characterId);
        logEntry.setEndpoint(endpoint);
        logEntry.setTimestamp(LocalDateTime.now().toString());
        saveLogEntry(logEntry);
    }
}

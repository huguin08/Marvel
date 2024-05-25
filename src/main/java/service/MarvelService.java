package service;

import model.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.LogRepository;

import java.util.List;

@Service
public class MarvelService {

    public List<Character> getCharacters() {

    }

    public Character getCharacter(Long id) {

    }

    @Autowired
    private LogRepository logRepository;

    public void saveLogEntry(LogEntry logEntry) {
        logRepository.save(logEntry);
    }
}

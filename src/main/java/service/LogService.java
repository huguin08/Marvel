package service;

import model.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.LogRepository;

import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public List<LogEntry> getLogs() {
        return logRepository.findAll();
    }
}

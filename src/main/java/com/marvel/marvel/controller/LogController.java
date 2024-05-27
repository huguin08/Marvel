package com.marvel.marvel.controller;

import com.marvel.marvel.model.LogEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.marvel.marvel.service.LogService;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping
    public List<LogEntry> getLogs() {
        return logService.getLogs();
    }
}

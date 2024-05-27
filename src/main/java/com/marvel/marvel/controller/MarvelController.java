package com.marvel.marvel.controller;

import com.marvel.marvel.model.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.marvel.marvel.service.MarvelService;

import java.util.List;

@RestController
@RequestMapping("api/marvel")
public class MarvelController {

    @Autowired
    private MarvelService marvelService;

    @GetMapping("/characters")
    public List<Character> getCharacters() {
        System.out.println("entre **********************************");
        return marvelService.getCharacters();
    }

    @GetMapping("/characters/{id}")
    public Character getCharacter(@PathVariable Long id) {
        return marvelService.getCharacter(id);
    }
}

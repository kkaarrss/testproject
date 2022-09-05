package com.kars.test.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.kars.test.dto.PersonDto;
import com.kars.test.mapper.PersonDtoMapper;
import com.kars.test.repositories.PersonRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class Controller{
    private final PersonRepository personRepository;
    private final PersonDtoMapper personDtoMapper;

    @GetMapping("/persons/{id}")
    public PersonDto test(@PathVariable Long id)  {
        return personDtoMapper.map(personRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Unable to find resource")));
    }
}

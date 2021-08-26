package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.exception.InvalidIdException;
import com.epam.esm.exception.InvalidNameException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagServiceImpl tagService = TagServiceImpl.getInstance();

    @GetMapping
    public List<TagDTO> getTags() throws NotFoundException {
        return tagService.getTags();
    }

    @GetMapping(value = "/id")
    @ExceptionHandler({NotFoundException.class, InvalidIdException.class})
    //todo handle exceptions
    public TagDTO getTagById(@RequestParam("id") long id) throws NotFoundException, InvalidIdException {
        return tagService.getTagById(id);
    }

    @GetMapping(value = "/name")
    @ExceptionHandler({NotFoundException.class, javax.naming.InvalidNameException.class})
    //todo handle exceptions
    public TagDTO getTagById(@RequestParam("name") String name) throws NotFoundException, InvalidNameException {
        return tagService.getTagByName(name);
    }

    @PostMapping
    public TagDTO addTag(@RequestBody TagDTO tagDTO) throws InvalidNameException {
        return tagService.addTag(tagDTO);
    }

    @DeleteMapping
    public boolean deleteTagById(@RequestParam("id") long id) throws NotFoundException, InvalidIdException {
        return tagService.deleteTagById(id);
    }
}

package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tags")
public class TagController {

    private final TagServiceImpl tagService;

    @Autowired
    public TagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDTO> getTags() {
        return tagService.getTags();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDTO getTagById(@PathVariable("id") long id) {
        return tagService.getTagById(id);
    }

    @GetMapping(value = "/name")
    @ResponseStatus(HttpStatus.OK)
    public TagDTO getTagByName(@RequestParam String name) {
        return tagService.getTagByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public TagDTO addTag(@RequestBody TagDTO tagDTO) {
        return tagService.addTag(tagDTO);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteTagById(@PathVariable("id") long id) {
        return tagService.deleteTagById(id);
    }
}

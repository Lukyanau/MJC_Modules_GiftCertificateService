package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> getTags() {
        return tagService.getTags();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto getTagById(@PathVariable("id") long id) {
        return tagService.getTagById(id);
    }

    @GetMapping(value = "/name")
    @ResponseStatus(HttpStatus.OK)
    public TagDto getTagByName(@RequestParam String name) {
        return tagService.getTagByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto addTag(@RequestBody TagDto tagDto) {
        return tagService.addTag(tagDto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteTagById(@PathVariable("id") long id) {
        return tagService.deleteTagById(id);
    }
}

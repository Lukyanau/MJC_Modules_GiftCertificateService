package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<TagDTO> getTags() {
        return tagService.getTags();
    }

    @GetMapping(value = "/id")
    public TagDTO getTagById(@RequestParam("id") long id) {
        System.out.println("invalid id");
        return tagService.getTagById(id);
    }

    @GetMapping(value = "/name")
    public TagDTO getTagByName(@RequestParam("name") String name) {
        return tagService.getTagByName(name);
    }

    @PostMapping
    public TagDTO addTag(@RequestBody TagDTO tagDTO) {
        return tagService.addTag(tagDTO);
    }

    @DeleteMapping
    public boolean deleteTagById(@RequestParam("id") long id) {
        return tagService.deleteTagById(id);
    }
}

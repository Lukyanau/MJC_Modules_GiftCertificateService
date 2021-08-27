package com.epam.esm.controller;

import com.epam.esm.dto.TagDTO;
import com.epam.esm.service.impl.TagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tags")
public class TagController {
    private final TagServiceImpl tagService;

    @Autowired
    public TagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    @ResponseBody
    public List<TagDTO> getTags() {
        System.out.println("server is working");
        return tagService.getTags();
    }

    @GetMapping(value = "/id")
    @ResponseBody
    public TagDTO getTagById(@RequestParam("id") long id) {
        return tagService.getTagById(id);
    }

    @GetMapping(value = "/name")
    @ResponseBody
    public TagDTO getTagById(@RequestParam("name") String name) {
        return tagService.getTagByName(name);
    }

    @PostMapping
    @ResponseBody
    public TagDTO addTag(@RequestBody TagDTO tagDTO) {
        return tagService.addTag(tagDTO);
    }

    @DeleteMapping
    @ResponseBody
    public boolean deleteTagById(@RequestParam("id") long id) {
        return tagService.deleteTagById(id);
    }
}

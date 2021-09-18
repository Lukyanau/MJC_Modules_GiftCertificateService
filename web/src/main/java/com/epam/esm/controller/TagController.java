package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Tag controller with CRD methods
 * @author Lukyanau I.M.
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tags")
public class TagController {

    private final TagService tagService;

    /**
     * method founds all tags with or without name sorting
     * @param searchParams is getting from url
     * @return List of TagDto objects
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDto> getTags(@RequestParam(required = false) Map<String, String> searchParams) {
        return tagService.getTags(searchParams);
    }

    /**
     * method founds tag by its id
     * @param id is getting from url
     * @return a tagDto object if it exists
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TagDto getTagById(@PathVariable("id") long id) {
        return tagService.getTagById(id);
    }

    /**
     * method adds tag
     * @param tagDto is getting from request body
     * @return List of TagDto objects
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto addTag(@RequestBody TagDto tagDto) {
        return tagService.addTag(tagDto);
    }

    /**
     * method deletes tag
     * @param id is getting from url
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTagById(@PathVariable("id") long id) {
        tagService.deleteTagById(id);
    }
}

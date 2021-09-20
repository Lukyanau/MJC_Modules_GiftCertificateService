package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Tag controller with CRD methods
 *
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
     *
     * @param searchParams is getting from url
     * @return List of TagDto objects
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<TagDto> getTags(@RequestParam(required = false) Map<String, String> searchParams) {
        List<TagDto> currentTags = tagService.getTags(searchParams);
        currentTags.forEach(tag -> {
            Link getByIdLink = linkTo(TagController.class).slash(tag.getId()).withSelfRel();
            Link deleteLink = linkTo(TagController.class).slash(tag.getId()).withRel("deleteTag");
            Link addLink = linkTo(methodOn(TagController.class).addTag(tag)).withRel("addTags");
            tag.add(getByIdLink, addLink, deleteLink);
        });

        Link selfLink = linkTo(TagController.class).withSelfRel();
        return CollectionModel.of(currentTags, selfLink);
    }

    /**
     * method founds tag by its id
     *
     * @param id is getting from url
     * @return a tagDto object if it exists
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<TagDto> getTagById(@PathVariable("id") long id) {
        TagDto currentTag = tagService.getTagById(id);
        Link getAllLink = linkTo(methodOn(TagController.class).getTags(Collections.emptyMap())).withRel("getAllTags");
        Link addLink = linkTo(methodOn(TagController.class).addTag(currentTag)).withRel("addTag");
        Link deleteTagLink = linkTo(TagController.class).slash(currentTag.getId()).withRel("deleteTag");
        Link selfLink = linkTo(TagController.class).slash(currentTag.getId()).withSelfRel();
        return EntityModel.of(currentTag, getAllLink, addLink, deleteTagLink, selfLink);
    }

    /**
     * method adds tag
     *
     * @param tagDto is getting from request body
     * @return List of TagDto objects
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<TagDto> addTag(@RequestBody TagDto tagDto) {
        TagDto currentTag = tagService.addTag(tagDto);
        Link getAllLink = linkTo(methodOn(TagController.class).getTags(Collections.emptyMap())).withRel("getAllTags");
        Link getTagByIdLink = linkTo(methodOn(TagController.class).getTagById(currentTag.getId())).withRel("getTagById");
        Link deleteTagLink = linkTo(TagController.class).slash(currentTag.getId()).withRel("deleteTag");
        Link selfLink = linkTo(TagController.class).withSelfRel();
        return EntityModel.of(currentTag, getAllLink, getTagByIdLink, deleteTagLink, selfLink);
    }

    /**
     * method deletes tag
     *
     * @param id is getting from url
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTagById(@PathVariable("id") long id) {
        tagService.deleteTagById(id);
    }
}

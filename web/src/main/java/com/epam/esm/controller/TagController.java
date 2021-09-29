package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import com.epam.esm.util.TagLinkCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
  private final TagLinkCreator tagLinkCreator;

  /**
   * method founds all tags with or without name sorting
   *
   * @param searchParams is getting from url
   * @return List of TagDto objects
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public CollectionModel<TagDto> getTags(
      @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
      @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
      @RequestParam(required = false) Map<String, String> searchParams) {
    return tagLinkCreator.getTagsWithLinks(tagService.getTags(searchParams, page, size));
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
    return tagLinkCreator.getTagWithLinks(tagService.getTagById(id));
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
    return tagLinkCreator.getTagWithLinks(tagService.addTag(tagDto));
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

package com.epam.esm.util;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.epam.esm.utils.Constant.PAGE_DEFAULT;
import static com.epam.esm.utils.Constant.SIZE_DEFAULT;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TagLinkCreator {

    public CollectionModel<TagDto> getTagsWithLinks(List<TagDto> allTags) {
        allTags.forEach(this::addLinkToTag);
        Link selfLink = linkTo(TagController.class).withSelfRel();
        return CollectionModel.of(allTags, selfLink);
    }

    public EntityModel<TagDto> getTagWithLinks(TagDto tag) {
        addLinkToTag(tag);
        return EntityModel.of(tag);
    }

    private void addLinkToTag(TagDto tag) {
        Link getAllLink = linkTo(methodOn(TagController.class).getTags(PAGE_DEFAULT, SIZE_DEFAULT, null))
                .withRel("get all");
        Link getTagByIdLink = linkTo(methodOn(TagController.class).getTagById(tag.getId())).withRel("get by id");
        Link addLink = linkTo(methodOn(TagController.class).addTag(tag)).withRel("add");
        Link deleteTagLink = linkTo(TagController.class).slash(tag.getId()).withRel("delete");
        tag.add(getAllLink, getTagByIdLink, addLink, deleteTagLink);
    }
}

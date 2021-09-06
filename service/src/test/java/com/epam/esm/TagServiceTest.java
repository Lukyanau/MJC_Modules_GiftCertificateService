package com.epam.esm;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.mapper.TagMapper;
import com.epam.esm.repositoty.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TagServiceTest {

    private TagRepository tagRepository;
    private TagMapper tagMapper;
    private TagValidator tagValidator;
    private TagService tagService;

    @BeforeEach
    void setUp() {
        tagRepository = mock(TagRepository.class);
        tagMapper = mock(TagMapper.class);
        tagValidator = new TagValidator();
        tagService = new TagServiceImpl(tagRepository, tagMapper, tagValidator);

    }

    @AfterEach
    void tearDown() {
        tagRepository = null;
        tagMapper = null;
        tagValidator = null;
        tagService = null;
    }

    @Test
    void getTagsCorrectDataShouldReturnListWithTagDto() {
        Tag tag1 = new Tag();
        tag1.setId(1);
        tag1.setName("gilly");
        Tag tag2 = new Tag();
        tag1.setId(2);
        tag1.setName("ford");
        when(tagRepository.getAll(Collections.emptyMap())).thenReturn(Optional.of(Arrays.asList(tag1, tag2)));
        int expectedSize = 2;
        int actualSize = tagService.getTags(null).size();
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void getTagsNoTagsShouldThrowException() {
        when(tagRepository.getAll(Collections.emptyMap())).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> tagService.getTags(anyString()));
    }

    @Test
    void getTagByIdCorrectIdShouldReturnTagDto() {
        Tag tag1 = new Tag();
        tag1.setId(1);
        tag1.setName("gilly");
        TagDto expectedTagDto = new TagDto();
        expectedTagDto.setId(1);
        expectedTagDto.setName("gilly");
        when(tagRepository.getById(anyLong())).thenReturn(Optional.of(tag1));
        when(tagMapper.convertToDto(any(Tag.class))).thenReturn(expectedTagDto);
        TagDto actualTagDto = tagService.getTagById(1);
        assertEquals(actualTagDto, expectedTagDto);
    }

    @Test
    void getTagByIdIncorrectIdShouldThrowException() {
        long incorrectId = -10;
        when(tagRepository.getById(anyLong())).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> tagService.getTagById(incorrectId));
    }

    @Test
    void addTagCorrectDataShouldReturnTagDto() {
        TagDto expectedTag = new TagDto();
        expectedTag.setId(1);
        expectedTag.setName("FIFA");
        Tag addedTag = new Tag();
        addedTag.setName("fifa");
        when(tagRepository.getByName(anyString())).thenReturn(Optional.empty());
        when(tagMapper.convertToEntity(any(TagDto.class))).thenReturn(addedTag);
        when(tagRepository.add(any(Tag.class))).thenReturn(addedTag);
        when(tagRepository.getTagId(anyString())).thenReturn(1L);
        when(tagMapper.convertToDto(any(Tag.class))).thenReturn(expectedTag);
        TagDto actualTag = tagService.addTag(expectedTag);
        assertEquals(expectedTag, actualTag);
    }

    @Test
    void addTagIncorrectDataShouldThrowException() {
        TagDto expectedTag = new TagDto();
        expectedTag.setName("incorrectName@@@");
        assertThrows(ServiceException.class, () -> tagService.addTag(expectedTag));
    }

    @Test
    void deleteTagByIdExistTagShouldReturnTrue() {
        long correctId = 10;
        when(tagRepository.checkUsedTags(anyLong())).thenReturn(0L);
        when(tagRepository.delete(anyLong())).thenReturn(1);
        assertTrue(tagService.deleteTagById(correctId));
    }

    @Test
    void deleteTagByIdNonExistTagShouldReturnFalse() {
        long correctId = 10;
        when(tagRepository.checkUsedTags(anyLong())).thenReturn(0L);
        when(tagRepository.delete(anyLong())).thenReturn(0);
        assertFalse(tagService.deleteTagById(correctId));
    }

    @Test
    void deleteTagByIdExistUsingTagShouldThrowException() {
        long correctId = 10;
        when(tagRepository.checkUsedTags(anyLong())).thenReturn(1L);
        assertThrows(ServiceException.class, () -> tagService.deleteTagById(correctId));
    }

    @Test
    void getTagsByCertificateIdShouldReturnLongList() {
        long certificateId = 10;
        Optional<Tag> tag1 = Optional.of(new Tag("firstTag"));
        Optional<Tag> tag2 = Optional.of(new Tag("secondTag"));
        Optional<Tag> tag3 = Optional.of(new Tag("thirdTag"));
        List<Tag> expectedTagList = Arrays.asList(tag1.get(), tag2.get(), tag3.get());
        when(tagRepository.getTagsIdsByCertificateId(certificateId)).thenReturn(Optional.of(Arrays.asList(1L, 2L, 3L)));
        when(tagRepository.getById(1)).thenReturn(tag1);
        when(tagRepository.getById(2)).thenReturn(tag2);
        when(tagRepository.getById(3)).thenReturn(tag3);
        List<Tag> actualTagList = tagService.getTagsByCertificateId(certificateId);
        assertEquals(expectedTagList, actualTagList);
    }

    @Test
    void getTagsByCertificateIdShouldReturnEmptyList() {
        long correctCertificateId = 10;
        when(tagRepository.getTagsIdsByCertificateId(correctCertificateId)).thenReturn(Optional.empty());
        List<Tag> expectedTagList = Collections.emptyList();
        List<Tag> actualTagList = tagService.getTagsByCertificateId(correctCertificateId);
        assertEquals(expectedTagList, actualTagList);
    }
}

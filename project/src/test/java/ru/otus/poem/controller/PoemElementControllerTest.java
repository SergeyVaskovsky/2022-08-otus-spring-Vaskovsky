package ru.otus.poem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.poem.model.dto.PoemElementDto;
import ru.otus.poem.model.dto.PoemPictureElementDto;
import ru.otus.poem.model.dto.PoemTextElementDto;
import ru.otus.poem.service.PoemElementService;

import java.util.List;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PoemElementControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private PoemElementService poemElementService;

    private static Stream<Arguments> provideParamsForGet() {

        return Stream.of(
                Arguments.of("myemail@yandex.ru", "WRITER", status().isOk()),
                Arguments.of("myemail@yandex.ru", "MODERATOR", status().isOk()),
                Arguments.of("myemail@yandex.ru", "AUTHOR", status().isOk()),
                Arguments.of("myemail@yandex.ru", "READER", status().isOk())
        );
    }

    private static Stream<Arguments> provideParamsForPost() {

        return Stream.of(
                Arguments.of(new PoemTextElementDto(3L, "text", "Первые строчки"), "myemail@yandex.ru", "WRITER", status().isForbidden()),
                Arguments.of(new PoemTextElementDto(3L, "text", "Первые строчки"), "myemail@yandex.ru", "MODERATOR", status().isForbidden()),
                Arguments.of(new PoemTextElementDto(3L, "text", "Первые строчки"), "myemail@yandex.ru", "AUTHOR", status().isOk()),
                Arguments.of(new PoemTextElementDto(3L, "text", "Первые строчки"), "myemail@yandex.ru", "READER", status().isForbidden()),
                Arguments.of(new PoemPictureElementDto(3L, "picture", new byte[]{1, 2, 3}, (byte) 100), "myemail@yandex.ru", "WRITER", status().isForbidden()),
                Arguments.of(new PoemPictureElementDto(3L, "picture", new byte[]{1, 2, 3}, (byte) 100), "myemail@yandex.ru", "MODERATOR", status().isForbidden()),
                Arguments.of(new PoemPictureElementDto(3L, "picture", new byte[]{1, 2, 3}, (byte) 100), "myemail@yandex.ru", "AUTHOR", status().isOk()),
                Arguments.of(new PoemPictureElementDto(3L, "picture", new byte[]{1, 2, 3}, (byte) 100), "myemail@yandex.ru", "READER", status().isForbidden())
        );
    }

    private static Stream<Arguments> provideParamsForPut() {

        return Stream.of(
                Arguments.of("myemail@yandex.ru", "WRITER", status().isForbidden()),
                Arguments.of("myemail@yandex.ru", "MODERATOR", status().isForbidden()),
                Arguments.of("myemail@yandex.ru", "AUTHOR", status().isOk()),
                Arguments.of("myemail@yandex.ru", "READER", status().isForbidden())
        );
    }

    private static Stream<Arguments> provideParamsForDelete() {

        return Stream.of(
                Arguments.of("1", "myemail@yandex.ru", "WRITER", status().isForbidden()),
                Arguments.of("1", "myemail@yandex.ru", "MODERATOR", status().isForbidden()),
                Arguments.of("1", "myemail@yandex.ru", "AUTHOR", status().isOk()),
                Arguments.of("1", "myemail@yandex.ru", "READER", status().isForbidden()),
                Arguments.of("2", "myemail@yandex.ru", "WRITER", status().isForbidden()),
                Arguments.of("2", "myemail@yandex.ru", "MODERATOR", status().isForbidden()),
                Arguments.of("2", "myemail@yandex.ru", "AUTHOR", status().isOk()),
                Arguments.of("2", "myemail@yandex.ru", "READER", status().isForbidden())
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsForGet")
    void shouldCorrectGetAllPoemElements(String userName, String roleName, ResultMatcher statusMatcher) throws Exception {
        String expectedResult = mapper.writeValueAsString(List.of(poemElementService.getById(1L), poemElementService.getById(2L)));
        mockMvc.perform(get("/api/poems/1/elements")
                        .with(user(userName).authorities(new SimpleGrantedAuthority(roleName))))
                .andExpect(statusMatcher)
                .andExpect(content().json(expectedResult));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForPost")
    void shouldCorrectSaveNewPoemElement(PoemElementDto poemElementDto, String userName, String roleName, ResultMatcher statusMatcher) throws Exception {

        String expectedResult = mapper.writeValueAsString(poemElementDto);

        ResultActions resultActions = mockMvc.perform(post("/api/poems/1/elements")
                        .with(csrf()).with(user(userName).authorities(new SimpleGrantedAuthority(roleName)))
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(statusMatcher);

        if (statusMatcher.equals(status().isOk())) {
            resultActions.andExpect(content().json(expectedResult));
        }
    }

    @ParameterizedTest
    @MethodSource("provideParamsForPut")
    void shouldCorrectUpdateTextElement(String userName, String roleName, ResultMatcher statusMatcher) throws Exception {
        PoemTextElementDto poemTextElementDto = (PoemTextElementDto) poemElementService.getById(2L);
        PoemTextElementDto changedPoemTextElementDto = new PoemTextElementDto(
                poemTextElementDto.getId(),
                "text",
                "fdsljksfdkjlfdakjldsfkjladsf"
        );

        String expectedResult = mapper.writeValueAsString(changedPoemTextElementDto);

        ResultActions resultActions = mockMvc.perform(put("/api/poems/text-elements/2")
                        .with(csrf()).with(user(userName).authorities(new SimpleGrantedAuthority(roleName)))
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(statusMatcher);

        if (statusMatcher.equals(status().isOk())) {
            resultActions.andExpect(content().json(expectedResult));
        }
    }

    @ParameterizedTest
    @MethodSource("provideParamsForPut")
    void shouldCorrectUpdatePictureElement(String userName, String roleName, ResultMatcher statusMatcher) throws Exception {
        PoemPictureElementDto poemPictureElementDto = (PoemPictureElementDto) poemElementService.getById(1L);
        PoemPictureElementDto changedPoemPictureElementDto = new PoemPictureElementDto(
                poemPictureElementDto.getId(),
                "picture",
                new byte[]{4, 5, 6},
                Byte.valueOf((byte) 100)
        );

        String expectedResult = mapper.writeValueAsString(changedPoemPictureElementDto);

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "fileName",
                "image/*", new byte[]{4, 5, 6});

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/poems/picture-elements/1")
                        .file(mockMultipartFile)
                        .param("id", String.valueOf(1L))
                        .param("scale", String.valueOf(100))
                        .with(csrf())
                        .with(user(userName).authorities(new SimpleGrantedAuthority(roleName))))
                .andExpect(statusMatcher);

        if (statusMatcher.equals(status().isOk())) {
            resultActions.andExpect(content().json(expectedResult));
        }
    }

    @ParameterizedTest
    @MethodSource("provideParamsForPut")
    void shouldCorrectUpdatePictureElementScale(String userName, String roleName, ResultMatcher statusMatcher) throws Exception {

        String expectedResult = mapper.writeValueAsString(Byte.valueOf((byte) 50));

        ResultActions resultActions = mockMvc.perform(put("/api/poems/picture-elements/1/scale")
                        .with(csrf()).with(user(userName).authorities(new SimpleGrantedAuthority(roleName)))
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(statusMatcher);

        if (statusMatcher.equals(status().isOk())) {
            resultActions.andExpect(content().json(expectedResult));
        }
    }

    @ParameterizedTest
    @MethodSource("provideParamsForDelete")
    void shouldCorrectDeletePictureElement(String id, String userName, String roleName, ResultMatcher statusMatcher) throws Exception {

        mockMvc.perform(delete("/api/poems/elements/" + id)
                        .with(csrf()).with(user(userName).authorities(new SimpleGrantedAuthority(roleName))))
                .andExpect(statusMatcher);
    }
}

package ru.otus.poem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import ru.otus.poem.model.dto.PoemDto;
import ru.otus.poem.service.PoemService;

import java.time.LocalDateTime;
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
public class PoemControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private PoemService poemService;

    private static Stream<Arguments> provideParamsForGet() {

        return Stream.of(
                Arguments.of("myemail@yandex.ru", "WRITER", status().isOk(), "?readonly=true"),
                Arguments.of("myemail@yandex.ru", "MODERATOR", status().isOk(), "?readonly=true"),
                Arguments.of("myemail@yandex.ru", "AUTHOR", status().isOk(), "?readonly=true"),
                Arguments.of("myemail@yandex.ru", "READER", status().isOk(), "?readonly=true"),
                Arguments.of("myemail@yandex.ru", "WRITER", status().isOk(), "?readonly=false"),
                Arguments.of("myemail@yandex.ru", "MODERATOR", status().isOk(), "?readonly=false"),
                Arguments.of("myemail@yandex.ru", "AUTHOR", status().isOk(), "?readonly=false"),
                Arguments.of("myemail@yandex.ru", "READER", status().isOk(), "?readonly=false")
        );
    }

    private static Stream<Arguments> provideParamsForPostAndPut() {
        return Stream.of(
                Arguments.of("myemail@yandex.ru", "WRITER", status().isForbidden()),
                Arguments.of("myemail@yandex.ru", "MODERATOR", status().isForbidden()),
                Arguments.of("myemail@yandex.ru", "AUTHOR", status().isOk()),
                Arguments.of("myemail@yandex.ru", "READER", status().isForbidden())
        );
    }

    @ParameterizedTest
    @MethodSource("provideParamsForGet")
    void shouldCorrectGetAllPoem(String userName, String roleName, ResultMatcher statusMatcher, String readonly) throws Exception {
        String expectedResult;
        if ("?readonly=true".equals(readonly)) {
            expectedResult = mapper.writeValueAsString(List.of(poemService.getById(2L)));
        } else {
            expectedResult = mapper.writeValueAsString(List.of(poemService.getById(1L), poemService.getById(2L)));
        }
        mockMvc.perform(get("/api/poems" + readonly)
                        .with(user(userName).authorities(new SimpleGrantedAuthority(roleName))))
                .andExpect(statusMatcher)
                .andExpect(content().json(expectedResult));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForGet")
    void shouldCorrectGetPoemById(String userName, String roleName, ResultMatcher statusMatcher, String readonly) throws Exception {
        String expectedResult = mapper.writeValueAsString(poemService.getById(1L));
        mockMvc.perform(get("/api/poems/1")
                        .with(user(userName).authorities(new SimpleGrantedAuthority(roleName))))
                .andExpect(statusMatcher)
                .andExpect(content().json(expectedResult));
    }

    @ParameterizedTest
    @MethodSource("provideParamsForPostAndPut")
    void shouldCorrectSaveNewPoem(String userName, String roleName, ResultMatcher statusMatcher) throws Exception {
        PoemDto poemDto = new PoemDto(
                2L,
                "Мое второе стихотворение",
                null);
        String expectedResult = mapper.writeValueAsString(poemDto);

        ResultActions resultActions = mockMvc.perform(post("/api/poems")
                        .with(csrf()).with(user(userName).authorities(new SimpleGrantedAuthority(roleName)))
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(statusMatcher);

        if (statusMatcher.equals(status().isOk())) {
            resultActions.andExpect(content().json(expectedResult));
        }
    }

    @ParameterizedTest
    @MethodSource("provideParamsForPostAndPut")
    void shouldCorrectUpdateComment(String userName, String roleName, ResultMatcher statusMatcher) throws Exception {
        PoemDto poemDto = poemService.getById(1L);
        PoemDto changedPoemDto = new PoemDto(
                poemDto.getId(),
                poemDto.getTitle(),
                LocalDateTime.now()
        );
        String expectedResult = mapper.writeValueAsString(changedPoemDto);

        ResultActions resultActions = mockMvc.perform(put("/api/poems/1")
                        .with(csrf()).with(user(userName).authorities(new SimpleGrantedAuthority(roleName)))
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(statusMatcher);

        if (statusMatcher.equals(status().isOk())) {
            resultActions.andExpect(content().json(expectedResult));
        }
    }

}

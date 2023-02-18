package ru.otus.poem.mapping;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.otus.poem.model.PoemElement;
import ru.otus.poem.model.PoemPictureElement;
import ru.otus.poem.model.PoemTextElement;
import ru.otus.poem.model.dto.PoemElementDto;
import ru.otus.poem.model.dto.PoemPictureElementDto;
import ru.otus.poem.model.dto.PoemTextElementDto;

@Service
@RequiredArgsConstructor
public class PoemElementToPoemElementDto implements Converter<PoemElement, PoemElementDto> {

    @Override
    public PoemElementDto convert(PoemElement source) {
        return source instanceof PoemTextElement ?
                new PoemTextElementDto(
                        source.getId(),
                        //"text",
                        source.getPoem().getId(),
                        ((PoemTextElement) source).getContent()) :
                new PoemPictureElementDto(
                        source.getId(),
                        //"picture",
                        source.getPoem().getId(),
                        ((PoemPictureElement) source).getPicture(),
                        ((PoemPictureElement) source).getScale()
                );
    }
}

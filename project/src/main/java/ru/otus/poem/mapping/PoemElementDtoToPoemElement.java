package ru.otus.poem.mapping;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.otus.poem.model.Poem;
import ru.otus.poem.model.PoemElement;
import ru.otus.poem.model.PoemPictureElement;
import ru.otus.poem.model.PoemTextElement;
import ru.otus.poem.model.dto.PoemElementDto;
import ru.otus.poem.model.dto.PoemPictureElementDto;
import ru.otus.poem.model.dto.PoemTextElementDto;
import ru.otus.poem.service.PoemService;

@Service
@RequiredArgsConstructor
public class PoemElementDtoToPoemElement implements Converter<PoemElementDto, PoemElement> {

    private final PoemService poemService;
    private final ConversionService conversionService;

    @Override
    public PoemElement convert(PoemElementDto source) {
        Poem poem = conversionService.convert(poemService.getById(source.getPoemId()), Poem.class);
        return source instanceof PoemTextElementDto ?
                new PoemTextElement(source.getId(), poem, ((PoemTextElementDto) source).getContent()) :
                new PoemPictureElement(
                        source.getId(),
                        poem,
                        ((PoemPictureElementDto) source).getPicture(),
                        ((PoemPictureElementDto) source).getScale());
    }
}

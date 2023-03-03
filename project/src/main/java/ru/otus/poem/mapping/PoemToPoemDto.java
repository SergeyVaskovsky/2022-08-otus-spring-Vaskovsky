package ru.otus.poem.mapping;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.otus.poem.model.Poem;
import ru.otus.poem.model.dto.PoemDto;

@Service
public class PoemToPoemDto implements Converter<Poem, PoemDto> {

    @Override
    public PoemDto convert(Poem source) {
        return new PoemDto(
                source.getId(),
                source.getTitle(),
                source.getPublishTime());
    }
}

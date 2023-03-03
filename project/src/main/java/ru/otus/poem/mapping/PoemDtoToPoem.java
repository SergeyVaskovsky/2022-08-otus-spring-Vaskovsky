package ru.otus.poem.mapping;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.otus.poem.model.Poem;
import ru.otus.poem.model.dto.PoemDto;

@Service
public class PoemDtoToPoem implements Converter<PoemDto, Poem> {

    @Override
    public Poem convert(PoemDto source) {
        return new Poem(
                source.getId(),
                source.getTitle(),
                source.getPublishTime()
        );
    }
}

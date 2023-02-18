package ru.otus.poem.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ConvertersRegister {

	private final ConversionService conversionService;
	private final List<Converter<?, ?>> converters;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (conversionService instanceof ConverterRegistry) {
			ConverterRegistry converterRegistry = (ConverterRegistry) this.conversionService;
			converters.forEach(converterRegistry::addConverter);
		}
	}
}

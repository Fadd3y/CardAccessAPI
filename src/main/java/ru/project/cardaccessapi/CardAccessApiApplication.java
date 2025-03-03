package ru.project.cardaccessapi;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.project.cardaccessapi.dto.JobTitleDTO;
import ru.project.cardaccessapi.models.Authority;
import ru.project.cardaccessapi.models.JobTitle;
import ru.project.cardaccessapi.services.AuthoritiesService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class CardAccessApiApplication {

    private final AuthoritiesService authoritiesService;

    @Autowired
    public CardAccessApiApplication(AuthoritiesService authoritiesService) {
        this.authoritiesService = authoritiesService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CardAccessApiApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}

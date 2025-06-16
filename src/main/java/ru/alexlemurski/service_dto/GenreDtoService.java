package ru.alexlemurski.service_dto;

import org.springframework.stereotype.Service;

@Service
public final class GenreDtoService {

    public static final int genreMinValue = 2;
    public static final int genreMaxValue = 50;
    public static final String genreRegexExpressionRu = "^\\p{IsCyrillic}+$";

}
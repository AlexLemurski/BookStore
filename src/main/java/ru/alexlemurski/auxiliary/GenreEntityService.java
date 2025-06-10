package ru.alexlemurski.auxiliary;

import org.springframework.stereotype.Service;

@Service
public class GenreEntityService {

    public static final int genreMinValue = 2;
    public static final int genreMaxValue = 50;
    public static final String genreRegexExpressionRu = "^\\p{IsCyrillic}+$";

}
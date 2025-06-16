package ru.alexlemurski.service_dto;

import org.springframework.stereotype.Service;
import ru.alexlemurski.entity.Author;

@Service
public class AuthorDtoService {

    public static final int authorMinValue = 2;
    public static final int authorMaxValue = 50;
    public static final String authorRegexExpressionRu = "^\\p{IsCyrillic}+$";

    public final String buildAuthorName(Author author) {
        return String.format("id:%s %s %s.%s.",
            author.getId(), author.getSurName(), author.getName().charAt(0), author.getMiddleName().charAt(0));
    }
}
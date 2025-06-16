package ru.alexlemurski.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alexlemurski.entity.Author;
import ru.alexlemurski.layers.dto.AuthorDto;
import ru.alexlemurski.layers.dto.BooksDto;
import ru.alexlemurski.layers.payload.AuthorNewPayload;
import ru.alexlemurski.layers.payload.AuthorUpdatePayload;
import ru.alexlemurski.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public final class AuthorController {

    private final AuthorService authorService;

    @Operation(
        responses = {
            @ApiResponse(
                responseCode = "200",
                headers = @Header(name = "Content-Type", description = "тип Данных"),
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            type = "array",
                            implementation = AuthorDto.class,
                            properties = {
                                @StringToClassMapItem(key = "id", value = Long.class),
                                @StringToClassMapItem(key = "surName", value = String.class),
                                @StringToClassMapItem(key = "name", value = String.class),
                                @StringToClassMapItem(key = "middleName", value = String.class),
                                @StringToClassMapItem(key = "bookList", value = BooksDto.class)
                            })
                    )
                }
            )
        }
    )
    @GetMapping("/authors")
    @PreAuthorize("hasAnyRole('ADMIN', 'READ')")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        var result = authorService.getAllAuthors();
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result);
    }

    @Operation(
        responses = {
            @ApiResponse(
                responseCode = "200",
                headers = @Header(name = "Content-Type", description = "тип Данных"),
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            type = "object",
                            implementation = AuthorDto.class,
                            properties = {
                                @StringToClassMapItem(key = "id", value = Long.class),
                                @StringToClassMapItem(key = "surName", value = String.class),
                                @StringToClassMapItem(key = "name", value = String.class),
                                @StringToClassMapItem(key = "middleName", value = String.class),
                                @StringToClassMapItem(key = "bookList", value = BooksDto.class)
                            })
                    )
                }
            )
        }
    )
    @GetMapping("/author/{id:\\d+}")
    @PreAuthorize("hasAnyRole('ADMIN', 'READ')")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long id) {
        var result = authorService.getAuthorById(id);
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result);
    }

    @Operation(
        responses = {
            @ApiResponse(
                responseCode = "201",
                headers = @Header(name = "Content-Type", description = "тип Данных"),
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            type = "object",
                            properties = {
                                @StringToClassMapItem(key = "id", value = Long.class),
                                @StringToClassMapItem(key = "surName", value = String.class),
                                @StringToClassMapItem(key = "name", value = String.class),
                                @StringToClassMapItem(key = "middleName", value = String.class)
                            })
                    )
                }
            )
        }
    )
    @PostMapping("/author-create")
    @PreAuthorize("hasAnyRole('ADMIN', 'CREATE')")
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody AuthorNewPayload payload) {
        var result = authorService.createAuthor(payload.surName(), payload.name(), payload.middleName());
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result);

    }

    @Operation(
        responses = {
            @ApiResponse(
                responseCode = "204",
                headers = @Header(name = "Content-Type", description = "тип Данных"),
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(
                            type = "object",
                            properties = {
                                @StringToClassMapItem(key = "surName", value = String.class),
                                @StringToClassMapItem(key = "name", value = String.class),
                                @StringToClassMapItem(key = "middleName", value = String.class)
                            })
                    )
                }
            )
        }
    )
    @PatchMapping("/author-update/{id:\\d+}")
    @PreAuthorize("hasAnyRole('ADMIN', 'UPDATE')")
    public ResponseEntity<Author> updateAuthor(@PathVariable("id") Long id,
                                               @Validated @RequestBody AuthorUpdatePayload payload) {
        authorService.updateAuthor(id, payload.surName(), payload.name(), payload.middleName());
        return ResponseEntity.noContent().build();
    }

    @Operation(
        responses = {
            @ApiResponse(
                responseCode = "204",
                headers = @Header(name = "Content-Type", description = "тип Данных"),
                content = {
                    @Content(
                        schema = @Schema(type = "object")
                    )
                }
            )
        }
    )
    @DeleteMapping("/author-delete/{id:\\d+}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DELETE')")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

}
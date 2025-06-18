package ru.alexlemurski.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alexlemurski.entity.Books;
import ru.alexlemurski.layers.dto.BooksDto;
import ru.alexlemurski.layers.payload.BooksNewPayload;
import ru.alexlemurski.layers.payload.BooksUpdatePayload;
import ru.alexlemurski.service.BooksService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BooksController {

    private final BooksService booksService;

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
                            implementation = BooksDto.class,
                            properties = {
                                @StringToClassMapItem(key = "id", value = Long.class),
                                @StringToClassMapItem(key = "bookName", value = String.class),
                                @StringToClassMapItem(key = "author", value = String.class),
                                @StringToClassMapItem(key = "genre", value = String.class),
                            })
                    )
                }
            )
        }
    )
    @GetMapping("/books")
    @PreAuthorize("hasAnyRole('ADMIN', 'READ')")
    public ResponseEntity<List<BooksDto>> getAllBooks() {
        var result = booksService.getAllBooks();
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
                            implementation = BooksDto.class,
                            properties = {
                                @StringToClassMapItem(key = "id", value = Long.class),
                                @StringToClassMapItem(key = "bookName", value = String.class),
                                @StringToClassMapItem(key = "author", value = String.class),
                                @StringToClassMapItem(key = "genre", value = String.class),
                            })
                    )
                }
            )
        }
    )
    @GetMapping("/book/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'READ')")
    public ResponseEntity<BooksDto> getBookById(@PathVariable Long id) {
        var result = booksService.getBooksById(id);
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
                                @StringToClassMapItem(key = "bookName", value = String.class),
                                @StringToClassMapItem(key = "author", value = Long.class),
                                @StringToClassMapItem(key = "genre", value = Long.class),
                            })
                    )
                }
            )
        }
    )
    @PostMapping("/book-create")
    @PreAuthorize("hasAnyRole('ADMIN', 'CREATE')")
    public ResponseEntity<Books> createBook(@Validated @RequestBody BooksNewPayload payload) {
        var result = booksService.createBook(payload.bookName(), payload.authorId(), payload.genreId());
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
                                @StringToClassMapItem(key = "bookName", value = String.class),
                                @StringToClassMapItem(key = "author", value = Long.class),
                                @StringToClassMapItem(key = "genre", value = Long.class),
                            })
                    )
                }
            )
        }
    )
    @PatchMapping("/book-update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'UPDATE')")
    public ResponseEntity<Books> updateBook(@PathVariable("id") Long id,
                                            @Validated @RequestBody BooksUpdatePayload payload) {
        booksService.updateBook(id, payload.bookName(), payload.authorId(), payload.genreId());
        return ResponseEntity.noContent().build();
    }

    @Operation(
        responses = {
            @ApiResponse(
                responseCode = "200",
                headers = @Header(name = "Content-Type", description = "тип Данных"),
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(type = "object")
                    )
                }
            )
        }
    )
    @DeleteMapping("/book-delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DELETE')")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        booksService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
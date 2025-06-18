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
import org.springframework.web.bind.annotation.*;
import ru.alexlemurski.entity.Genre;
import ru.alexlemurski.layers.dto.GenreDto;
import ru.alexlemurski.layers.payload.GenreNewPayload;
import ru.alexlemurski.layers.payload.GenreUpdatePayload;
import ru.alexlemurski.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

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
                            implementation = GenreDto.class,
                            properties = {
                                @StringToClassMapItem(key = "id", value = Long.class),
                                @StringToClassMapItem(key = "genreName", value = String.class)
                            })
                    )
                }
            )
        }
    )
    @GetMapping("/genres")
    @PreAuthorize("hasAnyRole('ADMIN', 'READ')")
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        var result = genreService.getAllGenres();
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
                            implementation = GenreDto.class,
                            properties = {
                                @StringToClassMapItem(key = "id", value = Long.class),
                                @StringToClassMapItem(key = "genreName", value = String.class)
                            })
                    )
                }
            )
        }
    )
    @GetMapping("/genre/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'READ')")
    public ResponseEntity<GenreDto> getGenreById(@PathVariable Long id) {
        var result = genreService.getGenreById(id);
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
                                @StringToClassMapItem(key = "genreName", value = String.class)
                            })
                    )
                }
            )
        }
    )
    @PostMapping("/genre-create")
    @PreAuthorize("hasAnyRole('ADMIN', 'CREATE')")
    public ResponseEntity<Genre> createGenre(@Valid @RequestBody GenreNewPayload payload) {
        var result = genreService.createGenre(payload.genreName());
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
                                @StringToClassMapItem(key = "genreName", value = String.class),
                            })
                    )
                }
            )
        }
    )
    @PatchMapping("/genre-update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'UPDATE')")
    public ResponseEntity<Genre> updateGenre(@PathVariable("id") Long id,
                                             @Valid @RequestBody GenreUpdatePayload payload) {
        genreService.updateGenre(id, payload.genreName());
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
    @DeleteMapping("/genre-delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DELETE')")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }
}
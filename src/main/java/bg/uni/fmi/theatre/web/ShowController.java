package bg.uni.fmi.theatre.web;

import bg.uni.fmi.theatre.dto.ShowRequest;
import bg.uni.fmi.theatre.dto.ShowResponse;
import bg.uni.fmi.theatre.service.ShowService;
import bg.uni.fmi.theatre.vo.Genre;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shows")
@RequiredArgsConstructor
@Tag(name = "Shows", description = "CRUD operations for theatre shows")
public class ShowController {
    private final ShowService showService;

    @GetMapping
    @Operation(summary = "List shows with filters and pagination")
    @ApiResponse(responseCode = "200", description = "Paginated list of shows")
    public List<ShowResponse> listShows(
            @Parameter(description = "Title substring filter (case-insensitive)")
            @RequestParam(defaultValue = "") String title,
            @Parameter(description = "Filter by genre")
            @RequestParam(required = false) Genre genre,
            @Parameter(description = "Page number (zero-based)")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size")
            @RequestParam(defaultValue = "10") int size) {
        return showService.getAllShows();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get show by ID")
    @ApiResponse(responseCode = "200", description = "Show found")
    @ApiResponse(responseCode = "404", description = "Show not found")
    public ShowResponse getShow(@PathVariable Long id) {
        return showService.getShowById(id);
    }

    @PostMapping
    @Operation(summary = "Create a new show")
    @ApiResponse(responseCode = "201", description = "Show created")
    @ApiResponse(responseCode = "400", description = "Validation error")
    public ResponseEntity<ShowResponse> createShow(@Valid @RequestBody ShowRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(showService.addShow(req));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing show")
    @ApiResponse(responseCode = "200", description = "Show updated")
    @ApiResponse(responseCode = "404", description = "Show not found")
    @ApiResponse(responseCode = "400", description = "Validation error")
    public ShowResponse updateShow(@PathVariable Long id, @Valid @RequestBody ShowRequest req) {
        return showService.updateShow(id, req);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a show")
    @ApiResponse(responseCode = "204", description = "Show deleted")
    @ApiResponse(responseCode = "404", description = "Show not found")
    public ResponseEntity<Void> deleteShow(@PathVariable Long id) {
        showService.deleteShow(id);
        return ResponseEntity.noContent().build();
    }
}
package bg.uni.fmi.theatre.controller;

import bg.uni.fmi.theatre.model.Genre;
import bg.uni.fmi.theatre.model.Show;
import bg.uni.fmi.theatre.service.CatalogueService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShowController {
    private final CatalogueService catalogueService;

    public ShowController(CatalogueService catalogueService) {
        this.catalogueService = catalogueService;
    }

    public void displayAllShows() {
        catalogueService.getAllShows().forEach(System.out::println);
    }

    public void updateShow(long id, String title, String genreStr, int duration) {
        Genre genre = Genre.valueOf(genreStr.toUpperCase());
        Show updatedShow = new Show(id, title, "Updated description", genre, duration, null);
        catalogueService.addShow(updatedShow);
    }

    public List<Show> getShowsByGenre(String genreStr) {
        Genre genre = Genre.valueOf(genreStr.toUpperCase());
        return  catalogueService.searchShows(null, genre);
    }
}

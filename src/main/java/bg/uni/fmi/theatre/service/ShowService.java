package bg.uni.fmi.theatre.service;

import bg.uni.fmi.theatre.model.Genre;
import bg.uni.fmi.theatre.model.Show;
import bg.uni.fmi.theatre.repository.ShowRepository;
import org.springframework.stereotype.Service;

@Service
public class ShowService {
    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    public void addShow(String title, String genreStr, int duration) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        Genre genre = Genre.valueOf(genreStr.toUpperCase());

        long id = showRepository.findAll().size() + 1;
        Show show = new Show(id, title, "", genre, duration, null);
        showRepository.save(show);
    }
}

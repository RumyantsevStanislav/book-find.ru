package server.entities.dtos;

public record SeriesDtoImpl(String title, String description) implements  SeriesDto {

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }
}

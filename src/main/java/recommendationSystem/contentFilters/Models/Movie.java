package recommendationSystem.contentFilters.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import recommendationSystem.contentFilters.Models.Enums.Genre;
import recommendationSystem.contentFilters.Models.Enums.Language;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Movie {
  private Long id;
  private String name;
  private List<Language> languageList;
  private List<Genre> genreList;
  private List<String> keywords;
  private List<Artist> artistsList;
  private double averageRating;

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setLanguageList(List<Language> languageList) {
    this.languageList = languageList;
  }

  public void setGenreList(List<Genre> genreList) {
    this.genreList = genreList;
  }

  public void setArtistsList(List<Artist> artistsList) {
    this.artistsList = artistsList;
  }

  public void setAverageRating(double averageRating) {
    this.averageRating = averageRating;
  }

  public void setKeywords(List<Language> languageList, List<Genre> genreList, String movieName) {
    this.keywords =
        Arrays.stream(movieName.trim().split(" "))
            .map(String::trim)
            .filter(content -> (content.length() > 0))
            .collect(Collectors.toList());
    this.keywords.add(languageList.toString());
    this.keywords.add(genreList.toString());
  }
}

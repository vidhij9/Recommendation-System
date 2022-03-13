package recommendationSystem.contentFilters.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import recommendationSystem.contentFilters.Models.Enums.Genre;
import recommendationSystem.contentFilters.Models.Enums.Language;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Interests {
  List<Genre> genreList;
  List<Language> languageList;
}

package recommendationSystem.contentFilters.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Customer {
  List<MovieRating> watchedListWithRating;
  Recommendations recommendationsList;
  List<Interests> interestList;
  private Long id;
  private String name;
  private String password;
}

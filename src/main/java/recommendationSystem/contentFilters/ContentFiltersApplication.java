package recommendationSystem.contentFilters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ContentFiltersApplication {

  public static void main(String[] args) {
    SpringApplication.run(ContentFiltersApplication.class, args);
  }
}

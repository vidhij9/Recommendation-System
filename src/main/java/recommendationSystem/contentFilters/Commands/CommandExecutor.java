package recommendationSystem.contentFilters.Commands;

import recommendationSystem.contentFilters.Models.ContentRecommendation;

public abstract class CommandExecutor {
  ContentRecommendation contentRecommendation;

  public CommandExecutor(ContentRecommendation contentRecommendation) {
    this.contentRecommendation = contentRecommendation;
  }

  public abstract boolean validate(Command command);

  public abstract void execute(Command command);
}

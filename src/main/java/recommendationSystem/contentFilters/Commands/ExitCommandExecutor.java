package recommendationSystem.contentFilters.Commands;

import recommendationSystem.contentFilters.Models.ContentRecommendation;

public class ExitCommandExecutor extends CommandExecutor {

  public static final String COMMAND_NAME = "EXIT";

  public ExitCommandExecutor(ContentRecommendation contentRecommendation) {
    super(contentRecommendation);
  }

  @Override
  public boolean validate(Command command) {
    return false;
  }

  @Override
  public void execute(Command command) {}
}

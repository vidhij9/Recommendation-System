package recommendationSystem.contentFilters;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import recommendationSystem.contentFilters.Commands.Command;
import recommendationSystem.contentFilters.Commands.CommandExecutor;
import recommendationSystem.contentFilters.Commands.CommandExecutorFactory;
import recommendationSystem.contentFilters.Commands.ExitCommandExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class Run implements CommandLineRunner {

  private final CommandExecutorFactory commandExecutorFactory;

  public Run(CommandExecutorFactory commandExecutorFactory) {
    this.commandExecutorFactory = commandExecutorFactory;
  }

  @Override
  public void run(String... args) throws Exception {
    mainRun(args);
  }

  public void mainRun(String[] args) throws IOException {
    final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    while (true) {
      final String input = bufferedReader.readLine();
      final Command command = new Command(input);
      processCommand(command);
      if (command.getCommandName().equals(ExitCommandExecutor.COMMAND_NAME)) {
        break;
      }
    }
  }

  protected void processCommand(Command command) {
    final CommandExecutor commandExecutor = commandExecutorFactory.getCommandExecutor(command);
    if (commandExecutor.validate(command)) {
      commandExecutor.execute(command);
    } else {
      System.out.println("VALIDATION FAILED");
    }
  }
}

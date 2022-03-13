package recommendationSystem.contentFilters.Commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class Command {

  private static final String SPACE = " ";
  private String commandName;
  private List<String> params;

  public Command(String input) {
    input = input.toUpperCase();
    final List<String> commandList =
        Arrays.stream(input.trim().split(SPACE))
            .map(String::trim)
            .filter(content -> (content.length() > 0))
            .collect(Collectors.toList());

    if (commandList.size() == 0) {
      System.out.println("INVALID COMMAND");
    }

    commandName = commandList.get(0);
    commandList.remove(0);
    params = commandList;
  }
}

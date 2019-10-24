import java.util.List;

public class Automata {
  private List<FileParser.State> states;
  private List<FileParser.State> initialStates;
  private List<FileParser.State> currentStates;
  private List<FileParser.Transition> transitions;

  public Automata(List<FileParser.State> states,
                  List<FileParser.Transition> transitons){
    this.states = states;
    this.transitions = transitons;

  }

  public boolean isValid(String word){
    return false;
  }

  public List<FileParser.State> extractInitialStates(List<FileParser.State> states) {
    return states;
  }

  public void changeCurrentStates(String character){

  }

  public List<String> getCharacters(String word){
    return null;
  }

  //for debugging purposes
  @Override
  public String toString() {
    return "Automata{" +
    "states=" + states +
    ", transitions=" + transitions +
    '}';
  }
}

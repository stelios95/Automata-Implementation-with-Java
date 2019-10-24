import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Automata {
  private List<AutomataParser.State> states;
  private List<AutomataParser.State> initialStates = new ArrayList<>();
  private List<AutomataParser.State> currentStates = new ArrayList<>();
  private List<AutomataParser.Transition> transitions;

  public Automata(List<AutomataParser.State> states,
                  List<AutomataParser.Transition> transitons){
    this.states = states;
    this.transitions = transitons;

  }

  public boolean isValid(String word){
    List<String> characters = getCharacters(word);
    extractInitialStates();
    this.currentStates.addAll(this.initialStates);
    for(String s : characters){
      //TODO for each character apply the automata transitions
    }
    return false;
  }

  public List<String> getCharacters(String word){
    return Arrays.asList(word.split("|"));
  }

  public void extractInitialStates() {
    for(AutomataParser.State state : this.states){
      if(state.isInitial()) this.initialStates.add(state);
    }
  }

  public void changeCurrentStates(String character){

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

import java.util.*;

public class Automaton {
  private List<AutomataParser.State> states;
  private AutomataParser.State initialState;
  private List<AutomataParser.State> currentStates = new ArrayList<>();
  private List<AutomataParser.Transition> transitions;
  private Map<AutomataParser.State, List<AutomataParser.Transition>> transitionsByState = new HashMap<>();
  private Map<Integer, AutomataParser.State> statesById = new HashMap<>();

  @Override
  public String toString() {
    return "Automata{" +
    "states=" + states +
    ", initialState=" + initialState +
    ", currentStates=" + currentStates +
    ", transitions=" + transitions +
    ", transitionsByState=" + transitionsByState +
    '}';
  }

  public Automaton(List<AutomataParser.State> states,
                   List<AutomataParser.Transition> transitons){
    this.states = states;
    this.transitions = transitons;
    extractInitialState();
    this.currentStates.add(this.initialState);
    extractTransitionsByState();
    extraxtStatesById();
  }

  public boolean isValid(String word){
    List<String> characters = getCharacters(word);
    for(String s : characters){
      List<AutomataParser.State> nextStates = getNextStates(s, this.currentStates);
      if(nextStates.isEmpty()){
        return false;
      }
      this.currentStates.clear();
      this.currentStates.addAll(nextStates);
    }
    for(AutomataParser.State state : this.currentStates){
      if(state.isFinal()) {
        return true;
      }
    }
    return false;
  }

  public List<String> getCharacters(String word){
    return Arrays.asList(word.split("|"));
  }

  private void extractInitialState() {
    for(AutomataParser.State state : this.states){
      if(state.isInitial()) this.initialState = state;
    }
  }

  private void extractTransitionsByState(){
    for(AutomataParser.State s : this.states){
      List<AutomataParser.Transition> stateTransitions = new ArrayList<>();
      for(AutomataParser.Transition t : this.transitions){
        if(t.getCurrentStateId() == s.getStateId()){
          stateTransitions.add(t);
        }
      }
      this.transitionsByState.put(s, stateTransitions);
    }
  }

  private void extraxtStatesById(){
    for(AutomataParser.State state : this.states){
      this.statesById.put(state.getStateId(), state);
    }
  }

  public List<AutomataParser.State> getNextStates(String currentCharacter, List<AutomataParser.State> currentStates){
    //get all available transitions for all current states and filter them by transition character
    List<AutomataParser.State> nextStates = new ArrayList<>();
    List<AutomataParser.Transition> allTransitions = new ArrayList<>();
    List<AutomataParser.Transition> stateTransitions = new ArrayList<>();
    for(AutomataParser.State state : currentStates){
      stateTransitions = transitionsByState.get(state);
      //System.out.println("ALL TRANSITIONS: " + stateTransitions.toString());
      allTransitions.addAll(stateTransitions);
    }
    //filter next transitions by the current character
    for(AutomataParser.Transition t : allTransitions){
      if(t.getTransitionChar().equals(currentCharacter)){
        nextStates.add(statesById.get(t.getNextStateId()));
      }
    }
    //System.out.println("NEXT STATES : " + nextStates.toString());
    return nextStates;
  }
}

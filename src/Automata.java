import java.util.*;

public class Automata {
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

  public Automata(List<AutomataParser.State> states,
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
      List<AutomataParser.State> nextStates = new ArrayList<>();
      for(AutomataParser.State state : this.currentStates ){
        List<AutomataParser.Transition> transitions = transitionsByState.get(state);
        for(AutomataParser.Transition transition : transitions){
          if(transition.getTransitionChar().equals(s)){
            AutomataParser.State nxtState = statesById.get(transition.getNextStateId());
            nextStates.add(nxtState);
          }
        }
      }
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
}

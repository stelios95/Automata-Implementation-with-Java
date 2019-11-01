import java.util.*;

//TODO FINAL TESTING
public class Automaton {
  private List<AutomataParser.State> states;
  private AutomataParser.State initialState;
  private Set<AutomataParser.State> currentStates = new HashSet<AutomataParser.State>();
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
                   List<AutomataParser.Transition> transitions){
    this.states = states;
    this.transitions = transitions;
    extractInitialState();
    this.currentStates.add(this.initialState);
    extractTransitionsByState();
    extractStatesById();
    this.currentStates.addAll(getClosure(this.initialState));
    System.out.println("INITIAL STATES: " + this.currentStates);
  }

  public boolean isValid(String word){
    List<String> characters = getCharacters(word);
    for(String s : characters){
      Set<AutomataParser.State> nextStates = getNextStates(s, this.currentStates);
      System.out.println("NEXT STATES: " + nextStates.toString());
      if(nextStates == null || nextStates.isEmpty()){
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

  private void extractStatesById(){
    for(AutomataParser.State state : this.states){
      this.statesById.put(state.getStateId(), state);
    }
  }

  private List<AutomataParser.State> getClosure(AutomataParser.State state) {
    List<AutomataParser.State> closure = new ArrayList();
    closure.add(state);
      for (int i = 0; i < closure.size(); i++) {
        AutomataParser.State s = closure.get(i);
        List<AutomataParser.Transition> transitions = transitionsByState.get(s);
        for (AutomataParser.Transition transition : transitions) {
          if (transition.getTransitionChar() == null) {
            AutomataParser.State toState = statesById.get(transition.getNextStateId());
            if (!closure.contains(toState)) {
            closure.add(toState);
            }
          }
        }
    }
    System.out.println("CLOSURE: " + closure.toString());
    return closure;
  }

  public Set<AutomataParser.State> getNextStates(String currentCharacter, Set<AutomataParser.State> currentStates){
    //get all available transitions for all current states and filter them by transition character
    Set<AutomataParser.State> nextStates = new HashSet<>();
    List<AutomataParser.Transition> allTransitions = new ArrayList<>();
    for(AutomataParser.State state : currentStates){
      List<AutomataParser.Transition> stateTransitions = transitionsByState.get(state);
      allTransitions.addAll(stateTransitions);
    }
    //get all e-transitions and transitions filtered by currentCharacter
    for(AutomataParser.Transition t : allTransitions){
      if(t.getTransitionChar() == null){
        nextStates.addAll(getClosure(this.statesById.get(t.getCurrentStateId())));
      } else if(t.getTransitionChar().equals(currentCharacter)){
        nextStates.add(statesById.get(t.getNextStateId()));
      }
    }
    return nextStates;
  }
}

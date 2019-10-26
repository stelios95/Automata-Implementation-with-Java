import java.util.*;

public class AutomataParser {
  private ArrayList<String> doc;
  private ArrayList<State> states;
  private ArrayList<Transition> transitions;
  public AutomataParser(ArrayList<String> doc){
    this.doc = doc;
    this.states = createStates();
    this.transitions = createTransitions();
  }

  public Automaton createAutomaton(){
    Automaton automaton = new Automaton(this.states, this.transitions);
    return automaton;
  }

  public ArrayList<State> createStates(){
    ArrayList<State> states = new ArrayList<>();
    HashSet<Integer> allStateIds = new HashSet<>();
    HashSet<Integer> restStates = new HashSet<>();
    int totalStates = Integer.parseInt(doc.get(0));
    for(int i = 1; i < totalStates +1; i++){
      allStateIds.add(i);
    }
    int inititalStateId = Integer.parseInt(doc.get(1));
    restStates.add(inititalStateId);
    states.add(new State(true, false, inititalStateId));
    String[] tempIds = doc.get(2).split(" ");
    ArrayList<String> finalStatesIds = new ArrayList<>();
    finalStatesIds.addAll(Arrays.asList(tempIds));
    if(finalStatesIds.contains(Integer.toString(inititalStateId))){
      states.get(0).setFinal(true);
      finalStatesIds.remove(Integer.toString(inititalStateId));
    }
    for(String s : finalStatesIds){
      states.add(new State(false,true, Integer.parseInt(s)));
      restStates.add(Integer.parseInt(s));
    }
    //allStateIds now contains all the states that are neither initial nor final
    allStateIds.removeAll(restStates);
    for(Integer i : allStateIds){
      states.add(new State(false, false, i));
    }
    return states;
  }

  public ArrayList<Transition> createTransitions(){
    ArrayList<Transition> transitions = new ArrayList<>();
    for(int i = 3; i < doc.size(); i++){
      List<String> transitionInfo = Arrays.asList(doc.get(i).split(" "));
      if(transitionInfo.size() < 3){
        transitions.add(new Transition(Integer.parseInt(transitionInfo.get(0)),
                          null,
                                        Integer.parseInt(transitionInfo.get(1)),
                              true));
      } else if(transitionInfo.size() == 3){
        transitions.add(new Transition(Integer.parseInt(transitionInfo.get(0)),
                                        transitionInfo.get(1),
                                        Integer.parseInt(transitionInfo.get(2)),
                                false));
      }
    }
    return transitions;
  }

  public class State {
    private boolean isInitial;
    private boolean isFinal;
    private int stateId;
    //getters
    public boolean isInitial() {
      return isInitial;
    }
    public boolean isFinal() {
      return isFinal;
    }
    public int getStateId() {
      return stateId;
    }

    public void setFinal(boolean aFinal) {
      isFinal = aFinal;
    }

    public State(boolean isInitial, boolean isFinal, int stateId){
      this.isInitial = isInitial;
      this.isFinal = isFinal;
      this.stateId = stateId;
    }

    @Override
    public String toString() {
      return "State{" +
      "isInitial=" + isInitial +
      ", isFinal=" + isFinal +
      ", stateId=" + stateId +
      '}';
    }
  }

  public class Transition{
    private int currentStateId;
    private String transitionChar;
    private int nextStateId;
    private boolean isEpsilon;
    //getters
    public int getCurrentStateId() {
      return currentStateId;
    }
    public String getTransitionChar() {
      return transitionChar;
    }
    public int getNextStateId() {
      return nextStateId;
    }
    public boolean isEpsilon() {
      return isEpsilon;
    }

    public Transition(int currentStateId, String transitionChar, int nextStateId, boolean isEpsilon){
      this.currentStateId = currentStateId;
      this.transitionChar = transitionChar;
      this.nextStateId = nextStateId;
      this.isEpsilon = isEpsilon;
    }
  }
}

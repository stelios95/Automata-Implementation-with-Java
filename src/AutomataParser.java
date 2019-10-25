import com.sun.source.doctree.SeeTree;

import java.util.*;

public class AutomataParser {
  private ArrayList<String> doc;
  public AutomataParser(ArrayList<String> doc){
    this.doc = doc;
  }

  public Automata createAutomata(){
    ArrayList<State> states = createStates();
    ArrayList<Transition> transitions = createTransitions();
    Automata automata = new Automata(states, transitions);
    return automata;
  }

  public ArrayList<State> createStates(){
    ArrayList<State> states = new ArrayList<>();
    int totalStates = Integer.parseInt(doc.get(0));
    Set<Integer> allStates = new HashSet<>();
    Set<Integer> createdStates = new HashSet<>();
    for(int i = 1; i < totalStates; i++){
      allStates.add(i);
    }
    int initialStateId = Integer.parseInt(doc.get(1));
    states.add(new State(true, false, initialStateId));
    createdStates.add(initialStateId);
    List<String> finalStateIds = Arrays.asList(doc.get(2).split(" "));
    for(String s : finalStateIds){
      int id = Integer.parseInt(s);
      states.add(new State(false, true, id));
      createdStates.add(id);
    }
    allStates.removeAll(createdStates);
    for(Integer i : allStates){
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

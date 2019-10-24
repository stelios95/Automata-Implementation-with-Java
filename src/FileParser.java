import com.sun.source.doctree.SeeTree;

import java.util.*;

public class FileParser {
  private ArrayList<String> doc;
  public FileParser(ArrayList<String> doc){
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
      List<String> transitionElements = Arrays.asList(doc.get(i).split(" "));
      transitions.add(new Transition(Integer.parseInt(transitionElements.get(0)),
                                      transitionElements.get(1),
                                      Integer.parseInt(transitionElements.get(2)),
                                      (transitionElements.get(1).equals("*") ? true : false)));
    }
    return transitions;
  }

  public class State {
    private boolean isInitial;
    private boolean isFinal;
    private int stateId;

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

    public Transition(int currentStateId, String transitionChar, int nextStateId, boolean isEpsilon){
      this.currentStateId = currentStateId;
      this.transitionChar = transitionChar;
      this.nextStateId = nextStateId;
      this.isEpsilon = isEpsilon;
    }
  }
}

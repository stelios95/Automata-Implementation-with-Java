import java.util.List;

public class FileParser {
  private String doc;
  public FileParser(String doc){
    this.doc = doc;
  }

  public List<String> parseFile(){
    return null;
  }

  public Automata createAutomata(){
    return null;
  }

  public class State {
    private boolean isInitial;
    private boolean isFinal;
    private int stateId;

  }

  public class Transition{
    private State currentState;
    private String transitionChar;
    private List<State> nextStates;
    private boolean isE;

  }
}

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
  public static void main(String[] args) {
    //parse file
    File file = new File("NDAutomata.txt");
    BufferedReader br = null;
    ArrayList<String> fileContent = new ArrayList<String>();
    try {
      br = new BufferedReader(new FileReader(file));
      String st;
      while ((st = br.readLine()) != null){
        fileContent.add(st);
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found!The program will return now");
      return;
    } catch (IOException e) {
      System.out.println("There was an error retrieving the file.The program will return now");
      return;
    }
    if(fileContent.isEmpty()) return;
    AutomataParser parser = new AutomataParser(fileContent);
    boolean shouldReturn = false;
    do{
      //create automata
      Automaton automaton = parser.createAutomaton();
      //System.out.println(automaton.toString());
      System.out.println("Please enter a word to check if it's accepted from the automaton!");
      Scanner scanner = new Scanner(System.in);
      String word = scanner.nextLine();
      boolean isValid = automaton.isValid(word);
      if(isValid){
        System.out.println("The word is valid!!");
      } else {
        System.out.println("The word is NOT valid!!");
      }
      boolean rightInput = false;
      do{
        System.out.println("Do you want to enter a new word?(Y (yes) /N (no) )");
        String yesOrNo = scanner.nextLine();
        if(yesOrNo.equalsIgnoreCase("y")){
          rightInput = true;
        } else if (yesOrNo.equalsIgnoreCase("n")){
          return;
        } else {
          System.out.println("Please select 'y' if you want to enter another word or 'n' if you wish to close the program!");
        }
      } while (!rightInput);
    } while (!shouldReturn);
  }
}


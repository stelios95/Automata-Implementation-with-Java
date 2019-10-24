import java.io.*;
import java.util.ArrayList;


public class Main {
  public static void main(String[] args) {
    //parse file
    File file = new File("Automata.txt");
    BufferedReader br = null;
    ArrayList<String> fileContent = new ArrayList<String>();
    try {
      br = new BufferedReader(new FileReader(file));
      String st;
      while ((st = br.readLine()) != null){
        System.out.println(st);
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
    FileParser parser = new FileParser(fileContent);
    //create automata
    Automata automata = parser.createAutomata();
    System.out.println(automata.toString());

    //create automata
    //prompt user to enter a world
    //return if the world isValid

  }

}


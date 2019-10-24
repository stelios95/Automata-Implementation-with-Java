import java.io.*;

public class Main {
  public static void main(String[] args) {
    System.out.println("hello world");
    //parse file
    File file = new File("Automata.txt");

    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(file));
      String st;
      while ((st = br.readLine()) != null)
        System.out.println(st);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    //create automata
    //prompt user to enter a world
    //return if the world isValid

  }

}


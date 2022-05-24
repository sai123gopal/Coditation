import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Codiation{

  private final String RESET = "\u001B[0m";
  private final String CLEARCONSOLE = "\033[H\033[2J";
  private final String CrayColor = "\u001B[36m";
  private final String GreenColor = "\u001B[32m";

  private Scanner reader = new Scanner(System.in);

  public Codiation(){
    Word();
  }

  private void Word(){
    String chosenWord = getWord(getList());
    String[][] gameBoard = new String[6][5];

    for(int i = 0; i < gameBoard.length; i++){
      for(int j = 0; j < gameBoard[i].length; j++)
        gameBoard[i][j] = ":";
    }

    boolean userWon = false;

    int attemptNum = 0;

    while(!userWon && attemptNum < 6){
      userWon = attempt(gameBoard, chosenWord, attemptNum);
      attemptNum++;
    }
    if(!userWon){
      gameEnd(gameBoard, chosenWord, false);
    }
  }

  private String[] getList(){
    try{
      String[] words = new String(Files.readAllBytes(Paths.get("words.txt"))).split(" ");
      return words;
    }
    catch(Exception e){
      System.out.println("Error 404: File \"words.txt\" not found");
      System.exit(404);

      return null;
    }
  }
  private String getWord(String[] words) {
    int wordIndex = (int)(Math.random()*words.length);
    String word = words[wordIndex];
    return word;
  }

  private boolean attempt(String[][] gameBoard, String chosenWord, int attempt){
    System.out.print(CLEARCONSOLE);
    printGameBoard(gameBoard);
    System.out.println();
    boolean loop = true;
    while(loop){
      System.out.println("Enter five letter word");
      System.out.print(": ");
      String answer = reader.nextLine();
      System.out.println();
      if(answer.length() > 5){
        System.out.println("Entered word is higher than five letters");
        System.out.println("Enter a word that has five letters");
        System.out.println();
      }
      else if (answer.length() < 5){
        System.out.println("ENtered word is less than five letters");
        System.out.println("Enter a word that has five letters");
        System.out.println();
      }
      else if(answer.equals(chosenWord)){
        for(int i = 0; i < 5; i++)
          gameBoard[attempt][i] = GreenColor + Character.toString(answer.charAt(i)).toUpperCase() + RESET;

        gameEnd(gameBoard, chosenWord, true);
        return true;
      }
      else{
        for(String word : getList()){
          if(answer.equalsIgnoreCase(word)){
          for(int i = 0; i < 5; i++){
             
              String letter = Character.toString(answer.charAt(i));
              if(letter.equals(chosenWord.substring(i, i + 1))){
                letter = GreenColor + letter.toUpperCase() + RESET;
              }
              else if(chosenWord.contains(letter)){
                letter = CrayColor + letter.toUpperCase() + RESET;
              }
              else{
                letter = letter.toUpperCase();
              }
              gameBoard[attempt][i] = letter;
          }
            return false;
          }
        }
        System.out.println("Your word was not in the word list, Enter new word :: ");
        System.out.println();
      }
    }
    
    return false;
  }

  private void printGameBoard(String[][] gameBoard){
   
    System.out.print("⌈ ");
    for(int i = 0; i < gameBoard[0].length; i++)
      System.out.print(gameBoard[0][i] + " ");

    System.out.println("⌉");

    
    for(int i = 1; i < gameBoard.length - 1; i++){
      
      System.out.print("| ");
      for(int j = 0; j < gameBoard[i].length; j++)
        System.out.print(gameBoard[i][j] + " ");

      System.out.println("|");
    }

    System.out.print("⌊ ");
   
    for(int i = 0; i < gameBoard[5].length; i++)
      System.out.print(gameBoard[5][i] + " ");

    System.out.println("⌋");
  }


  private void gameEnd(String[][] gameBoard, String chosenWord, boolean userWon){
   
    System.out.print(CLEARCONSOLE);

    System.out.println("Game\n");
    printGameBoard(gameBoard);
    System.out.println();
  
    if(userWon){
      System.out.println("You Won");
      System.out.println();
    }
    else
    {
      System.out.println("You Lost");
      System.out.println("The word was: " + chosenWord.toUpperCase());
      System.out.println();
    }
    boolean isGameEnded = true;
    while(isGameEnded)
    { 
      System.out.println("Play again? (Y / N)");
      System.out.print(":: ");

      String answer = reader.nextLine();
      System.out.println();

      if(answer.equalsIgnoreCase("Y"))
      { 
        Word();
        isGameEnded = false;
      }
      else if(answer.equalsIgnoreCase("N"))
      {
        System.out.print(CLEARCONSOLE);
        reader.close();
        System.out.println("Exit");
        isGameEnded = false;
      }
      else
      {
        System.out.println("Enter valid input : ");
        System.out.println("Enter \"Y\" or \"N\"");
        System.out.println();
      }
    }
  }

   public static void main(String[] args){
    new Codiation();
  }
}
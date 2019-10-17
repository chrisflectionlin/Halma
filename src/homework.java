import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class homework {



    public static void main(String[] args) throws FileNotFoundException {
        //INITIAL SET UP FOR THE BOARD
        int board_width = 6;
        int board_length = 6;
        String mode;
        String side="";
        Float time;
        File file = new File("input.txt");
        ArrayList<ArrayList<String>> preboard = new ArrayList<>();
        Scanner scan = new Scanner(file);
        int count = 1;
        while (scan.hasNextLine()) {
            String temp = scan.nextLine();
            if (count == 1) {
                mode = temp;
            } else if (count == 2) {
                side = temp;
            } else if (count == 3) {
                time = Float.parseFloat(temp);
            } else if (count > 3) {
                ArrayList<String> temp_arr = new ArrayList<>();
                for (int i = 0; i < temp.length(); i++) {
                    temp_arr.add(Character.toString(temp.charAt(i)));
                }
                preboard.add(temp_arr);
            }
            count++;
        }
        Board board = new Board(preboard.size(),preboard.get(0).size());
        board.putPieces(preboard);
        //END OF INITIAL SET UP

        System.out.println(board.toString());
        ArrayList<Board> test2 = board.generateMove("BLACK");
        System.out.println("TEST STARTING");
        System.out.println(test2.size());
        for(Board b : test2){
            System.out.println(b);
        }
    }
}

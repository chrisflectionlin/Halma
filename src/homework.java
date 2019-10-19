import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class homework {

    public homework(){

    }

    public Board minimax(Board node,int depth, Boolean isMaxPlayer, String myside, double alpha, double beta) {
        if (depth == 0) {
            return node;
        }
        if (isMaxPlayer) {
            double bestval = Double.NEGATIVE_INFINITY;
            Board bestboard = node;
            for (Board b : node.generateMoves(myside)) {
                Board current = minimax(b, depth - 1, false,myside,alpha, beta);
                double eval = current.evaluation(myside);
                if (bestval < eval) {
                    bestval = eval;
                    bestboard = b;
                }
                alpha = Math.max(alpha, bestval);
                if (beta <= alpha) {
                    break;
                }
            }
            return bestboard;
        }else {
            double bestval = Double.POSITIVE_INFINITY;
            Board bestboard = node;
            for(Board b : node.generateMoves(changeSide(myside))){
                Board current = minimax(b,depth-1,true,myside,alpha,beta);
                double eval = current.evaluation(myside);
                if(bestval>eval){
                    bestval = eval;
                    bestboard = b;
                }
                beta = Math.min(beta,bestval);
                if(beta<=alpha){
                    break;
                }
            }
            return bestboard;
        }
    }

    public String changeSide(String side){
        if(side.equals("BLACK")){
            return "WHITE";
        }else{
            return "BLACK";
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        //INITIAL SET UP FOR THE BOARD
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

        Board board = new Board(preboard);
        //END OF INITIAL SET UP
        System.out.println(board.toString());
        homework test = new homework();
        long startTime = System.nanoTime();
        Board ans = test.minimax(board,3,true,side,
              Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        long endTime   = System.nanoTime();
        System.out.println(ans);
        System.out.println("PROGRAM FINISHED IN: " + (endTime-startTime)/1000000000 +" seconds");
    }
}

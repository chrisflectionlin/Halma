import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
        String mode="";
        String side="";
        Float time=(float)0;
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
        //System.out.println(board.toString());

        //start playing
        homework test = new homework();
        long startTime = System.currentTimeMillis();
        Board ans = null;
        if(mode.equals("SINGLE")){
            //System.out.println("IN SINGLE");
            ans = test.minimax(board,2,true,side,
                    Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        }else{
            //System.out.println("IN GAME");
            if(board.almostWon(side) && time>=5){
                //System.out.println("ALMOST WON");
                ans = test.minimax(board,4,true,side,
                        Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            }else if(time>=20){
                //System.out.println("TIME ENOUGH");
                ans = test.minimax(board,3,true,side,
                        Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            }else if(time<20 && time>1){
                //System.out.println("LESS THAN 20");
                ans = test.minimax(board,2,true,side,
                        Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            }else if(time<1){
                //System.out.println("LESS THAN 1");
                ans = test.minimax(board,1,true,side,
                        Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
            }
        }
        long endTime   = System.currentTimeMillis();
        //System.out.println(ans);
        String ans_str = "";
        if(!ans.jumped){
            ans_str = "E " + ans.lastx + "," + ans.lasty + " " + ans.currentx + "," + ans.currenty;
        }else{
            Board last = ans;
            ArrayList<Move> moves = new ArrayList<>();
            while(true){
                if(!last.last_board.jumped){
                    moves.add(new Move(last.currenty,last.currentx));
                    moves.add(new Move(last.lasty,last.lastx));
                    break;
                }else{
                    moves.add(new Move(last.currenty,last.currentx));
                    last = last.last_board;
                }
            }
            for(int i=moves.size()-1;i>=1;i--){
                Move current = moves.get(i);
                Move last_move = moves.get(i-1);
                ans_str += "J " + current.x + "," + current.y + " " + last_move.x + "," + last_move.y + "\n";
            }
            ans_str = ans_str.substring(0,ans_str.length()-1);
        }
        PrintWriter out = new PrintWriter("output.txt");
        out.print(ans_str);
        out.close();
        System.out.println("PROGRAM FINISHED IN: " + (float)(endTime-startTime)/1000 +" seconds");
    }
}

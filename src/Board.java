import com.sun.tools.javac.util.List;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    int width;
    int length;
    ArrayList<Piece> white = new ArrayList<>();
    ArrayList<Piece> black = new ArrayList<>();
    boolean jumped;
    Piece last;
    public Board(int in_length, int in_width){
        this.length = in_length;
        this.width = in_width;
        boolean jumped = false;
    }


    public Board(int in_length, int in_width, ArrayList<Piece> white, ArrayList<Piece> black){
        this.length = in_length;
        this.width = in_width;
        this.white = white;
        this.black = black;
        boolean jumped = false;
    }

    public ArrayList<Piece> getBlack() {
        return black;
    }

    public ArrayList<Piece> getWhite() {
        return white;
    }

    public boolean getJumped(){
        return jumped;
    }

    public void setJumped(boolean b){
        this.jumped = b;
    }

    public Piece getLast(){
        return this.last;
    }

    public void setLast(Piece p){
        this.last = p;
    }

    public void addPiece(Piece piece){
        if(piece.getColor().equals("B")){
            black.add(piece);
        }else{
            white.add(piece);
        }
    }
    //TODO: FINISH IT
    public ArrayList<Board> generateMove(String side){
        //IF CAMP IS  EMPTY, MMOVE AS PLEASE
        ArrayList<Board> moves = new ArrayList<>();
        if(campisEmpty(side)){
            if(side.equals("BLACK")){
                for(Piece p : this.black){
                    moves.addAll(possibleMove(p));
                }
            }else{
                for(Piece p : this.white){
                    moves.addAll(possibleMove(p));
                }
            }
        }else{
            //IF CAMP NOT EMPTY, GENERATE MOVES FOR THE PIECES IN CAMP
            if(side.equals("BLACK")){
                for(Piece p : this.black){
                    if(inCamp(p.getY(),p.getX(),p.getColor())){
                        moves.addAll(possibleMove(p));
                    }
                }
            }else{
                for(Piece p : this.white){
                    if(inCamp(p.getY(),p.getX(),p.getColor())){
                        moves.addAll(possibleMove(p));
                    }
                }
            }
        }
        return moves;
        //IF CAMP IS EMPTY, MOVE AS PLEASE
    }

    //TODO: IF PIECE IN CAMP, JUST GO FURTHER AWAY
    //POSSIBLE MOVE FOR 1 SINGLE PIECE
    public ArrayList<Board> possibleMove(Piece piece){
        int piece_y = piece.getY();
        int piece_x = piece.getX();
        ArrayList<Board> moves = new ArrayList<>();
        String[][] boardarr = construtBoard();
        //GET ALL E MOVES, 8 DIRECTIONS
        if(piece_y-1>=0 && piece_x-1>=0 &&!inCamp(piece_y-1,piece_x-1,piece.getColor())){
            if(boardarr[piece_y-1][piece_x-1].equals(".") ){
                //move piece left up
                moves.add(this.newBoard(piece,piece_y-1,piece_x-1));
            }
        }
        if(piece_y+1<this.length && piece_x+1<this.width &&!inCamp(piece_y+1,piece_x+1,piece.getColor())){
            if(boardarr[piece_y+1][piece_x+1].equals(".")){
                //move piece right down
                moves.add(this.newBoard(piece,piece_y+1,piece_x+1));
            }
        }
        if(piece_y+1<this.length && piece_x-1>=0&&!inCamp(piece_y+1,piece_x-1,piece.getColor())){
            if(boardarr[piece_y+1][piece_x-1].equals(".")){
                //move piece left down
                moves.add(this.newBoard(piece,piece_y+1,piece_x-1));
            }
        }
        if(piece_y-1>=0 && piece_x+1<this.width&&!inCamp(piece_y-1,piece_x+1,piece.getColor())){
            if(boardarr[piece_y-1][piece_x+1].equals(".")){
                //move piece right up
                moves.add(this.newBoard(piece,piece_y-1,piece_x+1));
            }
        }
        if(piece_y-1>=0&&!inCamp(piece_y-1,piece_x,piece.getColor())) {
            if (boardarr[piece_y-1][piece_x].equals(".")) {
                //move piece up
                moves.add(this.newBoard(piece,piece_y-1,piece_x));
            }
        }
        if(piece_y+1<this.length &&!inCamp(piece_y+1,piece_x,piece.getColor())){
            if(boardarr[piece_y+1][piece_x].equals(".")){
                //move piece down
                moves.add(this.newBoard(piece,piece_y+1,piece_x));
            }
        }
        if(piece_x-1>=0&&!inCamp(piece_y,piece_x-1,piece.getColor())){
            if(boardarr[piece_y][piece_x-1].equals(".")){
                //move piece left
                moves.add(this.newBoard(piece,piece_y,piece_x-1));
            }
        }
        if(piece_x+1<this.width&&!inCamp(piece_y,piece_x+1,piece.getColor())){
            if(boardarr[piece_y][piece_x+1].equals(".")){
                //move piece right
                moves.add(this.newBoard(piece,piece_y,piece_x+1));
            }
        }
        //GET ALL POSSIBLE JUMP MOVES, 8 DIRECTIONS
        moves.addAll(jump(piece,new ArrayList<Board>(),this,piece));
        return moves;
    }

    public ArrayList<Board> jump(Piece piece, ArrayList<Board> boards,Board current,Piece original){
        String[][] boardarr = current.construtBoard();
        if(!jumpExist(piece.getY(),piece.getX(),original)){
            return boards;
        }
        int piece_y = piece.getY();
        int piece_x = piece.getX();
        //left up
        if(piece_y-2>=0 && piece_x-2>=0&&!boardarr[piece_y-1][piece_x-1].equals(".")
                && boardarr[piece_y-2][piece_x-2].equals(".")
                && original.getY()!=piece_y-2 && original.getX()!=piece_x-2){
            Board newmove = current.newBoard(piece,piece_y-2,piece_x-2);
            newmove.setJumped(true);
            newmove.setLast(piece);
            boards.add(newmove);
            Piece newpiece = new Piece(piece.getColor(),piece_y-2,piece_x-2);
            jump(newpiece,boards,newmove,piece);
        }
        //down right
        if(piece_y+2<this.length && piece_x+2<this.width&&!boardarr[piece_y+1][piece_x+1].equals(".")
                && boardarr[piece_y+2][piece_x+2].equals(".")
                && original.getY()!=piece_y+2 && original.getX()!=piece_x+2){
            Board newmove = current.newBoard(piece,piece_y+2,piece_x+2);
            newmove.setJumped(true);
            newmove.setLast(piece);
            boards.add(newmove);
            Piece newpiece = new Piece(piece.getColor(),piece_y+2,piece_x+2);
            jump(newpiece,boards,newmove,piece);
        }
        //up right
        if(piece_y-2>=0 && piece_x+2<this.width&&!boardarr[piece_y-1][piece_x+1].equals(".")
                && boardarr[piece_y-2][piece_x+2].equals(".")
                && original.getY()!=piece_y-2 && original.getX()!=piece_x+2){
            Board newmove = current.newBoard(piece,piece_y-2,piece_x+2);
            newmove.setJumped(true);
            newmove.setLast(piece);
            boards.add(newmove);
            Piece newpiece = new Piece(piece.getColor(),piece_y-2,piece_x+2);
            jump(newpiece,boards,newmove,piece);
        }
        //left down
        if(piece_y+2<this.length && piece_x-2>=0&&!boardarr[piece_y+1][piece_x-1].equals(".")
                && boardarr[piece_y+2][piece_x-2].equals(".")
                && original.getY()!=piece_y+2 && original.getX()!=piece_x-2){
            Board newmove = current.newBoard(piece,piece_y+2,piece_x-2);
            newmove.setJumped(true);
            newmove.setLast(piece);
            boards.add(newmove);
            Piece newpiece = new Piece(piece.getColor(),piece_y+2,piece_x-2);
            jump(newpiece,boards,newmove,piece);
        }
        //down
        if(piece_y+2<this.length&&!boardarr[piece_y+1][piece_x].equals(".")
                && boardarr[piece_y+2][piece_x].equals(".")
                && original.getY()!=piece_y+2){
            Board newmove = current.newBoard(piece,piece_y+2,piece_x);
            newmove.setJumped(true);
            newmove.setLast(piece);
            boards.add(newmove);
            Piece newpiece = new Piece(piece.getColor(),piece_y+2,piece_x);
            jump(newpiece,boards,newmove,piece);
        }
        //up
        if(piece_y-2>=0&&!boardarr[piece_y-1][piece_x].equals(".")
                && boardarr[piece_y-2][piece_x].equals(".")
                && original.getY()!=piece_y-2){
            Board newmove = current.newBoard(piece,piece_y-2,piece_x);
            newmove.setJumped(true);
            newmove.setLast(piece);
            boards.add(newmove);
            Piece newpiece = new Piece(piece.getColor(),piece_y-2,piece_x);
            jump(newpiece,boards,newmove,piece);
        }
        //left
        if(piece_x-2>=0&&!boardarr[piece_y][piece_x-1].equals(".")
                && boardarr[piece_y][piece_x-2].equals(".")
                && original.getX()!=piece_x-2){
            Board newmove = current.newBoard(piece,piece_y,piece_x-2);
            newmove.setJumped(true);
            newmove.setLast(piece);
            boards.add(newmove);
            Piece newpiece = new Piece(piece.getColor(),piece_y,piece_x-2);
            jump(newpiece,boards,newmove,piece);
        }
        //right
        if(piece_x+2<=this.width-1&&!boardarr[piece_y][piece_x+1].equals(".")
                && boardarr[piece_y][piece_x+2].equals(".")
                && original.getX()!=piece_x+2){
            Board newmove = current.newBoard(piece,piece_y,piece_x+2);
            newmove.setJumped(true);
            newmove.setLast(piece);
            boards.add(newmove);
            Piece newpiece = new Piece(piece.getColor(),piece_y,piece_x+2);
            jump(newpiece,boards,newmove,piece);
        }
        return boards;

    }

    public boolean jumpExist(int piece_y, int piece_x,Piece original){
        String[][] boardarr = construtBoard();
        //jump up left
        if(piece_y-2>=0 && piece_x-2>=0){
            if(!boardarr[piece_y-1][piece_x-1].equals(".") && boardarr[piece_y-2][piece_x-2].equals(".") ){
                if(original.getY()!=piece_y-2 && original.getX()!=piece_x-2) {
                    return true;
                }
            }
        }
        //jump down right
        if(piece_y+2<this.length && piece_x+2<this.width){
            if(!boardarr[piece_y+1][piece_x+1].equals(".") && boardarr[piece_y+2][piece_x+2].equals(".") ){
                if(original.getY()!=piece_y+2 && original.getX()!=piece_x+2) {
                    return true;
                }
            }
        }
        //jump down left
        if(piece_y+2<this.length && piece_x-2>=0){
            if(!boardarr[piece_y+1][piece_x-1].equals(".") && boardarr[piece_y+2][piece_x-2].equals(".") ){
                if(original.getY()!=piece_y+2 && original.getX()!=piece_x-2) {
                    return true;
                }
            }
        }
        //jump up right
        if(piece_y-2>=0 && piece_x+2<this.width){
            if(!boardarr[piece_y-1][piece_x+1].equals(".") && boardarr[piece_y-2][piece_x+2].equals(".") ){
                if(original.getY()!=piece_y-2 && original.getX()!=piece_x+2) {
                    return true;
                }
            }
        }
        //jump down
        if(piece_y+2<this.length){
            if(!boardarr[piece_y+1][piece_x].equals(".") && boardarr[piece_y+2][piece_x].equals(".") ){
                if(original.getY()!=piece_y+2) {
                    return true;
                }
            }
        }
        //jump up
        if(piece_y-2>=0){
            if(!boardarr[piece_y-1][piece_x].equals(".") && boardarr[piece_y-2][piece_x].equals(".") ){
                if(original.getY()!=piece_y-2) {
                    return true;
                }
            }
        }
        //jump left
        if(piece_x-2>=0){
            if(!boardarr[piece_y][piece_x-1].equals(".") && boardarr[piece_y][piece_x-2].equals(".") ){
                if(original.getX()!=piece_x-2) {
                    return true;
                }
            }
        }
        //jump right
        if(piece_x+2<this.width){
            if(!boardarr[piece_y][piece_x+1].equals(".") && boardarr[piece_y][piece_x+2].equals(".") ){
                if(original.getX()!=piece_x+2) {
                    return true;
                }
            }
        }
        return false;
    }
    public Board newBoard(Piece piece,int movey, int movex){
        if(piece.getColor().equals("B")){
            ArrayList<Piece> clone = new ArrayList<>();
            for(Piece p : this.black){
                if(p.getX() == piece.getX() && p.getY()==piece.getY()){
                    clone.add(new Piece(piece.getColor(),movey,movex));
                }else {
                    clone.add(p.clone());
                }
            }
            return new Board(this.length,this.width,this.white,clone);
        }else{
            ArrayList<Piece> clone = new ArrayList<>();
            for(Piece p : this.white){
                if(p.getX() == piece.getX() && p.getY()==piece.getY()){
                    clone.add(new Piece(piece.getColor(),movey,movex));
                }else {
                    clone.add(p.clone());
                }
            }
            return new Board(this.length,this.width,clone,this.black);
        }
    }

    //TODO : FIX THIS BEOFRE CHANGING THE MAP TO 19x19
    public boolean inCamp(int piecey, int piecex,String side){
        if(side.equals("B")) {
            ArrayList<Piece> blackcamp = new ArrayList<>(
                    List.of(new Piece("B",0,0),
                            new Piece("B",0,1),
                            new Piece("B",0,2),
                            new Piece("B",1,0),
                            new Piece("B",1,1),
                            new Piece("B",2,0)));
            for(Piece p : blackcamp){
                if(piecey == p.getY() && piecex == p.getX()){
                    return true;
                }
            }
            return false;
        }else{
            ArrayList<Piece> whitecamp = new ArrayList<>(
                    List.of(new Piece("W",3,5),
                            new Piece("W",4,5),
                            new Piece("W",4,4),
                            new Piece("W",5,3),
                            new Piece("W",5,4),
                            new Piece("W",5,5)));
            for(Piece p : whitecamp){
                if(piecey == p.getY() && piecex == p.getX()){
                    return true;
                }
            }
            return false;
        }

    }

    public boolean campisEmpty(String side){
        if(side.equals("BLACK")){
            for(int i=0;i<black.size();i++){
                Piece p = black.get(i);
                if(inCamp(p.getY(),p.getX(),"B")){
                    return false;
                }
            }
        }else{
            for(int i=0;i<white.size();i++){
                Piece p = white.get(i);
                if(inCamp(p.getY(),p.getX(),"W")){
                    return false;
                }
            }
        }
        return true;
    }


    public double evaluation(String side){
        double eval = 0;
        if(side.equals("BLACK")){
            for(int i=0;i<black.size();i++){
                Piece current = black.get(i);
                eval+= Math.sqrt(Math.pow(current.getY() - length-1,2) + Math.pow(current.getX()-width-1,2));
            }
        }else{
            for(int i=0;i<white.size();i++){
                Piece current = white.get(i);
                eval+= Math.sqrt(Math.pow(current.getY() - 0,2) + Math.pow(current.getX()-0,2));
            }
        }
        return -eval;
    }


    public void putPieces(ArrayList<ArrayList<String>> input){
        for(int i=0;i<input.size();i++){
            for(int j=0;j<input.get(0).size();j++){
                if(input.get(i).get(j).equals("B")){
                    this.addPiece(new Piece("B",i,j));
                }else if(input.get(i).get(j).equals("W")){
                    this.addPiece(new Piece("W",i,j));
                }
            }
        }
    }

    private String[][] construtBoard(){
        String[][] ans = new String[length][width];
        for(int i=0;i<length;i++){
            Arrays.fill(ans[i],".");
        }
        for (Piece current : white) {
            ans[current.getY()][current.getX()] = current.getColor();
        }
        for (Piece current : black) {
            ans[current.getY()][current.getX()] = current.getColor();
        }
        return ans;
    }

    public String toString(){
        String output = "";
        String[][] boardarray = new String[length][width];
        for (Piece current : white) {
            boardarray[current.getY()][current.getX()] = current.getColor();
        }
        for (Piece current : black) {
            boardarray[current.getY()][current.getX()] = current.getColor();
        }
        for(int i=0;i<length;i++){
            for(int j=0;j<width;j++){
                if(boardarray[i][j] == null){
                    boardarray[i][j] = ".";
                }
            }
        }
        for(int i=0;i<length;i++){
            for(int j=0;j<width;j++){
                output += boardarray[i][j] + " ";
            }
            output += "\n";
        }
        return output;
    }
}
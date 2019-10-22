import java.util.ArrayList;

public class Board {
    int width;
    int length;
    String[][] board;
    boolean jumped;
    Board last_board;
    int lasty;
    int lastx;
    int currenty;
    int currentx;
    public Board(ArrayList<ArrayList<String>> preboard){
        this.width = preboard.get(0).size();
        this.length = preboard.size();
        this.board = new String[this.length][this.width];
        for(int i=0;i<preboard.size();i++){
            for(int j=0;j<preboard.get(0).size();j++){
                this.board[i][j] = preboard.get(i).get(j);
            }
        }
        this.jumped = false;
        this.lasty =-1;
        this.lastx =-1;
        this.currenty = -1;
        this.currentx = -1;
        this.last_board = null;

    }
    public Board(String[][] preboard){
        this.width = preboard[0].length;
        this.length = preboard.length;
        this.board = preboard;
        this.jumped = false;
        this.lasty =-1;
        this.lastx =-1;
        this.currenty = -1;
        this.currentx = -1;
        this.last_board = null;

    }

    public void setJumped(Boolean bo){
        this.jumped = bo;
    }

    public void setLast(int y, int x){
        this.lasty = y;
        this.lastx = x;
    }

    public void setCurrent(int y, int x){
        this.currenty = y;
        this.currentx = x;
    }

    public void setLast_board(Board b){
        this.last_board = b;
    }


    public ArrayList<Board> generateMoves(String side){
        String[][] boardarr = this.board;
        ArrayList<Board> moves = new ArrayList<>();
        //if camp is empty move as please
        if(campisEmpty(side)){
            for(int i=0;i<boardarr.length;i++){
                for(int j=0;j<boardarr[0].length;j++){
                    if(side.equals("BLACK")) {
                        if(boardarr[i][j].equals("B")) {
                            moves.addAll(this.possibleMoves(i, j, side));
                        }
                    }else{
                        if(boardarr[i][j].equals("W")){
                            moves.addAll(this.possibleMoves(i,j,side));
                        }
                    }
                }
            }
        }else{
            //if camp is not empty, move the pieces in camp first.
            for(int i=0;i<boardarr.length;i++) {
                for (int j = 0; j < boardarr[0].length; j++) {
                    if(inCamp(i,j,side) && boardarr[i][j].equals(side.substring(0,1))){
                        moves.addAll(this.possibleMoves(i,j,side));
                    }
                }
            }
            //pieces in camp cant be moved
            if(moves.isEmpty()){
                for(int i=0;i<boardarr.length;i++){
                    for(int j=0;j<boardarr[0].length;j++){
                        if(side.equals("BLACK")) {
                            if(boardarr[i][j].equals("B")) {
                                moves.addAll(this.possibleMoves(i, j, side));
                            }
                        }else{
                            if(boardarr[i][j].equals("W")){
                                moves.addAll(this.possibleMoves(i,j,side));
                            }
                        }
                    }
                }
            }
        }
        return moves;
    }

    //Move for 1 single space
    public ArrayList<Board> possibleMoves(int y, int x,String side){
        ArrayList<Board> moves = new ArrayList<>();
        if(inCamp(y,x,side)){
            if(side.equals("BLACK")){
                if(y+1<=this.length && x+1<=this.width && this.board[y+1][x+1].equals(".")){
                    Board newmove = this.deepMove(y,x,y+1,x+1);
                    newmove.setLast(y,x);
                    newmove.setCurrent(y+1,x+1);
                    moves.add(newmove);
                }
                if(y+1<=this.length && this.board[y+1][x].equals(".")){
                    Board newmove = this.deepMove(y,x,y+1,x);
                    newmove.setLast(y,x);
                    newmove.setCurrent(y+1,x);
                    moves.add(newmove);
                }
                if(x+1<=this.width && this.board[y][x+1].equals(".")){
                    Board newmove = this.deepMove(y,x,y,x+1);
                    newmove.setLast(y,x);
                    newmove.setCurrent(y,x+1);
                    moves.add(newmove);
                }
            }else{
                if(y-1>=0 && x-1>=0 && this.board[y-1][x-1].equals(".")){
                    Board newmove = this.deepMove(y,x,y-1,x-1);
                    newmove.setLast(y,x);
                    newmove.setCurrent(y-1,x-1);
                    moves.add(newmove);
                }
                if(y-1>=0 && this.board[y-1][x].equals(".")){
                    Board newmove = this.deepMove(y,x,y-1,x);
                    newmove.setLast(y,x);
                    newmove.setCurrent(y-1,x);
                    moves.add(newmove);
                }
                if(x-1>=0 && this.board[y][x-1].equals(".")){
                    Board newmove = this.deepMove(y,x,y,x-1);
                    newmove.setLast(y,x);
                    newmove.setCurrent(y,x-1);
                    moves.add(newmove);
                }
            }
        }else{
            if (y - 1 >= 0 && x - 1 >= 0 && !inCamp(y - 1, x - 1, side)) {
                if (this.board[y - 1][x - 1].equals(".")) {
                    //move y,x left up
                    Board newmove = this.deepMove(y,x,y-1,x-1);
                    newmove.setLast(y,x);
                    newmove.setCurrent(y-1,x-1);
                    moves.add(newmove);
                }
            }
            if (y + 1 < this.length && x + 1 < this.width && !inCamp(y + 1, x + 1, side)) {
                if (this.board[y + 1][x + 1].equals(".")) {
                    //move y,x right down
                    Board newmove = this.deepMove(y,x,y+1,x+1);
                    newmove.setLast(y,x);
                    newmove.setCurrent(y+1,x+1);
                    moves.add(newmove);
                }
            }
            if (y + 1 < this.length && x - 1 >= 0 && !inCamp(y + 1, x - 1, side)) {
                if (this.board[y + 1][x - 1].equals(".")) {
                    //move y,x left down
                    Board newmove = this.deepMove(y,x,y+1,x-1);
                    newmove.setLast(y,x);
                    newmove.setCurrent(y+1,x-1);
                    moves.add(newmove);
                }
            }
            if (y - 1 >= 0 && x + 1 < this.width && !inCamp(y - 1, x + 1, side)) {
                if (this.board[y - 1][x + 1].equals(".")) {
                    //move y,x right up
                    Board newmove = this.deepMove(y,x,y-1,x+1);
                    newmove.setLast(y,x);
                    newmove.setCurrent(y-1,x+1);
                    moves.add(newmove);
                }
            }
            if (y - 1 >= 0 && !inCamp(y - 1, x, side)) {
                if (this.board[y - 1][x].equals(".")) {
                    //move y,x up
                    Board newmove = this.deepMove(y,x,y-1,x);
                    newmove.setLast(y,x);
                    newmove.setCurrent(y-1,x);
                    moves.add(newmove);
                }
            }
            if (y + 1 < this.length && !inCamp(y + 1, x, side)) {
                if (this.board[y + 1][x].equals(".")) {
                    //move y,x down
                    Board newmove = this.deepMove(y,x,y+1,x);
                    newmove.setLast(y,x);
                    newmove.setCurrent(y+1,x);
                    moves.add(newmove);
                }
            }
            if (x - 1 >= 0 && !inCamp(y, x - 1, side)) {
                if (this.board[y][x - 1].equals(".")) {
                    //move y,x left
                    Board newmove = this.deepMove(y,x,y,x-1);
                    newmove.setLast(y,x);
                    newmove.setCurrent(y,x-1);
                    moves.add(newmove);
                }
            }
            if (x + 1 < this.width && !inCamp(y, x + 1, side)) {
                if (this.board[y][x + 1].equals(".")) {
                    //move y,x right
                    Board newmove = this.deepMove(y,x,y,x+1);
                    newmove.setLast(y,x);
                    newmove.setCurrent(y,x+1);
                    moves.add(newmove);
                }
            }
        }
        moves.addAll(jump(y,x,new ArrayList<Board>(),this,y,x,new ArrayList<Move>()));
        return moves;
    }
    public ArrayList<Board> jump(int y, int x, ArrayList<Board> moves, Board current,int original_y, int original_x, ArrayList<Move> visited){
        visited.add(new Move(y,x));
        if(!jumpExist(y,x,original_y,original_x,current)){
            return moves;
        }
        String[][] boardarr = current.board;
        //down right
        if(y+2<this.length && x+2<this.width&&!boardarr[y+1][x+1].equals(".")
                && boardarr[y+2][x+2].equals(".")
                && !visited.contains(new Move(y+2,x+2))){
            Board newmove = current.deepMove(y,x,y+2,x+2);
            newmove.setJumped(true);
            newmove.setLast(y,x);
            newmove.setCurrent(y+2,x+2);
            newmove.setLast_board(current);
            moves.add(newmove);
            jump(y+2,x+2,moves,newmove,y,x,visited);
        }
        //up left
        if(y-2>=0 && x-2>=0&&!boardarr[y-1][x-1].equals(".")
                && boardarr[y-2][x-2].equals(".")
                && !visited.contains(new Move(y-2,x-2))){
            Board newmove = current.deepMove(y,x,y-2,x-2);
            newmove.setJumped(true);
            newmove.setLast(y,x);
            newmove.setCurrent(y-2,x-2);
            newmove.setLast_board(current);
            moves.add(newmove);
            jump(y-2,x-2,moves,newmove,y,x,visited);
        }
        //up right
        if(y-2>=0 && x+2<this.width&&!boardarr[y-1][x+1].equals(".")
                && boardarr[y-2][x+2].equals(".")
                && !visited.contains(new Move(y-2,x+2))){
            Board newmove = current.deepMove(y,x,y-2,x+2);
            newmove.setJumped(true);
            newmove.setLast(y,x);
            newmove.setCurrent(y-2,x+2);
            newmove.setLast_board(current);
            moves.add(newmove);
            jump(y-2,x+2,moves,newmove,y,x,visited);
        }
        //left down
        if(y+2<this.length && x-2>=0&&!boardarr[y+1][x-1].equals(".")
                && boardarr[y+2][x-2].equals(".")
                && !visited.contains(new Move(y+2,x-2))){
            Board newmove = current.deepMove(y,x,y+2,x-2);
            newmove.setJumped(true);
            newmove.setLast(y,x);
            newmove.setCurrent(y+2,x-2);
            newmove.setLast_board(current);
            moves.add(newmove);
            jump(y+2,x-2,moves,newmove,y,x,visited);
        }
        //down
        if(y+2<this.length&&!boardarr[y+1][x].equals(".")
                && boardarr[y+2][x].equals(".")
                && !visited.contains(new Move(y+2,x))){
            Board newmove = current.deepMove(y,x,y+2,x);
            newmove.setJumped(true);
            newmove.setLast(y,x);
            newmove.setCurrent(y+2,x);
            newmove.setLast_board(current);
            moves.add(newmove);
            jump(y+2,x,moves,newmove,y,x,visited);
        }
        //up
        if(y-2>=0&&!boardarr[y-1][x].equals(".")
                && boardarr[y-2][x].equals(".")
                && !visited.contains(new Move(y-2,x))){
            Board newmove = current.deepMove(y,x,y-2,x);
            newmove.setJumped(true);
            newmove.setLast(y,x);
            newmove.setCurrent(y-2,x);
            newmove.setLast_board(current);
            moves.add(newmove);
            jump(y-2,x,moves,newmove,y,x,visited);
        }
        //left
        if(x-2>=0&&!boardarr[y][x-1].equals(".")
                && boardarr[y][x-2].equals(".")
                && !visited.contains(new Move(y,x-2))){
            Board newmove = current.deepMove(y,x,y,x-2);
            newmove.setJumped(true);
            newmove.setLast(y,x);
            newmove.setCurrent(y,x-2);
            newmove.setLast_board(current);
            moves.add(newmove);
            jump(y,x-2,moves,newmove,y,x,visited);
        }
        //right
        if(x+2<this.width&&!boardarr[y][x+1].equals(".")
                && boardarr[y][x+2].equals(".")
                && !visited.contains(new Move(y,x+2))){
            Board newmove = current.deepMove(y,x,y,x+2);
            newmove.setJumped(true);
            newmove.setLast(y,x);
            newmove.setCurrent(y,x+2);
            newmove.setLast_board(current);
            moves.add(newmove);
            jump(y,x+2,moves,newmove,y,x,visited);
        }
        return moves;
    }

    public boolean jumpExist(int y,int x, int original_y, int original_x,Board current){
        String[][] boardarr = current.board;
        //jump up left
        if(y-2>=0 && x-2>=0){
            if(!boardarr[y-1][x-1].equals(".") && boardarr[y-2][x-2].equals(".") ){
                if(original_y!=y-2 && original_x!=x-2) {
                    return true;
                }
            }
        }
        //jump down right
        if(y+2<this.length && x+2<this.width){
            if(!boardarr[y+1][x+1].equals(".") && boardarr[y+2][x+2].equals(".") ){
                if(original_y!=y+2 && original_x!=x+2) {
                    return true;
                }
            }
        }
        //jump down left
        if(y+2<this.length && x-2>=0){
            if(!boardarr[y+1][x-1].equals(".") && boardarr[y+2][x-2].equals(".") ){
                if(original_y!=y+2 && original_x!=x-2) {
                    return true;
                }
            }
        }
        //jump up right
        if(y-2>=0 && x+2<this.width){
            if(!boardarr[y-1][x+1].equals(".") && boardarr[y-2][x+2].equals(".") ){
                if(original_y!=y-2 && original_x!=x+2) {
                    return true;
                }
            }
        }
        //jump down
        if(y+2<this.length){
            if(!boardarr[y+1][x].equals(".") && boardarr[y+2][x].equals(".") ){
                if(original_y!=y+2) {
                    return true;
                }
            }
        }
        //jump up
        if(y-2>=0){
            if(!boardarr[y-1][x].equals(".") && boardarr[y-2][x].equals(".") ){
                if(original_y!=y-2) {
                    return true;
                }
            }
        }
        //jump left
        if(x-2>=0){
            if(!boardarr[y][x-1].equals(".") && boardarr[y][x-2].equals(".") ){
                if(original_x!=x-2) {
                    return true;
                }
            }
        }
        //jump right
        if(x+2<this.width){
            if(!boardarr[y][x+1].equals(".") && boardarr[y][x+2].equals(".") ){
                if(original_x!=x+2) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
    public boolean inCamp(int y, int x, String side){
        if(side.equals("BLACK")){
            return  (y==0&&x==0) ||(y==0&&x==1) || (y==0&&x==2) ||
                    (y==1&&x==0) ||(y==1&&x==1) ||
                    (y==2&&x==0);
        }else{
            return  (y==5&&x==5) ||(y==5&&x==4) || (y==5&&x==3) ||
                    (y==4&&x==5) ||(y==4&&x==4) ||
                    (y==3&&x==5);
        }
    }*/


    public boolean inCamp(int y, int x,String side){
        if(side.equals("BLACK")){
            return  (y==0&&x==0) || (y==0&&x==1) || (y==0&&x==2) || (y==0&&x==3) || (y==0&&x==4) ||
                    (y==1&&x==0) || (y==1&&x==1) || (y==1&&x==2) || (y==1&&x==3) || (y==1&&x==4) ||
                    (y==2&&x==0) || (y==2&&x==1) || (y==2&&x==2) || (y==2&&x==3) ||
                    (y==3&&x==0) || (y==3&&x==1) || (y==3&&x==2) ||
                    (y==4&&x==0) || (y==4&&x==1);
        }else{
            return  (y==15&&x==15) || (y==15&&x==14) || (y==15&&x==13)|| (y==15&&x==12)|| (y==15&&x==11)||
                    (y==14&&x==15) || (y==14&&x==14) || (y==14&&x==13)|| (y==14&&x==12)|| (y==14&&x==11)||
                    (y==13&&x==15) || (y==13&&x==14) || (y==13&&x==13)|| (y==13&&x==12)||
                    (y==12&&x==15) || (y==12&&x==14) || (y==12&&x==13)||
                    (y==11&&x==15) || (y==11&&x==14);
        }
    }
    //TODO
    public boolean campisEmpty(String side){
        if(side.equals("BLACK")){
            if(!this.board[0][0].equals("B")&&
                    !this.board[0][1].equals("B")&&
                    !this.board[0][2].equals("B")&&
                    !this.board[0][3].equals("B")&&
                    !this.board[0][4].equals("B")&&
                    !this.board[1][0].equals("B")&&
                    !this.board[1][1].equals("B")&&
                    !this.board[1][2].equals("B")&&
                    !this.board[1][3].equals("B")&&
                    !this.board[1][4].equals("B")&&
                    !this.board[2][0].equals("B")&&
                    !this.board[2][1].equals("B")&&
                    !this.board[2][2].equals("B")&&
                    !this.board[2][3].equals("B")&&
                    !this.board[3][0].equals("B")&&
                    !this.board[3][1].equals("B")&&
                    !this.board[3][2].equals("B")&&
                    !this.board[4][0].equals("B")&&
                    !this.board[4][1].equals("B")){
                return true;
            }
            return false;
        }else{
            if(!this.board[15][15].equals("W")&&
                    !this.board[15][14].equals("W")&&
                    !this.board[15][13].equals("W")&&
                    !this.board[15][12].equals("W")&&
                    !this.board[15][11].equals("W")&&
                    !this.board[14][15].equals("W")&&
                    !this.board[14][14].equals("W")&&
                    !this.board[14][13].equals("W")&&
                    !this.board[14][12].equals("W")&&
                    !this.board[14][11].equals("W")&&
                    !this.board[13][15].equals("W")&&
                    !this.board[13][14].equals("W")&&
                    !this.board[13][13].equals("W")&&
                    !this.board[13][12].equals("W")&&
                    !this.board[12][15].equals("W")&&
                    !this.board[12][14].equals("W")&&
                    !this.board[12][13].equals("W")&&
                    !this.board[11][15].equals("W")&&
                    !this.board[11][14].equals("W")){
                return true;
            }
            return false;
        }
        /*
        public boolean campisEmpty(String side){
            if(side.equals("BLACK")){
                if(!this.board[0][0].equals("B")&&
                        !this.board[0][1].equals("B")&&
                        !this.board[0][2].equals("B")&&
                        !this.board[1][0].equals("B")&&
                        !this.board[1][1].equals("B")&&
                        !this.board[2][0].equals("B")){
                    return true;
                }
                return false;
            }else{
                if(!this.board[5][5].equals("W")&&
                        !this.board[5][4].equals("W")&&
                        !this.board[5][3].equals("W")&&
                        !this.board[4][5].equals("W")&&
                        !this.board[4][4].equals("W")&&
                        !this.board[3][5].equals("W")){
                    return true;
                }
                return false;
            }*/

    }
    public Board deepMove(int movefromy, int movefromx, int movetoy, int movetox){
        String[][] ans = new String[this.board.length][this.board.length];
        for(int i=0;i<ans.length;i++){
            for(int j=0;j<ans[0].length;j++){
                ans[i][j] = this.board[i][j];
            }
        }
        String temp = ans[movefromy][movefromx];
        ans[movefromy][movefromx] = ".";
        ans[movetoy][movetox] = temp;
        return new Board(ans);
    }

    public double distancetoCloest(int y, int x, String side){
        double dist = Double.POSITIVE_INFINITY;
        if(side.equals("BLACK")){
            dist = Math.abs(y-15) + Math.abs(x-15);
            if(dist > Math.abs(y-15) + Math.abs(x-14)){
                dist = Math.abs(y-15) + Math.abs(x-14);
            }
            if(dist > Math.abs(y-15) + Math.abs(x-13)){
                dist = Math.abs(y-15) + Math.abs(x-13);
            }
            if(dist > Math.abs(y-15) + Math.abs(x-12)){
                dist = Math.abs(y-15) + Math.abs(x-12);
            }
            if(dist > Math.abs(y-15) + Math.abs(x-11)){
                dist = Math.abs(y-15) + Math.abs(x-11);
            }
            if(dist > Math.abs(y-14) + Math.abs(x-15)){
                dist = Math.abs(y-14) + Math.abs(x-15);
            }
            if(dist > Math.abs(y-14) + Math.abs(x-14)){
                dist = Math.abs(y-14) + Math.abs(x-14);
            }
            if(dist > Math.abs(y-14) + Math.abs(x-13)){
                dist = Math.abs(y-14) + Math.abs(x-13);
            }
            if(dist > Math.abs(y-14) + Math.abs(x-12)){
                dist = Math.abs(y-14) + Math.abs(x-12);
            }
            if(dist > Math.abs(y-14) + Math.abs(x-11)){
                dist = Math.abs(y-14) + Math.abs(x-11);
            }
            if(dist > Math.abs(y-13) + Math.abs(x-15)){
                dist = Math.abs(y-13) + Math.abs(x-15);
            }
            if(dist > Math.abs(y-13) + Math.abs(x-14)){
                dist = Math.abs(y-13) + Math.abs(x-14);
            }
            if(dist > Math.abs(y-13) + Math.abs(x-13)){
                dist = Math.abs(y-13) + Math.abs(x-13);
            }
            if(dist > Math.abs(y-13) + Math.abs(x-12)){
                dist = Math.abs(y-13) + Math.abs(x-12);
            }
            if(dist > Math.abs(y-12) + Math.abs(x-15)){
                dist = Math.abs(y-12) + Math.abs(x-15);
            }
            if(dist > Math.abs(y-12) + Math.abs(x-14)){
                dist = Math.abs(y-12) + Math.abs(x-14);
            }
            if(dist > Math.abs(y-12) + Math.abs(x-13)){
                dist = Math.abs(y-12) + Math.abs(x-13);
            }
            if(dist > Math.abs(y-11) + Math.abs(x-15)){
                dist = Math.abs(y-11) + Math.abs(x-15);
            }
            if(dist > Math.abs(y-11) + Math.abs(x-14)){
                dist = Math.abs(y-11) + Math.abs(x-14);
            }
        }else{
            dist = Math.abs(y) + Math.abs(x);
            if(dist > Math.abs(y) + Math.abs(x-1)){
                dist = Math.abs(y) + Math.abs(x-1);
            }
            if(dist > Math.abs(y) + Math.abs(x-2)){
                dist = Math.abs(y) + Math.abs(x-2);
            }
            if(dist > Math.abs(y) + Math.abs(x-3)){
                dist = Math.abs(y) + Math.abs(x-3);
            }
            if(dist > Math.abs(y) + Math.abs(x-4)){
                dist = Math.abs(y) + Math.abs(x-4);
            }
            if(dist > Math.abs(y-1) + Math.abs(x)){
                dist = Math.abs(y-1) + Math.abs(x);
            }
            if(dist > Math.abs(y-1) + Math.abs(x-1)){
                dist = Math.abs(y-1) + Math.abs(x-1);
            }
            if(dist > Math.abs(y-1) + Math.abs(x-2)){
                dist = Math.abs(y-1) + Math.abs(x-2);
            }
            if(dist > Math.abs(y-1) + Math.abs(x-3)){
                dist = Math.abs(y-1) + Math.abs(x-3);
            }
            if(dist > Math.abs(y-1) + Math.abs(x-4)){
                dist = Math.abs(y-1) + Math.abs(x-4);
            }
            if(dist > Math.abs(y-2) + Math.abs(x)){
                dist = Math.abs(y-2) + Math.abs(x);
            }
            if(dist > Math.abs(y-2) + Math.abs(x-1)){
                dist = Math.abs(y-2) + Math.abs(x-1);
            }
            if(dist > Math.abs(y-2) + Math.abs(x-2)){
                dist = Math.abs(y-2) + Math.abs(x-2);
            }
            if(dist > Math.abs(y-2) + Math.abs(x-3)){
                dist = Math.abs(y-2) + Math.abs(x-3);
            }
            if(dist > Math.abs(y-3) + Math.abs(x)){
                dist = Math.abs(y-3) + Math.abs(x);
            }
            if(dist > Math.abs(y-3) + Math.abs(x-1)){
                dist = Math.abs(y-3) + Math.abs(x-1);
            }
            if(dist > Math.abs(y-3) + Math.abs(x-2)){
                dist = Math.abs(y-3) + Math.abs(x-2);
            }
            if(dist > Math.abs(y-4) + Math.abs(x)){
                dist = Math.abs(y-4) + Math.abs(x);
            }
            if(dist > Math.abs(y-4) + Math.abs(x-1)){
                dist = Math.abs(y-4) + Math.abs(x-1);
            }
        }
        return dist;
    }
    public boolean almostWon(String side){
        int need_to_fill = 0;
        if(side.equals("BLACK")){
            if(campisEmpty("WHITE")) {
                if (this.board[15][15].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[15][14].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[15][13].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[15][12].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[15][11].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[14][15].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[14][14].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[14][13].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[14][12].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[14][11].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[13][15].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[13][14].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[13][13].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[13][12].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[12][15].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[12][14].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[12][13].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[11][15].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[11][14].equals(".")) {
                    need_to_fill++;
                }
            }
        }else{
            if(campisEmpty("BLACK")) {
                if (this.board[0][0].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[0][1].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[0][2].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[0][3].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[0][4].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[1][0].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[1][1].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[1][2].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[1][3].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[1][4].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[2][0].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[2][1].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[2][2].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[2][3].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[3][0].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[3][1].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[3][2].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[4][0].equals(".")) {
                    need_to_fill++;
                }
                if (this.board[4][1].equals(".")) {
                    need_to_fill++;
                }
            }
        }
        return need_to_fill==1;
    }
    //need better eval function
    public double evaluation(String side){
        double eval = 0;
        if(won(side)){
            return 100;
        }
        if(side.equals("BLACK")){
            if(won("WHITE")){
                return -100;
            }
        }
        if(side.equals("WHITE")){
            if(won("BLACK")){
                return -100;
            }
        }
        if(side.equals("BLACK")){
            for(int i=0;i<this.length;i++){
                for(int j=0;j<this.width;j++){
                    if(this.board[i][j].equals("B")){
                        if(inCamp(i,j,"BLACK")){
                            eval += distancetoCloest(i,j,"BLACK") + (Math.abs(i-15)+Math.abs(j-15))+50;
                        }else {
                            eval += distancetoCloest(i, j, "BLACK") + (Math.abs(i - 15) + Math.abs(j - 15));
                        }
                    }
                }
            }
        }else{
            for(int i=0;i<this.length;i++){
                for(int j=0;j<this.width;j++){
                    if(this.board[i][j].equals("W")){
                        if(inCamp(i,j,"WHITE")){
                            eval += distancetoCloest(i,j,"WHITE") + (Math.abs(i)+Math.abs(j)) + 50;
                        }else {
                            eval += distancetoCloest(i, j, "WHITE") + (Math.abs(i) + Math.abs(j));
                        }
                    }
                }
            }
        }
        return -eval;
    }
    public boolean won(String side){
        if(side.equals("BLACK")) {
            if((this.board[15][15].equals("B") ||
                    this.board[15][14].equals("B")||
                    this.board[15][13].equals("B")||
                    this.board[15][12].equals("B")||
                    this.board[15][11].equals("B")||
                    this.board[14][15].equals("B")||
                    this.board[14][14].equals("B")||
                    this.board[14][13].equals("B")||
                    this.board[14][12].equals("B")||
                    this.board[14][11].equals("B")||
                    this.board[13][15].equals("B")||
                    this.board[13][14].equals("B")||
                    this.board[13][13].equals("B")||
                    this.board[13][12].equals("B")||
                    this.board[12][15].equals("B")||
                    this.board[12][14].equals("B")||
                    this.board[12][13].equals("B")||
                    this.board[11][15].equals("B")||
                    this.board[11][14].equals("B")) &&
                    (!this.board[15][15].equals(".")&&
                            !this.board[15][14].equals(".")&&
                            !this.board[15][13].equals(".")&&
                            !this.board[15][12].equals(".")&&
                            !this.board[15][11].equals(".")&&
                            !this.board[14][15].equals(".")&&
                            !this.board[14][14].equals(".")&&
                            !this.board[14][13].equals(".")&&
                            !this.board[14][12].equals(".")&&
                            !this.board[14][11].equals(".")&&
                            !this.board[13][15].equals(".")&&
                            !this.board[13][14].equals(".")&&
                            !this.board[13][13].equals(".")&&
                            !this.board[13][12].equals(".")&&
                            !this.board[12][15].equals(".")&&
                            !this.board[12][14].equals(".")&&
                            !this.board[12][13].equals(".")&&
                            !this.board[11][15].equals(".")&&
                            !this.board[11][14].equals("."))){
                return true;
            }else{
                return false;
            }
        }else{
            if((this.board[0][0].equals("W")||
                    this.board[0][1].equals("W")||
                    this.board[0][2].equals("W")||
                    this.board[0][3].equals("W")||
                    this.board[0][4].equals("W")||
                    this.board[1][0].equals("W")||
                    this.board[1][1].equals("W")||
                    this.board[1][2].equals("W")||
                    this.board[1][3].equals("W")||
                    this.board[1][4].equals("W")||
                    this.board[2][0].equals("W")||
                    this.board[2][1].equals("W")||
                    this.board[2][2].equals("W")||
                    this.board[2][3].equals("W")||
                    this.board[3][0].equals("W")||
                    this.board[3][1].equals("W")||
                    this.board[3][2].equals("W")||
                    this.board[4][0].equals("W")||
                    this.board[4][1].equals("W")) &&
                    (!this.board[0][0].equals(".")&&
                            !this.board[0][1].equals(".")&&
                            !this.board[0][2].equals(".")&&
                            !this.board[0][3].equals(".")&&
                            !this.board[0][4].equals(".")&&
                            !this.board[1][0].equals(".")&&
                            !this.board[1][1].equals(".")&&
                            !this.board[1][2].equals(".")&&
                            !this.board[1][3].equals(".")&&
                            !this.board[1][4].equals(".")&&
                            !this.board[2][0].equals(".")&&
                            !this.board[2][1].equals(".")&&
                            !this.board[2][2].equals(".")&&
                            !this.board[2][3].equals(".")&&
                            !this.board[3][0].equals(".")&&
                            !this.board[3][1].equals(".")&&
                            !this.board[3][2].equals(".")&&
                            !this.board[4][0].equals(".")&&
                            !this.board[4][1].equals("."))){
                return true;
            }else{
                return false;
            }
        }
    }
    /*
    public boolean won(String side){
        if(side.equals("BLACK")){
            if((this.board[5][5].equals("B")||
                    this.board[5][4].equals("B")||
                    this.board[5][3].equals("B")||
                    this.board[4][5].equals("B")||
                    this.board[4][4].equals("B")||
                    this.board[3][5].equals("B")) &&
                    (!this.board[5][5].equals(".")&&
                    !this.board[5][4].equals(".")&&
                    !this.board[5][3].equals(".")&&
                    !this.board[4][5].equals(".")&&
                    !this.board[4][4].equals(".")&&
                    !this.board[3][5].equals("."))){
                return true;
            }else{
                return false;
            }
        }else{
            if((this.board[0][0].equals("W")||
                    this.board[0][1].equals("W")||
                    this.board[0][2].equals("W")||
                    this.board[1][0].equals("W")||
                    this.board[1][1].equals("W")||
                    this.board[2][0].equals("W")) &&
                    (!this.board[0][0].equals(".")&&
                            !this.board[0][1].equals(".")&&
                            !this.board[0][2].equals(".")&&
                            !this.board[1][0].equals(".")&&
                            !this.board[1][1].equals(".")&&
                            !this.board[2][0].equals("."))){
                return true;
            }else{
                return false;
            }
        }
    }*/


    @Override
    public String toString() {
        String ans = "";
        for(int i=0;i<this.length;i++){
            for(int j=0;j<this.width;j++){
                ans += this.board[i][j] + " ";
            }
            ans+= "\n";
        }
        return ans;
    }
}

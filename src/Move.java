public class Move {
    int x;
    int y;
    public Move(int y, int x){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        Move m = (Move) obj;
        return m.y==this.y && m.x==this.x;
    }
}

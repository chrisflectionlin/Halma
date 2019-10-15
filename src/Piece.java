import java.util.ArrayList;

public class Piece {
    int y;
    int x;
    String color;

    public Piece(){
    }

    public Piece(String in_color, int in_y, int in_x){
        y = in_y;
        x = in_x;
        color = in_color;
    }

    public String getColor() {
        return color;
    }

    public int getY(){
        return y;
    }
    public int getX() {
        return x;
    }

    public Piece clone(){
        Piece p = new Piece();
        p.y = y;
        p.x = x;
        p.color = color;
        return p;
    }

    public String toString() {
        return "(" + this.color + ", " + this.y + ", " + this.x + ")";
    }

    public static void main(String[] args){
        ArrayList<Piece> test = new ArrayList<>();
        Piece p1 = new Piece("B",2,1);
        Piece p2 = new Piece("W",5,10);
        test.add(p1);
        test.add(p2);
        System.out.println(test);
        test.remove(new Piece("B",2,1));
        System.out.println(test);
        Piece p3 = p1.clone();
        System.out.println(p1);
        System.out.println(p1);
        System.out.println(p3);
    }
}

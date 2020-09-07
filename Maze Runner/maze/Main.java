package maze;

public class Main {

    public static void main(String[] args) {
       MazeRunner mazeRunner = new MazeRunner();
       Menu menu = new Menu(mazeRunner);

       while (menu.notExit()) {
            menu.action();
       }
    }
}

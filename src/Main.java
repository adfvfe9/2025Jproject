import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        while (true) {
            Game game = new Game();
            game.start(game.startMenu());
        }
    }
}

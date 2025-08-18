import java.util.Scanner;

public class Main {
    public static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        startMenu();
    }
    static int startMenu() {
        String modes[] = {"1. 게임 시작", "2. 게임 설명", "3. 설정"};
        int select = -1;
        int fsel = 0;
        boolean isSelect = false;
        System.out.print("\033[H\033[2J");      // 걍 터미널 초기화
        while (true) {
            System.out.print("\033[H\033[2J");
            if (!isSelect) {
                System.out.println("게임 모드를 선택하세요.\n");
                for (String mode : modes) {
                    System.out.println(mode);
                }
            }
            else {
                feedLine(10);
                System.out.println("s를 눌러 선택하세요.\n");
            }
            if (select <= 3 && select >= 1) {
                for (int i = 0; i < modes.length; i++) {
                    if (select - 1 == i) {
                        System.out.println(red(modes[i]) + " <");
                    } else {
                        System.out.println(modes[i]);
                    }
                }
                fsel = select;
            }
            select = scan.next().charAt(0) - '0';
            if (select != -1) isSelect = true;
            if (select > 3 || select < 1) System.err.println("1부터 3 사이의 값을 입력하세요.");
            if (select == 's' - '0') {
                System.out.println(green("선택되었습니다!"));
                return fsel;
            }
        }
    }
    void feedLine(int a) {
        for (int i = 1; i <= a; i++) {
            System.out.println();
        }
    }
    String reset(String s) {
        return "\u001B[0m" + s;
    }
    String red(String s) {
        return "\u001B[31m" + s;
    }
    String green(String s) {
        return "\u001B[32m" + s;
    }
}

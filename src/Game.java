public class Game {
    boolean start(int gamemode) {
        return true;
    }
    int startMenu() {
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
            if (select > 3 || select < 1) System.err.println("1부터 3 사이의 값을 입력하세요.");
            if (select <= 3 && select >= 1) {
                for (int i = 0; i < modes.length; i++) {
                    if (select - 1 == i) {
                        System.out.println(red(modes[i]) + red(" <"));
                    } else {
                        System.out.println(modes[i]);
                    }
                }
                fsel = select;
            }
            select = Main.scan.next().charAt(0) - '0';
            if (select != -1) isSelect = true;
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
    String red(String s) {
        return "\u001B[31m" + s + "\u001B[0m";
    }
    String green(String s) {
        return "\u001B[32m" + s + "\u001B[0m";
    }
}

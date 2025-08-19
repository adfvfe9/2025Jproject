public class Game {
    boolean start(int gamemode) {
        switch (gamemode) {
            case 1 : {

            } break;
            case 2 : {
                feedLine(10);

            } break;
            case 3 : {
                feedLine(10);
                System.out.println("바꿀 키의 번호를 입력하세요.");
                for (int i = 0; i < keys.length; i++) {
                    System.out.println((i + 1) + ". " + keys[i] + " ----- " + selectkey[i]);
                }
                int changeKeyNum;
                while (true) {
                    changeKeyNum = Main.scan.nextInt();
                    if (changeKeyNum >= 1 && changeKeyNum <= keys.length) {
                        break;
                    }
                }
                System.out.print(keys[changeKeyNum - 1] + " 키를 무슨 키로 바꾸시겠습니까? : ");
                selectkey[changeKeyNum - 1] = Main.scan.next().charAt(0);
                System.out.println(green("변경되었습니다!"));
            }
        }
        return true;
    }
    int startMenu() {
        String modes[] = {"1. 게임 시작", "2. 게임 설명", "3. 설정"};
        int select = -1;
        int fsel = 0;
        boolean isSelect = false;
        System.out.print("\033[H\033[2J");
        while (true) {
            System.out.print("\033[H\033[2J");
            if (!isSelect) {
                System.out.println("게임 모드를 선택하세요.\n");
                for (String mode : modes) {
                    System.out.println(mode);
                }
            } else {
                feedLine(10);
                System.out.println(this.selectkey[0] + "를 눌러 선택하세요.\n");
            }
            if (select > 3 || select < 1)
                System.err.println("1부터 3 사이의 값을 입력하세요.");
            if (select >= 1 && select <= 3) {
                for (int i = 0; i < modes.length; i++) {
                    if (select - 1 == i) {
                        System.out.println(red(modes[i]) + red(" <"));
                    } else {
                        System.out.println(modes[i]);
                    }
                }
                fsel = select;
            }
            String input = Main.scan.next();
            if (input.length() == 1) {
                char c = input.charAt(0);
                if (c >= '1' && c <= '3') {
                    select = c - '0';
                    isSelect = true;
                }
                else if (isSelect && c == this.selectkey[0]) {
                    System.out.println(green("선택되었습니다!"));
                    return fsel;
                }
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
    String keys[] = {"선택"};
    static char selectkey[] = {'s'};   // 0.select
}

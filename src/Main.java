import java.io.File;
import java.util.*;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static Random ran = new Random();
    public static void main(String[] args) {
        String keys[] = {"상호작용"};
        char selectkey[] = {'s'};
        while (true) {
            String modes[] = {"1. 게임 시작", "2. 게임 설명", "3. 설정", "4. 프로그램 종료"};
            int select = -1;
            int fsel = 0;
            boolean isSelect = false;

            while (true) {
                if (!isSelect) {
                    System.out.println("게임 모드를 선택하세요.\n");
                    for (String mode : modes) {
                        System.out.println(mode);
                    }
                } else {
                    resetScreen();
                    System.out.println(selectkey[0] + "를 눌러 선택하세요.\n");
                }

                if (select >= 1 && select <= 4) {
                    for (int i = 0; i < modes.length; i++) {
                        if (select - 1 == i) {
                            System.out.println(red(modes[i]) + red(" <"));
                        } else {
                            System.out.println(modes[i]);
                        }
                    }
                    fsel = select;
                }

                String input = scan.next();
                if (input.length() == 1) {
                    char c = input.charAt(0);
                    if (c >= '1' && c <= '4') {
                        select = c - '0';
                        isSelect = true;
                    } else if (isSelect && c == selectkey[0]) {
                        System.out.println(green("선택되었습니다!"));
                        newLine();
                        break;
                    }
                }
            }
            switch (fsel) {
                case 1: {
                    resetScreen();
                    new Game().start();
                } break;

                case 2: {
                    resetScreen();
                } break;

                case 3: {
                    System.out.println("바꿀 키의 번호를 입력하세요.");
                    for (int i = 0; i < keys.length; i++) {
                        System.out.println((i + 1) + ". " + keys[i] + " ----- " + selectkey[i]);
                    }
                    int changeKeyNum;
                    while (true) {
                        changeKeyNum = scan.nextInt();
                        if (changeKeyNum >= 1 && changeKeyNum <= keys.length) {
                            break;
                        }
                    }
                    System.out.print(red(keys[changeKeyNum - 1] + " (" + selectkey[changeKeyNum - 1] + ") ") + "키를 무슨 키로 바꾸시겠습니까? : ");
                    selectkey[changeKeyNum - 1] = scan.next().charAt(0);
                    System.out.println(green("변경되었습니다!"));
                    newLine();
                } break;

                case 4 : {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
            }
        }
    }
    static void resetScreen() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
    static void newLine() {
        System.out.println("-------------------------------------------------");
    }
    static String red(String s) {
        return "\u001B[31m" + s + "\u001B[0m";
    }
    static String green(String s) {
        return "\u001B[32m" + s + "\u001B[0m";
    }
}
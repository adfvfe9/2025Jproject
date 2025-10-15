import java.io.File;
import java.util.*;

public class Main {
    static Scanner scan = new Scanner(System.in);
    static Random ran = new Random();
    static String keys[] = {"상호작용", "취소"};
    static char selectkey[] = {'s', 'q'};
    static Object settings[][] = new Object[][]{{"딜레이 On / Off", true}};
    public static void main(String[] args) {

        while (true) {
            String modes[] = {"1. 게임 시작", "2. 게임 설명", "3. 키 설정", "4. 기능 설정", "5. 프로그램 종료"};
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

                if (select >= 1 && select <= 5) {
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
                    if (c >= '1' && c <= '5') {
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
                    System.out.println("바꿀 설정의 번호를 입력하세요.");
                    for (int i = 0; i < settings.length; i++) {
                        System.out.println((i + 1) + ". " + settings[i][0] + " ----- " + ((boolean)settings[i][1] ? "켜짐" : "꺼짐"));
                    }
                    int changeKeyNum;
                    while (true) {
                        changeKeyNum = scan.nextInt();
                        if (changeKeyNum >= 1 && changeKeyNum <= settings.length) {
                            break;
                        }
                    }
                    scan.nextLine();
                    System.out.print(red(settings[changeKeyNum - 1][0].toString()) + " 설정을 " + (((boolean)settings[changeKeyNum - 1][1]) ? "끄시겠습니까? " : "켜시겠습니까? ") + "(" + selectkey[0] + " / " + selectkey[1] + ") : ");
                    char input = scan.nextLine().charAt(0);
                    if (input == selectkey[0]) {
                        settings[changeKeyNum - 1][1] = !(boolean)settings[changeKeyNum - 1][1];
                        System.out.println(green("변경되었습니다!"));
                    } else if (input == selectkey[1]) {
                        System.out.println("취소되었습니다!");
                    } else {
                        System.err.println("올바르지 않은 입력입니다!");
                    }
                    newLine();
                    break;
                }

                case 5 : {
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
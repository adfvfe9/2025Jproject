import java.io.File;
import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in); // 쓰기 편하게 하려고 static
    public static void main(String[] args) {
        String keys[] = {"선택"};
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
                    feedLine(10);
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
                        break;
                    }
                }
            }
            switch (fsel) {
                case 1: {
                    feedLine(10);
                    while (true) {
                        System.out.print("2명 ~ 4명 사이의 인원 수 입력 : ");
                        int n = scan.nextInt();
                        if (n < 2 || n > 4) {
                            System.out.println("2 ~ 4 사이의 정수를 입력하세요.");
                        } else {
                            new Game(n);
                            break;
                        }
                    }

                } break;

                case 2: {
                    feedLine(10);
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
                    System.out.print(keys[changeKeyNum - 1] + " 키를 무슨 키로 바꾸시겠습니까? : ");
                    selectkey[changeKeyNum - 1] = scan.next().charAt(0);
                    System.out.println(green("변경되었습니다!"));
                } break;

                case 4 : {
                    System.out.println("프로그램을 종료합니다.");
                    return;
                }
            }
        }
    }
    static void feedLine(int a) {
        for (int i = 1; i <= a; i++) {
            System.out.println();
        }
    }
    static String red(String s) {
        return "\u001B[31m" + s + "\u001B[0m";
    }
    static String green(String s) {
        return "\u001B[32m" + s + "\u001B[0m";
    }
}
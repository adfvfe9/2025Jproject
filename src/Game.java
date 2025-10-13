import java.util.ArrayList;
import java.util.Arrays;

class Game {
    static String items[] = {"수갑", "돋보기", "쇠톱", "대포폰", "변환기", "아드레날린", "상한 약", "진통제", "에너지드링크"};

    int currentRound = 1;
    boolean isFirstOfRound = true;
    boolean isPlayerRound = true;
    int index = 0;
    boolean bullets[];
    Player p = new Player("player", currentRound * 2);
    Bot b = new Bot("bot", currentRound * 2);


    ArrayList<Object> playerstates = new ArrayList<>();
    long startTime = System.currentTimeMillis();

    Game() {

    }
    void start() {
        while (true) {
            if (isFirstOfRound) {
                isFirstOfRound = false;
                printRound();
                bullets = printBullets();
                p.resetItems();
                b.resetItems();
            }
            if (p.getIndex() != -1) {
                p.items[p.getIndex()] = items[Main.ran.nextInt(items.length)];
            }
            if (b.getIndex() != -1) {
                b.items[b.getIndex()] = items[Main.ran.nextInt(items.length)];
            }
            System.out.println(p);
            System.out.println(b + "\n");
            if (isPlayerRound) {
                System.out.print("행동을 선택하세요 (1 - 본인 / 2 - 봇 / 3 - 아이템 사용) : ");
                Outer: while (true) {
                    int sel = Main.scan.nextInt();
                    switch (sel) {
                        case 1: {
                            for (int i = 1; i <= 3; i++) {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                }
                                System.out.print(".  ");
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                            }
                            System.out.println();
                            if (!bullets[index++]) {
                                System.out.println("👍 공포탄!");
                            } else {
                                System.out.println("💣 실탄!");
                                p.health -= 1;
                                isPlayerRound = false;
                            }
                            System.out.println();
                        }
                        break Outer;
                        case 2: {

                        }
                        break Outer;
                        case 3: {

                        }
                        default: {
                            System.out.println("1과 2 사이의 값을 입력해 주세요");
                        }
                    }
                }
            }
        }
    }
    void printRound() {
        for (int i = 1; i <= 3; i++) {
            if (i == currentRound) {
                System.out.print("Round " + i + " ●");
            } else {
                System.out.print("Round " + i + " ○");
            }
            if (i != 3) {
                System.out.print("   ");
            }
        }
        System.out.println();
    }
    boolean[] printBullets(){
        int bullets = 2 + 2 * currentRound;
        int gongpotan = Main.ran.nextInt(bullets - 1) + 1;
        // System.out.println("공포탄 수 : " + gongpotan);
        int siltan = bullets - gongpotan;
        System.out.print("\n현재 라운드 총알 : ");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        for (int i = 1; i <= gongpotan; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}
            System.out.print("□ ");
        }
        for (int i = 1; i <= siltan; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}
            System.out.print("■ ");
        }
        System.out.println();

        boolean[] arr = new boolean[gongpotan + siltan];
        int index = 0;
        for (int k = 0; k < gongpotan; k++) arr[index++] = false;
        for (int k = 0; k < siltan; k++) arr[index++] = true;
        for (int k = arr.length - 1; k > 0; k--) {
            int r = Main.ran.nextInt(k + 1);
            boolean temp = arr[k];
            arr[k] = arr[r];
            arr[r] = temp;
        }
        return arr;
    }
}

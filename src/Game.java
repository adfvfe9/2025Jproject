import java.util.*;

class Game {
    static String items[] = {"수갑", "돋보기", "쇠톱", "대포폰", "변환기", "아드레날린", "상한 약", "진통제", "에너지 드링크"};

    int currentRound = 1;
    boolean isFirstOfRound = true;
    boolean isPlayerRound = true;
    boolean playerSugab = false;
    boolean botSugab = false;
    boolean isPlayerWin = false;
    int ironTobBonus = 0;
    int index = 0;
    boolean bullets[];
    Player p = new Player("player", currentRound * 2);
    Bot b = new Bot("bot", currentRound * 2);

    ArrayList<Object> playerstates = new ArrayList<>();
    long startTime = System.currentTimeMillis();

    Game() {}

    void start() {
        Outer: while (true) {
            startRoundSetting();
            giveItem();
            printStatus();
            itemEffectCheck();

            if (isPlayerRound) {
                playerTurn();
            } else {
                b.play(this);
                isPlayerRound = true;
            }

            if (victoryCheck()) break Outer;
            roundEndCheck();
        }
    }

    void startRoundSetting() {
        if (isFirstOfRound) {
            isFirstOfRound = false;
            printRound();
            bullets = printBullets();
            index = 0;
            b.knownBullets.clear();
        }
    }

    void giveItem() {
        if (p.getItemCount() != 8) {
            p.items[p.getItemCount()] = items[Main.ran.nextInt(items.length)];
        }
        if (b.getItemCount() != 8) {
            b.items[b.getItemCount()] = items[Main.ran.nextInt(items.length)];
        }
    }
    void giveItem(int index) {
        if (p.getItemCount() != 8) {
            p.items[p.getItemCount()] = b.items[index];
        } else {
            System.out.println("아이템 칸이 꽉 찼습니다!");
            useItem();
        }
    }
    void giveItem(String itemName) {
        if (p.getItemCount() != 8) {
            p.items[p.getItemCount()] = itemName;
        } else {
            System.out.println("아이템 칸이 꽉 찼습니다!");
            useItem();
        }
    }

    void printStatus() {
        System.out.println(p);
        System.out.println(b + "\n");
        if (isPlayerRound) {
            System.out.print("행동을 선택하세요 (1 - 본인에게 쏘기 / 2 - 봇에게 쏘기 / 3 - 아이템 사용 / 4 - 강제 종료) : ");
        }
    }

    void playerTurn() {
        Inner: while (true) {
            int sel = Main.scan.nextInt();
            switch (sel) {
                case 1: {
                    if ((boolean)Main.settings[0][1]) {
                        simulateDelay();
                    }
                    System.out.println();
                    if (!bullets[index++]) {
                        System.out.println("👍 공포탄!");
                    } else {
                        System.out.println("💣 실탄!");
                        p.health -= 1 + ironTobBonus;
                        isPlayerRound = false;
                    }
                    System.out.println();
                }
                break Inner;
                case 2: {
                    if ((boolean)Main.settings[0][1]) {
                        simulateDelay();
                    }
                    System.out.println();
                    if (!bullets[index++]) {
                        System.out.println("💣 공포탄!");
                    } else {
                        System.out.println("👍 실탄!");
                        b.health -= 1 + ironTobBonus;
                    }
                    isPlayerRound = false;
                    System.out.println();
                }
                break Inner;
                case 3: {
                    useItem();
                    printStatus();
                    break;
                }
                case 4 : {
                    System.out.println("⚠️ 게임을 강제 종료합니다.");
                    break Inner;
                }
                default: {
                    System.out.println("1과 3 사이의 값을 입력해 주세요");
                }
            }
        }
    }

    void roundEndCheck() {
        if (index >= bullets.length) {
            isFirstOfRound = true;
        }
    }

    boolean victoryCheck() {
        if (b.health <= 0) {
            if (currentRound == 3) { // 현재 라운드가 3라운드인지 확인
                System.out.println(Main.green("\n게임을 클리어하셨습니다!\n"));
                isPlayerWin = true;
                return true; // 게임 클리어 메시지 출력 후 true를 반환하여 게임 종료
            } else {
                System.out.println(Main.green("\n승리했습니다!\n"));
                currentRound++;
                isFirstOfRound = true;
                isPlayerRound = true;
                p.maxhealth = currentRound * 2;
                b.maxhealth = currentRound * 2;
                p.health = currentRound * 2;
                b.health = currentRound * 2;
                playerSugab = false;
                botSugab = false;
                ironTobBonus = 0;
                p.resetItems();
                b.resetItems();
            }
        }
        if (p.health <= 0) {
            System.out.println(Main.red("\n패배했습니다...\n"));
            return true;
        }
        return false;
    }

    void simulateDelay() {
        for (int i = 1; i <= 3; i++) {
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
            System.out.print(".  ");
        }
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }

    void printRound() {
        for (int i = 1; i <= 3; i++) {
            if (i == currentRound) System.out.print("Round " + i + " ●");
            else System.out.print("Round " + i + " ○");
            if (i != 3) System.out.print("   ");
        }
        System.out.println();
    }

    boolean[] printBullets() {
        int bullets = 2 + 2 * currentRound;
        int gongpotan = Main.ran.nextInt(bullets - 1) + 1;
        int siltan = bullets - gongpotan;

        System.out.print("\n현재 라운드 총알 : ");
        if ((boolean)Main.settings[0][1]) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
        for (int i = 1; i <= gongpotan; i++) {
            if ((boolean)Main.settings[0][1]) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
            }
            System.out.print("□ ");
        }
        for (int i = 1; i <= siltan; i++) {
            if ((boolean)Main.settings[0][1]) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {}
            }
            System.out.print("■ ");
        }
        System.out.println();

        boolean[] arr = new boolean[gongpotan + siltan];
        int idx = 0;
        for (int k = 0; k < gongpotan; k++) arr[idx++] = false;
        for (int k = 0; k < siltan; k++) arr[idx++] = true;
        shuffleArray(arr);
        return arr;
    }

    void shuffleArray(boolean[] arr) {
        for (int k = arr.length - 1; k > 0; k--) {
            int r = Main.ran.nextInt(k + 1);
            boolean temp = arr[k];
            arr[k] = arr[r];
            arr[r] = temp;
        }
    }
    void useItem() {
        int currentItemCount = p.getItemCount();
        if (currentItemCount == 0) {
            System.out.println("사용할 수 있는 아이템이 없습니다...");
            return;
        }

        int itemSel = 0;
        while (true) {
            System.out.print("사용할 아이템의 번호를 입력하세요 (취소: " + Main.selectkey[1] + ") : ");
            String input = Main.scan.next();

            if (input.equalsIgnoreCase(String.valueOf(Main.selectkey[1]))) {
                System.out.println("\n아이템 사용을 취소했습니다.");
                return;
            }

            try {
                itemSel = Integer.parseInt(input);
                if (itemSel >= 1 && itemSel <= currentItemCount) {
                    break;
                } else {
                    System.out.println("올바른 아이템 번호를 입력해 주세요!");
                }
            } catch (NumberFormatException e) {
                System.out.println("올바른 번호 또는 취소 키를 입력해 주세요.");
            }
        }

        System.out.println();
        String usedItem = p.items[itemSel - 1];
        System.out.println(usedItem + "을(를) 사용했습니다!\n");

        int removeIndex = itemSel - 1;
        for (int i = removeIndex; i < currentItemCount - 1; i++) {
            p.items[i] = p.items[i + 1];
        }
        p.items[currentItemCount - 1] = "";

        switch (usedItem) {
            case "수갑" : {
                if (isPlayerRound) {
                    if (botSugab) {
                        System.out.println("⚠️ 이미 봇은 수갑을 찬 상태입니다.");
                        giveItem("수갑");
                        break;
                    }
                    botSugab = true;
                    System.out.println("🔒 봇이 1턴 쉽니다.");
                } else {
                    playerSugab = true;
                    System.out.println("🔒 플레이어가 1턴 쉽니다.");
                }
                break;
            }
            case "돋보기" : {
                System.out.println("이번 총알은 " + ((bullets[index]) ? "실탄입니다!" : "공포탄입니다!"));
                break;
            }
            case "쇠톱" : {
                if (ironTobBonus == 0) {
                    System.out.println("💪 다음 총알의 피해량이 2배 증가합니다!");
                    ironTobBonus = 1;
                } else {
                    System.out.println("⚠️ 이미 쇠톱이 활성화되었습니다!");
                    giveItem("쇠톱");
                }
                break;
            }
            case "대포폰": {
                if (index >= bullets.length - 1) {
                    System.out.println("확인할 다음 총알이 없습니다!");
                    break;
                }
                int daepoIndex = Main.ran.nextInt(bullets.length - (index + 1)) + (index + 1);
                System.out.println("📞 " + (daepoIndex + 1) + "번째 총알은 " + (bullets[daepoIndex] ? "실탄입니다!" : "공포탄입니다!"));
                break;
            }
            case "변환기" : {
                System.out.println("🔁 총알이 변환되었습니다!");
                for (int i = index; i < bullets.length; i++) {
                    bullets[i] = !bullets[i];
                }
                break;
            }
            case "아드레날린" : {
                int botItemSel;
                if (b.getItemCount() == 0) {
                    System.out.println("봇이 가지고 있는 아이템이 없습니다!");
                    break;
                }
                while (true) {
                    System.out.print("빼앗을 아이템의 번호를 입력하세요 (취소: " + Main.selectkey[1] + ") : ");
                    String input = Main.scan.next();

                    try {
                        botItemSel = Integer.parseInt(input);
                        if (botItemSel >= 1 && botItemSel <= b.getItemCount()) {
                            break;
                        } else {
                            System.out.println("올바른 아이템 번호를 입력해 주세요!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("올바른 번호 또는 취소 키를 입력해 주세요.");
                    }
                }
                if (!b.items[botItemSel - 1].equals("아드레날린")) {
                    System.out.println("⛓️‍💥 봇에게서 " + b.items[botItemSel - 1] + "을(를) 빼앗았습니다!");

                    giveItem(botItemSel - 1);
                    removeIndex = botItemSel - 1;
                    for (int i = removeIndex; i < b.getItemCount() - 1; i++) {
                        b.items[i] = b.items[i + 1];
                    }
                    b.items[b.getItemCount() - 1] = "";
                } else {
                    System.out.println("⚠️ 아드레날린으로 아드레날린을 빼앗을 수 없습니다!");
                    giveItem("아드레날린");
                }
                break;
            }
            case "상한 약" : {
                if (Main.ran.nextBoolean()) {
                    p.heal(2);
                    System.out.println("♥️ 플레이어의 체력이 2만큼 회복되었습니다!");
                } else {
                    p.heal(-1);
                    System.out.println("💔 플레이어의 체력이 1만큼 감소했습니다");
                }
                break;
            }
            case "진통제" : {
                p.heal(1);
                System.out.println("♥️ 플레이어의 체력이 1만큼 회복되었습니다!");
                break;
            }
            case "에너지 드링크" : {
                if (index >= bullets.length) {
                    System.out.println("제거할 총알이 없습니다!");
                    break;
                }
                System.out.println(((bullets[index++]) ? "실탄" : "공포탄") + "을 하나 제거하였습니다.");
                break;
            }
        }
    }
    void itemEffectCheck() {
        if (isPlayerRound && playerSugab) {
            System.out.println("플레이어는 수갑에 묶여 행동할 수 없습니다!");
            playerSugab = false;
            isPlayerRound = false;
        }
        if (!isPlayerRound && botSugab) {
            System.out.println("봇은 수갑에 묶여 행동할 수 없습니다!");
            botSugab = false;
            isPlayerRound = true;
            printStatus();
        }
        if (ironTobBonus == 1) {
            ironTobBonus = 0;
        }
    }
}
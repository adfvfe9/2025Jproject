import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

class Bot extends Player {
    Map<Integer, Boolean> knownBullets = new HashMap<>();

    Bot(String name, int maxhealth) {
        super(name, maxhealth);
    }

    void play(Game game) {
        System.out.print("🤖 봇의 턴입니다");
        if ((boolean)Main.settings[0][1]) game.simulateDelay();
        else System.out.println(".  .  .");

        if (knownBullets.containsKey(game.index)) {
            if (knownBullets.get(game.index)) {
                shootPlayer(game);
            } else {
                shootSelf(game);
            }
            return;
        }

        if (shouldUseItemNow(game)) {
            useItemAI(game);
            return;
        }

        decideActionByInference(game);
    }

    private boolean shouldUseItemNow(Game game) {
        if (getItemCount() > 0) {
            if (this.health <= 1 && (hasItem("진통제") || hasItem("상한 약"))) return true;
            if (game.p.health <= 2 && hasItem("쇠톱") && game.ironTobBonus == 0) return true;
        }
        return false;
    }

    private void decideActionByInference(Game game) {
        int liveCount = 0;
        int blankCount = 0;
        for (int i = game.index; i < game.bullets.length; i++) {
            if (game.bullets[i]) liveCount++;
            else blankCount++;
        }

        if (liveCount + blankCount == 0) {
            shootPlayer(game);
            return;
        }

        double liveChance = (double) liveCount / (liveCount + blankCount);

        if (liveChance >= 0.65) {
            shootPlayer(game);
        } else if (liveChance <= 0.35) {
            shootSelf(game);
        } else {
            if (getItemCount() > 0) useItemAI(game);
            else {
                if (this.health > game.p.health) shootSelf(game);
                else shootPlayer(game);
            }
        }
    }

    void shootPlayer(Game game) {
        if (game.index >= game.bullets.length || !game.bullets[game.index++]) {
            System.out.println("👍 공포탄!");
        } else {
            System.out.println("💣 실탄!");
            game.p.health -= 1 + game.ironTobBonus;
            game.ironTobBonus = 0;
        }
    }

    void shootSelf(Game game) {
        knownBullets.remove(game.index);
        if ((boolean)Main.settings[0][1]) game.simulateDelay();

        if (game.index >= game.bullets.length || !game.bullets[game.index++]) {
            System.out.println("👍 공포탄!");
            this.play(game);
        } else {
            System.out.println("💣 실탄!");
            this.health -= 1 + game.ironTobBonus;
            game.ironTobBonus = 0;
        }
    }

    void useItemAI(Game game) {
        if (getItemCount() == 0) {
            shootPlayer(game);
            return;
        }

        String item = chooseBestItem(game);
        System.out.println(item + "을(를) 사용했습니다!");
        removeItemFromArray(item);

        switch (item) {
            case "돋보기" -> {
                if (game.index < game.bullets.length) {
                    knownBullets.put(game.index, game.bullets[game.index]);
                }
            }
            case "대포폰" -> {
                if (game.index >= game.bullets.length - 1) {
                    break;
                }
                int idx = Main.ran.nextInt(game.bullets.length - (game.index + 1)) + (game.index + 1);
                knownBullets.put(idx, game.bullets[idx]);
            }
            case "변환기" -> {
                System.out.println("🔁 총알이 변환되었습니다!");
                for (int i = game.index; i < game.bullets.length; i++) {
                    game.bullets[i] = !game.bullets[i];
                }
                knownBullets.clear();
            }
            case "수갑" -> {
                if (game.botSugab) { break; }
                game.playerSugab = true;
                System.out.println("🔒 플레이어가 1턴 쉽니다.");
            }
            case "쇠톱" -> {
                if (game.ironTobBonus == 0) {
                    game.ironTobBonus = 1;
                    System.out.println("💪 다음 총알의 피해량이 2배 증가합니다!");
                }
            }
            case "아드레날린" -> {
                if (game.p.getItemCount() == 0) { break; }
                int stealIdx = Main.ran.nextInt(game.p.getItemCount());
                String stolen = game.p.items[stealIdx];
                if (stolen.equals("아드레날린")) { break; }
                System.out.println("⛓️‍💥 봇에게서 " + stolen + "을(를) 빼앗았습니다!");
                this.items[this.getItemCount()] = stolen;
                for (int i = stealIdx; i < game.p.getItemCount() - 1; i++)
                    game.p.items[i] = game.p.items[i + 1];
                game.p.items[game.p.getItemCount() - 1] = "";
            }
            case "상한 약" -> {
                if (Main.ran.nextBoolean()) {
                    this.heal(2);
                    System.out.println("♥️ 플레이어의 체력이 2만큼 회복되었습니다!");
                } else {
                    this.heal(-1);
                    System.out.println("💔 플레이어의 체력이 1만큼 감소했습니다");
                }
            }
            case "진통제" -> {
                this.heal(1);
                System.out.println("♥️ 플레이어의 체력이 1만큼 회복되었습니다!");
            }
            case "에너지 드링크" -> {
                if (game.index >= game.bullets.length) { break; }
                System.out.println(((game.bullets[game.index]) ? "실탄" : "공포탄") + "을 하나 제거하였습니다.");
                knownBullets.remove(game.index);
                game.index++;
            }
        }
    }

    private String chooseBestItem(Game game) {
        List<String> currentItems = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            currentItems.add(items[i]);
        }

        if (this.health <= 1) {
            if (currentItems.contains("진통제")) return "진통제";
            if (currentItems.contains("상한 약")) return "상한 약";
        }
        if (!knownBullets.containsKey(game.index)) {
            if (currentItems.contains("돋보기")) return "돋보기";
        }
        if (game.ironTobBonus == 0 && currentItems.contains("쇠톱")) return "쇠톱";
        if (!game.playerSugab && currentItems.contains("수갑")) return "수갑";

        return items[0];
    }

    private void removeItemFromArray(String item) {
        int removeIndex = -1;
        for (int i = 0; i < getItemCount(); i++) {
            if (items[i].equals(item)) {
                removeIndex = i;
                break;
            }
        }
        if (removeIndex != -1) {
            for (int i = removeIndex; i < getItemCount() - 1; i++) {
                items[i] = items[i + 1];
            }
            items[getItemCount() - 1] = "";
        }
    }

    private boolean hasItem(String itemName) {
        for (int i = 0; i < getItemCount(); i++) {
            if (items[i].equals(itemName)) return true;
        }
        return false;
    }
}

/* TODO
    프로그램 매우 불친절
    웬만하면? GUI로 하는게 좋을듯
    player, bot 상속해주는 아빠 하나 만들기
    게임 설명 좀 넣기
    data에 날짜같은것도 넣기
    된다면 멀티플레이 구현도 해보기
    체력, 총알 대미지 등등 플레이어가 꼭 알아야 하는 것들은 웬만하면 표기하기
    재미없음. 좀 레벨이나 라운드별로 변하는 걸 넣어보기
    웬만하면 일반 배열보다는 ArrayList를 이용해서 만들어보기 (아이템 등등)

 */
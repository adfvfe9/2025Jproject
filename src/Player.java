import java.util.Arrays;

class Player {
    String name;
    int maxhealth;
    int health;
    String items[] = new String[8];

    int getItemCount() {
        for (int i = 0; i < items.length; i++) {
            if (items[i].equals("")) {
                return i;
            }
        }
        return 8;
    }

    Player(String name, int maxhealth) {
        this.name = name;
        this.maxhealth = maxhealth;
        health = maxhealth;
        Arrays.fill(items, "");
    }

    public String toString() {
        return getStatusString(name);
    }

    String getStatusString(String name) {
        StringBuilder s = new StringBuilder(String.format("%-10s | ", name));
        for (int i = 1; i <= maxhealth; i++) {
            if (health >= i) {
                s.append("♥");
            } else {
                s.append("♡");
            }
        }
        s.append(" | ");
        if (getItemCount() == -1) {
            s.append(Arrays.toString(items));
        } else if (getItemCount() == 0) {
            s.append("아이템 없음");
        } else {
            s.append("[");
            for (int i = 0; i < getItemCount(); i++) {
                s.append(i + 1).append(" : ").append(items[i]);
                if (i != getItemCount() - 1) {
                    s.append(", ");
                }
            }
            s.append("]");
        }
        return s.toString();
    }

    void resetItems() {
        Arrays.fill(items, "");
    }

    void heal(int health) {
        this.health = Math.min(this.health + health, maxhealth);
    }
}

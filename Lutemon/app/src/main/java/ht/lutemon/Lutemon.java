package ht.lutemon;

import java.util.Locale;

public class Lutemon {

    int id;
    String name;
    int background_color;
    String team;
    int attack;
    int defence;
    int currentHealth;
    int maxHealth;
    int xp;
    int imageSource;

    public Lutemon() {

    }

    public int getImageSource() {
        return imageSource;
    }

    public String getTeam() {
        return team;
    }

    public String getName() {
        return name;
    }

    public int getAttack() {
        return attack;
    }

    public int getBackground_color() {
        return background_color;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getDefence() {
        return defence;
    }

    public int getId() {
        return id;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getXp() {
        return xp;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setXp(int xp) {
        this.xp = xp;
    }

    public String statString() {
        return String.format(Locale.getDefault(),
                "Attack: %d\nDefence: %d\nHealth: %d/%d\nXP: %d",
                attack, defence, currentHealth, maxHealth, xp);
    }

    public String oneLineInfo() {
        return String.format(Locale.getDefault(),
                "%s [%s] CP %d, DP %d, HP %d/%d, XP %d",
                 name, team.toUpperCase(),attack, defence, currentHealth, maxHealth, xp);
    }
}

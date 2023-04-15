package ht.lutemon;

import android.util.Log;

import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.Locale;

public class Lutemon implements Serializable {

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

    String arena;

    public Lutemon() {
        int idCounter = Helper.getMaxID();
        Log.d("ZZ lutemon", "idCounter: " + idCounter );

        id = ++idCounter;
        arena = Arena.HOME.name();
        xp = 0;
        currentHealth = maxHealth;
        Log.d("ZZ Lutemon", "constructor id = " + id);
    }

    public String getArena() {
        return arena;
    }

    public void setArena(String arena) {
        this.arena = arena;
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


    public String statLongInfo() {
        return String.format(Locale.getDefault(),
                "Attack: %d\nDefence: %d\nHealth: %d/%d\nXP: %d",
                attack, defence, currentHealth, maxHealth, xp);
    }

    public String shortInfo() {
        return String.format(Locale.getDefault(),
                "%s [%s] CP %d, DP %d, HP %d/%d, XP %d",
                 name, team.toUpperCase(), attack, defence, currentHealth, maxHealth, xp);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s [%s]\n\n%s", name, team, this.statLongInfo());
    }
}
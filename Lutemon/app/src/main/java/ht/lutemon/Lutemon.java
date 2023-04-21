package ht.lutemon;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Locale;

@Entity(tableName="Lutemons")
public class Lutemon implements Cloneable, Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="name")
    String name;

    @ColumnInfo(name="backColor")
    int background_color;

    @ColumnInfo(name="team")
    String team;
    @ColumnInfo(name="attack")
    int attack;
    @ColumnInfo(name="defence")
    int defence;
    @ColumnInfo(name="currentHealth")
    int currentHealth;
    @ColumnInfo(name="maxHealth")
    int maxHealth;
    @ColumnInfo(name="xp")
    int xp;
    @ColumnInfo(name="icon")
    int imageSource;

    @ColumnInfo(name="arena")
    String arena;

    public Lutemon() {
        arena = Arena.HOME.name();
        xp = 0;
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

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
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


    public String statMultilineInfo() {
        return String.format(Locale.getDefault(),
                "Attack: %d\nDefence: %d\nHealth: %d/%d\nXP: %d",
                attack, defence, currentHealth, maxHealth, xp);
    }

    public String shortInfo() {
        return String.format(Locale.getDefault(),
                "%s [%s] CP %d DP %d HP %d/%d XP %d",
                 name, team, attack, defence, currentHealth, maxHealth, xp);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s [%s]\n\n%s", name, team, this.statMultilineInfo());
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String battleOutputFormat() {
        return String.format(Locale.getDefault(),
                "%d: %s(%s) att: %d; def: %d; exp:% d; health: %d/%d\n",
                id, team, name, attack, defence, xp, currentHealth, maxHealth);
    }

    public String attack(Lutemon opponent) {

        String ret = this.battleOutputFormat() + opponent.battleOutputFormat() +
                String.format(Locale.getDefault(),
                        "%s(%s) attacks %s(%s)\n",
                        this.team, this.name, opponent.getTeam(), opponent.getName());

        int damage = (this.attack + this.xp);
        opponent.setCurrentHealth(opponent.getCurrentHealth() + opponent.getDefence() - damage);

        return ret;
    }
}
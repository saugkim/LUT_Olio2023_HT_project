package ht.lutemon;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Green extends Lutemon implements Serializable {
    public Green() {
        super();
        background_color = 0xFFEDF3EA;
        imageSource = R.mipmap.green_back;
        name = "Rare Green";
        team = Team.GREEN.toString();
        attack = 6;
        defence = 3;
        maxHealth = 19;
        currentHealth = maxHealth;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s [%s]\n\n%s", name, team, this.statMultilineInfo());
    }
}

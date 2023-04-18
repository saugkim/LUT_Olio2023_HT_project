package ht.lutemon;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Orange extends Lutemon implements Serializable {
    public Orange() {
        super();
        background_color = 0xFFF6EAD7;
        imageSource = R.mipmap.oragne_back;
        name = "Epic Oranssi";
        team = Team.ORANGE.toString();
        attack = 8;
        defence = 1;
        maxHealth = 17;
        currentHealth = maxHealth;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s [%s]\n\n%s", name, team, this.statMultilineInfo());
    }
}

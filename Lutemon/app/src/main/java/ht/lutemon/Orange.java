package ht.lutemon;

import androidx.annotation.NonNull;

public class Orange extends Lutemon {
    public Orange() {
        super();
        background_color = 0xFFF6EAD7;
        imageSource = R.mipmap.oragne_back;
        name = "Epic Oranssi";
        team = "orange";
        attack = 8;
        defence = 1;
        maxHealth = 17;
        currentHealth = maxHealth;
        xp = 0;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s [%s]\n\n%s", name, team, this.statString());
    }
}

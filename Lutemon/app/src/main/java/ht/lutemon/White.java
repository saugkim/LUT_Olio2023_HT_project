package ht.lutemon;

import androidx.annotation.NonNull;

public class White extends Lutemon{
    public White() {
        super();
        background_color = 0xFFF6F6FB;
        imageSource = R.mipmap.white_back;
        name = "Valkea";
        team = "white";
        attack = 5;
        defence = 4;
        maxHealth = 20;
        currentHealth = maxHealth;
        xp = 0;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s [%s]\n\n%s", name, team, this.statString());
    }
}

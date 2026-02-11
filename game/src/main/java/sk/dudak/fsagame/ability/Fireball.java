package sk.dudak.fsagame.ability;

import sk.dudak.fsagame.character.Character;

/**
 * Fireball is casted ability that wil do damage to single target
 */
public class Fireball extends Ability {

    private int baseDamage = 5;

    public Fireball() {
        super(AbilityId.FIREBALL, AbilityClass.DAMAGING, 3, new Fireblast());
    }

    @Override
    protected void applyEffect(Character target) {
        target.applyDamage(calculateDamage());
    }

    int calculateDamage() {
        return baseDamage * level;
    }

}
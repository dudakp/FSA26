package sk.dudak.fsagame.ability;

import sk.dudak.fsagame.character.Character;

/**
 * Fireball is instant ability that wil do damage to single target and apply DoT to target
 */
public class Fireblast extends Ability {

    private int damage;

    Fireblast() {
        super(AbilityId.FIREBLAST, AbilityClass.DAMAGING, 2, new AbilityView(AbilityId.FIREBALL, 3));
    }

    @Override
    protected void applyEffect(Character target) {
        target.applyDoT(damage, 5);
    }

}

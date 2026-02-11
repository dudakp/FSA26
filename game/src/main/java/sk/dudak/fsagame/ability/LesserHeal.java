package sk.dudak.fsagame.ability;

import sk.dudak.fsagame.character.Character;

public class LesserHeal extends Ability {

    private int baseHeal = 5;

    LesserHeal() {
        super(AbilityId.LESSER_HEAL, AbilityClass.HEALING,  1);
    }

    @Override
    protected void applyEffect(Character target) {
        target.dealDamage(level * (-5));
    }
}

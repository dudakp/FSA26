package sk.dudak.fsagame.character;

import sk.dudak.fsagame.ability.AbilityId;
import sk.dudak.fsagame.ability.AbilityNotLearnedException;

public interface CanUseAbility {

    void useAbility(AbilityId abilityId, Character target) throws AbilityNotLearnedException;

}

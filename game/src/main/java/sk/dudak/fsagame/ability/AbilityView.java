package sk.dudak.fsagame.ability;

/**
 * Stateless snapshot view of ability
 *
 * @param abilityId
 * @param level        ability level
 */

public record AbilityView(
        AbilityId abilityId,
        int level,
        AbilityView requiredKnownAbility
) {

    AbilityView(AbilityId abilityId) {
        this(abilityId, 1, null);
    }

    AbilityView(AbilityId abilityId, int level) {
        this(abilityId, level, null);
    }

    public boolean satisfies(AbilityView other) {
        return abilityId.equals(other.abilityId) && level >= other.level;
    }
}

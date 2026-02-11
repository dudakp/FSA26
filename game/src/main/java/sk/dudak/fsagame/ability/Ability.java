package sk.dudak.fsagame.ability;

import sk.dudak.fsagame.character.Character;

import java.util.Optional;
import java.util.logging.Logger;

public abstract class Ability {

    private static final Logger LOG = Logger.getLogger(Ability.class.getName());

    Ability(AbilityId abilityId, AbilityClass abilityClass, int maxLevel, AbilityView requiredKnownAbility) {
        this.abilityId = abilityId;
        this.abilityClass = abilityClass;
        this.maxLevel = maxLevel;
        this.requiredKnownAbility = requiredKnownAbility;
    }

    Ability(AbilityId abilityId, AbilityClass abilityClass, int maxLevel, Ability nextAbilityForm) {
        this.abilityId = abilityId;
        this.abilityClass = abilityClass;
        this.maxLevel = maxLevel;
        this.nextAbilityForm = nextAbilityForm;
    }

    Ability(AbilityId abilityId, AbilityClass abilityClass, int maxLevel) {
        this.abilityId = abilityId;
        this.abilityClass = abilityClass;
        this.maxLevel = maxLevel;
    }

    public final AbilityId abilityId;
    public final AbilityClass abilityClass;
    protected int level = 1;
    private final int maxLevel;

    // ability ktora musi byt v ramci AbilityTree aby sa aktualna ability mohla upgradnut
    protected AbilityView requiredKnownAbility;

    // ability na ktoru sa dana ability zmeni po urcitom pocte upgradov
    protected Ability nextAbilityForm;

    abstract protected void applyEffect(Character target);

    public AbilityView asView() {
        return new AbilityView(abilityId, level, requiredKnownAbility);
    }

    public Optional<AbilityId> upgrade() {
        if (level < maxLevel) {
            level += 1;
            return Optional.of(abilityId);
        }

        return Optional.ofNullable(nextAbilityForm)
                .map(a -> a.abilityId);
    }

    public void use(Character target) {
        applyEffect(target);
        LOG.info("%s used!".formatted(this.getClass().getSimpleName()));
    }

    public int getLevel() {
        return level;
    }

    public boolean isMaxLevel() {
        return level == maxLevel;
    }
}

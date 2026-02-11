package sk.dudak.fsagame.character.hero;

import sk.dudak.fsagame.ability.Ability;
import sk.dudak.fsagame.ability.AbilityCannotBeLearnedException;
import sk.dudak.fsagame.ability.AbilityClass;
import sk.dudak.fsagame.ability.AbilityId;
import sk.dudak.fsagame.ability.AbilityNotLearnedException;
import sk.dudak.fsagame.ability.AbilityView;
import sk.dudak.fsagame.character.CanUseAbility;
import sk.dudak.fsagame.character.Character;
import sk.dudak.fsagame.game.World;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

public final class Hero extends Character {

    private static final Logger LOG = Logger.getLogger(Hero.class.getName());

    private final AbilityTree abilityTree;
    private int xp = 0;

    public Hero(int health) {
        super(HeroIdGenerator.INSTANCE, health);
        this.abilityTree = new AbilityTree(this);
    }

    @Override
    public void useAbility(AbilityId ability, Character target) throws AbilityNotLearnedException {
        Optional<Ability> abilityToUse = abilityTree.getAbility(ability);
        if (abilityToUse.isEmpty()) {
            LOG.warning("Ability: %s, not yed learned".formatted(ability));
            throw new AbilityNotLearnedException();
        }
        LOG.info("Using ability %s".formatted(abilityToUse.get().asView()));
        if (AbilityClass.DAMAGING.equals(abilityToUse.get().abilityClass)) {
            LOG.info("Targeting enemy");
            abilityToUse.get().use(target);
        } else {
            LOG.info("Targeting self");
            abilityToUse.get().use(this);
        }
    }

    public AbilityId learnAbility(AbilityId abilityId) throws AbilityCannotBeLearnedException {
        AbilityId res = abilityTree.learnAbility(abilityId);
        LOG.info("Leaded ability: %s".formatted(res));
        return res;
    }

    public AbilityId upgradeAbility(AbilityId abilityClass) throws AbilityCannotBeLearnedException, AbilityNotLearnedException, NotEnoughXpException {
        return abilityTree.upgradeAbility(abilityClass);
    }

    public Collection<AbilityView> getLearnedAbilities() {
        return abilityTree.learnedAbilities();
    }

    @Override
    public void onCharacterDied() {
        World.getInstance().onHeroDied();
    }

    public int getXp() {
        return this.xp;
    }

    public void gainXp(int xp) {
        this.xp += xp;
    }

    void spendXp(int xp) {
        this.xp -= xp;
    }

    public Collection<AbilityId> upgradableAbilities() {
        return abilityTree.learnedAbilities.stream()
                .filter(Predicate.not(Ability::isMaxLevel))
                .map(ability -> ability.abilityId)
                .toList();
    }
}

package sk.dudak.fsagame.character.enemy;

import sk.dudak.fsagame.ability.Ability;
import sk.dudak.fsagame.ability.AbilityFactory;
import sk.dudak.fsagame.ability.AbilityId;
import sk.dudak.fsagame.ability.AbilityNotLearnedException;
import sk.dudak.fsagame.character.CanUseAbility;
import sk.dudak.fsagame.character.Character;
import sk.dudak.fsagame.character.enemy.ai.AiCombatStrategy;
import sk.dudak.fsagame.character.hero.Hero;
import sk.dudak.fsagame.game.World;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public final class Enemy extends Character implements CanUseAbility {

    private static final Logger LOG = Logger.getLogger(Enemy.class.getName());

    private final AiCombatStrategy ai;

    private final List<Ability> abilities;


    Enemy(int health, List<AbilityId> abilities, AiCombatStrategy ai) {
        super(EnemyIdGenerator.INSTANCE, health);
        this.abilities = abilities.stream()
                .map(AbilityFactory::create)
                .toList();
        this.ai = ai;
    }

    public void attackHero(Character target) {
        AbilityId abilityToUse = ai.selectNextAbility(this);
        try {
            useAbility(abilityToUse, target);
        } catch (AbilityNotLearnedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void useAbility(AbilityId ability, Character target) throws AbilityNotLearnedException {
        Optional<Ability> abilityToUse = abilities.stream()
                .filter(a -> a.abilityId.equals(ability))
                .findAny();
        if (abilityToUse.isEmpty()) {
            throw new AbilityNotLearnedException();
        }
        abilityToUse.get().use(target);
    }

    @Override
    public void onCharacterDied() {
        LOG.info("character: %s died".formatted(this.getClass()));
        World.getInstance().onEnemyDied();
    }
}

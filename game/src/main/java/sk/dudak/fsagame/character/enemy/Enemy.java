package sk.dudak.fsagame.character.enemy;

import sk.dudak.fsagame.ability.Ability;
import sk.dudak.fsagame.ability.AbilityFactory;
import sk.dudak.fsagame.ability.AbilityId;
import sk.dudak.fsagame.ability.AbilityNotLearnedException;
import sk.dudak.fsagame.character.Character;
import sk.dudak.fsagame.character.CharacterHealth;
import sk.dudak.fsagame.character.CharacterId;
import sk.dudak.fsagame.character.CharacterState;
import sk.dudak.fsagame.character.enemy.ai.AiCombatStrategy;
import sk.dudak.fsagame.game.World;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public final class Enemy implements Character {

    private static final Logger LOG = Logger.getLogger(Enemy.class.getName());

    private final EnemyId id;
    private final CharacterHealth health;
    private CharacterState state = CharacterState.ALIVE;
    private final AiCombatStrategy ai;
    private final List<Ability> abilities;


    Enemy(int health, List<AbilityId> abilities, AiCombatStrategy ai) {
        this.id = EnemyIdGenerator.INSTANCE.getNext();
        this.health = new CharacterHealth(health);
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
            throw new AbilityNotLearnedException(ability);
        }
        abilityToUse.get().use(target);
    }

    @Override
    public void onCharacterDied() {
        LOG.info("character: %s died".formatted(this.getClass()));
        World.getInstance().onEnemyDied();
    }

    @Override
    public Integer getHealth() {
        return health.getHealth();
    }

    @Override
    public CharacterId<Long> getId() {
        return id;
    }

    @Override
    public CharacterState getState() {
        return state;
    }

    @Override
    public void dealDamage(int damage) {
        this.state = this.health.dealDamage(damage, this);
        if (CharacterState.DEAD.equals(this.state)) {
            onCharacterDied();
        }
    }
}

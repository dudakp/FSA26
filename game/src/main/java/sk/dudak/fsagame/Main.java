import sk.dudak.fsagame.ability.AbilityCannotBeLearnedException;
import sk.dudak.fsagame.ability.AbilityId;
import sk.dudak.fsagame.ability.AbilityNotLearnedException;
import sk.dudak.fsagame.character.hero.Hero;
import sk.dudak.fsagame.character.hero.NotEnoughXpException;
import sk.dudak.fsagame.game.Difficulty;
import sk.dudak.fsagame.game.World;

import java.util.logging.Logger;

private static final Logger LOG = Logger.getLogger("entrypoint");

void main() {
    LOG.info("Welcome to FSA game");
    LOG.info("initializing game world");

    World world = World.getInstance();
    world.setGameDifficulty(Difficulty.EASY);
    Hero hero = new Hero(21);
    world.addHero(hero);


    try {
        LOG.info("Abilities to use: %s".formatted(hero.getLearnedAbilities()));
        Map<Integer, AbilityId> usableAbilities = Map.ofEntries(
                Map.entry(0, hero.learnAbility(AbilityId.FIREBALL)),
                Map.entry(1, hero.learnAbility(AbilityId.LESSER_HEAL))
        );

        int turnNum = 0;
        while (!world.isGameOver()) {
            if (turnNum % 2 == 0) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(System.in));
                if (hero.getXp() > 0) {
                    IO.println("Able to upgrade abilities: %s".formatted(
                            hero.upgradableAbilities())
                    );
                    IO.println("Select ability to upgrade: %s");
                    int abilityToUpgrade = Integer.parseInt(reader.readLine());
                    hero.upgradeAbility(usableAbilities.get(abilityToUpgrade));
                    continue;
                }

                IO.println("It's your turn your health is: %d, your xp: %d \n current active enemy health is: %d, %d enemies remaining \n select ability to use: \n %s".formatted(
                        hero.getHealth(),
                        hero.getXp(),
                        world.getActiveEnemy().getHealth(),
                        world.getRemainingEnemies(),
                        usableAbilities
                ));
                int selectedAbility = Integer.parseInt(reader.readLine());

                boolean killedEnemy = world.heroTurn(usableAbilities.get(selectedAbility));
                if (killedEnemy) {
                    LOG.info("enemy killed, you get extra move!");
                    continue;
                }
            } else {
                world.enemyTurn();
            }
            turnNum += 1;
        }
        if (world.getRemainingEnemies() == 0) {
            LOG.info("you won!");
        }

    } catch (AbilityCannotBeLearnedException e) {
        IO.println(e);
        throw new RuntimeException(e);
    } catch (AbilityNotLearnedException | IOException | NotEnoughXpException e) {
        throw new RuntimeException(e);
    }

}
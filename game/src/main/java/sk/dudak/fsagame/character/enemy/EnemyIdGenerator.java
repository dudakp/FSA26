package sk.dudak.fsagame.character.enemy;

import sk.dudak.fsagame.character.CharacterIdGenerator;

enum EnemyIdGenerator implements CharacterIdGenerator<Long> {
    INSTANCE;

    private long currentValue = 0L;

    public EnemyId getNext() {
        return new EnemyId(currentValue++);
    }

}

package sk.dudak.fsagame.game;

public enum Difficulty {
    EASY(3),
    MEDIUM(5),
    HARD(6);

    final int numOfEnemiesInWave;

    Difficulty(int numOfEnemiesInWave) {
        this.numOfEnemiesInWave = numOfEnemiesInWave;
    }

}

package me.tade.therun.game;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * This file was created by Tadeáš Drab on 25. 12. 2018
 */
public class GameRunnable extends BukkitRunnable {

    private Game game;
    private int countdown;
    private long lastCounted = System.currentTimeMillis();
    private long endTime = 0;

    public GameRunnable(Game game) {
        this.game = game;
        this.countdown = this.game.getCountdown();

        runTaskTimer(game.getPlugin(), 0, 1);
    }

    @Override
    public void run() {
        //Main stuff for game (countdown)
        if (game.getGameState() == GameState.LOBBY) {
            if (game.getPlugin().getPlayersFromGame(game).size() >= game.getMinPlayers()) {

            }
        } else if (game.getGameState() == GameState.STARTING) {
            if (System.currentTimeMillis() - lastCounted >= 1000) {
                lastCounted = System.currentTimeMillis();
                countdown--;
            }

            if (countdown <= 0) {
                prepareToStartGame();

                this.countdown = this.game.getCountdown();
            }
        } else if (game.getGameState() == GameState.PLAYING) {
            if (System.currentTimeMillis() >= endTime) {
                prepareToEndGame();
            }
        } else if (game.getGameState() == GameState.ENDING) {
            if (System.currentTimeMillis() >= (game.getGameEndTime() + 10000)) { //Wait 10 seconds and restart the game
                prepareToRestartGame();
            }
        }

        //Do other stuff for game
        game.onTick();
    }

    private void prepareToStartGame() {
        endTime = System.currentTimeMillis() + 1800000; //End time = 30 minutes

        game.setGameStartTime(System.currentTimeMillis());

        game.setGameState(GameState.PLAYING);

        game.startGame();
    }

    private void prepareToEndGame() {
        endTime = 0;

        game.setGameEndTime(System.currentTimeMillis());

        game.setGameState(GameState.ENDING);

        game.endGame();
    }

    public void prepareToRestartGame() {
        game.setGameEndTime(0);

        game.setForceStart(false);

        game.setGameState(GameState.RESTARTING);

        game.restartGame();
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }
}

package me.tade.therun.game;

import org.bukkit.scheduler.BukkitRunnable;

/**
 * This file was created by Tadeáš Drab on 25. 12. 2018
 */
public class GameRunnable extends BukkitRunnable {

    private Game game;
    private int countdown;
    private long lastCounted = System.currentTimeMillis();

    public GameRunnable(Game game) {
        this.game = game;
        this.countdown = this.game.getCountdown();
    }

    @Override
    public void run() {
        //Main stuff for game (countdown)
        if(game.getGameState() == GameState.STARTING){
            if(System.currentTimeMillis() - lastCounted >= 1000) {
                lastCounted = System.currentTimeMillis();
                countdown--;
            }

            if(countdown <= 0){
                prepareToStartGame();

                game.startGame();

                this.countdown = this.game.getCountdown();
            }
        }

        //Do other stuff for game
        game.onTick();
    }

    private void prepareToStartGame(){
        game.setGameState(GameState.PLAYING);
    }
}

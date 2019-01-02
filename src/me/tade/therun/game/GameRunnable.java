package me.tade.therun.game;

import me.tade.therun.event.GameStateChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
        if (game.getGameState() == GameState.LOBBY){
            if(game.getPlugin().getPlayersFromGame(game).size() >= game.getMinPlayers()){

            }
        }else if(game.getGameState() == GameState.STARTING){
            if(System.currentTimeMillis() - lastCounted >= 1000) {
                lastCounted = System.currentTimeMillis();
                countdown--;
            }

            if(countdown <= 0){
                prepareToStartGame();

                game.startGame();

                this.countdown = this.game.getCountdown();
            }
        }else if (game.getGameState() == GameState.PLAYING){
            if(System.currentTimeMillis() >= endTime){
                prepareToEndGame();

                game.endGame();
            }
        }else if (game.getGameState() == GameState.ENDING){
            if(System.currentTimeMillis() >= (game.getGameEndTime() + 10000)){ //Wait 10 seconds and restart the game
                prepareToRestartGame();

                game.restartGame();
            }
        }

        //Do other stuff for game
        game.onTick();
    }

    private void prepareToStartGame(){
        game.setGameState(GameState.PLAYING);

        endTime = System.currentTimeMillis() + 1200000; //End time = 20 minutes

        game.setGameStartTime(System.currentTimeMillis());
    }

    private void prepareToEndGame(){
        game.setGameState(GameState.ENDING);

        endTime = 0;

        game.setGameEndTime(System.currentTimeMillis());
    }

    private void prepareToRestartGame(){
        game.setGameState(GameState.RESTARTING);

        game.setGameEndTime(0);
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public int getCountdown() {
        return countdown;
    }
}

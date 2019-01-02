package me.tade.therun.game.managers;

import me.tade.therun.event.PlayerGameJoinEvent;
import me.tade.therun.event.PlayerGameQuitEvent;
import me.tade.therun.game.Game;
import me.tade.therun.game.GameState;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * This file was created by Tadeáš Drab on 2. 1. 2019
 */
public class GameManager implements Listener {

    private Game game;

    public GameManager(Game game) {
        this.game = game;

        Bukkit.getPluginManager().registerEvents(this, game.getPlugin());
    }

    @EventHandler
    public void onGameJoin(PlayerGameJoinEvent event) {
        if (event.getGame() != game)
            return;

        //Disallow join to playing game
        if (game.getGameState() == GameState.PLAYING || game.getGameState() == GameState.ENDING || game.getGameState() == GameState.RESTARTING) {
            event.setCancelled(true);
            return;
        }

        //Disallow to join full arena
        if (game.getPlugin().getPlayersFromGame(game).size() >= game.getMaxPlayers()) {
            event.setCancelled(true);
            return;
        }

        if (game.getPlugin().getPlayersFromGame(game).size() >= game.getMinPlayers()) {
            //Start timer
            if (game.getGameState() == GameState.LOBBY) {
                game.setGameState(GameState.STARTING);
            }

            //Short countdown when game is full
            if (game.getMaxPlayers() - game.getPlugin().getPlayersFromGame(game).size() <= 1)
                game.shortCountdown(10);
        }
    }

    @EventHandler
    public void onGameQuit(PlayerGameQuitEvent event) {
        if (event.getGame() != game)
            return;

        if (game.getPlugin().getPlayersFromGame(game).size() < game.getMinPlayers()) {
            //Stop timer
            if ((game.getPlugin().getPlayersFromGame(game).size() <= 1 || !game.isForceStart()) && game.getGameState() == GameState.STARTING) {
                game.setGameState(GameState.LOBBY);

                game.setCountdown(game.getCountdown());
            }

            if (game.getPlugin().getPlayersFromGame(game).size() <= 1 && game.getGameState() == GameState.PLAYING) {
                game.emergencyRestartGame();
            }
        }
    }
}

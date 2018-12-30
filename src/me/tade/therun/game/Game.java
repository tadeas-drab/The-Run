package me.tade.therun.game;

import me.tade.therun.event.GameStateChangeEvent;
import org.bukkit.Bukkit;

/**
 * This file was created by Tadeáš Drab on 25. 12. 2018
 */
public abstract class Game {

    private String name;
    private int minPlayers;
    private int maxPlayers;
    private boolean forceStart = false;
    private GameState gameState = GameState.RESTARTING;
    private int countdown = 30;

    public Game(String name, int minPlayers, int maxPlayers) {
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;

        //Call GameState update event for game
        Bukkit.getPluginManager().callEvent(new GameStateChangeEvent(this, this.gameState));
    }

    public String getName() {
        return name;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public boolean isForceStart() {
        return forceStart;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getCountdown() {
        return countdown;
    }

    public abstract void onTick();

    public abstract void startGame();
}

package me.tade.therun.game;

import me.tade.therun.TheRun;
import me.tade.therun.event.GameStateChangeEvent;
import me.tade.therun.game.managers.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

/**
 * This file was created by Tadeáš Drab on 25. 12. 2018
 */
public abstract class Game {

    private TheRun plugin;
    private String name;
    private int minPlayers;
    private int maxPlayers;
    private boolean forceStart = false;
    private GameState gameState = GameState.RESTARTING;
    private int countdown = 30;
    private GameRunnable gameRunnable;
    private long gameStartTime;
    private long gameEndTime;
    private GameManager gameManager;

    public Game(TheRun plugin, String name, int minPlayers, int maxPlayers) {
        this.plugin = plugin;
        this.name = name;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;

        gameRunnable = new GameRunnable(this);
        gameManager = new GameManager(this);
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

    public void setForceStart(boolean forceStart) {
        this.forceStart = forceStart;

        if (getGameState() == GameState.LOBBY)
            setGameState(GameState.STARTING);
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;

        //Call GameState update event for game
        Bukkit.getPluginManager().callEvent(new GameStateChangeEvent(this, this.gameState));
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;

        shortCountdown(getCountdown());
    }

    public void shortCountdown(int countdown) {
        gameRunnable.setCountdown(countdown);
    }

    public long getGameStartTime() {
        return gameStartTime;
    }

    public void setGameStartTime(long gameStartTime) {
        this.gameStartTime = gameStartTime;
    }

    public long getGameEndTime() {
        return gameEndTime;
    }

    public void setGameEndTime(long gameEndTime) {
        this.gameEndTime = gameEndTime;
    }

    public TheRun getPlugin() {
        return plugin;
    }

    public void emergencyRestartGame() {
        gameRunnable.prepareToRestartGame();
    }

    public abstract void onTick();

    public abstract void startGame();

    public abstract void endGame();

    public abstract void restartGame();

    public void disable() {
        gameRunnable.cancel();

        HandlerList.unregisterAll(gameManager);
    }
}

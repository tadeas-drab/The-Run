package me.tade.therun.event;

import me.tade.therun.game.Game;
import me.tade.therun.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This file was created by Tadeáš Drab on 25. 12. 2018
 */
public class GameStateChangeEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Game game;
    private GameState gameState;

    public GameStateChangeEvent(Game game, GameState gameState) {
        this.game = game;
        this.gameState = gameState;
    }

    public Game getGame() {
        return game;
    }

    public GameState getGameState() {
        return gameState;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}

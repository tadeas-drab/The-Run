package me.tade.therun.event;

import me.tade.therun.game.Game;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This file was created by Tadeáš Drab on 2. 1. 2019
 */
public class PlayerGameQuitEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Game game;

    public PlayerGameQuitEvent(Player player, Game game) {
        this.player = player;
        this.game = game;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

}

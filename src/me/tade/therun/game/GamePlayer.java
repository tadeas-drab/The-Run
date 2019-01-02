package me.tade.therun.game;

import org.bukkit.entity.Player;

/**
 * This file was created by Tadeáš Drab on 2. 1. 2019
 */
public class GamePlayer {

    private Player player;
    private Game game;

    public GamePlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}

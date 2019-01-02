package me.tade.therun;

import me.tade.therun.event.PlayerGameJoinEvent;
import me.tade.therun.event.PlayerGameQuitEvent;
import me.tade.therun.game.Game;
import me.tade.therun.game.GamePlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * This file was created by Tadeáš Drab on 4. 11. 2018
 */
public class TheRun extends JavaPlugin {

    private List<GamePlayer> gamePlayers = new ArrayList<>();
    private List<Game> games = new ArrayList<>();

    @Override
    public void onEnable(){
        super.onEnable();

        //Add all players on server to list (Only when reloading server to not to break things)
        for(Player player : Bukkit.getOnlinePlayers()){
            createGamePlayer(player);
        }
    }

    public List<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    public List<Game> getGames() {
        return games;
    }

    public GamePlayer getGamePlayer(Player player){
        for(GamePlayer gamePlayer : gamePlayers){
            if(gamePlayer.getPlayer() == player)
                return gamePlayer;
        }
        return null;
    }

    public GamePlayer createGamePlayer(Player player){
        GamePlayer gamePlayer = new GamePlayer(player);
        gamePlayers.add(gamePlayer);
        return gamePlayer;
    }

    public List<GamePlayer> getPlayersFromGame(Game game){
        List<GamePlayer> currentGamePlayers = new ArrayList<>();
        for(GamePlayer gamePlayer : gamePlayers){
            if(gamePlayer.getGame() == game)
                currentGamePlayers.add(gamePlayer);
        }
        return currentGamePlayers;
    }

    protected void joinGame(Player player, Game game){
        GamePlayer gamePlayer = getGamePlayer(player);

        PlayerGameJoinEvent playerGameJoinEvent = new PlayerGameJoinEvent(player, game);

        Bukkit.getPluginManager().callEvent(playerGameJoinEvent);

        if(playerGameJoinEvent.isCancelled())
            return;

        gamePlayer.setGame(game);
    }

    protected void quitGame(Player player, Game game){
        GamePlayer gamePlayer = getGamePlayer(player);

        PlayerGameQuitEvent playerGameQuitEvent = new PlayerGameQuitEvent(player, game);

        Bukkit.getPluginManager().callEvent(playerGameQuitEvent);

        gamePlayer.setGame(null);
    }
}

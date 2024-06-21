package com.zanowsley.leagueservice.api;

import com.zanowsley.leagueservice.exceptions.*;
import com.zanowsley.leagueservice.organization.PlayerCollection;
import com.zanowsley.leagueservice.responses.CommonErrorResponses;
import com.zanowsley.leagueservice.responses.GetPlayers;
import com.zanowsley.leagueservice.responses.ModifyPlayer;
import com.zanowsley.leagueservice.responses.PostPlayer;
import com.zanowsley.leagueservice.utils.AllLeaguesData;
import com.zanowsley.leagueservice.utils.LeagueData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class PlayerController {

    private final AllLeaguesData allLeaguesData;

    @Autowired
    public PlayerController(AllLeaguesData allLeaguesData) {
        this.allLeaguesData = allLeaguesData;
    }

    @GetMapping("/players/all")
    public GetPlayers getPlayers(@RequestParam(value = "leagueName") String leagueName) {
        int resultCode;
        String message;
        PlayerCollection playerCollection;

        try {
            LeagueData leagueData = allLeaguesData.getLeagueData(leagueName);
            resultCode = GetPlayers.CODE_SUCCESS;
            message = String.format(GetPlayers.MSG_SUCCESS, leagueName);
            playerCollection = leagueData.getPlayerCollection();

        } catch (LeagueNotFoundException e) {
            resultCode = CommonErrorResponses.CODE_INVALID_LEAGUE;
            message = e.getMessage();
            playerCollection = null;

        } catch (IOException e) {
            resultCode = CommonErrorResponses.CODE_IO_EXCEPTION;
            message = e.getMessage();
            playerCollection = null;
        }

        return new GetPlayers(resultCode, message, playerCollection);
    }

    @GetMapping("/players/active")
    public GetPlayers getActivePlayers(@RequestParam(value = "leagueName") String leagueName) {
        int resultCode;
        String message;
        PlayerCollection playerCollection;

        try {
            LeagueData leagueData = allLeaguesData.getLeagueData(leagueName);
            resultCode = GetPlayers.CODE_SUCCESS;
            message = String.format(GetPlayers.MSG_SUCCESS, leagueName);
            playerCollection = leagueData.getPlayerCollection().withOnlyActivePlayers();

        } catch (LeagueNotFoundException e) {
            resultCode = CommonErrorResponses.CODE_INVALID_LEAGUE;
            message = e.getMessage();
            playerCollection = null;

        } catch (IOException e) {
            resultCode = CommonErrorResponses.CODE_IO_EXCEPTION;
            message = e.getMessage();
            playerCollection = null;
        }

        return new GetPlayers(resultCode, message, playerCollection);
    }

    @PostMapping("/players/new")
    public PostPlayer postPlayer(@RequestParam(value = "leagueName") String leagueName,
                                 @RequestParam(value = "username") String username,
                                 @RequestParam(value = "name") String name,
                                 @RequestParam(value = "email") String email) {
        int resultCode;
        String message;

        try {
            LeagueData leagueData = allLeaguesData.getLeagueData(leagueName);
            leagueData.addNewPlayer(username, name, email);

            resultCode = PostPlayer.CODE_SUCCESS;
            message = String.format(PostPlayer.MSG_SUCCESS, name);

        } catch (LeagueNotFoundException e) {
            resultCode = CommonErrorResponses.CODE_INVALID_LEAGUE;
            message = e.getMessage();

        } catch (IOException e) {
            resultCode = CommonErrorResponses.CODE_IO_EXCEPTION;
            message = e.getMessage();

        } catch (PlayerUsernameExistsException e) {
            resultCode = PostPlayer.CODE_USERNAME_EXISTS;
            message = e.getMessage();

        } catch (PlayerNameExistsException e) {
            resultCode = PostPlayer.CODE_NAME_EXISTS;
            message = e.getMessage();

        } catch (PlayerEmailExistsException e) {
            resultCode = PostPlayer.CODE_EMAIL_EXISTS;
            message = e.getMessage();
        }

        return new PostPlayer(resultCode, message);
    }

    @PutMapping("/players/modify")
    public ModifyPlayer modifyPlayer(@RequestParam(value = "leagueName") String leagueName,
                                     @RequestParam(value = "username") String username,
                                     @RequestParam(value = "parameter") String parameter,
                                     @RequestParam(value = "value") String value) {
        int resultCode;
        String message;

        try {
            LeagueData leagueData = allLeaguesData.getLeagueData(leagueName);
            leagueData.modifyPlayer(username, parameter, value);

            resultCode = ModifyPlayer.CODE_SUCCESS;
            message = String.format(ModifyPlayer.MSG_SUCCESS, username);

        } catch (LeagueNotFoundException e) {
            resultCode = CommonErrorResponses.CODE_INVALID_LEAGUE;
            message = e.getMessage();

        } catch (IOException e) {
            resultCode = CommonErrorResponses.CODE_IO_EXCEPTION;
            message = e.getMessage();

        } catch (PlayerNotFoundException e) {
            resultCode = ModifyPlayer.CODE_PLAYER_NOT_FOUND;
            message = e.getMessage();

        } catch (PlayerNameExistsException e) {
            resultCode = ModifyPlayer.CODE_PLAYER_NAME_EXISTS;
            message = e.getMessage();
        } catch (PlayerEmailExistsException e) {
            resultCode = ModifyPlayer.CODE
        } catch (IllegalArgumentException e) {

        }

        return new ModifyPlayer(resultCode, message);
    }

    @PutMapping("/players/modify/email")
    public ModifyPlayer putPlayerEmail(@RequestParam(value = "leagueName") String leagueName,
                                       @RequestParam(value = "username") String username,
                                       @RequestParam(value = "email") String email) {
        int resultCode;
        String message;

        try {
            LeagueData leagueData = allLeaguesData.getLeagueData(leagueName);
            PlayerCollection playerCollection = leagueData.getPlayerCollection();
            playerCollection.modifyPlayerEmail(username, email);
            leagueData.savePlayerCollection(playerCollection);

            resultCode = ModifyPlayer.CODE_SUCCESS;
            message = String.format(ModifyPlayer.MSG_SUCCESS, username);

        } catch (LeagueNotFoundException e) {
            resultCode = CommonErrorResponses.CODE_INVALID_LEAGUE;
            message = e.getMessage();

        } catch (IOException e) {
            resultCode = CommonErrorResponses.CODE_IO_EXCEPTION;
            message = e.getMessage();

        } catch (PlayerNotFoundException e) {
            resultCode = ModifyPlayer.CODE_PLAYER_NOT_FOUND;
            message = e.getMessage();

        } catch (PlayerEmailExistsException e) {
            resultCode = ModifyPlayer.CODE_PLAYER_EMAIL_EXISTS;
            message = e.getMessage();
        }

        return new ModifyPlayer(resultCode, message);
    }

    @PutMapping("/players/modify/active")
    public ModifyPlayer putPlayerIsActive(@RequestParam(value = "leagueName") String leagueName,
                                          @RequestParam(value = "username") String username,
                                          @RequestParam(value = "isActive") boolean isActive) {
        int resultCode;
        String message;

        try {
            LeagueData leagueData = allLeaguesData.getLeagueData(leagueName);
            PlayerCollection playerCollection = leagueData.getPlayerCollection();
            playerCollection.modifyPlayerIsActive(username, isActive);
            leagueData.savePlayerCollection(playerCollection);

            resultCode = ModifyPlayer.CODE_SUCCESS;
            message = String.format(ModifyPlayer.MSG_SUCCESS, username);

        } catch (LeagueNotFoundException e) {
            resultCode = CommonErrorResponses.CODE_INVALID_LEAGUE;
            message = e.getMessage();

        } catch (IOException e) {
            resultCode = CommonErrorResponses.CODE_IO_EXCEPTION;
            message = e.getMessage();

        } catch (PlayerNotFoundException e) {
            resultCode = ModifyPlayer.CODE_PLAYER_NOT_FOUND;
            message = e.getMessage();
        }

        return new ModifyPlayer(resultCode, message);
    }

    private ModifyPlayer modifyPlayer(String leagueName, String username, String name, String email, boolean isActive) {
        int resultCode;
        String message;

        try {
            LeagueData leagueData = allLeaguesData.getLeagueData(leagueName);

            resultCode = ModifyPlayer.CODE_SUCCESS;
            message = String.format(ModifyPlayer.MSG_SUCCESS, username);

        } catch (LeagueNotFoundException e) {
            resultCode = CommonErrorResponses.CODE_INVALID_LEAGUE;
            message = e.getMessage();

        } catch (IOException e) {
            resultCode = CommonErrorResponses.CODE_IO_EXCEPTION;
            message = e.getMessage();

        }

        return new ModifyPlayer(resultCode, message);
    }
}

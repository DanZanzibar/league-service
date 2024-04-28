package com.zanowsley.leagueservice.api;

import com.zanowsley.leagueservice.LeagueData;
import com.zanowsley.leagueservice.player.PlayerCollection;
import com.zanowsley.leagueservice.records.NewPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {

    private final LeagueData leagueData;

    @Autowired
    public PlayerController(LeagueData leagueData) {
        this.leagueData = leagueData;
    }

    @PostMapping("/players/add-new")
    public NewPlayer addNewPlayer(@RequestParam(value = "username") String username,
                                  @RequestParam(value = "name") String name,
                                  @RequestParam(value = "email") String email) {

        PlayerCollection playerCollection = leagueData.getPlayerCollection();

        int resultCode;
        String message;

        if (playerCollection.isUsernameUsed(username)) {
            resultCode = NewPlayer.FAILED_USERNAME_EXISTS;
            message = NewPlayer.MSG_USERNAME_EXISTS;
        } else if (playerCollection.isNameUsed(name)) {
            resultCode = NewPlayer.FAILED_NAME_EXISTS;
            message = NewPlayer.MSG_NAME_EXISTS;
        } else if (playerCollection.isEmailUsed(email)) {
            resultCode = NewPlayer.FAILED_EMAIL_EXISTS;
            message = NewPlayer.MSG_EMAIL_EXISTS;
        } else {
            resultCode = NewPlayer.SUCCESS;
            message = NewPlayer.MSG_SUCCESS;
        }

        return new NewPlayer(resultCode, message);
    }
}

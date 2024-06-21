package com.zanowsley.leagueservice.api;

import com.zanowsley.leagueservice.exceptions.LeagueDirectoryCreationFailedException;
import com.zanowsley.leagueservice.responses.BasicResponse;
import com.zanowsley.leagueservice.responses.CommonErrorResponses;
import com.zanowsley.leagueservice.responses.PostLeague;
import com.zanowsley.leagueservice.utils.AllLeaguesData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class LeagueController {

    private final AllLeaguesData allLeaguesData;

    @Autowired
    public LeagueController(AllLeaguesData allLeaguesData) {
        this.allLeaguesData = allLeaguesData;
    }

    @GetMapping("/leagues/all")
    public List<String> getLeagueNames() {
        return allLeaguesData.getLeagueNames();
    }

    @PostMapping("/leagues/add")
    public PostLeague postLeague(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "maxBookings") long maxBookings,
            @RequestParam(value = "dayOfMatches") String dayOfMatches,
            @RequestParam(value = "timeOfMatches") String timeOfMatches,
            @RequestParam(value = "dayBookingOpens") String dayBookingOpens,
            @RequestParam(value = "timeBookingOpens") String timeBookingOpens,
            @RequestParam(value = "dayBookingCloses") String dayBookingCloses,
            @RequestParam(value = "timeBookingCloses") String timeBookingCloses
    ) {
        int resultCode;
        String message;

        try {
            allLeaguesData.addNewLeague(name, maxBookings, dayOfMatches, timeOfMatches, dayBookingOpens,
                    timeBookingOpens, dayBookingCloses, timeBookingCloses);
            resultCode = PostLeague.CODE_SUCCESS;
            message = String.format(PostLeague.MSG_SUCCESS, name);

        } catch (IOException e) {
            resultCode = CommonErrorResponses.CODE_IO_EXCEPTION;
            message = e.getMessage();

        } catch (LeagueDirectoryCreationFailedException e) {
            resultCode = PostLeague.CODE_FAILED;
            message = e.getMessage();
        }

        return new PostLeague(resultCode, message);
    }
}

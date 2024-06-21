package com.zanowsley.leagueservice.api;

import com.zanowsley.leagueservice.exceptions.*;
import com.zanowsley.leagueservice.history.Booking;
import com.zanowsley.leagueservice.history.BookingCollection;
import com.zanowsley.leagueservice.organization.PlayerCollection;
import com.zanowsley.leagueservice.responses.BookRequest;
import com.zanowsley.leagueservice.responses.BookingInfo;
import com.zanowsley.leagueservice.utils.AllLeaguesData;
import com.zanowsley.leagueservice.utils.LeagueData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RestController
public class BookingController {

    private final AllLeaguesData allLeaguesData;

    @Autowired
    public BookingController(AllLeaguesData allLeaguesData) {
        this.allLeaguesData = allLeaguesData;
    }

    @GetMapping("bookings/info")
    public BookingInfo getBookingInfo(@RequestParam(value = "leagueName") String leagueName,
                                      @RequestParam(value = "eventDate")LocalDate eventDate) {
        int resultCode;
        String message;
    }

    @PostMapping("bookings/book")
    public BookRequest postBookRequest(@RequestParam(value = "leagueName") String leagueName,
                                       @RequestParam(value = "eventDateStr") String eventDateStr,
                                       @RequestParam(value = "username") String username) {
        try {
            LocalDate eventDate = LocalDate.parse(eventDateStr);
            LeagueData leagueData = allLeaguesData.getLeagueData(leagueName);
            PlayerCollection activePlayers = leagueData.getPlayerCollection().withOnlyActivePlayers();

            if (!activePlayers.isUsernameUsed(username))
                throw new PlayerNotFoundException(username);

            BookingCollection bookingCollection = leagueData.getBookingCollection(eventDate.getYear());
            bookingCollection.bookPlayer(eventDate, username);

        } catch (IOException e) {

        } catch (DateTimeParseException e) {

        } catch (LeagueNotFoundException e) {

        } catch (PlayerNotFoundException e) {

        } catch (SeasonNotFoundException e) {

        } catch (BookingNotFoundException e) {

        } catch (BookingNotOpenException e) {

        } catch (BookingFullException e) {

        }
    }
}

package com.zanowsley.leagueservice.history;

import com.zanowsley.leagueservice.exceptions.BookingFullException;
import com.zanowsley.leagueservice.exceptions.BookingNotFoundException;
import com.zanowsley.leagueservice.exceptions.BookingNotOpenException;
import com.zanowsley.leagueservice.exceptions.PrebookingNotOpenException;
import com.zanowsley.leagueservice.organization.LeagueConfig;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BookingCollection {

    public static final String ROW_DELIMITER = "\n";
    public static final int DEFAULT_START_MONTH = 3;
    public static final int DEFAULT_END_MONTH = 10;

    private List<Booking> bookings;

    public BookingCollection(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public static BookingCollection fromCSVFormat(String csvString) {
        List<Booking> bookings = new ArrayList<>();
        String[] csvRows = csvString.split(ROW_DELIMITER);

        for (String row : csvRows)
            if (!row.isEmpty())
                bookings.add(Booking.fromCSVFormat(row));

        return new BookingCollection(bookings);
    }

    public String toCSVFormat() {
        StringBuilder stringBuilder = new StringBuilder();

        for (Booking booking : bookings)
            stringBuilder.append(booking.toCSVFormat()).append(ROW_DELIMITER);

        return stringBuilder.toString();
    }

    public static BookingCollection fromNewSeason(LeagueConfig config, LocalDate startDate, LocalDate endDate) {
        BookingCollection bookingCollection = new BookingCollection(new ArrayList<>());

        LocalDate dayBeforeStart = startDate.minusDays(1);
        LocalTime startOfDay = LocalTime.of(0, 0);

        LocalDateTime referenceDateTime = LocalDateTime.of(dayBeforeStart, startOfDay);
        LocalDateTime endDateTime = LocalDateTime.of(endDate, startOfDay);

        LocalDateTime nextPotentialEventDateTime = config.getNextMatchesDateTime(referenceDateTime);

        while (nextPotentialEventDateTime.isBefore(endDateTime)) {
            bookingCollection.addBooking(new Booking(nextPotentialEventDateTime, config));
            nextPotentialEventDateTime = config.getNextMatchesDateTime(nextPotentialEventDateTime);
        }

        return bookingCollection;
    }

    public static BookingCollection fromNewSeason(LeagueConfig config, int year) {
        LocalDate startDate = LocalDate.of(year, DEFAULT_START_MONTH, 1);
        LocalDate endDate = LocalDate.of(year, DEFAULT_END_MONTH, 1);

        return fromNewSeason(config, startDate, endDate);
    }

    public void prebookPlayer(LocalDate eventDate, String username) throws BookingNotFoundException,
            PrebookingNotOpenException, BookingFullException {
        Booking booking = getBookingByDate(eventDate);

        if (!booking.isPrebookingOpen())
            throw new PrebookingNotOpenException(eventDate);

        if (!booking.isBookingSpotAvailable())
            throw new BookingFullException(eventDate);

        booking.addPlayerToBooked(username);
    }

    public void bookPlayer(LocalDate eventDate, String username) throws BookingNotFoundException,
            BookingNotOpenException, BookingFullException {
        Booking booking = getBookingByDate(eventDate);

        if (!booking.isBookingOpen())
            throw new BookingNotOpenException(eventDate);

        if (!booking.isBookingSpotAvailable())
            throw new BookingFullException(eventDate);

        booking.addPlayerToBooked(username);
    }

    private Booking getBookingByDate(LocalDate eventDate) throws BookingNotFoundException {
        Booking matchingBooking = null;

        for (Booking booking : bookings)
            if (booking.getEventDateTime().toLocalDate().equals(eventDate)) {
                matchingBooking = booking;
                break;
            }

        if (matchingBooking == null)
            throw new BookingNotFoundException(eventDate);

        return matchingBooking;
    }

    private void addBooking(Booking booking) {
        bookings.add(booking);
    }
}

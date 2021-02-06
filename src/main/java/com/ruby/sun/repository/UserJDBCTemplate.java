package com.ruby.sun.repository;

import com.ruby.sun.domain.Guest;
import com.ruby.sun.domain.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class UserJDBCTemplate {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Reservation getReservation(long reservationId){
        //basic queries, providing parameters using ? character
        //queryForObject
        int count = jdbcTemplate.queryForObject("select count(*) from RESERVATION", Integer.class);
        //queryForList
        List<Map<String, Object>> reservations =  jdbcTemplate.queryForList("select * from RESERVATION where RESERVATION_ID = ?",
                new Object[]{reservationId});
        //update
        jdbcTemplate.update("insert into Guest values(?, ?, ?, ?)", "firstname", "lastname", "email", 123333);
        //execute
        jdbcTemplate.execute("select * from guest");

        //query with NamedParameterJdbcTemplate
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", 1);
        namedParameterJdbcTemplate.queryForObject(
                "SELECT FIRST_NAME FROM GUEST WHERE ID = :id", namedParameters, String.class);

        String sql = "SELECT * FROM guest WHERE ID = ?";
        //mapping query results to Java objects

        //BeanPropertyRowMapper
        Guest guest = (Guest) jdbcTemplate.query(sql, new Object[]{1}, new BeanPropertyRowMapper(Guest.class));
        List<Guest> guests = jdbcTemplate.query(sql, new Object[]{1}, new BeanPropertyRowMapper(Guest.class));

        //RowMapper -> mapRow(resultSet, rowNum)
        jdbcTemplate.query( //return a List<Guest>
                sql, new Object[] { 1 }, (resultSet, rowNum)->{
                    Guest newGuest = new Guest();
                    newGuest.setId(resultSet.getInt("ID"));
                    newGuest.setFirstName(resultSet.getString("FIRST_NAME"));
                    newGuest.setLastName(resultSet.getString("LAST_NAME"));
                    newGuest.setAddress(resultSet.getString("ADDRESS"));
                    return newGuest;
                });
        jdbcTemplate.query(sql, new GuestRowMapper());

        //ResultSetExtractor -> extractData(resultSet)
        jdbcTemplate.query(sql, (resultSet) -> {
            Map<String, List<String>> guestMap = new HashMap<>();
            while (resultSet.next()) {
                String country = resultSet.getString("country");
                String guestName = resultSet.getString("guest");
                if(guestMap.containsKey(country)){
                    List<String> newGuests = guestMap.get(country);
                    newGuests.add(guestName);
                    guestMap.put(country, newGuests);
                } else {
                    guestMap.put(country, Collections.singletonList(guestName));
                }
            }
            return guestMap;
        });
        jdbcTemplate.query(sql, new GuestResultSetExtractor());

        List newGuests = new ArrayList<>();
        //RowCallbackHandler -> processRow(ResultSet)
        jdbcTemplate.query(sql, (resultSet) -> {
            Guest newGuest = new Guest();
            newGuest.setId(resultSet.getInt("ID"));
            newGuest.setFirstName(resultSet.getString("FIRST_NAME"));
            newGuest.setLastName(resultSet.getString("LAST_NAME"));
            newGuest.setAddress(resultSet.getString("ADDRESS"));
            newGuests.add(newGuest);
        });
        return null;
    }
}

class GuestRowCallbackHandler implements RowCallbackHandler {
    List<Guest> guests = new ArrayList<>();
    @Override
    public void processRow(ResultSet resultSet) throws SQLException {
        Guest guest = new Guest();
        guest.setId(resultSet.getInt("ID"));
        guest.setFirstName(resultSet.getString("FIRST_NAME"));
        guest.setLastName(resultSet.getString("LAST_NAME"));
        guest.setAddress(resultSet.getString("ADDRESS"));
        guests.add(guest);
    }
}

class GuestRowMapper implements RowMapper<Guest> {
    @Override
    public Guest mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Guest guest = new Guest();
        guest.setId(resultSet.getInt("ID"));
        guest.setFirstName(resultSet.getString("FIRST_NAME"));
        guest.setLastName(resultSet.getString("LAST_NAME"));
        guest.setAddress(resultSet.getString("ADDRESS"));
        return guest;
    }
}

class GuestResultSetExtractor implements ResultSetExtractor<Map<String, List<String>>> {
    @Override
    public Map<String, List<String>> extractData(ResultSet rs) throws SQLException {
        Map<String, List<String>> guestMap = new HashMap<>();
        while (rs.next()) {
            String country = rs.getString("country");
            String guestName = rs.getString("guest");
            if(guestMap.containsKey(country)){
                List<String> guests = guestMap.get(country);
                guests.add(guestName);
                guestMap.put(country, guests);
            } else {
                guestMap.put(country, Collections.singletonList(guestName));
            }
        }
        return guestMap;
    }
}

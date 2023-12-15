package vttp.ssf.assessment.eventmanagement.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static vttp.ssf.assessment.eventmanagement.Utils.*;

public class Event {
    private Integer eventId;
    private String eventName;
    private Integer eventSize;
    private Long eventDate;
    private Integer participants;
    private String eventDateString;

    public String getEventDateString() {
        return eventDateString;
    }
    public void setEventDateString(String eventDateString) {
        this.eventDateString = eventDateString;
    }
    public Integer getEventId() {
        return eventId;
    }
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
    public String getEventName() {
        return eventName;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
    public Integer getEventSize() {
        return eventSize;
    }
    public void setEventSize(Integer eventSize) {
        this.eventSize = eventSize;
    }
    public Long getEventDate() {
        return eventDate;
    }
    public void setEventDate(Long eventDate) {
        this.eventDate = eventDate;
    }
    public Integer getParticipants() {
        return participants;
    }
    public void setParticipants(Integer participants) {
        this.participants = participants;
    }

    public static Event toEvent(String payload){
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonObject data = reader.readObject();

        return toEvent(data);
    }

    public static Event toEvent(JsonObject data){
        Event event = new Event();
        event.setEventId(Integer.parseInt(data.getString(ATTR_EVENTID)));
        event.setEventName(data.getString(ATTR_EVENTNAME));
        event.setEventSize(Integer.parseInt(data.getString(ATTR_EVENTSIZE)));
        event.setEventDate(Long.parseLong(data.getString(ATTR_EVENTDATE)));
        event.setParticipants(Integer.parseInt(data.getString(ATTR_EVENTPARTICIPANTS)));
        event.setEventDateString(event.getDateString());
        return event;
    }

    public static String toJsonString(Event event){
        String eventString = Json.createObjectBuilder()
                                .add(ATTR_EVENTID, event.getEventId().toString())
                                .add(ATTR_EVENTNAME, event.getEventName())
                                .add(ATTR_EVENTSIZE, event.getEventSize().toString())
                                .add(ATTR_EVENTDATE, event.getEventDate().toString())
                                .add(ATTR_EVENTPARTICIPANTS, event.getParticipants().toString())
                                .build().toString();

        return eventString;
    }

    public static Event fileEventToEvent(JsonObject data){
        Event event = new Event();
        event.setEventId(data.getInt(ATTR_EVENTID));
        event.setEventName(data.getString(ATTR_EVENTNAME));
        event.setEventSize(data.getInt(ATTR_EVENTSIZE));
        event.setEventDate(Long.parseLong(data.get(ATTR_EVENTDATE).toString()));
        event.setParticipants(data.getInt(ATTR_EVENTPARTICIPANTS));
        event.setEventDateString(event.getDateString());
        return event;
    }

    public String getDateString(){
        Date date = new Date(eventDate);
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        return format.format(date);
    }
    
    @Override
    public String toString(){
        return "EventId: %d, EventName: %s, EventSize: %d, EventDate: %d, EventParticipants: %d\n".formatted(eventId, eventName, eventSize, eventDate, participants);
    }

    public Boolean exceedEventSize(Integer ticketsRequested){
        return (ticketsRequested.intValue() + participants.intValue()) > eventSize.intValue();
    }
    
}

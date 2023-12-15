package vttp.ssf.assessment.eventmanagement.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonReader;
import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.repositories.RedisRepository;

@Service
public class DatabaseService {

    @Autowired
    private RedisRepository redisRepo;

    // TODO: Task 1
    public List<Event> readFile(String fileName) throws Exception{
        try(FileReader fr = new FileReader(fileName)){
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            br.lines().forEach(line -> sb.append(line.trim()));
            br.close();
            JsonReader reader = Json.createReader(new StringReader(sb.toString()));
            JsonArray data = reader.readArray();
            List<Event> events = data.stream()
                                        .map(jsonValue -> Event.fileEventToEvent(jsonValue.asJsonObject()))
                                        .toList();
            return events;
        }
    }

    public void saveRecord(Event event){
        redisRepo.saveRecord(event);
    }

    public List<Event> getAllEvents(){
        return redisRepo.getAllEvents();
    }

    public Event getEvent(Integer id){
        return redisRepo.getEvent(id - 1);
    }

    public Boolean registerParticipant(Integer eventId, Integer ticketsRequested){
        Event event = getEvent(eventId);
        if (event.exceedEventSize(ticketsRequested)){
            return false;
        }

        Integer updatedNumberOfparticpants = event.getParticipants() + ticketsRequested;
        event.setParticipants(updatedNumberOfparticpants);
        redisRepo.updateEvent(event);
        return true;
    }

    public Boolean ofAge(LocalDate birthDate){
        return (birthDate.equals(LocalDate.now().minusYears(21)) || birthDate.isBefore(LocalDate.now().minusYears(21)));
    }
}

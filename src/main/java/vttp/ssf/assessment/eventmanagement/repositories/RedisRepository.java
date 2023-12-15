package vttp.ssf.assessment.eventmanagement.repositories;

import static vttp.ssf.assessment.eventmanagement.Utils.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.ssf.assessment.eventmanagement.models.Event;

@Repository
public class RedisRepository {

	@Autowired @Qualifier(BEAN_REDIS)
	private RedisTemplate<String, String> template;

	// TODO: Task 2
	public void saveRecord(Event event){
		ListOperations<String, String> listOps = template.opsForList();
		listOps.rightPush("events", Event.toJsonString(event));
	}

	// TODO: Task 3
	public Long getNumberOfEvents(){
		ListOperations<String, String> listOps = template.opsForList();
		Long size = listOps.size("events");
		return size;
	}

	// TODO: Task 4
	public Event getEvent(Integer index){
		ListOperations<String, String> listOps = template.opsForList();
		String data = listOps.index("events", index);
		return Event.toEvent(data);
	}

	public List<Event> getAllEvents(){
		ListOperations<String, String> listOps = template.opsForList();
		List<Event> events = listOps.range("events", 0, getNumberOfEvents()).stream()
									.map(data -> Event.toEvent(data))
									.toList();
		return events;
	}

	public void updateEvent(Event event){
		ListOperations<String, String> listOps = template.opsForList();
		listOps.set("events", event.getEventId() - 1, Event.toJsonString(event));
	}
}

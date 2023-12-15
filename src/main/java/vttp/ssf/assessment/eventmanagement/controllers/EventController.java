package vttp.ssf.assessment.eventmanagement.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import vttp.ssf.assessment.eventmanagement.models.Event;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@Controller
@RequestMapping
public class EventController {
	
	@Autowired
	private DatabaseService dbSvc;

	//TODO: Task 5
	@GetMapping(path = "/events/listing")
	public ModelAndView displayEvents(){
		ModelAndView mav = new ModelAndView("events");
		List<Event> events = dbSvc.getAllEvents();
		mav.addObject("events", events);
		return mav;
	}
}

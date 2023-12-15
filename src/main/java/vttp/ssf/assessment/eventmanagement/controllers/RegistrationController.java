package vttp.ssf.assessment.eventmanagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.ssf.assessment.eventmanagement.models.Registration;
import vttp.ssf.assessment.eventmanagement.services.DatabaseService;

@Controller
@RequestMapping
public class RegistrationController {
    
    @Autowired
    private DatabaseService dbSvc;

    // TODO: Task 6
    @GetMapping(path = "/events/register/{id}")
	public ModelAndView createRegistrationForm(@PathVariable Integer id, HttpSession session){
		ModelAndView mav = new ModelAndView("eventregister");
        session.setAttribute("eventId", id);
		mav.addObject("event", dbSvc.getEvent(id));
		mav.addObject("registration", new Registration());
		return mav;
	}

    // TODO: Task 7
    // have to always get latest event from redis
    @PostMapping(path = "/registration/register")
    public ModelAndView processRegistration(@Valid @ModelAttribute Registration registration, BindingResult result, HttpSession session){
        ModelAndView mav = new ModelAndView();
        Integer eventId = (Integer) session.getAttribute("eventId");
        if (result.hasErrors()){
            mav.setViewName("eventregister");
            mav.addObject("event", dbSvc.getEvent(eventId));
            return mav;
        }

        if (!dbSvc.ofAge(registration.getBirthDate(), dbSvc.getEvent(eventId))){
            mav.setViewName("registererror");
            mav.addObject("error", "You must be 21 years old or older to register for the event.");
            return mav;
        }
        
        Boolean registered = dbSvc.registerParticipant(eventId, registration.getTicketsRequested());
        
        if (!registered){
            mav.setViewName("registererror");
            mav.addObject("error", "Your request for tickets exceeded the event size.");
            return mav;
        }

        mav.setViewName("success");
        mav.addObject("event", dbSvc.getEvent(eventId));
        session.invalidate();
        return mav;
    }
}

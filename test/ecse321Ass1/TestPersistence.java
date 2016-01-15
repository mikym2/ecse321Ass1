package ecse321Ass1;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ecse321.eventregistration.model.Event;
import ecse321.eventregistration.model.Participant;
import ecse321.eventregistration.model.Registration;
import ecse321.eventregistration.model.RegistrationManager;

public class TestPersistence {

	@Before
	public void setUp() throws Exception {
		RegistrationManager rm = RegistrationManager.getInstance();
	
		//create participants
		Participant pa = new Participant("Martin");
		Participant pa2= new Participant("Jennifer");
		
		//create event 
		Calendar c = Calendar.getInstance();
		c.set(2105,Calendar.SEPTEMBER,15,10,0,0);
		Date eventDate = new Date(c.getTimeInMillis());
		Time startTime = new Time(c.getTimeInMillis());
        c.set(2015, Calendar.SEPTEMBER,15,10,0,0);
        Time endTime = new Time(c.getTimeInMillis());
        Event ev = new Event("Concert",eventDate, startTime, endTime);
        
        //register participants to event
        Registration re = new Registration(pa,ev);
        Registration re2 = new Registration(pa2,ev);
        
        
        //manage registrations 
        rm.addRegistration(re);
        rm.addRegistration(re2);
        rm.addEvent(ev);
        rm.addParticipant(pa);
        rm.addParticipant(pa2);
	}

	@After
	public void tearDown() throws Exception {
		//clear all registration
		RegistrationManager rm = RegistrationManager.getInstance();
		rm.delete();
	
	}

	@Test
	public void test() {
		RegistrationManager rm = RegistrationManager.getInstance();
		PersistenceXStream.setFilename("test"+File.separator+"ca"+File.separator+"mcgill"+File.separator+"event"+
		File.separator + "persistance" + File.separator+"data.xml");
		PersistenceXStream.setAlias("event",Event.class);
		PersistenceXStream.setAlias("partcipant", Participant.class);
		PersistenceXStream.setAlias("registration", Registration.class);
		if(!PersistenceXStream.saveToXMLwithXstream(rm)){
			fail("could not save file. " );
			
			//clear the model in memory
			rm.delete();
			assertEquals(0, rm.getParticipants().size());
			assertEquals(0, rm.getEvents().size());
			assertEquals(0, rm.getRegistrations().size());
			
			//load model
			rm= (RegistrationManager) PersistenceXStream.loadFRomXMLwithXStream();
			if(rm == null)
				fail("Could not load file.");
			
			//check participants
			assertEquals(2, rm.getParticipants().size());
			assertEquals("Martin",rm.getParticipant(0).getName());
			assertEquals("JEnnifer",rm.getParticipant(1).getName());
		
			//check event 
			assertEquals(1, rm.getEvents().size());
			assertEquals("Concert", rm.getEvent(0).getName());
			Calendar c = Calendar.getInstance();
			c.set(2015, Calendar.SEPTEMBER,15,8,30,0);;
			
			
		}
		
	}
	
	

}
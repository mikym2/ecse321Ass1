package ca.mcgill.ecse321.eventregistration.persistence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import ca.mcgill.ecse321.eventregistration.model.Event;
import ca.mcgill.ecse321.eventregistration.model.Participant;
import ca.mcgill.ecse321.eventregistration.model.Registration;
import ca.mcgill.ecse321.eventregistration.model.RegistrationManager;

public class PersistenceEventRegistration {
	
	private static void initialiazeXStream(){
		PersistenceXStream.setFilename("eventregistration.xml");
		PersistenceXStream.setAlias("event", Event.class);
		PersistenceXStream.setAlias("participant",Participant.class);
		PersistenceXStream.setAlias("registration", Registration.class);
		PersistenceXStream.setAlias("manager", RegistrationManager.class);
		
	}
	
	
	public static void loadEventRegistrationModel(){
		RegistrationManager rm = RegistrationManager.getInstance();
		
		
		PersistenceEventRegistration.initialiazeXStream();
		
		RegistrationManager rm2 = (RegistrationManager)PersistenceXStream.loadFromXMLwithXStream();
		if(rm2 != null){
			//unfortunatly , this creates a second REgistrarion Manager object, even thoit is a singleton
			//copy loaded model into singleton instance of REgistrationManager, becuase this will be used throughout the application
			Iterator<Participant> pIt = rm2.getParticipants().iterator();
			while(pIt.hasNext())
				rm.addParticipant(pIt.next());
			Iterator<Event> eIt = rm2.getEvents().iterator();
			while(eIt.hasNext())
				rm.addEvent(eIt.next());
			Iterator<Registration> rIt = rm2.getRegistrations().iterator();
			while(rIt.hasNext())
				rm.addRegistration(rIt.next());
		}
	
	
	}
	


}

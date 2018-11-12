package com.coachingeleven.coachingsoftware.application.service;

import static javax.ejb.TransactionAttributeType.REQUIRED;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;

import com.coachingeleven.coachingsoftware.application.exception.TeamContactAlreadyExistsException;
import com.coachingeleven.coachingsoftware.persistence.entity.TeamContact;
import com.coachingeleven.coachingsoftware.persistence.entity.TeamContactId;
import com.coachingeleven.coachingsoftware.persistence.repository.TeamContactRepository;

@LocalBean
@Stateless(name = "TeamContactService")
@TransactionAttribute(REQUIRED)
public class TeamContactService implements TeamContactServiceRemote {

	private static final Logger logger = Logger.getLogger(TeamContactService.class.getName());
	
	@EJB
    private TeamContactRepository teamContactRepository;
	
	@Override
	public TeamContact createTeamContact(TeamContact teamContact) throws TeamContactAlreadyExistsException {
		logger.log(Level.INFO, "Creating teamContact with teamID " + teamContact.getTeam().getID() + " and contactID " + teamContact.getContact().getID());
		if(teamContact != null && teamContact.getTeam() != null && teamContact.getContact() != null) {
			TeamContactId teamContactId = new TeamContactId(teamContact.getTeam().getID(), teamContact.getContact().getID(), teamContact.getJoinDate());
			if (teamContactRepository.find(TeamContact.class, teamContactId) != null) {
	            logger.log(Level.INFO, "TeamContact with teamID " + teamContact.getTeam().getID() + " and contactID " + teamContact.getContact().getID() + " already exists");
	            throw new TeamContactAlreadyExistsException();
	        }
		}
        return teamContactRepository.persist(teamContact);
	}

}
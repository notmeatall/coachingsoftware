/*
 * Copyright (c) 2018 Schildknecht Elias & Palmitessa Alexander, Berner Fachhochschule, Switzerland.
 *
 * Project 'com.coachingeleven.coachingsoftware Coaching Administration System'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */

package com.coachingeleven.coachingsoftware.persistence.repository;

import com.coachingeleven.coachingsoftware.persistence.entity.Player;
import com.coachingeleven.coachingsoftware.persistence.entity.Team;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import static javax.ejb.TransactionAttributeType.SUPPORTS;

import java.util.List;

@Stateless
public class TeamRepository extends Repository<Team> {

    @TransactionAttribute(SUPPORTS)
    public Team find(String name) {
    	try {
			TypedQuery<Team> query = entityManager.createNamedQuery("findTeam", Team.class);
			query.setParameter("teamname", name);
			return query.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
    }
    
    @TransactionAttribute(SUPPORTS)
    public List<Team> findTeamsByClubID(int clubId) {
    	try {
			TypedQuery<Team> query = entityManager.createNamedQuery("findTeamsByClubId", Team.class);
			query.setParameter("clubId", clubId);
			return query.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
    }
    
    @TransactionAttribute(SUPPORTS)
    public List<Team> findTeamsBySeasonID(int seasonID) {
    	try {
			TypedQuery<Team> query = entityManager.createNamedQuery("findTeamsBySeasonID", Team.class);
			query.setParameter("seasonID", seasonID);
			return query.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
    }
    
    @TransactionAttribute(SUPPORTS)
	public Team addPlayerToTeam(int teamID, Player player) {
		Team team = entityManager.find(Team.class, teamID);
		team.getCurrentPlayers().add(player);
		return entityManager.merge(team);
	}
    
    @TransactionAttribute(SUPPORTS)
	public Team addCurrentPlayersToTeam(int oldTeamID, int newTeamID) {
		Team oldTeam = entityManager.find(Team.class, oldTeamID);
		Team newTeam = entityManager.find(Team.class, newTeamID);
		for(Player player : oldTeam.getCurrentPlayers()) {
			newTeam.getCurrentPlayers().add(player);
		}
		return entityManager.merge(newTeam);
	}
    
    @TransactionAttribute(SUPPORTS)
	public Team addHistoryPlayersToTeam(int oldTeamID, int newTeamID) {
		Team oldTeam = entityManager.find(Team.class, oldTeamID);
		Team newTeam = entityManager.find(Team.class, newTeamID);
		for(Player player : oldTeam.getHistoryPlayers()) {
			newTeam.getHistoryPlayers().add(player);
		}
		return entityManager.merge(newTeam);
	}
}

/*
 * Copyright (c) 2018 Schildknecht Elias & Palmitessa Alexander, Berner Fachhochschule, Switzerland.
 *
 * Project 'com.coachingeleven.coachingsoftware Coaching Administration System'
 *
 * Distributable under GPL license. See terms of license at gnu.org.
 */

package com.coachingeleven.coachingsoftware.persistence.repository;

import com.coachingeleven.coachingsoftware.persistence.entity.Club;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.util.List;

import static javax.ejb.TransactionAttributeType.SUPPORTS;

@Stateless
public class ClubRepository extends Repository<Club> {

	@TransactionAttribute(SUPPORTS)
	public Club find(String name) {
		try {
			TypedQuery<Club> query = entityManager.createNamedQuery("findClub", Club.class);
			query.setParameter("clubname", name);
			return query.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@TransactionAttribute(SUPPORTS)
	public List<Club> findAll(){
		try {
			TypedQuery<Club> query = entityManager.createNamedQuery("findAllClubs", Club.class);
			return query.getResultList();
		} catch (NoResultException ex) {
			return null;
		}
	}
}

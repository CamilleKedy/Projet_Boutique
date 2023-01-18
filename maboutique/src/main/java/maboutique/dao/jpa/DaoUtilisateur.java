package maboutique.dao.jpa;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import maboutique.data.Utilisateur;

@Stateless
@LocalBean
public class DaoUtilisateur {

	// Champs
	@PersistenceContext
	private EntityManager em;

	// Actions
	public int inserer(Utilisateur utilisateur) {
		em.persist(utilisateur);
		em.flush();
		return utilisateur.getId();
	}

	public void modifier(Utilisateur utilisateur) {
		em.merge(utilisateur);
	}

	public void supprimer(int idUtilisateur) {
		em.remove(em.getReference(Utilisateur.class, idUtilisateur));
	}

	public Utilisateur retrouver(int idUtilisateur) {
		return em.find(Utilisateur.class, idUtilisateur);
	}

	public List<Utilisateur> listerTout() {
		var jpql = "SELECT u FROM Utilisateur u ORDER BY u.nom, u.prenom";
		var query = em.createQuery(jpql, Utilisateur.class);
		return query.getResultList();
	}

	public Utilisateur authentifier(String email, String motDePasse) {
		var jpql = "SELECT u FROM Utilisateur u " + " WHERE u.email = :email AND u.motDePasse = :mdp";
		var query = em.createQuery(jpql, Utilisateur.class);
		query.setParameter("email", email);
		query.setParameter("mdp", motDePasse);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}

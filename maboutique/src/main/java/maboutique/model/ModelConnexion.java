package maboutique.model;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import maboutique.dao.jpa.DaoUtilisateur;
import maboutique.data.Utilisateur;
import maboutique.util.UtilJsf;

@Named
@SessionScoped
public class ModelConnexion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	private String motDePasse;
	private Utilisateur utilisateurActif;

	@Inject
	private DaoUtilisateur daoUtilisateur;

	// Getters et Setters

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public Utilisateur getUtilisateurActif() {
		return utilisateurActif;
	}

	public String login() {
		utilisateurActif = daoUtilisateur.authentifier(email, motDePasse);
		if (utilisateurActif != null) {
			UtilJsf.messageInfo("Connexion réussie.");
			email = null;
			motDePasse = null;
			return "home";
		} else {
			UtilJsf.messageError("Identifiant ou mot de passe invalide.");
			return "login";
		}
	}

	public String logout() {
		UtilJsf.sessionInvalidate();
		UtilJsf.messageInfo("Vous avez été déconnecté");
		return "login";
	}

	public boolean isLoggedIn() {
		return utilisateurActif != null;
	}
	
	public boolean isAdmin () {
		return isLoggedIn() && utilisateurActif.isAdmin();
	}

}

package maboutique.data;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Utilisateur implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;
	
	@NotBlank( message="Le nom doit être renseigné")
	@Size(max=50, message="Valeur trop longue pour le nom : 50 car. maxi" )
	private String nom;
	
	@NotBlank( message="Le prénom doit être renseigné")
	@Size(max=50, message="Valeur trop longue pour le prénom : 50 car. maxi")
	private String prenom;
	
	@NotBlank( message="L'adresse e-mail doit être renseignée")
	@Email( message="L'adresse e-mail n'est pas valide.")
	private String email;
	
	@NotBlank( message="Le mot de passe doit être renseigné")
	@Size(min=5, message="Le mot d passe doit avoir au moins 5 caractères" )
	@Size(max=50, message="Le mot d passe doit avoir au plus 50 caractères")
	private String motDePasse;
	
	private boolean admin;
	
	// Getters et Setters
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
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
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	
	

}

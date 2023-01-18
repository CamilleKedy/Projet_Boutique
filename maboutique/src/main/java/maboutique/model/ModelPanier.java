package maboutique.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import maboutique.dao.jpa.DaoProduit;
import maboutique.data.Produit;
import maboutique.util.UtilJsf;

@Named
@SessionScoped
@SuppressWarnings("serial")
public class ModelPanier implements Serializable {
	
	@Inject
	private ModelConnexion modelConnexion;
	
	@Inject
	private DaoProduit daoProduit;
	
	private List<Produit> panier = new ArrayList<>();

	public List<Produit> getPanier() {
		return panier;
	}
	
	public double getTotal() {
		double montant = 0;
		for (Produit produit : panier) {
			montant = montant + produit.getPrix();
		}
		return montant;
	}
	
	public String valider() {
		for (Produit produit : panier) {
			produit.setStatut("C");
			produit.setAcheteur(modelConnexion.getUtilisateurActif());
			daoProduit.modifier(produit);
		}
		panier.clear();
		UtilJsf.messageInfo("Votre commande a bien été enregistrée");
		return "home";
		
	}
	
	public String supprimer(Produit produit) {
		panier.remove(produit);
		return null;	
	}

	public void setPanier(List<Produit> panier) {
		this.panier = panier;
	}
	


}

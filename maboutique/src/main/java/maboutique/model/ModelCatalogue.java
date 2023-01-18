package maboutique.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import maboutique.dao.jpa.DaoProduit;
import maboutique.data.Produit;

@Named
@ViewScoped
@SuppressWarnings("serial")
public class ModelCatalogue implements Serializable {

	// Champs
	private List<ProduitAChoisir> liste;

	@Inject
	private DaoProduit daoProduit;

	@Inject
	private ModelConnexion modelConnexion;
	
	@Inject
	private ModelPanier modelPanier;

	public List<ProduitAChoisir> getListe() {
		if (liste == null) {
			liste = new ArrayList<>();
			List<Produit> listeProduits;
			listeProduits = daoProduit.listerCatalogue(modelConnexion.getUtilisateurActif());
			for (Produit produit : listeProduits) {
				ProduitAChoisir produitAChoisir = new ProduitAChoisir(produit, false);
				if(modelPanier.getPanier().contains(produitAChoisir.getProduit())){
					produitAChoisir.setChoisi(true);
				}
				liste.add(produitAChoisir);
			}
		}
		return liste;
	}
	
	public String remplirPanier() {
		modelPanier.getPanier().clear();
		for (ProduitAChoisir produitAChoisir : liste) {
			if(produitAChoisir.choisi) {
				modelPanier.getPanier().add(produitAChoisir.getProduit());
			}
		}
		return "panier";
	}

	// Classe auxiliaire
	public static class ProduitAChoisir {
		// Champs
		private Produit produit;
		private boolean choisi;

		// Constructeur
		public ProduitAChoisir(Produit produit, boolean choisi) {
			this.produit = produit;
			this.choisi = choisi;
		}

		// Getters & Setters
		public Produit getProduit() {
			return produit;
		}

		public void setProduit(Produit produit) {
			this.produit = produit;
		}

		public boolean isChoisi() {
			return choisi;
		}

		public void setChoisi(boolean choisi) {
			this.choisi = choisi;
		}
	}

}

package maboutique.converter;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

import maboutique.dao.jpa.DaoUtilisateur;
import maboutique.data.Utilisateur;

@Named
@RequestScoped
public class ConverterUtilisateur implements Converter<Utilisateur> {
	
	// Champs
	@Inject
	private DaoUtilisateur daoUtilisateur;

	// Actions
	@Override
	public Utilisateur getAsObject(FacesContext context, UIComponent component, String id) {
		if (id == null || id.isEmpty()) {
			return null;
		}
		try {
			return daoUtilisateur.retrouver(Integer.valueOf(id));
		} catch (NumberFormatException e) {
			throw new ConverterException(new FacesMessage(String.format("Identifiant de catégorie invalide : %s", id)),
					e);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Utilisateur item) {
		if (item == null) {
			return "";
		}
		return String.valueOf(item.getId());
	}
}

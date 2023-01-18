package maboutique.dao.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Lock;
import javax.ejb.LockType;

import maboutique.data.Utilisateur;


//@Singleton
//@LocalBean
public class DaoUtilisateur {
	
	
	// Constantes
	
	private static final String CHEMIN_FICHIER = "C:\\TEMP\\tp-web-java\\utilisateur.data";
	
	
	// Champs
	
	private Map<Integer, Utilisateur> map = new HashMap<>();
	
	
	// Initialisation et fermeture
	
	@PostConstruct
	private void init() {

		try {
			
			BufferedReader in = null;
			String ligne;
			in =new BufferedReader(new FileReader( CHEMIN_FICHIER ));
			while ((ligne = in.readLine()) != null) {
				String items[] = ligne.split(";");
				Utilisateur u = new Utilisateur();
				u.setId( Integer.parseInt( items[0]) );
				u.setNom( items[1] );
				u.setPrenom( items[2] );
				u.setEmail( items[3] );
				u.setMotDePasse( items[4] );
				u.setAdmin( Boolean.parseBoolean( items[5] ) );
				map.put( u.getId(), u);
			}
			in.close();
			
		} catch (IOException e) {
			throw new RuntimeException( e );
		}
	}
	
	
	// Actions
	
	@Lock(LockType.WRITE)
	public int inserer( Utilisateur utilisateur ) {
		utilisateur.setId( Collections.max( map.keySet() ) + 1 );
		map.put( utilisateur.getId(), utilisateur );
		sauvegarder();
		return utilisateur.getId();
	}
	
	@Lock(LockType.WRITE)
	public void modifier( Utilisateur utilisateur ) {
		map.put( utilisateur.getId(), utilisateur );
		sauvegarder();
	}
	
	@Lock(LockType.WRITE)
	public void supprimer( int idUtilisateur ) {
		map.remove( idUtilisateur );
		sauvegarder();
	}
	
	@Lock(LockType.READ)
	public Utilisateur retrouver( int idUtilisateur ) {
		return clone( map.get( idUtilisateur ) );
	}
	
	@Lock(LockType.READ)
	public List<Utilisateur> listerTout() {
		var liste = new ArrayList<Utilisateur>();
		for ( Utilisateur u : map.values() ) {
			liste.add( clone( u ) );
		}
		return liste;
	}
	
	@Lock(LockType.READ)
	public Utilisateur authentifier( String email, String  motDePasse ) {
		for( Utilisateur u : map.values() ) {
			if ( u.getEmail().equals(email) && u.getMotDePasse().equals(motDePasse) ) {
				return clone( u );
			}
		}
		return null;
	}

	
	// Méthodes auxiliaires
	
	private Utilisateur clone( Utilisateur source ) {
		Utilisateur target = new Utilisateur();
		target.setId( source.getId() );
		target.setNom( source.getNom() );
		target.setPrenom( source.getPrenom() );
		target.setEmail( source.getEmail() );
		target.setMotDePasse( source.getMotDePasse() );
		target.setAdmin( source.isAdmin() );
		return target;
	}

	
	private void sauvegarder() {
		try {
			BufferedWriter out = null;
			out =new BufferedWriter(new FileWriter( CHEMIN_FICHIER ) );
			String  ligne;
			for( Utilisateur u : map.values() ) {
				ligne = String.format( "%d;%s;%s;%s;%s;%b", u.getId(), u.getNom(), u.getPrenom(), u.getEmail(), u.getMotDePasse(), u.isAdmin() );
				out.write( ligne );
				out.newLine();
			}
			out.close();
			
		} catch (IOException e) {
			throw new RuntimeException( e );
		}
	}
}

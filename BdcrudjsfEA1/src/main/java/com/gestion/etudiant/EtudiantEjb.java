/**
 * 
 */
package com.gestion.etudiant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * 
 */

@RequestScoped
@Named
public class EtudiantEjb {
	private Etudiant etudiant;
	private List<Etudiant> listeEtudiants;
	private Date date;
	private boolean modif=false;
	private static int etuId;
	

	
	
	public Connection connexe(){
			
	          try {
	          	// 1 charger le driver mysql
	             Class.forName("com.mysql.cj.jdbc.Driver");
	          	  
	          	  
	          	// 2 creation la connection
	             Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdcrudjsf", "root", "");
	             return connection;
	             
	          }catch (ClassNotFoundException e) {
	        	  e.printStackTrace();
	        	  Connection connection = null;
	        	  return connection;
	        	  
	          }catch (SQLException e) {
	        	  
	        	  
	        	  e.printStackTrace();
	        	  Connection connection = null;
	        	  return connection;
	        	  
	          }
	          
	
	   }
	
	
	
	
	
	public List<Etudiant>  afficheEtudiants() {
		listeEtudiants = new ArrayList<Etudiant>();
		
		String req = "select *from etudiant ";
		try {
			PreparedStatement ps = connexe().prepareStatement(req);
			
			ResultSet res = ps.executeQuery();
			
			while(res.next()) {
				Etudiant etu = new Etudiant();
				etu.setId(res.getInt("id"));
				etu.setNom(res.getString("nom"));
				etu.setPrenom(res.getString("prenom"));
				etu.setDatenaiss(res.getDate("datenaiss"));
				listeEtudiants.add(etu);
			}
			
		   return listeEtudiants;
			
		}catch (SQLException e) {
			//TODO Auto-generated catch block 
			
			e.printStackTrace();
			return listeEtudiants;
		}
		
	}
	
	
	
	
	
	public void ajoutEtudiant() {
		
		String req = "insert into etudiant (nom,prenom,datenaiss) value(?,?,?)";
		etudiant.setDatenaiss(convDate(date));
		
		try {
			PreparedStatement ps = connexe().prepareStatement(req);
			ps.setString(1, etudiant.getNom());
			ps.setString(2, etudiant.getPrenom());
			ps.setDate(3, etudiant.getDatenaiss());
			ps.execute();
			
			afficheEtudiants();
			etudiant = new Etudiant();
			date = null;
			
			
		}catch(SQLException e1) {
			
			//TODO Auto-generated catch block
			e1.printStackTrace();
		}
   }
	
	
	public void supprimerEtudiants(Etudiant etu) {
		String req ="delete from etudiant where id =?";
		try {
			PreparedStatement ps = connexe().prepareStatement(req);
			ps.setInt(1, etu.getId());
			ps.execute();		
			
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void affiche(Etudiant etu) {
		etuId = etu.getId();
		date = etu.getDatenaiss();
		etudiant = etu;
		modif= true;
	}
	
	
	public void modifEtudiant() {
		etudiant.setDatenaiss(convDate(date));
		
		try {
			String req = "UPDATE etudiant SET nom =?, prenom= ?, datenaiss= ?, WHERE id = ?";
			PreparedStatement ps = connexe().prepareStatement(req);
			ps.setString(1, etudiant.getNom());
			ps.setString(2, etudiant.getPrenom());
			ps.setDate(3, etudiant.getDatenaiss());
			ps.setInt(4, etuId);
			
			System.out.println(ps);
			ps.executeUpdate();
			
			afficheEtudiants();
			etudiant = new Etudiant();
			date= null;
		
		} catch (SQLException e1) {
			//TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public java.sql.Date convDate(java.util.Date calendarDate) {
		return new java.sql.Date(calendarDate.getTime());
	}





	public List<Etudiant> getLisEtudiants() {
		return afficheEtudiants();
	}


       


	






	public boolean isModif() {
		return modif;
	}





	public void setModif(boolean modif) {
		this.modif = modif;
	}
	
	
	
	
	
	
	





	public int getEtuId() {
		return etuId;
	}





	public void setEtuId(int etuId) {
		EtudiantEjb.etuId = etuId;
	}





	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public List<Etudiant> getListeEtudiants() {
		return listeEtudiants;
	}

	public void setListeEtudiants(List<Etudiant> listeEtudiants) {
		this.listeEtudiants = listeEtudiants;
	}
	
	
	
	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public EtudiantEjb() {
		//TODO Auto-generated constructor stub
		etudiant = new Etudiant();
	}
	
	
	
	

}

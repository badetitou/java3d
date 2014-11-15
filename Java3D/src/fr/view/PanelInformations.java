package fr.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelInformations extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JLabel nomObjet;
	private final JLabel auteur;
	private final JLabel dateAjout;
	private final JLabel derniereModif;
	private final JLabel nbChargements;
	private final JLabel nbImages;
	private final JLabel nbRealisations;
	private final int size=Window.outil.getScreenSize().height/27;

	private Font font;

	public PanelInformations(){
		this.setBackground(new Color(215,215,215));
		this.setLayout(new FlowLayout(0,30,30));

		JPanel panelInfo = new JPanel();
		panelInfo.setLayout(new GridLayout(3,3,15,0));
		panelInfo.setPreferredSize(new Dimension(Window.outil.getScreenSize().width-500,Window.outil.getScreenSize().height/6));

		nomObjet=new JLabel("Nom de l'objet : ");
		font = nomObjet.getFont();
		nomObjet.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize()));
		nomObjet.setIcon(new ImageIcon(new ImageIcon("ressources/icones/iconesInformations/nomObjet.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT)));

		auteur=new JLabel("Nom auteur : ");
		font = auteur.getFont();
		auteur.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize()));
		auteur.setIcon(new ImageIcon(new ImageIcon("ressources/icones/iconesInformations/auteur.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT)));

		dateAjout=new JLabel("Date d'ajout : ");
		font = dateAjout.getFont();
		dateAjout.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize()));
		dateAjout.setIcon(new ImageIcon(new ImageIcon("ressources/icones/iconesInformations/dateAjout.png").getImage().getScaledInstance(size,size, Image.SCALE_DEFAULT)));

		derniereModif=new JLabel("Dernière modification : ");
		font = derniereModif.getFont();
		derniereModif.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize()));
		derniereModif.setIcon(new ImageIcon(new ImageIcon("ressources/icones/iconesInformations/derniereModif.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT)));

		nbChargements=new JLabel("Nombre de chargements : ");
		font = nbChargements.getFont();
		nbChargements.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize()));
		nbChargements.setIcon(new ImageIcon(new ImageIcon("ressources/icones/iconesInformations/nbChargements.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT)));

		nbImages=new JLabel("Nombre d'images : ");
		font = nbImages.getFont();
		nbImages.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize()));
		nbImages.setIcon(new ImageIcon(new ImageIcon("ressources/icones/iconesInformations/nbImages.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT)));

		nbRealisations=new JLabel("Nombre de réalisations : ");
		font = nbRealisations.getFont();
		nbRealisations.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize()));
		nbRealisations.setIcon(new ImageIcon(new ImageIcon("ressources/icones/iconesInformations/nbRealisations.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT)));



		panelInfo.add(nomObjet);
		panelInfo.add(auteur);
		panelInfo.add(dateAjout);
		panelInfo.add(derniereModif);
		panelInfo.add(nbChargements);
		panelInfo.add(nbImages);
		panelInfo.add(nbRealisations);

		this.add(panelInfo);


	}

}

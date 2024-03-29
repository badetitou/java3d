package fr.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

import fr.model.Face;
import fr.model.Model;
import fr.model.OutilsBdd;

public class WindowEnregistrer extends JFrame {

	private final String lienGts;
	private final ArrayList<Object> listeOnglets;
	public WindowEnregistrer(JTabbedPane tabbedPane, ArrayList<Object> listeOnglets, PanelInformations panelInfos,boolean nouveau,String nomFichier,String nomAuteur,String lienGts,Menu menu) {
		this.lienGts=lienGts;
		this.listeOnglets=listeOnglets;
		PanelEnregistrer pE = new PanelEnregistrer(this, tabbedPane, listeOnglets, panelInfos,nouveau,nomFichier,nomAuteur,menu);
		this.setTitle("Enregistrer dans la BDD");
		this.setSize(500, 300);
		this.setResizable(false);
		this.setAlwaysOnTop(true);

		this.setLocationRelativeTo(null);
		this.setContentPane(pE);
		this.setVisible(true);
	}

	public class PanelEnregistrer extends JPanel implements MouseListener{

		private JButton jbOk=null;
		private JLabel jlFen=null;
		private JLabel jlNomAuteur=null;
		private JLabel jlNomObjet=null;
		private JLabel jlDateAjout=null;
		private JLabel jlDerniereModif=null;
		private JLabel jlNbChargements=null;
		private JLabel jlNbImages=null;
		private JLabel jlNbRealisations=null;
		private final JFrame windowE;
		private final OutilsBdd obdd;
		private boolean nouveau;
		private final String nomAuteur;
		private final String nomFichier;
		private final String dateModiff;
		private final String dateAjoutt;
		private final int nImages;
		private int nRealisations=0;
		private final int nChargements;
		private String description;
		private final ArrayList<String>listeImages;
		private final int nbImages;
		private final Component onglet;
		private final ButtonGroup group;
		private final JRadioButton newRea;
		private final JRadioButton oldRea;
		private final Menu menu;
		int res;
		public PanelEnregistrer(JFrame windowE, JTabbedPane tabbedPane, ArrayList<Object> listeOnglets, PanelInformations panelInfos,boolean nouveau,String nomFichier,String nomAuteur,Menu menu) {
			this.windowE = windowE;
			this.nouveau=nouveau;
			this.menu=menu;
			this.nomFichier=nomFichier;
			this.nomAuteur=nomAuteur;
			this.setPreferredSize(new Dimension(500, 300));
			obdd = new OutilsBdd("Database.db");
			onglet = tabbedPane.getSelectedComponent();
			listeImages=((Onglet) onglet).getListeImages();
			description=((Onglet)onglet).getPbdd().getDescription().getDescription();
			nbImages=((Onglet)onglet).getNbIm();
			group=new ButtonGroup();
			newRea=new JRadioButton(" Sauvegarder en tant que nouvelle realisation");
			oldRea=new JRadioButton(" Ecraser la realisation courante");
			group.add(newRea);
			group.add(oldRea);
			newRea.setSelected(true);
			oldRea.setSelected(false);
			newRea.addMouseListener(this);
			oldRea.addMouseListener(this);

			this.dateAjoutt=panelInfos.getDateAjoutt();
			this.dateModiff=panelInfos.getDateModiff();
			this.nChargements=panelInfos.getnChargements();
			this.nRealisations=panelInfos.getnRealisations();
			this.nImages=panelInfos.getnImages();
			jbOk = new JButton("Valider la sauvegarde");
			jlFen = new JLabel("Vous allez enregistr� l'objet " +this.nomFichier+ " dans la BDD avec les informations suivantes:");
			jlNomAuteur = new JLabel("Nom Auteur : " + this.nomAuteur);
			jlNomObjet = new JLabel("Nom Objet : " + this.nomFichier);
			jlDateAjout = new JLabel("Date d'ajout : " + this.dateAjoutt);
			jlDerniereModif = new JLabel("Derni�re modification : " + this.dateModiff);
			jlNbChargements = new JLabel("Nombre de chargements : " + this.nChargements);
			jlNbRealisations = new JLabel("Nombre de r�alisations : " + this.nRealisations);
			jlNbImages=new JLabel("Nombre d'images : "+listeImages.size());
			this.setLayout(new GridLayout(12, 1));
			this.add(jlFen);
			this.add(jlNomAuteur);
			this.add(jlNomObjet);
			this.add(jlDateAjout);
			this.add(jlDerniereModif);
			this.add(jlNbImages);
			this.add(jlNbChargements);
			this.add(jlNbRealisations);
			this.add(newRea);
			this.add(oldRea);
			this.add(jbOk);
			jbOk.addMouseListener(this);
		}

		public boolean copier( File source, File destination ){ //Methode permettant la copie d'un fichier
			boolean resultat = false;

			// Declaration des flux
			java.io.FileInputStream sourceFile=null;
			java.io.FileOutputStream destinationFile=null;
			try {
				// Cr�ation du fichier :
				destination.createNewFile();
				// Ouverture des flux
				sourceFile = new java.io.FileInputStream(source);
				destinationFile = new java.io.FileOutputStream(destination);
				// Lecture par segment de 0.5Mo
				byte buffer[]=new byte[512*1024];
				int nbLecture;
				while( (nbLecture = sourceFile.read(buffer)) != -1 ) {
					destinationFile.write(buffer, 0, nbLecture);
				}

				// Copie r�ussie
				resultat = true;
			} catch( java.io.FileNotFoundException f ) {
			} catch( java.io.IOException e ) {
			} finally {
				// Quoi qu'il arrive, on ferme les flux
				try {
					sourceFile.close();
				} catch(Exception e) { }
				try {
					destinationFile.close();
				} catch(Exception e) { }
			}
			return( resultat );
		}

		public boolean copieGTS(File source, File destination){
			boolean resultat = false;
			Model mod = ((Onglet) onglet).getDp().getPanel().getM();
			// Declaration des flux
			Scanner sourceFile= null;
			PrintWriter destinationFile=null;
			try {
				// Cr�ation du fichier :
				destination.createNewFile();
				// Ouverture des flux
				sourceFile = new Scanner(source);
				destinationFile = new PrintWriter(destination);
				// Lecture par segment de 0.5Mo
				destinationFile.println(sourceFile.nextLine());
				for(int i = 0;i<mod.getListPoint().size() + mod.getSegment().size();++i){
					destinationFile.println(sourceFile.nextLine());
				}
				for(Face f : mod.getFace()){
					destinationFile.println(f.getSegment1() + " " + f.getSegment2()+ " " + f.getSegment3() +" "+ f.getColor().getRed() + " "+f.getColor().getGreen()+" "+f.getColor().getBlue());
				}

				// Copie r�ussie
				resultat = true;
			} catch( java.io.FileNotFoundException f ) {
			} catch( java.io.IOException e ) {
			} finally {
				// Quoi qu'il arrive, on ferme les flux
				try {
					sourceFile.close();
				} catch(Exception e) { }
				try {
					destinationFile.close();
				} catch(Exception e) { }
			}
			return( resultat );
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getSource().equals(jbOk)){
				if (this.nouveau){
					new File("fichiers"+File.separator+this.nomFichier).mkdirs();
					new File("fichiers"+File.separator+this.nomFichier+File.separator+"images").mkdirs();
					new File("fichiers"+File.separator+this.nomFichier+File.separator+"realisations").mkdirs();
					//copier le point gts
					copier( new File(lienGts), new File("fichiers"+File.separator+this.nomFichier+File.separator+this.nomFichier+".gts"));
					//enregistrer les images.
					for (int i=0;i<listeImages.size();i++){
						File ff=new File("fichiers"+File.separator+this.nomFichier+File.separator+"images"+File.separator+this.nomFichier+i+".png");
						int j=i;
						while(ff.exists()){
							ff=new File("fichiers"+File.separator+this.nomFichier+File.separator+"images"+File.separator+this.nomFichier+j+".png");
							j++;
						}
						if (copier( new File(listeImages.get(i)), ff )){
							//System.out.println("Sauvegarde r�ussie");
						}
					}
					if(newRea.isSelected()){
						this.nRealisations++;
						if(this.copieGTS(new File(lienGts), new File("fichiers"+File.separator+this.nomFichier+File.separator+"realisations"+File.separator+this.nomFichier+this.nRealisations+".gts"))){
							windowE.dispose();
						}
						else {
							JOptionPane.showMessageDialog(null,"La sauvegarde de : "+lienGts+" a �chou� !","Sauvegarde �chou�e", JOptionPane.OK_OPTION);
						}
					}
					else if (oldRea.isSelected()){
						if(this.copieGTS(new File(lienGts), new File("tmp"))){
							if(this.copieGTS(new File("tmp"), new File(lienGts))){
								windowE.dispose();
							}
						}
						else {
							JOptionPane.showMessageDialog(null,"La sauvegarde de : "+lienGts+" a �chou� !","Sauvegarde �chou�e", JOptionPane.OK_OPTION);
						}
					}
					DefaultListModel mod=((Onglet)onglet).getPbdd().getPt().getListModel();
					for (int i=0;i<mod.size();i++){
						obdd.addTag(mod.getElementAt(i).toString(),this.nomFichier);
					}
					obdd.addFile(this.nomFichier, "fichiers"+File.separator+this.nomFichier+File.separator+this.nomFichier+".gts", this.description, this.nomAuteur, this.nChargements, listeImages.size(), this.nRealisations, this.nRealisations, "fichiers"+File.separator+this.nomFichier+File.separator+"images"+File.separator);
					this.nouveau=false;
					((Onglet)onglet).getPbdd().getPt().setNouveau(false);
					((Onglet)onglet).setNouveau(false);
					((Onglet)onglet).actualiserOnglet(this.nomFichier);
					((Onglet)onglet).getPbdd().getInformations().setNouveau(false);
					((Onglet)onglet).getPbdd().getPanelDescription().setNouveau(false);
					((Onglet)onglet).getPbdd().getImages().setNouveau(false);
				}
				else {
					String lien=obdd.getLinkImg(nomFichier);
					File repertoire = new File(lien);
					File[] listefichiers;
					File f;
					int i;
					if(repertoire.list()!=null){
						listefichiers=repertoire.listFiles();

						ArrayList<String> fichiers=new ArrayList<String>();
						for (int j =0;j<listefichiers.length;j++){
							fichiers.add(listefichiers[j]+"");
						}

						int g=0;
						for(i=0;i<fichiers.size();i++){
							if(!(listeImages.contains(fichiers.get(i)))){
								listefichiers[g].delete();
								fichiers.remove(i);
								i--;
							}
							g++;
						}

						for(int k=0;k<listeImages.size();k++){
							if(!(fichiers.contains(listeImages.get(k)))){
								File ff=new File("fichiers"+File.separator+this.nomFichier+File.separator+"images"+File.separator+this.nomFichier+k+".png");
								copier( new File(listeImages.get(k)), ff);
							}
						}
					}
					this.description=((Onglet)onglet).getPbdd().getDescription().getDescription();
					if(newRea.isSelected()){
						this.nRealisations++;
						if(this.copieGTS(new File(lienGts), new File("fichiers"+File.separator+this.nomFichier+File.separator+"realisations"+File.separator+this.nomFichier+this.nRealisations+".gts"))){
							windowE.dispose();
						}
						else {
							JOptionPane.showMessageDialog(null,"La sauvegarde de : "+lienGts+" a �chou� !","Sauvegarde �chou�e", JOptionPane.OK_OPTION);
						}
					}
					else if (oldRea.isSelected()){
						if(this.copieGTS(new File(lienGts), new File("tmp"))){
							if(this.copieGTS(new File("tmp"), new File(lienGts))){
								windowE.dispose();
							}
						}
						else {
							JOptionPane.showMessageDialog(null,"La sauvegarde de : "+lienGts+" a �chou� !","Sauvegarde �chou�e", JOptionPane.OK_OPTION);
						}
					}
					DefaultListModel mod=((Onglet)onglet).getPbdd().getPt().getListModel();
					for (int g=0;g<mod.size();g++){
						obdd.addTag(mod.getElementAt(g).toString(),this.nomFichier);
					}
					obdd.updateFile(this.nomFichier,this.description, this.nChargements, listeImages.size(), this.nRealisations, "fichiers"+File.separator+this.nomFichier+File.separator+"images"+File.separator,0);
				}
				menu.actualiserFichiersRecents(obdd.getLinkFile(this.nomFichier));
				((Onglet)onglet).getPbdd().getInformations().actualiserInfos(this.nomFichier, this.nomAuteur, this.nbImages, this.nRealisations,obdd.getDateLastModif(nomFichier));
				((Onglet)onglet).getPbdd().getPanelDescription().actualiserDesc(this.description);
			}
			((OngletMenu)listeOnglets.get(0)).getPlbdd().actualiser();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
	}
}
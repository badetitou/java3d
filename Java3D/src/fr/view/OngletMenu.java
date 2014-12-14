package fr.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;

import javax.swing.BorderFactory;
import javax.swing.DefaultRowSorter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import fr.model.OutilsBdd;

public class OngletMenu extends JPanel implements MouseListener{


	private final JLabel closeButon;
	private final JLabel ic;
	private final JPanel p1;
	private final JTabbedPane tabbedPane;
	private final ArrayList<Object>listeOnglets;

	public OngletMenu(JTabbedPane tabbedPane,ArrayList<Object>listeOnglets){
		this.listeOnglets=listeOnglets;
		this.tabbedPane=tabbedPane;
		this.setLayout(new GridLayout(1,3));
		this.add(new PanelCrit());
		this.add(new PanelListebdd(null));
		this.add(new PanelArboPreview());
		listeOnglets.add(this);
		closeButon = new JLabel(new ImageIcon(new ImageIcon("ressources/icones/fermer.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
		closeButon.addMouseListener(this);
		ic = new JLabel(new ImageIcon(new ImageIcon("ressources/icones/iconeMenu.png").getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH)));
		p1=new JPanel();
	}

	public void dessineOnglet(){
		p1.setOpaque(false);
		JLabel lbTitle=new JLabel("Menu");
		p1.add(ic);
		p1.add(lbTitle);
		p1.add(closeButon);
		this.tabbedPane.setTabComponentAt(rechercheOnglet(),p1);
		this.repaint();
		this.revalidate();
	}


	public int rechercheOnglet(){
		for(int i=0;i<listeOnglets.size();i++){
			if(listeOnglets.get(i).equals(this))
				return i;
		}
		return -1;
	}


	public class PanelCrit extends JPanel{

		private final JButton valider;
		private final JLabel sensASC;
		private final JLabel sensDESC;
		private final JLabel jt1;
		private final JLabel jt2;
		private final JLabel jt3;

		public PanelCrit(){
			this.setLayout(new FlowLayout());
			this.valider = new JButton("Valider");
			this.jt1 = new JLabel("Recherche Avanc�e: ");
			this.jt2 = new JLabel("Crit�re: ");
			this.jt3 = new JLabel("Sens: ");
			this.sensASC = new JLabel(new ImageIcon(new ImageIcon("ressources/icones/flecheHaut.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
			this.sensDESC = new JLabel(new ImageIcon(new ImageIcon("ressources/icones/flecheBas.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
			this.add(jt1);
			this.add(jt2);
			this.add(jt3);
			this.add(sensASC);
			this.add(sensDESC);
			this.add(valider);
			this.setBorder(BorderFactory.createLoweredBevelBorder());

		}

	}

	public class PanelListebdd extends JPanel{

		private final JTable bdd;
		private final OutilsBdd obdd;
		private Object[][] data;

		public PanelListebdd(TableModel model){
			obdd = new OutilsBdd("Database.db");
			data = obdd.getAllData();
			String title[] = { "Nom", "Auteur", "Derni�re Modif", "Nb ouverture", "Nb images"};
			MyTableModel mtm = new MyTableModel(data, title);
			this.bdd = new JTable(mtm);
			RowSorter<MyTableModel> sorter = new TableRowSorter<>(mtm);
			bdd.setRowSorter(sorter);
			RowFilter<MyTableModel, Object> rf = null;
		    try {
		        rf = RowFilter.regexFilter("Lapin", 0);
		    } catch (PatternSyntaxException pse) {
		        return;
		    }
		    ((DefaultRowSorter<MyTableModel, Integer>) sorter).setRowFilter(rf);

			add(new JScrollPane(bdd), BorderLayout.CENTER );
			bdd.getTableHeader().setReorderingAllowed(false);
			bdd.getTableHeader().setResizingAllowed(false);
			bdd.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        bdd.addMouseListener(new java.awt.event.MouseAdapter() {
	            public void mouseClicked(java.awt.event.MouseEvent evt) {
	                //N� de la ligne s�l�ctionn�e
	                int row = bdd.getSelectedRow();
	                //N� de ligne du tableau tri�
	                int sortedRow = bdd.convertRowIndexToModel(row);
	                Object row1 = bdd.getModel().getValueAt(sortedRow, 0);
	                Object row2 = bdd.getModel().getValueAt(sortedRow, 1);
	                Object row3 = bdd.getModel().getValueAt(sortedRow, 2);
	                Object row4 = bdd.getModel().getValueAt(sortedRow, 3);
	                Object row5 = bdd.getModel().getValueAt(sortedRow, 4);
	            }
	        });
			this.setBorder(BorderFactory.createLoweredBevelBorder());

		}
	}

	public class PanelArboPreview extends JPanel{

		private final JPanel panelTree;
		private final JPanel panelImage;
		private final JTree tree;
		public PanelArboPreview(){
			File repertoire = new File("fichiers"+File.separator);
			File[] listefichiers;
			listefichiers=repertoire.listFiles();

			tree=new JTree(listefichiers);

			this.setLayout(new BorderLayout());
			this.setBorder(BorderFactory.createLoweredBevelBorder());
			panelTree=new JPanel();

			panelTree.add(tree);
			panelImage=new JPanel();
			JPanel panelPreview=new JPanel();
			JLabel l=new JLabel();
			String path="ressources/image/800x400.png";
			l.setIcon(new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(Window.outil.getScreenSize().width/9, Window.outil.getScreenSize().width/9, Image.SCALE_SMOOTH)));
			panelPreview.add(l);
			panelPreview.setBorder(BorderFactory.createLoweredBevelBorder());
			panelImage.add(panelPreview);
			panelTree.setBorder(BorderFactory.createLoweredBevelBorder());

			this.add(panelTree,BorderLayout.CENTER);
			this.add(panelImage,BorderLayout.SOUTH);
			/* POUR LOIC GGWP */
		}
	}

	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(closeButon)){
			tabbedPane.remove(this);
			listeOnglets.remove(this);
		}

	}

	public void mouseEntered(MouseEvent arg0) {
		closeButon.setIcon(new ImageIcon(new ImageIcon("ressources/icones/fermer2.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

	}

	public void mouseExited(MouseEvent arg0) {
		closeButon.setIcon(new ImageIcon(new ImageIcon("ressources/icones/fermer.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));

	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	public class MyTableModel extends DefaultTableModel {
		 
	    MyTableModel(Object[][] rows, String[] headers) {
	        super(rows, headers);
	    }
	    
	    @Override
	    public Class getColumnClass(int column) {
	        Class returnValue;
	        if ((column >= 0) && (column < getColumnCount())) {
	            returnValue = getValueAt(0, column).getClass();
	        } else {
	            returnValue = Object.class;
	        }
	        return returnValue;
	    }
	    
	    public boolean isCellEditable(int rowIndex, int columnIndex){
	    	return false;
	    }
	}
}

package fr.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PanelEdit extends JPanel implements MouseListener, ChangeListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JButton boutonCentre;
	private final MyDeskTopPane dp;
	private final BarreVerticale bv;
	private final JColorChooser jcc;
	private final JPanel panel;

	public PanelEdit(MyDeskTopPane dp,BarreVerticale bv){
		this.bv=bv;
		this.dp=dp;
		this.setPreferredSize(new Dimension(Window.outil.getScreenSize().width/4,Window.outil.getScreenSize().height/2));
		this.setBackground(new Color(190,190,190));
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		jcc=new JColorChooser();

		panel=new JPanel();
		/*
		 * JBouton Centre
		 */
		boutonCentre=new JButton("Recentrer");
		boutonCentre.addMouseListener(this);
		panel.add(boutonCentre);
		AbstractColorChooserPanel[] oldPanels=jcc.getChooserPanels();
		for (int i=2;i<oldPanels.length;i++){
			jcc.removeChooserPanel(oldPanels[i]);
		}
		jcc.getSelectionModel().addChangeListener(this);
		this.add(panel);
		this.add(jcc);
	}
	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(boutonCentre)){
			if(bv.isBb1() || bv.isModeEdit() ){
				this.dp.getModel().zoomAuto();
			}
			else{
				this.dp.getModel().zoomAuto();
			}
			this.dp.repaint();
		}

	}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0){}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	public void stateChanged(ChangeEvent arg0) {
		if(bv.isModeEdit()){
			dp.getModel().changeColor(jcc.getColor());
			dp.getPanel().repaint();
		}
	}
}

package fr.view;

import java.awt.Graphics;

import javax.swing.JPanel;

import fr.model.Model;

public class Panneau extends JPanel {
	Model m;

	public Panneau(Model m) {
		this.m=m;

		//System.out.println(m.getFace());
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < m.getFace().size(); i++) {
			m.getFace().get(i).paint(g);
		}
	}
}

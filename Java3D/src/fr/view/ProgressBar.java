package fr.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


/**
 * @author Lo�c
 * Traitement de la barre de progression de l'�cran de chargement.
 * TIMER_PAUSE = temps pour arriver jusqu'� 100%
 * 
 */

public class ProgressBar extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JProgressBar progressBar = new JProgressBar();
	private static int PROGBAR_MAX=100;
	private static SplashScreen ss;
	private MyDeskTopPane dp;
	Thread t ;
	Thread t2;
	public static String url="ressources/image/icosa.gts";
	public ProgressBar(SplashScreen ss){
		progressBar.setMaximum(PROGBAR_MAX);
		progressBar.setBorderPainted(false);
		progressBar.setBackground(Color.lightGray);
		progressBar.setPreferredSize(new Dimension(180,18));
		progressBar.setIndeterminate(true);
		ProgressBar.ss=ss;
		this.setLayout(new GridLayout(2,1,0,4));
		this.setOpaque(false);
		JLabel jl = new JLabel(" Chargement des fichiers ...");
		jl.setForeground(Color.white);
		this.add(jl);
		this.add(progressBar);
		startProgressBar();
	}

	private void startProgressBar() {
		System.out.println("cc1");
		t = new Thread() {

			@Override
			public void run() {
				dp=new MyDeskTopPane(url);
			}
		};
		System.out.println("cc2");
		t.start();
		t2=new Thread(){
			@Override
			public void run() {
				while(t.isAlive()){
					try {
						this.sleep(2000);
					} catch (InterruptedException e) {}
				}
				ss.dispose();
				new Window(dp);
			}
		};
		System.out.println("cc3");
		t2.start();
	}
}

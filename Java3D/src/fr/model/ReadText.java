package fr.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Lo�c Cette classe a pour but de lire un fichier texte contenant le
 *         code d'un objet et de le parser puis de le ranger dans une ArrayList.
 */
public class ReadText {

	private final List<Point> pointList;
	private final List<Face> faceList;
	private Scanner scanner;
	private final String file;
	@SuppressWarnings("resource")
	public ReadText(String file) {
		pointList=new ArrayList<Point>();
		faceList=new ArrayList<Face>();
		Map<Integer,CouplePoint> map;
		this.file=file;
		try {
			scanner = new Scanner(new File(file));
			int i=0;
			int j=0;
			int nbPoints=0;
			int nbSegments=0;
			int nbFaces=0;
			String line="";
			map = new HashMap<Integer,CouplePoint>();

			// On boucle sur chaque champ detect�
			while (scanner.hasNextLine()) {
				line = scanner.nextLine();
				if (i==0){
					nbPoints=extractLine(line)[0];
					System.out.println(nbPoints);
					nbSegments=extractLine(line)[1];
					System.out.println(nbSegments);
					nbFaces=extractLine(line)[2];
					System.out.println(nbFaces);
				}

				else if (i<=nbPoints){
					pointList.add(new Point(extractLine(line)[0], extractLine(line)[1], extractLine(line)[2]));
					System.out.println(pointList.toString());
				}

				else if (i> nbPoints && i<= nbSegments){
					map.put(j, new CouplePoint(extractLine(line)[0],extractLine(line)[1]));
					j++;
				}

				else {

				}
				i++;
			}

			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("Fichier introuvable");
			e.printStackTrace();
		}

	}

	public int[] extractLine(String line){
		int [] tab = new int [3];
		int j=0;
		int k=0;
		for (int i=0;i<line.length();i++){
			if(line.charAt(i)==' '){
				tab[k]=Integer.parseInt(line.substring(j, i));
				j=i+1;
				k++;
			}
		}
		tab[k]=Integer.parseInt(line.substring(j,line.length()));

		return tab;
	}

	public String getFile(){
		return this.file;
	}

	public List<Point> getPointList(){
		return this.pointList;
	}

	public List<Face> getFaceList(){
		return this.faceList;
	}

}

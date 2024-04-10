package bptv;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import datastructure.Tree.TypeOfConnectivity;
import datastructure.set.AdjacencySet.OptimalOption;
import metric.others.CooccurrenceMatrixMetric;
import standard.sequential.BPT;
import utils.Log;
import utils.SaveBPT;

/**
 * XP on the example 2 of the paper *New algorithms for multivalued component-trees* by Nicolas Passat, Romain Perrin, Jimmy Francky Randrianasoa, Camille Kurtz, and Benoît Naegel. 
 * 1- Prepare values
 * 2- Generate image
 * 3- Build a BPTVC (BPTValue Set using Cooccurrence Matrix
 * 4- Export the BPT to DOT format
 * (!) For now, the labels are in double format !!!
 */
public class Main {
	
	// define labels according to the figure 6 of the paper
	static double a_label = 0; // blue
	static double b_label = 1; // light_blue
	static double c_label = 2; // green
	static double d_label = 3; // fuchsia
	static double e_label = 4; // gray
	static double f_label = 5; // cyan
	static double g_label = 6; // red - this label exists but not necessarily in the image
	static double h_label = 7; // orange
	static double i_label = 8; // yellow

	public static void main(String[] args) {
		
		/* 1- Prepare values
		 * ************************************************************************************/		
		System.out.println("## Step-1: Prepare values ## ");
		int width = 5;
		int height = 5;
		int nbValues = 9; // number of possible values of each point
		
		ArrayList<Double> valueSet = new ArrayList<Double>(nbValues);
		valueSet.add(a_label);
		valueSet.add(b_label);
		valueSet.add(c_label);
		valueSet.add(d_label);
		valueSet.add(e_label);
		valueSet.add(f_label);
		valueSet.add(g_label);
		valueSet.add(h_label);
		valueSet.add(i_label);		
		
		System.out.println("Values preparation: SUCCESS \n");

		/* 2- Generate Image
		 * ************************************************************************************/		
		System.out.println("## Step-2: Generate Image ##");
		/* Prepare the image of the paper */
		BufferedImage img = Main.generateImgExample(width, height, valueSet);
		System.out.println("Image Generation: SUCCESS  \n");

		/* 3- Create a BPTValue Set using a Cooccurrence Matrix
		 * ************************************************************************************/
		System.out.println("## Step-3: BPT creation from a value set (using cooccurrence Matrix) ##");
		Log.show = true;
		
		/* Prepare the tree */
		BPT<Double> bptvc = new BPT<Double>(valueSet);
		bptvc.setOptimalOption(OptimalOption.MAXIMUM);
		
		/* Let all the points of the RAG be connected to all the others */
		bptvc.setConnectivity(TypeOfConnectivity.ALL);

		/* Prepare the metric: cooccurrence matrix metric */
		int connexity = 4;
		CooccurrenceMatrixMetric<Double> metric = new CooccurrenceMatrixMetric<Double>(valueSet, img, connexity); 
		bptvc.setMetric(metric);
						
		/* Let the tree grow */
		bptvc.grow();
		if(bptvc.hasEnded()) {
			
			System.out.println("[Test] BPTVS Creation succeded with the "+ bptvc.getMetric().type +" metric!");
		}
		System.out.println("BPTVC Creation: SUCCESS \n");

		/* 4- Export bptvc to DOT format
		 * ************************************************************************************/
		System.out.println("## Step-4: Export to DOT format ##");
		bptvc.setDirectory(".");
		bptvc.setName("bpt_labels_fig6.dot");
		SaveBPT.toDOT(bptvc);
		System.out.println("BPTVC exportation: "+ bptvc.getDirectory() + File.separator + bptvc.getName());
		System.out.println("BPTVC exportation: SUCCESS \n");
	}

	/**
	 * Just generate an image for the test.
	 * @return a specific generated image.
	 */
	private static BufferedImage generateImgExample(int width, int height, ArrayList<Double> values) {

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);		
		
		/* 1st column */
		img.setRGB(0, 0, genColor(b_label).getRGB());
		img.setRGB(0, 1, genColor(b_label).getRGB());
		img.setRGB(0, 2, genColor(a_label).getRGB());
		img.setRGB(0, 3, genColor(e_label).getRGB());
		img.setRGB(0, 4, genColor(e_label).getRGB());
		
		/* 2nd column */
		img.setRGB(1, 0, genColor(c_label).getRGB());
		img.setRGB(1, 1, genColor(i_label).getRGB());
		img.setRGB(1, 2, genColor(i_label).getRGB());
		img.setRGB(1, 3, genColor(i_label).getRGB());
		img.setRGB(1, 4, genColor(c_label).getRGB());
		
		/* 3rd column */
		img.setRGB(2, 0, genColor(d_label).getRGB());
		img.setRGB(2, 1, genColor(d_label).getRGB());
		img.setRGB(2, 2, genColor(a_label).getRGB());
		img.setRGB(2, 3, genColor(a_label).getRGB());
		img.setRGB(2, 4, genColor(c_label).getRGB());

		/* 4th column */
		img.setRGB(3, 0, genColor(f_label).getRGB());
		img.setRGB(3, 1, genColor(f_label).getRGB());
		img.setRGB(3, 2, genColor(e_label).getRGB());
		img.setRGB(3, 3, genColor(e_label).getRGB());
		img.setRGB(3, 4, genColor(a_label).getRGB());

		/* 5th column */
		img.setRGB(4, 0, genColor(i_label).getRGB());
		img.setRGB(4, 1, genColor(i_label).getRGB());
		img.setRGB(4, 2, genColor(f_label).getRGB());
		img.setRGB(4, 3, genColor(f_label).getRGB());
		img.setRGB(4, 4, genColor(h_label).getRGB());
		
		return img;
	}
	
	/**
	 * Just transform a gray scale value v into an RGB Color object.
	 * @param v gray scale value.
	 * @return a Color object.
	 */
	private static Color genColor(double v) {
		return  new Color((int) v, (int) v, (int) v);
	}
}


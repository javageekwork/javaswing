package com.java.sample.graph.linechart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

public class MainWindow{
	
	public static void main(String[] args) {
		JFrame mainFrame = new JFrame("Sample Line Chart 2D");
		mainFrame.setSize(700, 500);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		List<Double> xPoints = new ArrayList<Double>();
		xPoints.add(1d);
		xPoints.add(2d);
		xPoints.add(3d);
		xPoints.add(4.5d);
		xPoints.add(5d);
		
		List<Double> yPoints = new ArrayList<Double>();
		yPoints.add(1d);
		yPoints.add(2.5d);
		yPoints.add(3d);
		yPoints.add(4d);
		yPoints.add(5.6d);
		
		String graphLable = "Test Performance Line chart";
		
		LineChart2D panel = new LineChart2D(xPoints, yPoints, graphLable);
		mainFrame.add(panel);
		mainFrame.setVisible(true);
	}

}

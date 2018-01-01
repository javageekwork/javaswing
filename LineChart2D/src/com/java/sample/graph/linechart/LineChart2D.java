package com.java.sample.graph.linechart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class LineChart2D extends JPanel{

	//creating default variables and its value
	private static final int PREF_W = 300;
	private static final int PREF_H = 200;
	private static final int BORDER_GAP = 30;
	private static final int Y_HATCH_CNT = 10;
	private static final int GRAPH_POINT_WIDTH = 12;
	private static final Color GRAPH_COLOR = Color.green;
	private static final Color GRAPH_POINT_COLOR = new Color(150, 50, 50, 180);
	private static final Stroke GRAPH_STROKE = new BasicStroke(3f);

	//Line chart specific values
	private String graphLable = "Line Chart Demo";
	private List<Double> xPoints, yPoints;

	//variables to calculate for chart
	private int noOfPoints ;
	private Double xMax, yMax;

	//creating graph point list 
	List<Point> graphPoints = new ArrayList<Point>();

	public LineChart2D(List<Double> xPoints, List<Double> yPoints, String graphLable) {
		this.xPoints = xPoints;
		this.yPoints = yPoints;
		this.graphLable = graphLable;

		//number of points in graph
		noOfPoints = yPoints.size();

		//Max value on x-axis
		xMax = 0d;
		for(Double dd : yPoints){
			if(dd>xMax)
				xMax = dd;
		}
		//Max value on x-axis
		yMax = 0d;
		for(Double dd : yPoints){
			if(dd>yMax)
				yMax = dd;
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}

	@Override
	protected void paintComponent(Graphics g) {
		//calling graph paint method
		paintLineChart2D(g);
	}

	private void paintLineChart2D(Graphics g){
		//creating 2D graphics object
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		//show graph lable..
		g2.drawString(graphLable, ((PREF_W/2)-20), 12);

		// create x and y axes 
		g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, BORDER_GAP, BORDER_GAP);
		g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);

		//calculate total width and height of x and y axis
		Double yAxisWidth = new Double(( getHeight() - (BORDER_GAP * 2 ) ) );
		Double  xAxisWidth =  new Double(( getWidth() - (BORDER_GAP * 2 ) ) );

		// calculating factor for points relative to graph size
		Double intoXFactor = 1d ,intoYFactor = 1d ;
		//calculating y factor
		boolean xIsMultiply = true; 
		if(xAxisWidth > xMax){
			intoXFactor = xAxisWidth/xMax;
			xIsMultiply = true; 
		}else {
			intoXFactor = xMax/xAxisWidth;
			xIsMultiply = false; 
		}//End of: calculating factor for points relative to graph size

		//calculating y factor
		boolean yIsMultiply = true; 
		if(yAxisWidth > yMax){
			intoYFactor = yAxisWidth/yMax;
			yIsMultiply = true; 
		}else {
			intoYFactor = yMax/yAxisWidth;
			yIsMultiply = false; 
		}//End of: calculating factor for points relative to graph size

		//adding graph pints to point list
		graphPoints = new ArrayList<Point>();		
		int count =0 ;
		for (int i = 0; i < noOfPoints; i++) {
			//calculating x cordinate for each point
			Double x1 =  BORDER_GAP + (xPoints.get(count)*intoXFactor);
			if(!xIsMultiply)
				x1 =  BORDER_GAP + (xPoints.get(count)/intoXFactor);

			//calculating y cordinate for each point
			Double y1 =  BORDER_GAP + (yPoints.get(count)*intoYFactor);
			if(!yIsMultiply)
				y1 =  BORDER_GAP + (yPoints.get(count)/intoYFactor);

			//x1 = (xAxisWidth - x1)+BORDER_GAP;

			//adding to list
			graphPoints.add(new Point(x1.intValue(), y1.intValue()));
			count++;
		}

		// create hatch marks for y axis. 
		for (int i = 0; i < Y_HATCH_CNT; i++) {
			int x0 = BORDER_GAP;
			int x1 = GRAPH_POINT_WIDTH + BORDER_GAP;
			int y0 = getHeight() - (((i + 1) * (getHeight() - BORDER_GAP * 2)) / Y_HATCH_CNT + BORDER_GAP);
			int y1 = y0;
			g2.drawLine(x0, y0, x1, y1);
		}

		//creating stroke for graph lines
		Stroke oldStroke = g2.getStroke();
		g2.setColor(GRAPH_COLOR);
		g2.setStroke(GRAPH_STROKE);
		for (int i = 0; i < graphPoints.size() - 1; i++) {
			int x1 = graphPoints.get(i).x;
			int y1 = graphPoints.get(i).y;
			int x2 = graphPoints.get(i + 1).x;
			int y2 = graphPoints.get(i + 1).y;
			g2.drawLine(x1, y1, x2, y2);         
		}

		//setting stroke
		g2.setStroke(oldStroke);      
		g2.setColor(GRAPH_POINT_COLOR);
		for (int i = 0; i < graphPoints.size(); i++) {
			int x = graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
			int y = graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;;
			int ovalW = GRAPH_POINT_WIDTH;
			int ovalH = GRAPH_POINT_WIDTH;
			g2.fillOval(x, y, ovalW, ovalH);
		}
	}

}

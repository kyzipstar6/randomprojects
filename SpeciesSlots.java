package speciesslots;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SpeciesSlots extends Application {
	SimpleIntegerProperty score = new SimpleIntegerProperty(0);
	@Override
	public void start(Stage stg) throws Exception {
		Pane tb = new Pane();
		int w= 1740, h=720;
		tb.setPrefSize(w, h);
		tb.setStyle("-fx-background-color: linear-gradient(gold,white,gold);");
		int slotsperline= 5;
		HBox slotlinesBox = new HBox(5);
		slotlinesBox.setStyle("-fx-background-color: white;");
		for(int i =0; i<slotsperline;i++) {
			Pane p = new Pane();
			p.setPrefSize(22, 22);
			p.setStyle("-fx-background-color: linear-gradient(brown, red);");
			slotlinesBox.getChildren().add(p);
		}
		Random rd = new Random();
		Button draw = new Button("Draw");draw.setStyle("-fx-text-fill: white; -fx-background-color: red; -fx-font-size:16 px;");
		draw.setShape(new Circle(22));
		HBox slotlinesHBox = new HBox(2,slotlinesBox, draw);
		slotlinesHBox.setLayoutX(w/3);slotlinesHBox.setLayoutY(h/3);

		tb.getChildren().add(slotlinesHBox);
		String [] imageuris = "fr.png,moose.png,reddeer.png,kangaroo.jpg,roedeer.jpg".split(",");
		Label scoreLabel = new Label(score+" points");
		scoreLabel.setStyle("-fx-text-fill: red; -fx-font-size:23 px; -fx-font-family: 'Comic Sans MS';");
		 scoreLabel.setLayoutX(w-400); scoreLabel.setLayoutY(50);
			tb.getChildren().add(scoreLabel);
			
		draw.setOnAction(ev->{
			List<String> slotsvalue = new ArrayList();
			slotsvalue.clear();

			slotlinesBox.getChildren().clear();
			for(int i =0; i<slotsperline;i++) {
				
				Pane p = new Pane();
				int nextrd = rd.nextInt(imageuris.length);
				String bguri= "file:files/imgs/"+imageuris[nextrd];
				SlotToken sT = new SlotToken(imageuris[nextrd]);
				
				slotsvalue.add(bguri);
				p.setPrefSize(22, 22);
				p.setBackground(background(new Image(bguri)));
				slotlinesBox.getChildren().add(p);
				
			}
			
			for(int i = 0; i<slotsvalue.size()-2;i++) {
				System.out.println(slotsvalue.get(i)+slotsvalue.get(i+1));
				if(slotsvalue.get(i).contains(slotsvalue.get(i+1))) {int s=score.get();score.set(s+1);
				Platform.runLater(()->{ System.out.println("Changed score to "+score);int nscore = score.get();scoreLabel.setText(nscore+" points");});}
			}
			System.out.println("Finished draw process");
		});
		Pane thing = draw("deer");
		thing.setLayoutX(250);thing.setLayoutY(150);
		thing.setScaleX(2.0);		thing.setScaleY(2.0);
		candleChart();
		tb.getChildren().add(thing);
		Scene sc = new Scene(tb);
		stg.setScene(sc);
		stg.show();

	}
	record SlotToken(String value){
	
	}
	Background background(Image img) {
		BackgroundSize size = new BackgroundSize(1, 1, true, true, false, false);
		Background bck = new Background(new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size));
		return bck;
	}
	public Pane draw(String arg) {
		Pane p = new Pane();
		Random rd = new Random();
		switch(arg.toLowerCase()) {
		case("moon")->{
			Circle c = new Circle(250);
			p.setShape(c);
			p.setPrefSize(c.getRadius(),c.getRadius());
			p.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 90%, #FFFFFF, #333);");
			for (int i =0; i<21;i++) {
				Pane hole = new Pane();
			Circle holerad = new Circle(10);
			hole.setPrefSize(holerad.getRadius(), holerad.getRadius());
			hole.setShape(holerad);
			hole.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 60%, #333333, #000000);");
			hole.setLayoutX(rd.nextDouble(90, c.getRadius()-90));
			hole.setLayoutY(rd.nextDouble(90, c.getRadius()-90));
			p.getChildren().add(hole);
			}
			
			
		}
		case("chart")->{
			
				p.getChildren().add(candleChart());
			
			
		}
		case("deer")->{
			Polygon ply = new Polygon();
			int [] xpts= {2,3,5,7,8,9,11,13,14},ypts= {2,7,9,28,29,30,29,28,9,7,2};
			for(int i =0; i<xpts.length;i++) {
				ply.getPoints().addAll((double)xpts[i],(double)ypts[i]);
			}
			int w=findMax(xpts), h=findMax(ypts);
			p.setPrefSize(w, h);
			p.setStyle("-fx-background-color: linear-gradient(to right, #000000, #7F3505,white,#7F3505, #000000);");
			p.setShape(ply);
			Pane [] eyes = {new Pane(),new Pane()};
			for(int i =0; i<eyes.length;i++) {
				Pane e = eyes[i];
				e.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 82%, #000000, #666666);");
				e.setLayoutX(7+i*5);
				e.setLayoutY(10);
				Circle ball = new Circle(5);
				e.setPrefSize(ball.getRadius(), ball.getRadius());
				e.setShape(ball);
				p.getChildren().add(e);
			}
			
		}
		default->{}
		}
		return p;
	}
	private Pane candleChart() {
		Stage stg = new Stage();
		Pane p = new Pane();
		p.setStyle("-fx-background-color: radial-gradient(center 44% 34%, radius 114%, #1A0068, #000000);");
		p.setPrefSize(500, 300);		
		double hratio = p.getPrefHeight(),wratio= p.getPrefWidth();

		double [] ovals = {322.5,325.5,321.8,335.8,339.8,335.8,341.8,522.8,658.4,550.6,421.5};
		double [] maxvals = {350.5,332.8,331.8,341.8,341.8,514.4,680.6,435.5,399.9};
		double [] minvals = {320.5,319.8,317.8,330.8,329.8,402.6,522.5,389.6,366.5};
		for(int i=0; i<maxvals.length;i++) {
		double ub = findMax(maxvals)+2.0;
		double lb = findMin(minvals)-2.0;
		double scale = 1;
		double ph =(maxvals[i]-minvals[i])/scale;
		double h = hratio/ph, w=5.0; 
		Rectangle candle = new Rectangle();
		candle.setHeight(h);
		candle.setWidth(w);
		candle.setY(0);
		Pane lpane = new Pane();
		boolean steady = ovals[i+1]==ovals[i];
		boolean rising = ovals[i+1]>ovals[i]; 
		lpane.setPrefSize(w, h);
		lpane.setShape(candle);
		String steadystyle = "-fx-background-color: linear-gradient(to bottom, #4D8080, #99CCCC);",fallingstyle = "-fx-background-color: linear-gradient(to bottom, #FF0000, #FF8080);",
				risingstyle = "-fx-background-color: linear-gradient(to bottom, #00FF00, #008000);";
		String barstyle = (steady)? steadystyle : (rising)? risingstyle : fallingstyle;
		lpane.setStyle(barstyle);
		double incr = i*ovals.length;
		double lx = incr+(w*i)+((wratio*i)/ovals.length);
		double lybar = ((ub-(double)maxvals[i])/scale);
		double ly = (lybar/hratio)*hratio;

		System.out.println(String.join(",", incr+"",lx+"",ly+"",ub+"",lb+"",h+"",w+""));
		lpane.setLayoutX(lx);lpane.setLayoutY(ly);
		p.getChildren().add(lpane);
		}
		stg.setScene(new Scene(p));stg.show();

		return p;
	}
	private int findMin(int[] minvals) {
		int max = Integer.MAX_VALUE;
		for(int s : minvals)if((int)s<max)max=(int)s;
		
		return max;
	}
	private int findMax(int[] maxvals) {
		int max = Integer.MIN_VALUE;
		for(int s : maxvals)if(s>max)max=s;
		
		return max;
	}
	private double findMin(double[] minvals) {
		double max = Double.MAX_VALUE;
		for(double s : minvals)if((double)s<max)max=(double)s;
		
		return max;
	}
	private double findMax(double[] maxvals) {
		double max = Double.MIN_VALUE;
		for(double s : maxvals)if((double)s>max)max=(double)s;
		
		return max;
	}
	public static void main(String [] args) {
		launch(args);
	}
}

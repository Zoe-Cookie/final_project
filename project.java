package project;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class project extends Application{
	@Override
	public void start(Stage primaryStage) {
		//create a pane
		Pane pane=new Pane();
		
		//create circle
		Circle c=new Circle(100,50,25);
		c.setStroke(Color.BLACK);
		c.setFill(Color.WHITE);
		c.setCenterX(c.getCenterX()+10);
		
		//event
		c.setOnKeyPressed(e->{
			switch(e.getCode()) {
			case UP:
				c.setCenterY(c.getCenterY()-10);
				break;
			case DOWN:
				c.setCenterY(c.getCenterY()+10);
				break;
			case RIGHT:
				//讓圓不超出右側螢幕
				if(c.getCenterX()<=475) {
					c.setCenterX(c.getCenterX()+10);
				}
				break;
			case LEFT:
				//讓圓不超出左側螢幕
				if(c.getCenterX()>=25) {
					c.setCenterX(c.getCenterX()-10);
				}
				break;
			default:
				break;
			}
			
		});
		
		//Rectangle r=new Rectangle((int)(Math.random()*420),(int)(Math.random()*400)+80,60,10);//(80,75,60,10)
		//create n rectangles
		int n=10;
		Rectangle [] rs=new Rectangle[n+1];
		Rectangle start=new Rectangle(80,75,60,10);
		start.setFill(Color.BLACK);
		start.setStroke(Color.BLACK);
		rs[0]=start;
		pane.getChildren().add(start);
		for(int i=1;i<n+1;i++) {
			Rectangle r=new Rectangle((int)(Math.random()*420),(int)(Math.random()*400)+80,60,10);
			r.setFill(Color.BLACK);
			r.setStroke(Color.BLACK);
			rs[i]=r;
			pane.getChildren().add(r);
		}
		//add to pane
		pane.getChildren().add(c);
		
		//create a handler
		EventHandler <ActionEvent> eventhandler= (e ->{
			boolean touchGround=false;
			for(int i=0;i<n+1;i++) {
			//球碰到地板
				if((c.getCenterX()>=rs[i].getX() && c.getCenterX()<=rs[i].getX()+rs[i].getWidth())
					&& c.getCenterY()>=rs[i].getY()-c.getRadius() && c.getCenterY()<=rs[i].getY()-c.getRadius()+10) {
					touchGround=true;
				}
			}
			if(!touchGround) {
				c.setCenterY(c.getCenterY()+1);
			}
		});
		//Time line
		Timeline animation=new Timeline(new
				KeyFrame(Duration.millis(10),eventhandler));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
		//add pane to scene and stage
		Scene scene=new Scene(pane,500,500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Project");
		primaryStage.show();
		
		c.requestFocus();
	}

	public static void main(String[] args) {
		launch(args);
	}

}


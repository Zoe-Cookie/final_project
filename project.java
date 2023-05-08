<<<<<<< HEAD
package project;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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
		
		//create n rectangles
		int n=10;
		Rectangle [] rs=new Rectangle[n+1];
		Rectangle start=new Rectangle(80,75,60,10);
		start.setFill(Color.BLACK);
		start.setStroke(Color.BLACK);
		rs[0]=start;
		pane.getChildren().add(start);
		for(int i=1;i<n+1;i++) {
			Rectangle r=new Rectangle((int)(Math.random()*420),(int)(Math.random()*500),60,10);//0<=x<=420, 0<=y<=500
			r.setFill(Color.BLACK);
			r.setStroke(Color.BLACK);
			rs[i]=r;
			pane.getChildren().add(r);
		}
		for(int i=0;i<n+1;i++) {
			moveRectangle(rs[i],c);
		}
		//add to pane
		pane.getChildren().add(c);
	
		//create a handler 讓球碰到板子不會掉下去
		EventHandler <ActionEvent> eventhandler= (e ->{
			boolean touchGround=false;
			int index=0;
			for(int i=0;i<n+1;i++) {
			//球碰到地板
				if((c.getCenterX()>=rs[i].getX() && c.getCenterX()<=rs[i].getX()+rs[i].getWidth())
					&& c.getCenterY()>=rs[i].getY()-c.getRadius() && c.getCenterY()<=rs[i].getY()-c.getRadius()+1) {
					touchGround=true;
					index=i;
				}
			}
			if(!touchGround) {
				c.setCenterY(c.getCenterY()+0.1);
			}
			else {
				c.setCenterY(rs[index].getY() - c.getRadius());
			}
		});
		
		
		//Time line
		Timeline animation=new Timeline(new
				KeyFrame(Duration.millis(1),eventhandler));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
		
		//add pane to scene and stage
		Scene scene=new Scene(pane,500,500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Project");
		primaryStage.show();
		c.requestFocus();
		
		
	}
	//讓板子往上動的函式
	public static void moveRectangle(Rectangle r, Circle c) {
		
		EventHandler <ActionEvent> handler= (e ->{
			boolean moveWithRectangle=false;
			if (c.getCenterX()>=r.getX() && c.getCenterX()<=r.getX()+r.getWidth()
				&& c.getCenterY()>=r.getY()-c.getRadius() && c.getCenterY()<=r.getY()-c.getRadius()+1) {
				moveWithRectangle = true;
	        }
	        
	        if (moveWithRectangle) {
	            //r.setY(c.getCenterY() - 25);
	        	r.setY(r.getY() - 10);
	        	c.setCenterY(c.getCenterY()-10);
	        } else if (r.getY() >= 0) {
	        	r.setY(r.getY() - 10);
	        } else {
	            r.setX((int) (Math.random() * 420));
	            r.setY(500);
	            moveWithRectangle = false;
	        }
			/*if(r.getY()>=0) {
				r.setY(r.getY()-10);
			}
			else {
				r.setX((int)(Math.random()*420));
				r.setY(500);
			}*/
		});
		
		Timeline animation=new Timeline(new
				KeyFrame(Duration.millis(200),handler));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
	}
	public static void circleMoveUP(Rectangle r, Circle c) {
		Line line=new Line(c.getCenterX(),r.getY()-25,c.getCenterX(),0);
		PathTransition pt=new PathTransition();
		pt.setDuration(Duration.millis(1000));
		pt.setPath(line);
		pt.setNode(c);
		pt.play();
	}
	public static void main(String[] args) {
		launch(args);
	}

}




=======
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

>>>>>>> 976fce5f5899291016feff55303e0b555aeb105e

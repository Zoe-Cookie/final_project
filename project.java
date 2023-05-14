package project;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
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
        
        //天花板尖刺和尖刺板子(還改了整體視窗x變成400、板子減少一個放成尖刺、板子的y間距
		Image Ceiling = new Image("/project/image/Ceiling.png");
		Image Nails = new Image("/project/image/Nails.png");
		ImageView iv_ceiling = new ImageView(Ceiling);
		ImagePattern ip_nails = new ImagePattern(Nails);
		pane.getChildren().add(iv_ceiling);
		
		//create circle
		Circle c=new Circle(100,50,10);
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
				if(c.getCenterX()<=375) {
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
		Rectangle start=new Rectangle(80,500d/11,60,10);
		start.setFill(Color.BLACK);
		start.setStroke(Color.BLACK);
		rs[0]=start;
		pane.getChildren().add(start);
		for(int i=1;i<n;i++) {
			Rectangle r=new Rectangle((int)(Math.random()*320),500d/11*(i+1),60,10);//0<=x<=420, 0<=y<=500
			r.setFill(Color.BLACK);
			r.setStroke(Color.BLACK);
			rs[i]=r;
			pane.getChildren().add(r);
		}
        Rectangle danger=new Rectangle((int)Math.random()*320,500,60,10);
		danger.setFill(ip_nails);
		rs[10] = danger;
		pane.getChildren().add(danger);
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
			
			if(c.getCenterY()>=525 || c.getCenterY()<=c.getRadius() ) {//掉到最下面或碰到頂部失敗
				//gameOver(pane,c);
			}
			else if(!touchGround) {
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
		Scene scene=new Scene(pane,400,500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Project");
		primaryStage.show();
		c.requestFocus();
		
		
	}
	//讓板子往上動的函式
	public static void moveRectangle(Rectangle r, Circle c) {
		
		EventHandler <ActionEvent> handler= (e ->{
			boolean moveWithRectangle=false;
			Floor f=new Floor();
			if (c.getCenterX()>=r.getX() && c.getCenterX()<=r.getX()+r.getWidth()
				&& c.getCenterY()>=r.getY()-c.getRadius() && c.getCenterY()<=r.getY()-c.getRadius()+1) {
				moveWithRectangle = true;
	        }
	        
			if(c.getCenterY()>=525 || c.getCenterY()<=c.getRadius() ) {
				//do nothing 圓掉到最下面或碰到頂部而停止
			}else if (moveWithRectangle) {
	            //r.setY(c.getCenterY() - 25);
	        	r.setY(r.getY() - 1);
	        	c.setCenterY(c.getCenterY()-1);
	        } else if (r.getY() >= 0) {
	        	r.setY(r.getY() - 1);
	        } else {
	            r.setX((int) (Math.random() * 420));
	            r.setY(500);
	            moveWithRectangle = false;
	            f.add_time();
	        }
			if(f.getAppearTime()==10){
				
			}
			
		});
		
		Timeline animation=new Timeline(new
				KeyFrame(Duration.millis(20),handler));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
	}
	//目前沒用到
	public static void circleMoveUP(Rectangle r, Circle c) {
		Line line=new Line(c.getCenterX(),r.getY()-25,c.getCenterX(),0);
		PathTransition pt=new PathTransition();
		pt.setDuration(Duration.millis(1000));
		pt.setPath(line);
		pt.setNode(c);
		pt.play();
	}
	//遊戲結束
	public static void gameOver(Pane pane,Circle c) {
		//Circle c= new Circle(250,250,25);
		Rectangle r=new Rectangle(150,150,200,200);
		r.setFill(Color.BLUE);
		r.setStroke(Color.BLACK);
		//pane.getChildren().add(r);
		Button bt=new Button("play again");
		pane.getChildren().add(bt);
		bt.setOnMouseClicked(e->{
			pane.getChildren().remove(r);
			c.setCenterX(100);
			c.setCenterY(50);
			pane.getChildren().remove(bt);
		});
		bt.requestFocus();
		//pane.getChildren().add(c);
	}
	//重新開始
	public static void restart(Pane pane) {
		
		
	}
	public static void main(String[] args) {
		launch(args);
	}

}

//製作角色或許可以繼承圓形?

//繼承矩形
class Floor extends Rectangle{
	private int appear_time=0;
	
	public void add_time() {
		appear_time++;
	}
	public int getAppearTime() {
		return appear_time;
	}
}

/////////
//
/////


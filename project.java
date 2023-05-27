package project;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class project extends Application{
	
	@Override
	
	public void start(Stage primaryStage) {
		//create a pane
		Pane pane=new Pane();
		//背景
		Image background = new Image("/project/image/blueback.jpg");
		ImageView iv_background = new ImageView(background);
		iv_background.setFitWidth(400);
		iv_background.setFitHeight(500);
		pane.getChildren().add(iv_background);
		
        //天花板尖刺和尖刺板子(還改了整體視窗x變成400、板子減少一個放成尖刺、板子的y間距
		Image Ceiling = new Image("/project/image/Ceiling.png");
		Image Nails = new Image("/project/image/Nails.png");
		ImageView iv_ceiling = new ImageView(Ceiling);
		ImagePattern ip_nails = new ImagePattern(Nails);
		pane.getChildren().add(iv_ceiling);

		//松鼠角色(有些在底下全域)
		ImageView iv_squirrel = new ImageView(role.iv_squirrel_stop.getImage()); 
		iv_squirrel.setFitWidth(40);
		iv_squirrel.setFitHeight(40);
		iv_squirrel.setLayoutX(90);
		iv_squirrel.setLayoutY(2000d/11-30);//一開始在第四快板子上
		
		
		//遊戲資訊視窗(包括標題，層數和重新開始按鈕，設定每11塊板子一層)
		Rectangle r_info=new Rectangle(400,0,200,500);
		r_info.setFill(Color.ALICEBLUE);
		Rectangle r_button=new Rectangle(470,400,60,30);//重新開始紐
		r_button.setStroke(Color.GREEN);
		r_button.setFill(Color.WHITE);
		Rectangle r_start=new Rectangle(470,360,60,30);//開始紐
		r_start.setStroke(Color.BLUE);
		r_start.setFill(Color.WHITE);
		Text title=new Text(430,50,"松鼠下樓梯");
		// 設定字體(可以設定原本就有的字體，但沒辦法設定成網路上下載的字體)
		//String fontPath = "/resources/fonts/setofont.ttf";
        //Font font = Font.loadFont(getClass().getResourceAsStream(fontPath), 25);
		//title.setFont(Font.loadFont("/resources/fonts/SetoFont.ttf", 25));
		//Font.loadFont("/resources/fonts/STXINGKA.TTF", 25);
		//title.setStyle("-fx-font-family: SetoFont");
		title.setFont(Font.font(25));
		//title.setFont(Font.font("STXingkai",25));
		//title.setFont(font);
		Text rule=new Text(430,90,"        遊戲說明\n\n頭上尖刺步步逼近\n操縱松鼠往下逃吧\n\n左鍵->角色往左移動\n右鍵->角色往右移動"
				+ "\n\n小心下面來的尖刺\n碰到尖刺及墜落地\n底即遊戲結束\n\n你能下到第幾層呢?");
		rule.setFont(Font.font(15));
		Text t_layer=new Text(460,348,"現在層數: 1");
		t_layer.setFont(Font.font(15));
		Text t_start=new Text(488,378,"開始");
		Text t_restart=new Text(478,418,"重新開始");
		pane.getChildren().addAll(r_info,r_start,title,t_layer,t_start,rule);
		//讓層數資料更新
		Timeline changeinfo=new Timeline(new
				KeyFrame(Duration.millis(100),e->{t_layer.setText("現在層數: "+((Layer.layer/11)+1));}));
		changeinfo.setCycleCount(Timeline.INDEFINITE);
		changeinfo.play();
		
		//create n rectangles
		int n=10;
		Rectangle [] rs=new Rectangle[n+1];
		Rectangle start=new Rectangle(80,500d/11,60,10);//1250d/11 210
		Image Normal = new Image("/project/image/Normal.png");
		ImagePattern ip_normal = new ImagePattern(Normal);
		start.setFill(ip_normal);
		rs[0]=start;
		pane.getChildren().add(start);
		for(int i=1;i<n;i++) {
			Rectangle r=new Rectangle((int)(Math.random()*320),500d/11*(i+1),60,10);//0<=x<=420, 0<=y<=500
			if(i==3) {//一開始圓在的固定板子
				r.setX(80);
			}
			r.setFill(ip_normal);
			rs[i]=r;
			pane.getChildren().add(r);
		}
        Rectangle danger=new Rectangle((int)Math.random()*320,500,60,10);
		danger.setFill(ip_nails);
		rs[10] = danger;
		pane.getChildren().add(danger);
		/*在3,4,5層數會把指定板子變尖刺*/
		EventHandler <ActionEvent> thirdhandler= (e ->{
			//int i = (int)(Math.random()*10);
			if(Layer.layer>=44 && rs[3].getY()==500) {
				rs[3].setFill(ip_nails);
			}
			else if(Layer.layer>=33 && rs[7].getY()==500) {
				rs[7].setFill(ip_nails);
			}
			else if(Layer.layer>=22 && rs[1].getY()==500) {
				rs[1].setFill(ip_nails);
        	}
		});
		
		Timeline difficulties=new Timeline(new KeyFrame(Duration.millis(1),thirdhandler));
		difficulties.setCycleCount(Timeline.INDEFINITE);
		difficulties.play();
		
		t_start.setOnMouseClicked(e->{//按到start開始遊戲
			for(int i=0;i<n+1;i++) {  //連續按會加快速度
				moveRectangle(rs[i],iv_squirrel);
			}
			registerKeyboardEventHandler(iv_squirrel);//上下左右改成函數
			iv_squirrel.requestFocus();
			pane.getChildren().remove(t_start);
			pane.getChildren().remove(r_start);
			pane.getChildren().addAll(r_button,t_restart);
		});
		
		t_restart.setOnMouseClicked(e->{//按到restart重新開始
			pane.getChildren().add(iv_squirrel);	//遊戲進行中不能按restart
			iv_squirrel.setLayoutX(90);			
			iv_squirrel.setLayoutY(2000d/11-30);
			for(int i=0;i<n+1;i++) {
				rs[i].setY(500d/11*(i+1));
				if(i==10) {
					rs[i].setFill(ip_nails);
				}
				else {
					rs[i].setFill(ip_normal);
				}
				if(i==3) {
					rs[i].setX(80);
				}
				else {
					rs[i].setX((int)(Math.random()*320));
				}
			}
			Layer.layer=0;//重設layer為
			registerKeyboardEventHandler(iv_squirrel);
			iv_squirrel.requestFocus();
			iv_squirrel.setImage(role.iv_squirrel_stop.getImage());

		});
		
		//add to pane
		pane.getChildren().add(iv_squirrel);
	
		//create a handler 讓球碰到板子不會掉下去
		EventHandler <ActionEvent> eventhandler= (e ->{
			boolean touchGround=false;
			int index=0;
			for(int i=0;i<n+1;i++) {
			//球碰到地板
				if((iv_squirrel.getLayoutX()>=rs[i].getX()-20 && iv_squirrel.getLayoutX()<=rs[i].getX()+rs[i].getWidth()-20)
					&& iv_squirrel.getLayoutY()>=rs[i].getY()-30 && iv_squirrel.getLayoutY()<=rs[i].getY()-30+1) {
					touchGround=true;
					index=i;
				}
			}
			
			if(iv_squirrel.getLayoutY()>=525 || iv_squirrel.getLayoutY()<=10 ) {//掉到最下面或碰到頂部失敗
				pane.getChildren().remove(iv_squirrel);
//				gameOver(pane,c);
			}
			else if(touchGround) {//碰到尖刺球不見但板子仍會繼續移動直到碰到天花板
				if(rs[10].getFill().equals(ip_nails) && index==10 ) {
					pane.getChildren().remove(iv_squirrel);
					iv_squirrel.setLayoutY(0);
				}
				else if(rs[1].getFill().equals(ip_nails) && index==1 ) {
					pane.getChildren().remove(iv_squirrel);
					iv_squirrel.setLayoutY(0);
					//Heart.number=Heart.number-1;
					//System.out.println(Heart.number);
				}
				else if(rs[7].getFill().equals(ip_nails) && index==7 ) {
					pane.getChildren().remove(iv_squirrel);
					iv_squirrel.setLayoutY(0);
					//Heart.number=Heart.number-1;
					//System.out.println(Heart.number);
				}
				else if(rs[3].getFill().equals(ip_nails) && index==3 ) {
					pane.getChildren().remove(iv_squirrel);
					iv_squirrel.setLayoutY(0);
					//Heart.number=Heart.number-1;
					//System.out.println(Heart.number);
	        	}
				
			}
			else if(!touchGround) {
				iv_squirrel.setLayoutY(iv_squirrel.getLayoutY()+0.1);
			}
			else {
				iv_squirrel.setLayoutY(rs[index].getY() - 30);
			}
			
			
		});
		
		
		//Time line
		Timeline animation=new Timeline(new
				KeyFrame(Duration.millis(1),eventhandler));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
		
		
		//add pane to scene and stage
		Scene scene=new Scene(pane,400+200,500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Project");
		primaryStage.show();
		iv_squirrel.requestFocus();
		
		
	}
	
	//控制上下左右放進函式
	public void registerKeyboardEventHandler(ImageView c) {

	    c.setOnKeyPressed(e -> {
	        switch (e.getCode()) {
	            case UP:
					c.setImage(role.iv_squirrel_stop.getImage());
	                c.setLayoutY(c.getLayoutY() - 10);
	                break;
	            case DOWN:
					c.setImage(role.iv_squirrel_stop.getImage());
	                c.setLayoutY(c.getLayoutY() + 10);
	                break;
	            case RIGHT:
					c.setImage(role.iv_squirrel_run2.getImage());
	                // Make sure the circle doesn't go beyond the right side of the screen
	                if (c.getLayoutX() <= 355) {
	                    c.setLayoutX(c.getLayoutX() + 10);
	                }
	                break;
	            case LEFT:
					c.setImage(role.iv_squirrel_run.getImage());
	                // Make sure the circle doesn't go beyond the left side of the screen
	                if (c.getLayoutX() >= 10) {
	                    c.setLayoutX(c.getLayoutX() - 10);
	                }
	                break;
	            default:
					c.setImage(role.iv_squirrel_stop.getImage());
	                break;
	        }
	    });
	    
		c.setOnKeyReleased(e -> {
				if(e.getCode()==KeyCode.LEFT) c.setImage(role.iv_squirrel_stop.getImage());
				else if(e.getCode()==KeyCode.RIGHT) c.setImage(role.iv_squirrel_stop2.getImage());
		});
		
	    Timeline animation=new Timeline(new
				KeyFrame(Duration.millis(100)));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
	}
	
	//讓板子往上動的函式
	public static void moveRectangle(Rectangle r, ImageView c) {
		
		EventHandler <ActionEvent> handler= (e ->{
			boolean moveWithRectangle=false;
			//Floor f=new Floor();
			if (c.getLayoutX()>=r.getX()-20 && c.getLayoutX()<=r.getX()+r.getWidth()-20
				&& c.getLayoutY()>=r.getY()-30 && c.getLayoutY()<=r.getY()-30+1) {
				moveWithRectangle = true;
	        }
	        
			if(c.getLayoutY()>=525 || c.getLayoutY()<=10 || (c.getLayoutX()==0 && c.getLayoutY()==0)) {
				//do nothing 圓掉到最下面或碰到頂部而停止
			}else if (moveWithRectangle) {
	            //r.setY(c.getCenterY() - 25);
	        	r.setY(r.getY() - 1);
	        	c.setLayoutY(c.getLayoutY()-1);
	        } else if (r.getY() >= 0) {
	        	r.setY(r.getY() - 1);
	        } else {
	        	
	            r.setX((int) (Math.random() * 320));
	            r.setY(500);
	            moveWithRectangle = false;
	            Layer.layer++;
	            //f.add_time();
	        }
			/*if(f.getAppearTime()==10){
				
			}*/
			
		});
		
		Timeline animation=new Timeline(new
				KeyFrame(Duration.millis(20),handler));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
		
		speedup(animation);
		
	}
	//板子隨時間加速
	public static void speedup(Timeline timeline) {
		double origin_time=timeline.getRate();
		EventHandler <ActionEvent> speedhandler= (e->{
			if(Layer.layer==0) {//重新開始後回到原速度
				timeline.setRate(origin_time);
			}
			timeline.setRate(timeline.getRate()+0.001);
		});
		
		Timeline speedUP=new Timeline(new
				KeyFrame(Duration.millis(10),speedhandler));
		speedUP.setCycleCount(Timeline.INDEFINITE);
		speedUP.play();
	}
	//遊戲結束(沒用到)
	public static void gameOver(Pane pane,Circle c) {
		//Circle c= new Circle(250,250,25);
		Rectangle r=new Rectangle(150,150,200,200);
		r.setFill(Color.BLUE);
		r.setStroke(Color.BLACK);
		pane.getChildren().add(r);
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



//繼承矩形(沒用到)
class Floor extends Rectangle{
	private int appear_time=0;
	
	public void add_time() {
		appear_time++;
	}
	public int getAppearTime() {
		return appear_time;
	}
}
//類似全域變數，計算層數
class Layer{
	static int layer=0;
	
}

//同上，全域的角色資訊
class role{
	static Image Squirrel = new Image("/project/image/squirrel.png");
	static Image Squirrel2 = new Image("/project/image/squirrel2.png");
	static Image Squirrel_run = new Image("/project/image/squirrel.gif");
	static Image Squirrel_run2 = new Image("/project/image/squirrel2.gif");
	static ImageView iv_squirrel_stop = new ImageView(Squirrel);
	static ImageView iv_squirrel_stop2 = new ImageView(Squirrel2);
	static ImageView iv_squirrel_run = new ImageView(Squirrel_run);
	static ImageView iv_squirrel_run2 = new ImageView(Squirrel_run2);
}

//血條(目前沒用到)
class Heart{
	static int number=3;
	
	public int getHeartNumber() {
		return number;
	}
	public void getHurt() {
		number--;
	}
}


/////////
//
/////


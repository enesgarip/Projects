// Muhammed Fethullah EROÐLU - 150116022 , Enes GARÝP - 150116034

// We use same algorithm in every level class so we don't write same things in every level class.

import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.util.Duration;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.animation.*;
import javafx.scene.input.*;
import javafx.animation.Timeline;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Project extends Application {
    public static void main(String[]args){
        launch(args);

    }



    public void start(Stage PrimaryStage){

    	// We created different classes for each level. Then we created objects for them and added them to scenes.



    	Level1 level1 = new Level1();
        Scene scene1 = new Scene(level1,250,300);


        Level2 level2 = new Level2();
        Scene scene2= new Scene(level2,250,300);

        Level3 level3 = new Level3();
        Scene scene3 = new Scene(level3,250,300);

        Level4 level4 = new Level4();
        Scene scene4 = new Scene(level4,250,300);

        Level5 level5 = new Level5();
        Scene scene5= new Scene(level5,250,300);

        Pane pane = new Pane();
        Scene finalscene = new Scene(pane,300,225);
        Image im = new Image("congrats.gif");
		pane.getChildren().add(new ImageView(im));


// I put button in order to to switch scenes. But they are temporarily



       PrimaryStage.setScene(scene1);
       PrimaryStage.setTitle("Level 1");


       Timeline pause = new Timeline(new KeyFrame(Duration.millis(1000)));



	level1.btNext.setOnMouseClicked(e ->{
		pause.play();
		 pause.setOnFinished(e1 -> {
    	level2.getChildren().clear();
    	level2.level2();
    	PrimaryStage.setTitle("Level 2");
        PrimaryStage.setScene(scene2);
    	}); });
	level2.btNext.setOnMouseClicked( e -> {
		pause.play();
		pause.setOnFinished(e1 -> {
    	level3.getChildren().clear();
    	level3.level3();
    	PrimaryStage.setTitle("Level 3");
    	PrimaryStage.setScene(scene3);
    });});

	level3.btNext.setOnMouseClicked( e -> {
		pause.play();
		pause.setOnFinished(e1 -> {
    	level4.getChildren().clear();
    	level4.level4();
    	PrimaryStage.setTitle("Level 4");
    	PrimaryStage.setScene(scene4);
    });});

	level4.btNext.setOnMouseClicked( e -> {
		pause.play();
		pause.setOnFinished(e1 -> {
    	level5.getChildren().clear();
    	level5.level5();
    	PrimaryStage.setTitle("Level 5");
    	PrimaryStage.setScene(scene5);
    });});
	level5.btNext.setOnMouseClicked(e ->{
		pause.play();
		pause.setOnFinished(e1 -> {
		PrimaryStage.setTitle("Congrats!");
        PrimaryStage.setScene(finalscene);
    	}); });

    level2.btPrev.setOnMouseClicked(e -> {
    	pause.play();
		pause.setOnFinished(e1 -> {
    	level1.getChildren().clear();
    	level1.level1();
    	PrimaryStage.setTitle("Level 1");
    	PrimaryStage.setScene(scene1);
    });});

    level3.btPrev.setOnMouseClicked(e -> {
    	pause.play();
		pause.setOnFinished(e1 -> {
    	level2.getChildren().clear();
    	level2.level2();
    	PrimaryStage.setTitle("Level 2");
    	PrimaryStage.setScene(scene2);
    });});

    level4.btPrev.setOnMouseClicked(e -> {
    	pause.play();
		pause.setOnFinished(e1 -> {
    	level3.getChildren().clear();
    	level3.level3();
    	PrimaryStage.setTitle("Level 3");
    	PrimaryStage.setScene(scene3);
    });});

    level5.btPrev.setOnMouseClicked(e -> {
    	pause.play();
		pause.setOnFinished(e1 -> {
    	level4.getChildren().clear();
    	level4.level4();
    	PrimaryStage.setTitle("Level 4");
    	PrimaryStage.setScene(scene4);
    });});

        PrimaryStage.show();


    }
}



    class Level1 extends Pane{


         Button btNext = new Button("Next");
         ArrayList<Node> allelements= new ArrayList<Node>();

        Level1(){

            level1();

            }

        public void level1(){

        	// We designed level 1 by using classes (Horizontal) we created before. These arc's for soft turnings.


            HorizontalLineWithCircle line1 = new HorizontalLineWithCircle(50,20,50,50);
            getChildren().add(line1.leftline);
            getChildren().add(line1.rightline);
            getChildren().add(line1.smallline);
            getChildren().add(line1.semicircle);


             Arc arc1 =new Arc(0,0,5,5,90,90);
            arc1.centerXProperty().bind((line1.leftline).startXProperty());
            arc1.centerYProperty().bind((line1.leftline).startYProperty().add(arc1.radiusXProperty()));
            arc1.setStroke(Color.BLUE);
            arc1.setType(ArcType.OPEN);
            arc1.setFill(Color.WHITE);
            arc1.setStrokeWidth(1);
            getChildren().add(arc1);

              Line line4 =new Line();
            line4.startXProperty().bind((line1.leftline).startXProperty().subtract(arc1.radiusXProperty()));
            line4.endXProperty().bind(line4.startXProperty());
            line4.startYProperty().bind((line1.leftline).startYProperty().add(arc1.getRadiusX()));
            line4.endYProperty().bind(line4.startYProperty().add(50));
            line4.setStroke(Color.BLUE);
            line4.setStrokeWidth(1);
            getChildren().add(line4);

           Arc arc2 =new Arc(0,0,5,5,180,90);
            arc2.centerXProperty().bind(line4.endXProperty().add(arc2.radiusXProperty()));
            arc2.centerYProperty().bind(line4.endYProperty());
            arc2.setStroke(Color.BLUE);
            arc2.setType(ArcType.OPEN);
            arc2.setFill(Color.WHITE);
            arc2.setStrokeWidth(1);
            getChildren().add(arc2);

            Line line5 =new Line();
            line5.startXProperty().bind(line4.startXProperty().add(arc2.radiusXProperty()));
            line5.endXProperty().bind(line5.startXProperty().add(100));
            line5.startYProperty().bind(line4.endYProperty().add(arc2.getRadiusX()));
            line5.endYProperty().bind(line5.startYProperty());
            line5.setStroke(Color.BLUE);
            line5.setStrokeWidth(1);
            getChildren().add(line5);



            Circle filled = new Circle(0,0,15);
            filled.centerXProperty().bind(line5.endXProperty().add(filled.getRadius()));
            filled.centerYProperty().bind(line5.endYProperty());
            filled.setStroke(Color.BLACK);
            filled.setFill(Color.BLACK);
            getChildren().add(filled);



            Line line8 =new Line();
            line8.setStartX((line1.semicircle).getCenterX());
            line8.endXProperty().bind(line8.startXProperty());
            line8.setStartY((line1.semicircle).getCenterY()+80);;
            line8.endYProperty().bind(line8.startYProperty().add(20));
            line8.setStroke(Color.BLUE);
            line8.setStrokeWidth(1);
            getChildren().add(line8);


            Line line6 =new Line();
            line6.setStartX((line1.semicircle).getCenterX());
            line6.setStartY(line8.getStartY()-85);
            line6.endXProperty().bind(line6.startXProperty());
            line6.setEndY(line8.getStartY());
            line6.setStroke(Color.BLACK);
            line6.setStrokeWidth(1.5);
            getChildren().add(line6);


            Line line7 = new Line();
            line7.setStartX(line8.getStartX()-6);
            line7.setEndX(line8.getStartX()+6);
            line7.setStartY(line8.getStartY());
            line7.endYProperty().bind(line7.startYProperty());
            line7.setStrokeWidth(2.5);
            line7.setStroke(Color.RED);
            getChildren().add(line7);


            Circle filled2 = new Circle(0,0,15);
            filled2.centerXProperty().bind(line8.endXProperty());
            filled2.centerYProperty().bind(line8.endYProperty().add(filled2.getRadius()));
            filled2.setStroke(Color.BLACK);
            filled2.setFill(Color.BLACK);
            getChildren().add(filled2);


    //  We added all elements to arraylist in order to senf them to getback2 method.

            allelements.add(arc1);allelements.add(arc2);allelements.add(line1.leftline);
        	allelements.add(line1.rightline);allelements.add(line1.semicircle);
        	allelements.add(line1.smallline);allelements.add(line4);allelements.add(line5);
        	allelements.add(line6);allelements.add(line7);allelements.add(line8);
        	allelements.add(filled);allelements.add(filled2);

        	GetBack2(allelements);

// We declared animations here.

            Timeline animation1 = new Timeline(new KeyFrame(Duration.millis(60) , e -> {
                line6.setStartY(line6.getStartY()+4);} )
                    );

            animation1.setCycleCount(20);

            Timeline animation2 = new Timeline(new KeyFrame(Duration.millis(60) , e -> {
                line1.leftline.setEndX(line1.leftline.getEndX()-2.5);

            } )
                    );

            animation2.setCycleCount(20);

            Timeline animation3 = new Timeline(new KeyFrame(Duration.millis(60) , e -> {
                line1.leftline.setEndX(line1.leftline.getEndX()-0.5);

            } )
                    );

            animation3.setCycleCount(18);


// We enlarged and shrinked circles when mouse entered or existed.

            filled.setOnMouseEntered(e -> { filled.setRadius(filled.getRadius()+5); });
            filled.setOnMouseExited(e -> { filled.setRadius(filled.getRadius()-5); });
            filled2.setOnMouseEntered(e -> { filled2.setRadius(filled2.getRadius()+5); });
            filled2.setOnMouseExited(e -> { filled2.setRadius(filled2.getRadius()-5); });


// This event handler works when mouse is clicked on pane.
            setOnMouseClicked(e ->{

                if(e.getTarget() == filled){

            //This is collision case . We added again button and collision text to the pane

                    if(line6.getStartY() <= line1.rightline.getStartY()) {
                        animation3.play();

                        Button again= new Button("Again ?");
                        again.setLayoutX(60);
                        again.setLayoutY(220);
                        getChildren().add(again);
                        Text collision = new Text("Collision has been occured.\nPlease try again.");
                        collision.setLayoutX(10);
                        collision.setLayoutY(180);
                        getChildren().add(collision);
                        again.setOnMouseClicked(e1-> {
                            getChildren().removeAll(line1.leftline,line1.rightline,line1.semicircle,line1.smallline,filled,filled2,line4,line5,line6,line7,line8,arc1,arc2);
                            line1.leftline.setEndX(line1.leftline.getStartX()+50);
                            getChildren().addAll(line1.leftline,line1.rightline,line1.semicircle,line1.smallline,filled,filled2,line4,line5,line6,line7,line8,arc1,arc2);
                            getChildren().removeAll(again,collision);
                        });


                    }

          // This is succesful possiblity .
                    else {

                        animation2.play();
                        line1.smallline.setStartY(line1.smallline.getStartY()-4);
                        line1.smallline.setEndY(line1.smallline.getEndY()+4);





                        ArrayList<Node> arr= new ArrayList<Node>();
                        arr.add(line1.leftline);
                       arr.add(line1.rightline);
                        arr.add(line1.semicircle);
                        arr.add(line1.smallline);
                        arr.add(filled);
                        arr.add(arc1);
                        arr.add(arc2);
                        arr.add(line4);
                        arr.add(line5);
                        FadeAway(arr);


// Mouse clicked event fires when the last circle disappears succesfully.

                        Event.fireEvent(btNext, new MouseEvent(MouseEvent.MOUSE_CLICKED,
              				   btNext.getLayoutX(),btNext.getLayoutY(), btNext.getLayoutX(), btNext.getLayoutY(), MouseButton.PRIMARY, 1,
              				   true, true, true, true, true, true, true, true, true, true, null));



                    }
                    }
                else if(e.getTarget()== filled2){
                    System.out.println("2");
                     line7.setStartX(line7.getStartX()-4);
                     line7.setEndX(line7.getEndX()+4);
                     animation1.play();


                    ArrayList<Node> nodes = new ArrayList<Node>();
                    nodes.add(line6);
                    nodes.add(line7);
                    nodes.add(line8);
                    nodes.add(filled2);
                    FadeAway(nodes);



                }
            });




        }


        // This method for FadeTransition animation.
        void FadeAway(ArrayList<Node> node){
            for(int i = 0 ; i<node.size();i++){
            FadeTransition ft = new FadeTransition(Duration.millis(1500),node.get(i));
            ft.setFromValue(1.0);
            ft.setToValue(0);
            ft.play();

            }

        }
        // This method for making invisible nodes visible.
        void GetBack(ArrayList<Node> node){
            for(int i = 0 ; i<node.size();i++){
            FadeTransition ft = new FadeTransition(Duration.millis(800),node.get(i));
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();}
        }

        // This method for level start.
        void GetBack2(ArrayList<Node> node){
            for(int i = 0 ; i<node.size();i++){
            FadeTransition ft = new FadeTransition(Duration.millis(1250),node.get(i));
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();}
        }




        }








    class Level2 extends Pane{
    	Button btNext = new Button("Next");
    	Button btPrev = new Button("Previous");
    	ArrayList<Node> allelements= new ArrayList<Node>();

    	Level2(){
           level2();
        }

        public void level2(){

        	// We designed level 2 by using classes we created before. These arc's for soft turnings.

        	btPrev.setLayoutX(10);
        	btPrev.setLayoutY(200);
        	getChildren().add(btPrev);

// 2. circle's bottom half

            VerticalLineWithCircle circleline = new VerticalLineWithCircle(50,100,20,20);

            getChildren().addAll(circleline.bottomline,circleline.topline,circleline.semicircle,circleline.smallline);


// 2. circle's top half

            VerticalLineWithCircle circleline2 = new VerticalLineWithCircle(20,100,0,10);
            circleline2.semicircle.setLength(-180);
            circleline2.semicircle.centerYProperty().bind(circleline.topline.startYProperty().subtract(10));
            circleline2.topline.endYProperty().bind(circleline.topline.startYProperty().subtract(20));
            circleline2.topline.startYProperty().bind(circleline2.topline.endYProperty().subtract(10));
            getChildren().addAll(circleline2.topline,circleline2.semicircle);

// 1.circle's black line

            HorizontalLineWithCircle circleline3 = new HorizontalLineWithCircle((int)(circleline2.semicircle.getCenterX()-35),((int)circleline2.semicircle.getCenterY()),40,0);
            getChildren().addAll(circleline3.leftline,circleline3.smallline);

// 4.circle's black part

            HorizontalLineWithCircle circleline4 = new HorizontalLineWithCircle((int)(circleline.semicircle.getCenterX()-5),((int)circleline.semicircle.getCenterY()),30,30);
            circleline4.rightline.startXProperty().unbind();
            circleline4.rightline.endXProperty().unbind();
            circleline4.leftline.endXProperty().bind(circleline4.rightline.startXProperty().subtract(20));
            circleline4.leftline.startXProperty().bind(circleline4.leftline.endXProperty().subtract(30));
            circleline4.semicircle.centerXProperty().bind(circleline4.rightline.startXProperty().subtract(10));




// 4.circle's red line

            Line smallline = new Line();
            smallline.setStartX(circleline4.rightline.getEndX());
            smallline.setEndX(smallline.getStartX());
            smallline.setStartY(circleline4.rightline.getEndY()-6);
            smallline.setEndY(circleline4.rightline.getEndY()+6);
            smallline.setStrokeWidth(2.5);
            smallline.setStroke(Color.RED);
            getChildren().addAll(circleline4.leftline,circleline4.rightline,circleline4.semicircle, smallline);

// 1. circle's arc

              Arc arc1 =new Arc(0,0,5,5,90,90);
              arc1.centerXProperty().bind((circleline3.leftline).startXProperty());
              arc1.centerYProperty().bind((circleline3.leftline).startYProperty().add(arc1.radiusXProperty()));
              arc1.setStroke(Color.BLUE);
              arc1.setType(ArcType.OPEN);
              arc1.setFill(Color.WHITE);
              arc1.setStrokeWidth(1);
              getChildren().add(arc1);

// 1.circle's blue line

              Line line =new Line();
              line.startXProperty().bind((circleline3.leftline).startXProperty().subtract(arc1.radiusXProperty()));
              line.endXProperty().bind(line.startXProperty());
              line.startYProperty().bind((circleline3.leftline).startYProperty().add(arc1.getRadiusX()));
              line.endYProperty().bind(line.startYProperty().add(100));
              line.setStroke(Color.BLUE);
              line.setStrokeWidth(1);
              getChildren().add(line);

// 1. circle

              Circle filled = new Circle(0,0,15);
              filled.centerXProperty().bind(line.endXProperty());
              filled.centerYProperty().bind(line.endYProperty().add(filled.getRadius()));
              filled.setStroke(Color.BLACK);
              filled.setFill(Color.BLACK);
              getChildren().add(filled);

// 3. circle's black line

              Line line31 = new Line();
              line31.setStartX(circleline4.semicircle.getCenterX());
              line31.endXProperty().bind(line31.startXProperty());
              line31.setStartY(circleline4.semicircle.getCenterY()-5);
              line31.setEndY(line31.getStartY()+30);
              line31.setStroke(Color.BLACK);
              line31.setStrokeWidth(1.5);
              getChildren().add(line31);

//3.circle's red line

              Line smallline3 = new Line();
                smallline3.setStartX(line31.getEndX()-6);
                smallline3.setEndX(smallline3.getStartX()+12);
                smallline3.setStartY(line31.getEndY());
                smallline3.setEndY(line31.getEndY());
                smallline3.setStrokeWidth(2.5);
              smallline3.setStroke(Color.RED);
              getChildren().add(smallline3);

// 3.circle's blue line

              Line line32 =new Line();
              line32.setStartX(line31.getEndX());
              line32.setEndX(line32.getStartX());
              line32.setStartY(line31.getEndY());
              line32.setEndY(line.getEndY());
              line32.setStroke(Color.BLUE);
              line32.setStrokeWidth(1);
              getChildren().add(line32);

// 3.circle
              Circle filled3 = new Circle(0,0,15);
              filled3.centerXProperty().bind(line32.endXProperty());
              filled3.centerYProperty().bind(line32.endYProperty().add(filled3.getRadius()));
              filled3.setStroke(Color.BLACK);
              filled3.setFill(Color.BLACK);
              getChildren().add(filled3);




// 2.circle's blue line

              Line line22 =new Line();
              line22.setStartX(circleline.bottomline.getEndX());
              line22.setEndX(line22.getStartX());
              line22.setStartY(circleline.bottomline.getEndY());
              line22.setEndY(line.getEndY());
              line22.setStroke(Color.BLUE);
              line22.setStrokeWidth(1);
              getChildren().add(line22);

// 2.circle

              Circle filled2 = new Circle(0,0,15);
              filled2.centerXProperty().bind(line22.endXProperty());
              filled2.centerYProperty().bind(line22.endYProperty().add(filled2.getRadius()));
              filled2.setStroke(Color.BLACK);
              filled2.setFill(Color.BLACK);
              getChildren().add(filled2);




// 4.circle's arc

              Arc arc2 =new Arc(0,0,5,5,90,-90);
              arc2.centerXProperty().bind((circleline4.rightline).endXProperty());
              arc2.centerYProperty().bind((circleline4.rightline).endYProperty().add(arc1.radiusXProperty()));
              arc2.setStroke(Color.BLUE);
              arc2.setType(ArcType.OPEN);
              arc2.setFill(Color.WHITE);
              arc2.setStrokeWidth(1);
              getChildren().add(arc2);

// 4.circle's blue line

              Line line41 = new Line();

              line41.setStartX(circleline4.rightline.getEndX()+arc2.getRadiusX());
              line41.endXProperty().bind(line41.startXProperty());
              line41.setStartY(circleline4.rightline.getEndY()+arc2.getRadiusX());
              line41.setEndY(line.getEndY());
              line41.setStroke(Color.BLUE);
              line41.setStrokeWidth(1);
              getChildren().add(line41);

// 4.circle

              Circle filled4 = new Circle(0,0,15);
              filled4.centerXProperty().bind(line41.endXProperty());
              filled4.centerYProperty().bind(line41.endYProperty().add(filled4.getRadius()));
              filled4.setStroke(Color.BLACK);
              filled4.setFill(Color.BLACK);
              getChildren().add(filled4);


              Button again= new Button("Again ?");
              again.setLayoutX(170);
              again.setLayoutY(245);

              allelements.add(filled4); allelements.add(filled); allelements.add(filled2); allelements.add(filled3); allelements.add(line41);
              allelements.add(arc2); allelements.add(line22); allelements.add(line32); allelements.add(line31); allelements.add(line);
              allelements.add(smallline3); allelements.add(smallline); allelements.add(circleline.topline);allelements.add(circleline.bottomline);
              allelements.add(circleline.semicircle);allelements.add(circleline.smallline);allelements.add(circleline2.bottomline);
              allelements.add(circleline2.semicircle);allelements.add(circleline2.smallline);allelements.add(circleline2.topline);
              allelements.add(circleline3.leftline);allelements.add(circleline3.rightline);allelements.add(circleline3.semicircle);
              allelements.add(circleline3.smallline);allelements.add(circleline4.leftline);allelements.add(circleline4.rightline);
              allelements.add(circleline4.semicircle);allelements.add(circleline4.smallline);allelements.add(arc1);

              GetBack2(allelements);



              Text collision = new Text("Collision has been occured.\nPlease try again.");
              collision.setLayoutX(10);
              collision.setLayoutY(255);


           // We enlarged and shrinked circles when mouse entered or existed.
              filled.setOnMouseEntered(e -> { filled.setRadius(filled.getRadius()+5); });
              filled.setOnMouseExited(e -> { filled.setRadius(filled.getRadius()-5); });
              filled2.setOnMouseEntered(e -> { filled2.setRadius(filled2.getRadius()+5); });
              filled2.setOnMouseExited(e -> { filled2.setRadius(filled2.getRadius()-5); });
              filled3.setOnMouseEntered(e -> { filled3.setRadius(filled3.getRadius()+5); });
              filled3.setOnMouseExited(e -> { filled3.setRadius(filled3.getRadius()-5); });
              filled4.setOnMouseEntered(e -> { filled4.setRadius(filled4.getRadius()+5); });
              filled4.setOnMouseExited(e -> { filled4.setRadius(filled4.getRadius()-5); });



              setOnMouseClicked(e-> {
                  if(e.getTarget() == filled){

                      Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
                          circleline3.leftline.setEndX(circleline3.leftline.getEndX()-3); } ) );

                      circleline3.smallline.setStartY(circleline3.smallline.getStartY()-4);
                      circleline3.smallline.setEndY(circleline3.smallline.getEndY()+4);
                      ArrayList<Node> arr = new ArrayList<>();
                      arr.add(circleline3.leftline);
                      arr.add(line);
                      arr.add(arc1);
                      arr.add(filled);
                      arr.add(circleline3.smallline);
                      FadeAway(arr);
                      animation.setCycleCount(12);
                      animation.play();
                  }
                  else if (e.getTarget() == filled2){
                      if((circleline.semicircle.getCenterX() < circleline4.leftline.getStartX()) && (circleline2.semicircle.getCenterX() > circleline3.leftline.getEndX())){
                          Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
                              circleline.bottomline.setStartY(circleline.bottomline.getStartY()+2); } ));
                          circleline.semicircle.centerYProperty().bind(circleline.bottomline.startYProperty().subtract(10));
                          circleline.topline.endYProperty().bind(circleline.semicircle.centerYProperty().subtract(10));
                          circleline.topline.startYProperty().bind(circleline.topline.endYProperty().subtract(20));

                          circleline.smallline.setStartX(circleline.smallline.getStartX()-4);
                          circleline.smallline.setEndX(circleline.smallline.getEndX()+4);

                          ArrayList<Node> arr = new ArrayList<>();
                          arr.add(circleline.semicircle);
                          arr.add(circleline.bottomline);
                          arr.add(circleline.smallline);
                          arr.add(circleline.topline);
                          arr.add(circleline2.semicircle);
                          arr.add(circleline2.topline);
                          arr.add(filled2);
                          arr.add(line22);

                          FadeAway(arr);
                          animation.setCycleCount(8);
                          animation.play();
                          btNext.setLayoutX(150);
                          btNext.setLayoutY(200);

                          Event.fireEvent(btNext, new MouseEvent(MouseEvent.MOUSE_CLICKED,
                 				   btNext.getLayoutX(),btNext.getLayoutY(), btNext.getLayoutX(), btNext.getLayoutY(), MouseButton.PRIMARY, 1,
                 				   true, true, true, true, true, true, true, true, true, true, null));


                      }
                      else{

                        	  Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
                                  circleline.bottomline.setStartY(circleline.bottomline.getStartY()+1); } ));
                        	  circleline.semicircle.centerYProperty().bind(circleline.bottomline.startYProperty().subtract(10));
                              circleline.topline.endYProperty().bind(circleline.semicircle.centerYProperty().subtract(10));
                              circleline.topline.startYProperty().bind(circleline.topline.endYProperty().subtract(20));

                        	  animation.setCycleCount(8);
                              animation.play();
                              getChildren().add(again);
                              getChildren().add(collision);
                              again.setOnMouseClicked(e2 -> {


                                  getChildren().removeAll(collision,again,circleline.bottomline,circleline.topline,circleline.semicircle,circleline.smallline,circleline2.topline,circleline2.semicircle,circleline3.leftline,circleline3.smallline,circleline4.leftline,circleline4.rightline,circleline4.semicircle, smallline,arc1,line,filled,line31,smallline3,line32,filled3,line22,filled2,arc2,line41,filled4);
                            	  if(circleline3.leftline.getEndX() != (int)(circleline2.semicircle.getCenterX()+5) ){
                                      circleline3.leftline.setEndX((int)(circleline2.semicircle.getCenterX()+5));
                                      circleline3.smallline.setStartY(circleline3.smallline.getStartY()+4);
                                      circleline3.smallline.setEndY(circleline3.smallline.getEndY()-4);

                                      ArrayList<Node> arr = new ArrayList<>();
                                      arr.add(circleline3.leftline);
                                      arr.add(line);
                                      arr.add(arc1);
                                      arr.add(filled);
                                      arr.add(circleline3.smallline);
                                      GetBack(arr);}

                            	  if(circleline4.rightline.getStartX() != (int)(circleline.semicircle.getCenterX()+45) ){
                                      circleline4.rightline.setStartX((int)(circleline2.semicircle.getCenterX()+45));
                                      smallline.setStartY(smallline.getStartY()+4);
                                      smallline.setEndY(smallline.getEndY()-4);
                                      ArrayList<Node> arr = new ArrayList<>();
                                      arr.add(circleline4.leftline);
                                      arr.add(circleline4.rightline);
                                      arr.add(arc2);
                                      arr.add(smallline);
                                      arr.add(circleline4.semicircle);
                                      arr.add(filled4);
                                      arr.add(line41);
                                      GetBack(arr);}


                                      if(line31.getStartY()!= (int)(circleline4.semicircle.getCenterY()-5) ){
                                          line31.setStartY((int)(circleline4.semicircle.getCenterY()-5));
                                          smallline3.setStartX(smallline3.getStartX()+4);
                                          smallline3.setEndX(smallline3.getEndX()-4);
                                          ArrayList<Node> arr = new ArrayList<>();
                                          arr.add(line31);
                                          arr.add(line32);
                                          arr.add(filled3);
                                          arr.add(smallline3);

                                          GetBack(arr);}
                                      circleline.bottomline.setStartY(circleline.bottomline.getStartY()-8);
                                      getChildren().addAll(circleline.bottomline,circleline.topline,circleline.semicircle,circleline.smallline,circleline2.topline,circleline2.semicircle,circleline3.leftline,circleline3.smallline,circleline4.leftline,circleline4.rightline,circleline4.semicircle, smallline,arc1,line,filled,line31,smallline3,line32,filled3,line22,filled2,arc2,line41,filled4);


                              });






                      }


                  }
                  else if (e.getTarget() == filled3){

                      Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
                          line31.setStartY(line31.getStartY()+2); } ));

                      smallline3.setStartX(smallline3.getStartX()-4);
                      smallline3.setEndX(smallline3.getEndX()+4);

                      ArrayList<Node> arr = new ArrayList<>();
                      arr.add(line32);
                      arr.add(line31);
                      arr.add(filled3);
                      arr.add(smallline3);
                      FadeAway(arr);
                      animation.setCycleCount(15);
                      animation.play();



                  }
                  else if (e.getTarget() == filled4){
                      if(line31.getStartY() >= circleline4.semicircle.getCenterY()){

                          Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
                              circleline4.rightline.setStartX(circleline4.rightline.getStartX()+2); } ));

                          smallline.setStartY(smallline.getStartY()-4);
                          smallline.setEndY(smallline.getEndY()+4);

                          ArrayList<Node> arr = new ArrayList<>();
                          arr.add(circleline4.leftline);
                          arr.add(circleline4.rightline);
                          arr.add(circleline4.semicircle);
                          arr.add(smallline);
                          arr.add(arc2);
                          arr.add(filled4);
                          arr.add(line41);
                          FadeAway(arr);

                          animation.setCycleCount(15);
                          animation.play();

                      }
                      else{
                    	  System.out.println("5");
                          Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
                              circleline4.rightline.setStartX(circleline4.rightline.getStartX()+0.5); } ));

                          animation.setCycleCount(16);
                          animation.play();

                          getChildren().add(again);
                          getChildren().add(collision);
                          again.setOnMouseClicked(e1 -> {

                              getChildren().removeAll(collision,again,circleline.bottomline,circleline.topline,circleline.semicircle,circleline.smallline,circleline.bottomline,circleline.topline,circleline.semicircle,circleline.smallline,circleline2.topline,circleline2.semicircle,circleline3.leftline,circleline3.smallline,circleline4.leftline,circleline4.rightline,circleline4.semicircle, smallline,arc1,line,filled,line31,smallline3,line32,filled3,line22,filled2,arc2,line41,filled4);

                              if(circleline3.leftline.getEndX() != (int)(circleline2.semicircle.getCenterX()+5) ){
                                  circleline3.leftline.setEndX((int)(circleline2.semicircle.getCenterX()+5));
                                  circleline3.smallline.setStartY(circleline3.smallline.getStartY()+4);
                                  circleline3.smallline.setEndY(circleline3.smallline.getEndY()-4);
                                  ArrayList<Node> arr = new ArrayList<>();
                                  arr.add(circleline3.leftline);
                                  arr.add(line);
                                  arr.add(arc1);
                                  arr.add(filled);
                                  arr.add(circleline3.smallline);
                                  GetBack(arr);}
                              circleline4.rightline.setStartX(circleline4.rightline.getStartX()-8);
                              getChildren().addAll(circleline.bottomline,circleline.topline,circleline.semicircle,circleline.smallline,circleline2.topline,circleline2.semicircle,circleline3.leftline,circleline3.smallline,circleline4.leftline,circleline4.rightline,circleline4.semicircle, smallline,arc1,line,filled,line31,smallline3,line32,filled3,line22,filled2,arc2,line41,filled4);
                          });


                      }


                  }
              });







        }
        void FadeAway(ArrayList<Node> node){
            for(int i = 0 ; i<node.size();i++){
            FadeTransition ft = new FadeTransition(Duration.millis(1500),node.get(i));
            ft.setFromValue(1.0);
            ft.setToValue(0);
            ft.play();}
        }
        void GetBack(ArrayList<Node> node){
            for(int i = 0 ; i<node.size();i++){
            FadeTransition ft = new FadeTransition(Duration.millis(800),node.get(i));
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();}
        }

        void GetBack2(ArrayList<Node> node){
            for(int i = 0 ; i<node.size();i++){
            FadeTransition ft = new FadeTransition(Duration.millis(1250),node.get(i));
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();}
        }


    }

    class Level3 extends Pane{
    	Button btPrev = new Button("Previous");
    	Button btNext = new Button();
    	ArrayList<Node> allelements= new ArrayList<Node>();

    	Level3(){
    		level3();
    	}

    	void level3(){



    		btPrev.setLayoutX(10);
        	btPrev.setLayoutY(210);
        	getChildren().add(btPrev);


    		// We designed Level3 by using the objects on JavaFx itself


    		Circle c1=new Circle();
    		Circle c2=new Circle();
    		Circle c3=new Circle();

    		c1.setRadius(15);
    		c2.setRadius(15);
    		c3.setRadius(15);

    		c1.setCenterX(30);
    		c1.setCenterY(100);
    		c1.setStroke(Color.BLACK);

    		c2.setCenterX(50);
    		c2.setCenterY(130);
    		c2.setStroke(Color.BLACK);

    		c3.setCenterX(100);
    		c3.setCenterY(190);
    		c3.setStroke(Color.BLACK);

    		Line l1=new Line(45,100,150,100);
    		Line l2=new Line(65,130,110,130);
    		Line l3=new Line(100,175,100,35);
    		Line l4=new Line(149,93,161,93);
    		Line l5=new Line(107,24,107,36);
    		Line l6=new Line(115,125,115,70);
    		Line l7=new Line(122,59,122,71);
    		Line l8=new Line(124,65,160,65);
    		Line l9=new Line(155,92,155,75);
    		Line l10=new Line(155,55,155,25);
    		Line l11=new Line(109,30,145,30);
    		Line l12=new Line(165,30,180,30);


    		l1.setStrokeWidth(1);
    		l2.setStrokeWidth(1);
    		l3.setStrokeWidth(1);
    		l4.setStrokeWidth(2.5);
    		l5.setStrokeWidth(2.5);
    		l6.setStrokeWidth(1);
    		l7.setStrokeWidth(2.5);
    		l8.setStrokeWidth(1.5);
    		l9.setStrokeWidth(1.5);
    		l10.setStrokeWidth(1.5);
    		l11.setStrokeWidth(1.5);
    		l12.setStrokeWidth(1.5);

    		l1.setStroke(Color.BLUE);
    		l2.setStroke(Color.BLUE);
    		l3.setStroke(Color.BLUE);
    		l4.setStroke(Color.RED);
    		l5.setStroke(Color.RED);
    		l6.setStroke(Color.BLUE);
    		l7.setStroke(Color.RED);
    		l8.setStroke(Color.BLACK);
    		l9.setStroke(Color.BLACK);
    		l10.setStroke(Color.BLACK);
    		l11.setStroke(Color.BLACK);
    		l12.setStroke(Color.BLACK);
    		l12.endXProperty().bind(l12.startXProperty().add(15));

    		Arc arc1=new Arc(150,95,5,5,-90,90);
    		arc1.setType(ArcType.OPEN);
    		arc1.setFill(Color.WHITE);
    		arc1.setStroke(Color.BLUE);
    		arc1.setStrokeWidth(1);

    		Arc arc2=new Arc(110,125,5,5,-90,90);
    		arc2.setType(ArcType.OPEN);
    		arc2.setFill(Color.WHITE);
    		arc2.setStroke(Color.BLUE);
    		arc2.setStrokeWidth(1);

    		Arc arc3=new Arc(105,35,5,5,90,90);
    		arc3.setType(ArcType.OPEN);
    		arc3.setFill(Color.WHITE);
    		arc3.setStroke(Color.BLUE);
    		arc3.setStrokeWidth(1);

    		Arc arc4=new Arc(120,70,5,5,90,90);
    		arc4.setType(ArcType.OPEN);
    		arc4.setFill(Color.WHITE);
    		arc4.setStroke(Color.BLUE);
    		arc4.setStrokeWidth(1);

    		Arc arc5=new Arc(155,65,10,10,-90,180);
    		arc5.setType(ArcType.OPEN);
    		arc5.setStroke(Color.BLACK);
    		arc5.setFill(null);
    		arc5.setStrokeWidth(1.5);

    		Arc arc6=new Arc(155,30,10,10,0,180);
    		arc6.setType(ArcType.OPEN);
    		arc6.setStroke(Color.BLACK);
    		arc6.setFill(null);
    		arc6.setStrokeWidth(1.5);

    		arc5.centerYProperty().bind(l9.endYProperty().subtract(10));
    		l10.startYProperty().bind(arc5.centerYProperty().subtract(10));
    		l10.endYProperty().bind(l10.startYProperty().subtract(30));


    		arc6.centerXProperty().bind(l11.endXProperty().add(10));
    		l12.startXProperty().bind(arc6.centerXProperty().add(10));





    		getChildren().addAll(c1,c2,c3);
    		getChildren().addAll(l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12);
    		getChildren().addAll(arc1,arc2,arc3,arc4,arc5,arc6);

    		Button again= new Button("Again ?");
            again.setLayoutX(170);
            again.setLayoutY(250);



            Text collision = new Text("Collision has been occured.\nPlease try again.");
            collision.setLayoutX(10);
            collision.setLayoutY(260);

         // We enlarged and shrinked circles when mouse entered or existed.
            c1.setOnMouseEntered(e -> { c1.setRadius(c1.getRadius()+5); });
            c2.setOnMouseEntered(e -> { c2.setRadius(c2.getRadius()+5); });
            c3.setOnMouseEntered(e -> { c3.setRadius(c3.getRadius()+5); });
            c1.setOnMouseExited(e -> { c1.setRadius(c1.getRadius()-5); });
            c2.setOnMouseExited(e -> { c2.setRadius(c2.getRadius()-5); });
            c3.setOnMouseExited(e -> { c3.setRadius(c3.getRadius()-5); });


            allelements.add(l1);allelements.add(l2);allelements.add(l3);allelements.add(l4);allelements.add(l5);allelements.add(l6);allelements.add(l7);allelements.add(l8);
            allelements.add(l9);allelements.add(l10);allelements.add(l11);allelements.add(l12);
            allelements.add(arc1);allelements.add(arc2);allelements.add(arc3);allelements.add(arc4);
            allelements.add(arc5);allelements.add(arc6);allelements.add(c1);allelements.add(c2);allelements.add(c3);

            GetBack2(allelements);

            setOnMouseClicked(e-> {
                if(e.getTarget() == c2){

                    Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
                        l8.setEndX(l8.getEndX()-3); } ) );

                    l7.setStartY(l7.getStartY()-4);
                    l7.setEndY(l7.getEndY()+4);
                    ArrayList<Node> arr = new ArrayList<>();
                    arr.add(c2);
                    arr.add(l2);
                    arr.add(arc2);
                    arr.add(l6);
                    arr.add(arc4);
                    arr.add(l8);
                    arr.add(l7);
                    FadeAway(arr);
                    animation.setCycleCount(12);
                    animation.play();
                }
                else if(e.getTarget() == c1){
                	if(arc5.getCenterX() > l8.getEndX()){

                		  Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
  	                        l9.setEndY(l9.getEndY()+1.4);} ) );

                		l4.setStartX(l4.getStartX()-4);
  	                    l4.setEndX(l4.getEndX()+4);

  	                    ArrayList<Node> arr = new ArrayList<>();
  	                    arr.add(c1);
  	                    arr.add(l4);
  	                    arr.add(arc5);
  	                    arr.add(l9);
  	                    arr.add(arc1);
  	                    arr.add(l1);
  	                    arr.add(l10);
  	                    FadeAway(arr);
  	                    animation.setCycleCount(12);
  	                    animation.play();



                	}

                	else{
                		Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
  	                        l9.setEndY(l9.getEndY()+1.4); } ) );
                		 animation.setCycleCount(6);

	  	                    animation.play();

	  	                  getChildren().add(collision);
	  	                  getChildren().add(again);

	  	                  again.setOnMouseClicked(e1->{
	  	                	  l9.setEndY(l9.getEndY()-8.4);

	  	                	  getChildren().removeAll(l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,arc1,arc2,arc3,arc4,arc5,arc6,c1,c2,c3,collision,again);

	  	                	 ArrayList<Node> arr = new ArrayList<>();

	  	                	 arr.add(l1);arr.add(l2);arr.add(l3);arr.add(l4);arr.add(l5);arr.add(l6);arr.add(l7);arr.add(l8);arr.add(l9);arr.add(l10);arr.add(l11);arr.add(l12);arr.add(arc1);arr.add(arc2);arr.add(arc3);arr.add(arc4);
	  	                	 arr.add(arc5);arr.add(arc6);arr.add(c1);arr.add(c2);arr.add(c3);



	  	                	 GetBack(arr);


	  	                	 getChildren().addAll(l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,arc1,arc2,arc3,arc4,arc5,arc6,c1,c2,c3);



	  	                  });
                	}

                }
                else if(e.getTarget() == c3){
                	if(l10.getEndY()>arc6.getCenterY()){
                	Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
	                        l11.setEndX(l11.getEndX()-3); } ) );

                	l5.setStartY(l5.getStartY()-4);
                    l5.setEndY(l5.getEndY()+4);
                    ArrayList<Node> arr = new ArrayList<>();
                    arr.add(c3);
                    arr.add(l5);
                    arr.add(l11);
                    arr.add(arc6);
                    arr.add(l3);
                    arr.add(l12);
                    arr.add(arc3);
                    FadeAway(arr);
                    animation.setCycleCount(12);
                    animation.play();
                    Event.fireEvent(btNext, new MouseEvent(MouseEvent.MOUSE_CLICKED,
           				   btNext.getLayoutX(),btNext.getLayoutY(), btNext.getLayoutX(), btNext.getLayoutY(), MouseButton.PRIMARY, 1,
           				   true, true, true, true, true, true, true, true, true, true, null));


                	}
                	else{
                	;
                		Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
	                        l11.setEndX(l11.getEndX()-0.7);} ) );

                		 animation.setCycleCount(12);
 	                     animation.play();
                		 getChildren().add(collision);

                		 getChildren().add(again);

                		 again.setOnMouseClicked(e1->{

                			 if(l8.getEndX() != 160 ){
                				 l8.setEndX(l8.getEndX()+36);
 		  	                	l7.setStartY(l7.getStartY()+4);
 		  	                    l7.setEndY(l7.getEndY()-4);
                			 }
                			 getChildren().removeAll(l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,arc1,arc2,arc3,arc4,arc5,arc6,c1,c2,c3,collision,again);

                			  ArrayList<Node> arr = new ArrayList<>();
                			  l11.setEndX(l11.getEndX()+8.4);

                              	arr.add(l1);arr.add(l2);arr.add(l3);arr.add(l4);arr.add(l5);arr.add(l6);arr.add(l7);arr.add(l8);arr.add(l9);arr.add(l10);arr.add(l11);arr.add(l12);arr.add(arc1);arr.add(arc2);arr.add(arc3);arr.add(arc4);
		  	                	arr.add(arc5);arr.add(arc6);arr.add(c1);arr.add(c2);arr.add(c3);
		  	                	GetBack(arr);

		  	                	 getChildren().addAll(l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,arc1,arc2,arc3,arc4,arc5,arc6,c1,c2,c3,collision,again);







                		 });
                	}
                }


            });

            }





    		void FadeAway(ArrayList<Node> node){
            for(int i = 0 ; i<node.size();i++){
            FadeTransition ft = new FadeTransition(Duration.millis(1500),node.get(i));
            ft.setFromValue(1.0);
            ft.setToValue(0);
            ft.play();}
        }
        void GetBack(ArrayList<Node> node){
            for(int i = 0 ; i<node.size();i++){
            FadeTransition ft = new FadeTransition(Duration.millis(800),node.get(i));
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();}
        }

        void GetBack2(ArrayList<Node> node){
            for(int i = 0 ; i<node.size();i++){
            FadeTransition ft = new FadeTransition(Duration.millis(1250),node.get(i));
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();}
        }

    }

    class Level4 extends Pane{

    	Button btNext = new Button("Next");
    	Button btPrev = new Button("Previous");
    	ArrayList<Node> allelements= new ArrayList<Node>();

    	Level4(){
    		level4();


    	}

    	void level4(){

    		btPrev.setLayoutX(10);
        	btPrev.setLayoutY(220);
        	getChildren().add(btPrev);
    		// We designed Level 4 by using the objects on JavaFx itself


    		Circle c1=new Circle();
    		Circle c2=new Circle();


    		c1.setRadius(15);
    		c2.setRadius(15);


    		c1.setCenterX(30);
    		c1.setCenterY(140);

    		c2.setCenterX(90);
    		c2.setCenterY(200);


    		c1.setFill(Color.BLACK);
    		c2.setFill(Color.BLACK);


// We created disconnector here.

    		Disconnector dc= new Disconnector(90,140);



    		Line l1=new Line(45,140,79,140);
    		Line l2=new Line(90,151,90,185);
    		Line l3=new Line(101,140,145,140);
    		Line l4=new Line(143,134,155,134);
    		Line l5=new Line(150,132,150,30);
    		Line l6=new Line(90,129,90,40);
    		Line l7=new Line(97,29,97,41);
    		Line l8=new Line(99,35,140,35);
    		Line l9=new Line(160,35,173,35);

    		l1.setStroke(Color.BLUE);
    		l2.setStroke(Color.BLUE);
    		l3.setStroke(Color.BLUE);
    		l4.setStroke(Color.RED);
    		l5.setStroke(Color.BLACK);
    		l6.setStroke(Color.BLUE);
    		l7.setStroke(Color.RED);
    		l8.setStroke(Color.BLACK);
    		l9.setStroke(Color.BLACK);


    		l1.setStrokeWidth(1);
    		l2.setStrokeWidth(1);
    		l3.setStrokeWidth(1);
    		l4.setStrokeWidth(2.5);
    		l5.setStrokeWidth(1.5);
    		l6.setStrokeWidth(1);
    		l7.setStrokeWidth(2.5);
    		l8.setStrokeWidth(1.5);
    		l9.setStrokeWidth(1.5);

    		Arc arc1=new Arc(145,135,5,5,-90,90);
    		arc1.setType(ArcType.OPEN);
    		arc1.setFill(Color.WHITE);
    		arc1.setStroke(Color.BLUE);
    		arc1.setStrokeWidth(1);

    		Arc arc2=new Arc(95,40,5,5,90,90);
    		arc2.setType(ArcType.OPEN);
    		arc2.setFill(Color.WHITE);
    		arc2.setStroke(Color.BLUE);
    		arc2.setStrokeWidth(1);

    		Arc arc3=new Arc(150,35,10,10,0,180);
    		arc3.setType(ArcType.OPEN);
    		arc3.setStroke(Color.BLACK);
    		arc3.setFill(null);
    		arc3.setStrokeWidth(1.5);

    		arc3.centerXProperty().bind(l8.endXProperty().add(10));
    		l9.startXProperty().bind(arc3.centerXProperty().add(10));
    		l9.endXProperty().bind(l9.startXProperty().add(13));



    		Button again= new Button("Again ?");
            again.setLayoutX(170);
            again.setLayoutY(260);



            Text collision = new Text("Collision has been occured.\nPlease try again.");
            collision.setLayoutX(10);
            collision.setLayoutY(270);

            getChildren().addAll(c1,c2);
    		getChildren().addAll(l1,l2,l3,l4,l5,l6,l7,l8,l9);
    		getChildren().addAll(arc1,arc2,arc3);
    		getChildren().addAll(dc.circle,dc.line);

    		// We enlarged and shrinked circles when mouse entered or existed.


    		 c1.setOnMouseEntered(e -> { c1.setRadius(c1.getRadius()+5); });
    		 c2.setOnMouseEntered(e -> { c2.setRadius(c2.getRadius()+5); });
    		 c1.setOnMouseExited(e -> { c1.setRadius(c1.getRadius()-5); });
    		 c2.setOnMouseExited(e -> { c2.setRadius(c2.getRadius()-5); });


    		 allelements.add(l1);allelements.add(l2);allelements.add(l3);allelements.add(l4);allelements.add(l5);allelements.add(l6);
    		 allelements.add(l7);allelements.add(l8);allelements.add(l9);allelements.add(dc.circle);allelements.add(dc.line);
             allelements.add(arc1);allelements.add(arc2);allelements.add(arc3);allelements.add(c1);allelements.add(c2);

             GetBack2(allelements);

            setOnMouseClicked(e->{

  // Here we created event handler for disconnector. if event fires disconnector will turn 90 degrees.
             	if(e.getTarget()==dc.line||e.getTarget()==dc.circle){
            		Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
	                    dc.line.setRotate(dc.line.getRotate()+10); } ));
	    		animation.setCycleCount(9);
	    		animation.play();

	    		}


                if(e.getTarget()==c1){
            	if(dc.line.getRotate()%4!=0){
            		Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
	                        l5.setEndY(l5.getEndY()+3); } ) );

	                    l4.setStartX(l4.getStartX()-4);
	                    l4.setEndX(l4.getEndX()+4);
	                    ArrayList<Node> arr = new ArrayList<>();
	                    arr.add(c1);
	                    arr.add(l1);
	                    arr.add(arc1);
	                    arr.add(l5);
	                    arr.add(l4);
	                    arr.add(l3);

	                    FadeAway(arr);
	                    animation.setCycleCount(12);
	                    animation.play();
            	}
            	else{

            	}

            	}
            	else if(e.getTarget()==c2){

            		if(dc.line.getRotate()%4==0){

            				if(l5.getEndY()>arc3.getCenterY()){

            				Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
	                        l8.setEndX(l8.getEndX()-3);} ) );

	                    l7.setStartY(l7.getStartY()-4);
	                    l7.setEndY(l7.getEndY()+4);
	                    ArrayList<Node> arr = new ArrayList<>();
	                    arr.add(c2);
	                    arr.add(l2);
	                    arr.add(dc.line);
	                    arr.add(dc.circle);
	                    arr.add(l6);
	                    arr.add(arc2);
	                    arr.add(l7);
	                    arr.add(l8);
	                    arr.add(arc3);
	                    arr.add(l9);

	                    FadeAway(arr);
	                    animation.setCycleCount(12);
	                    animation.play();

	                    Event.fireEvent(btNext, new MouseEvent(MouseEvent.MOUSE_CLICKED,
	              				   btNext.getLayoutX(),btNext.getLayoutY(), btNext.getLayoutX(), btNext.getLayoutY(), MouseButton.PRIMARY, 1,
	              				   true, true, true, true, true, true, true, true, true, true, null));
            			}
            				else {

            				System.out.println("kfdspofsdf");
            				Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
		                        l8.setEndX(l8.getEndX()-0.8);} ) );
            				 animation.setCycleCount(12);
 		                    animation.play();
            				 getChildren().add(collision);
            				 getChildren().add(again);
            				 again.setOnMouseClicked(e1->{


            					 l8.setEndX(l8.getEndX()+9.6);

            					getChildren().removeAll(c1,c2,dc.circle,dc.line,arc1,arc2,arc3,l1,l2,l3,l4,l5,l6,l7,l8,l9,collision,again);

            					 ArrayList<Node> arr = new ArrayList<>();

            					 arr.add(c1);arr.add(c2);arr.add(dc.circle);arr.add(dc.line);arr.add(arc1);arr.add(arc2);arr.add(arc3);
            					 arr.add(l1);arr.add(l2);arr.add(l3);arr.add(l4);arr.add(l5);arr.add(l6);arr.add(l7);arr.add(l8);arr.add(l9);

            					 GetBack(arr);

            					 getChildren().addAll(c1,c2,dc.circle,dc.line,arc1,arc2,arc3,l1,l2,l3,l4,l5,l6,l7,l8,l9);
            				 });


            			}
            		}

            	}

            });




    	}
    	void FadeAway(ArrayList<Node> node){
            for(int i = 0 ; i<node.size();i++){
            FadeTransition ft = new FadeTransition(Duration.millis(1500),node.get(i));
            ft.setFromValue(1.0);
            ft.setToValue(0);
            ft.play();}
        }
        void GetBack(ArrayList<Node> node){
            for(int i = 0 ; i<node.size();i++){
            FadeTransition ft = new FadeTransition(Duration.millis(800),node.get(i));
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();}
        }
        void GetBack2(ArrayList<Node> node){
            for(int i = 0 ; i<node.size();i++){
            FadeTransition ft = new FadeTransition(Duration.millis(1250),node.get(i));
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();}
        }
    }

    class Level5 extends Pane{
    	ArrayList<Node> allelements= new ArrayList<Node>();
    	Button btPrev = new Button("Previous");
    	Button btNext = new Button("Next");

    	Level5(){
    		level5();}

    	void level5(){

    		// We designed level 5 by using classes we created before. These arc's for soft turnings.


    		HorizontalLineWithCircle line3 = new HorizontalLineWithCircle(100,50,15,40);
    		allelements.add(line3.leftline);allelements.add(line3.rightline);allelements.add(line3.semicircle);allelements.add(line3.smallline);

    		Arc arc3 =new Arc(0,0,5,5,90,90);
            arc3.centerXProperty().bind((line3.leftline).startXProperty());
            arc3.centerYProperty().bind((line3.leftline).startYProperty().add(arc3.radiusXProperty()));
            arc3.setStroke(Color.BLUE);
            arc3.setType(ArcType.OPEN);
            arc3.setFill(null);
            arc3.setStrokeWidth(1);
            allelements.add(arc3);




    		VerticalLineWithCircle line1 = new VerticalLineWithCircle((int)(line3.semicircle.getCenterY()-5),(int)(line3.semicircle.getCenterX()),30,8);
    		allelements.add(line1.bottomline);allelements.add(line1.semicircle);allelements.add(line1.smallline);allelements.add(line1.topline);

    		Arc arc1 =new Arc(0,0,5,5,0,-90);
    		arc1.setCenterX((line1.bottomline).getEndX()-5);
            arc1.setCenterY((line1.bottomline).getEndY());
            arc1.setStroke(Color.BLUE);
            arc1.setType(ArcType.OPEN);
            arc1.setFill(null);
            arc1.setStrokeWidth(1);
            allelements.add(arc1);

    		VerticalLineWithCircle line2 = new VerticalLineWithCircle((int)(line3.rightline.getEndY()-20),(int)(line3.rightline.getEndX()-5),80,10);
    		allelements.add(line2.bottomline);allelements.add(line2.semicircle);allelements.add(line2.smallline);allelements.add(line2.topline);

    		Arc arc2 =new Arc(0,0,5,5,0,-90);
            arc2.centerXProperty().bind((line2.bottomline).endXProperty().subtract(arc2.getRadiusX()));
            arc2.centerYProperty().bind((line2.bottomline).endYProperty());
            arc2.setStroke(Color.BLUE);
            arc2.setType(ArcType.OPEN);
            arc2.setFill(null);
            arc2.setStrokeWidth(1);
            allelements.add(arc2);


    		line2.semicircle.setLength(-180);





    		Line line4 = new Line();
    		line4.setStartX(line1.semicircle.getCenterX()-5);
    		line4.setStartY(line1.semicircle.getCenterY());
    		line4.setEndX(line4.getStartX()+20);
    		line4.setEndY(line4.getStartY());
    		line4.setStrokeWidth(1.5);
    		allelements.add(line4);

    		Line smallline4 = new Line();
    		smallline4.setStartX(line4.getEndX());
    		smallline4.setStartY(line4.getEndY()-6);
    		smallline4.setEndX(smallline4.getStartX());
    		smallline4.setEndY(smallline4.getStartY()+12);
    		smallline4.setStroke(Color.RED);
    		smallline4.setStrokeWidth(2.5);
    		allelements.add(smallline4);

    		Arc arc4 =new Arc(0,0,5,5,90,-90);
            arc4.centerXProperty().bind((line4).endXProperty());
            arc4.centerYProperty().bind((line4).endYProperty().add(arc4.getRadiusX()));
            arc4.setStroke(Color.BLUE);
            arc4.setType(ArcType.OPEN);
            arc4.setFill(null);
            arc4.setStrokeWidth(1);
            allelements.add(arc4);

            getChildren().addAll(line2.bottomline , line2.semicircle , line2.smallline , line2.topline , arc2);
    		getChildren().addAll(line3.smallline , line3.leftline , line3.rightline , line3.semicircle , arc3);
    		getChildren().addAll(line1.bottomline , line1.semicircle , line1.smallline , line1.topline , arc1);
    		getChildren().addAll(arc4 , line4 , smallline4);


            Disconnector disconnector1 = new Disconnector((int)(line3.leftline.getStartX()-arc3.getRadiusX()),(int)(line1.bottomline.getEndY()+arc1.getRadiusX()));
            allelements.add(disconnector1.circle);allelements.add(disconnector1.line);

            disconnector1.circle.setFill(Color.WHITE);
            getChildren().add(disconnector1.circle);
    		getChildren().add(disconnector1.line);

    		Disconnector disconnector2 = new Disconnector((int)(line3.leftline.getStartX()-arc3.getRadiusX()),(int)(line2.bottomline.getEndY()+arc1.getRadiusX()));
    		allelements.add(disconnector2.circle);allelements.add(disconnector2.line);
            getChildren().add(disconnector2.circle);
     		getChildren().add(disconnector2.line);

     		Line blueline31 = new Line(line3.leftline.getStartX()-arc3.getRadiusX(),line3.leftline.getStartY()+arc3.getRadiusX(),line3.leftline.getStartX()-arc3.getRadiusX(),disconnector1.circle.getCenterY()-disconnector1.circle.getRadius());
     		Line blueline32 = new Line(blueline31.getStartX(),blueline31.getEndY()+(2*disconnector1.circle.getRadius()),blueline31.getEndX(),disconnector2.circle.getCenterY()-disconnector2.circle.getRadius());
     		Line blueline33 = new Line(blueline31.getEndX(),blueline32.getEndY()+(2*disconnector2.circle.getRadius()),blueline32.getEndX(),blueline32.getEndY()+(2*disconnector2.circle.getRadius())+20);
     		allelements.add(blueline31);allelements.add(blueline32);allelements.add(blueline33);

     		blueline31.setStrokeWidth(1);
     		blueline31.setStroke(Color.BLUE);
     		blueline32.setStrokeWidth(1);
     		blueline32.setStroke(Color.BLUE);
     		blueline33.setStrokeWidth(1);
     		blueline33.setStroke(Color.BLUE);
     		getChildren().addAll(blueline31,blueline32,blueline33);

     		Line blueline11 = new Line(disconnector1.circle.getCenterX()-disconnector1.circle.getRadius()-30,disconnector1.circle.getCenterY(),disconnector1.circle.getCenterX()-disconnector1.circle.getRadius(),disconnector1.circle.getCenterY());
     		Line blueline12 = new Line(disconnector1.circle.getCenterX()+disconnector1.circle.getRadius(),disconnector1.circle.getCenterY(),line1.bottomline.getEndX()-arc1.getRadiusX(),disconnector1.circle.getCenterY());
     		blueline11.setStroke(Color.BLUE);
     		blueline11.setStrokeWidth(1);
     		blueline12.setStroke(Color.BLUE);
     		blueline12.setStrokeWidth(1);
     		getChildren().addAll(blueline11 , blueline12);
     		allelements.add(blueline11);allelements.add(blueline12);

     		Line blueline21 = new Line(disconnector2.circle.getCenterX()-disconnector2.circle.getRadius()-30,disconnector2.circle.getCenterY(),disconnector2.circle.getCenterX()-disconnector2.circle.getRadius(),disconnector2.circle.getCenterY());
     		Line blueline22 = new Line(disconnector2.circle.getCenterX()+disconnector2.circle.getRadius(),disconnector2.circle.getCenterY(),line2.bottomline.getEndX()-arc2.getRadiusX(),disconnector2.circle.getCenterY());
     		blueline21.setStroke(Color.BLUE);
     		blueline21.setStrokeWidth(1);
     		blueline22.setStroke(Color.BLUE);
     		blueline22.setStrokeWidth(1);
     		getChildren().addAll(blueline21 , blueline22);
     		allelements.add(blueline21);allelements.add(blueline22);

     		Line blueline4 = new Line (line4.getEndX()+arc4.getRadiusX(),line4.getEndY()+arc4.getRadiusX(),line4.getEndX()+arc4.getRadiusX(),blueline33.getEndY());
     		blueline4.setStroke(Color.BLUE);
     		blueline4.setStrokeWidth(1);
     		getChildren().add(blueline4);
     		allelements.add(blueline4);

     		Circle filled1 = new Circle(blueline11.getStartX()-15,disconnector1.circle.getCenterY(),15);
     		Circle filled2 = new Circle(blueline21.getStartX()-15,disconnector2.circle.getCenterY(),15);
     		Circle filled3 = new Circle(disconnector2.circle.getCenterX(),blueline33.getEndY()+15,15);
     		Circle filled4 = new Circle(blueline4.getEndX(),filled3.getCenterY(),15);
     		filled1.setFill(Color.BLACK);
     		filled2.setFill(Color.BLACK);
     		filled3.setFill(Color.BLACK);
     		filled4.setFill(Color.BLACK);

     		getChildren().addAll(filled1,filled2,filled3,filled4);
     		allelements.add(filled1);allelements.add(filled2);allelements.add(filled3);allelements.add(filled4);

     		GetBack2(allelements);


     	// This eventhandlers turns the disconnectors when they are clicked.
     		disconnector1.circle.setOnMouseClicked(e -> {
     			Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
                    disconnector1.line.setRotate(disconnector1.line.getRotate()+10); } ));
    		animation.setCycleCount(9);
    		animation.play();

     		});

     		disconnector2.circle.setOnMouseClicked(e -> {
     			Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
                    disconnector2.line.setRotate(disconnector2.line.getRotate()+10); } ));
    		animation.setCycleCount(9);
    		animation.play();

     		});

     		Button again= new Button("Again ?");
            again.setLayoutX(60);
            again.setLayoutY(265);

            btPrev.setLayoutX(15);
            btPrev.setLayoutY(150);



            Text collision = new Text("Collision has been occured.\nPlease try again.");
            collision.setLayoutX(10);
            collision.setLayoutY(230);


         // We enlarged and shrinked circles when mouse entered or existed.
            filled1.setOnMouseEntered(e -> { filled1.setRadius(filled1.getRadius()+5); });
            filled1.setOnMouseExited(e -> { filled1.setRadius(filled1.getRadius()-5); });
            filled2.setOnMouseEntered(e -> { filled2.setRadius(filled2.getRadius()+5); });
            filled2.setOnMouseExited(e -> { filled2.setRadius(filled2.getRadius()-5); });
            filled3.setOnMouseEntered(e -> { filled3.setRadius(filled3.getRadius()+5); });
            filled3.setOnMouseExited(e -> { filled3.setRadius(filled3.getRadius()-5); });
            filled4.setOnMouseEntered(e -> { filled4.setRadius(filled4.getRadius()+5); });
            filled4.setOnMouseExited(e -> { filled4.setRadius(filled4.getRadius()-5); });

    		setOnMouseClicked(e -> {
    			if(e.getTarget() == filled4 ){
    				Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
                        line4.setStartX(line4.getStartX()+2); } ) );

                    smallline4.setStartY(smallline4.getStartY()-4);
                    smallline4.setEndY(smallline4.getEndY()+4);
                    ArrayList<Node> arr = new ArrayList<>();
                    arr.add(smallline4);
                    arr.add(line4);
                    arr.add(filled4);
                    arr.add(blueline4);
                    arr.add(arc4);
                    FadeAway(arr);
                    animation.setCycleCount(10);
                    animation.play();
    			}
    			else if(e.getTarget() == filled1){
    				if(disconnector1.line.getRotate()%4 != 0){
    					if(line4.getStartX() > line1.semicircle.getCenterX()){
    						Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
    	                        line1.bottomline.setStartY(line1.bottomline.getStartY()+3); } ) );
    						line1.semicircle.centerYProperty().bind(line1.bottomline.startYProperty().subtract(10));

                            line1.topline.endYProperty().bind(line1.semicircle.centerYProperty().subtract(10));
                            line1.topline.startYProperty().bind(line1.topline.endYProperty().subtract(8));


    	                    line1.smallline.setStartX(line1.smallline.getStartX()-4);
    	                    line1.smallline.setEndX(line1.smallline.getEndX()+4);
    	                    ArrayList<Node> arr = new ArrayList<>();
    	                    arr.add(line1.smallline);
    	                    arr.add(filled1);
    	                    arr.add(line1.bottomline);
    	                    arr.add(line1.semicircle);
    	                    arr.add(line1.topline);
    	                    arr.add(arc1);
    	                    arr.add(blueline11);
    	                    arr.add(blueline12);
    	                    FadeAway(arr);
    	                    animation.setCycleCount(10);
    	                    animation.play();



    				}
    					else {

   						Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
                                 line1.bottomline.setStartY(line1.bottomline.getStartY()+1); } ));
                       	  line1.semicircle.centerYProperty().bind(line1.bottomline.startYProperty().subtract(10));

                             line1.topline.endYProperty().bind(line1.semicircle.centerYProperty().subtract(10));
                             line1.topline.startYProperty().bind(line1.topline.endYProperty().subtract(8));

                       	  animation.setCycleCount(8);
                             animation.play();
                             getChildren().add(again);
                             getChildren().add(collision);
                             again.setOnMouseClicked(e2 -> {
                            	 getChildren().removeAll(again,collision);
                            	 line1.bottomline.setStartY(line1.bottomline.getStartY()-8);
                             });

    					}}


    			}
    			if(e.getTarget() == filled3){
    				System.out.println("5");
    				if((disconnector1.line.getRotate()%4 == 0) && (disconnector2.line.getRotate() == 0)){
    					if(line3.semicircle.getCenterY() < line1.topline.getStartY()){

    						Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
    	                        line3.leftline.setEndX(line3.leftline.getEndX() - 1.5); } ) );

    	                    line3.smallline.setStartY(line3.smallline.getStartY()-4);
    	                    line3.smallline.setEndY(line3.smallline.getEndY()+4);
    	                    ArrayList<Node> arr = new ArrayList<>();
    	                    arr.add(line3.smallline);
    	                    arr.add(filled3);
    	                    arr.add(line3.leftline);
    	                    arr.add(line3.semicircle);
    	                    arr.add(line3.rightline);
    	                    arr.add(arc3);
    	                    arr.add(blueline31);
    	                    arr.add(blueline32);
    	                    arr.add(blueline33);
    	                    arr.add(disconnector1);
    	                    FadeAway(arr);
    	                    animation.setCycleCount(10);
    	                    animation.play();

    					}

    					else{
    						Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
                                line3.leftline.setEndX(line3.leftline.getEndX()-1); } ));
                      	  line3.semicircle.centerXProperty().bind(line3.leftline.endXProperty().add(10));

                            line3.rightline.startXProperty().bind(line3.semicircle.centerXProperty().add(10));
                            line3.rightline.endXProperty().bind(line3.rightline.startXProperty().add(40));

                            animation.setCycleCount(8);
                            animation.play();
                            getChildren().add(again);
                            getChildren().add(collision);

                            again.setOnMouseClicked(e2 -> {
                            	if(line4.getStartX() != line1.semicircle.getCenterX()-5){
                            		line4.setStartX(line1.semicircle.getCenterX()-5);
                            		smallline4.setStartY(smallline4.getStartY()+4);
                                    smallline4.setEndY(smallline4.getEndY()-4);
                            		 ArrayList<Node> arr = new ArrayList<>();
                            		 arr.add(line4);
                            		 arr.add(arc4);
                            		 arr.add(smallline4);
                            		 arr.add(filled4);
                            		 arr.add(blueline4);
                            		 GetBack(arr);

                            		 getChildren().removeAll(again,collision);


                            	}

                            	 line3.leftline.setEndX(line3.leftline.getEndX()+8);

                            });

    					}



    				}

    			}
    			if(e.getTarget() == filled2){
    				if(disconnector2.line.getRotate()%4 != 0){
    					if(line2.semicircle.getCenterX() > line3.rightline.getEndX()){


    						Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
    	                        line2.bottomline.setStartY(line2.bottomline.getStartY() + 8); } ) );
    						line2.semicircle.centerYProperty().bind(line2.bottomline.startYProperty().subtract(10));
    						line2.topline.endYProperty().bind(line2.semicircle.centerYProperty().subtract(10));
                            line2.topline.startYProperty().bind(line2.topline.endYProperty().subtract(10));

    	                    line2.smallline.setStartX(line2.smallline.getStartX()-4);
    	                    line2.smallline.setEndX(line2.smallline.getEndX()+4);
    	                    ArrayList<Node> arr = new ArrayList<>();
    	                    arr.add(line2.smallline);
    	                    arr.add(filled2);
    	                    arr.add(line2.bottomline);
    	                    arr.add(line2.semicircle);
    	                    arr.add(line2.topline);
    	                    arr.add(arc2);
    	                    arr.add(blueline21);
    	                    arr.add(blueline22);


    	                    FadeAway(arr);
    	                    animation.setCycleCount(10);
    	                    animation.play();

    	                    Event.fireEvent(btNext, new MouseEvent(MouseEvent.MOUSE_CLICKED,
 	              				   btNext.getLayoutX(),btNext.getLayoutY(), btNext.getLayoutX(), btNext.getLayoutY(), MouseButton.PRIMARY, 1,
 	              				   true, true, true, true, true, true, true, true, true, true, null));

    					}

    					else{
    						Timeline animation = new Timeline(new KeyFrame(Duration.millis(60) , e1 -> {
    	                        line2.bottomline.setStartY(line2.bottomline.getStartY() + 1); } ) );
    						line2.semicircle.centerYProperty().bind(line2.bottomline.startYProperty().subtract(10));
    						line2.topline.endYProperty().bind(line2.semicircle.centerYProperty().subtract(10));
                            line2.topline.startYProperty().bind(line2.topline.endYProperty().subtract(10));

                            animation.setCycleCount(8);
                            animation.play();
                            getChildren().add(again);
                            getChildren().add(collision);

                            again.setOnMouseClicked(e2 -> {
                            	if(line4.getStartX() != line1.semicircle.getCenterX()-5){

                            		line4.setStartX(line1.semicircle.getCenterX()-5);

                            		getChildren().removeAll(collision,again);

                            		smallline4.setStartY(smallline4.getStartY()+4);
                                    smallline4.setEndY(smallline4.getEndY()-4);

                                   ArrayList<Node> arr = new ArrayList<>();
                            		 arr.add(line4);
                            		 arr.add(arc4);
                            		 arr.add(smallline4);
                            		 arr.add(filled4);
                            		 arr.add(blueline4);
                            		 GetBack(arr);




                            	}

                            	if(line1.bottomline.getStartY() != line3.semicircle.getCenterY()+23){
                            		line1.bottomline.setStartY(line3.semicircle.getCenterY()+23);

                            		line1.smallline.setStartX(line1.smallline.getStartX()+4);
                                    line1.smallline.setEndX(line1.smallline.getEndX()-4);

                                   ArrayList<Node> arr = new ArrayList<>();
                            		 arr.add(line1.bottomline);
                            		 arr.add(arc1);
                            		 arr.add(line1.smallline);
                            		 arr.add(filled1);
                            		 arr.add(blueline11);
                            		 arr.add(blueline12);
                            		 arr.add(line1.semicircle);
                            		 arr.add(line1.topline);
                            		 GetBack(arr);




                            	}
                            	 getChildren().removeAll(again,collision);
                            	 line2.bottomline.setStartY(line2.bottomline.getStartY() - 8);

                            });



    					}

    				}

    			}
    		});

    	}

    	  void FadeAway(ArrayList<Node> node){
              for(int i = 0 ; i<node.size();i++){
              FadeTransition ft = new FadeTransition(Duration.millis(1500),node.get(i));
              ft.setFromValue(1.0);
              ft.setToValue(0);
              ft.play();}
          }
          void GetBack(ArrayList<Node> node){
              for(int i = 0 ; i<node.size();i++){
              FadeTransition ft = new FadeTransition(Duration.millis(800),node.get(i));
              ft.setFromValue(0);
              ft.setToValue(1);
              ft.play();}
          }
          void GetBack2(ArrayList<Node> node){
              for(int i = 0 ; i<node.size();i++){
              FadeTransition ft = new FadeTransition(Duration.millis(1250),node.get(i));
              ft.setFromValue(0);
              ft.setToValue(1);
              ft.play();}
          }



    }

    // This class creates horizontal lines with semi-circle by taking the starting point,left line length and right line length.

    class HorizontalLineWithCircle extends Pane{

        Line leftline = new Line();
        Line rightline =new Line();
        Arc semicircle =new Arc();
        Line smallline = new Line();

        HorizontalLineWithCircle(int xcoordinate,int ycoordinate,int left,int right){
            leftline.setStartX(xcoordinate);
            leftline.setEndX(xcoordinate+left);
            leftline.setStartY(ycoordinate);
            leftline.setEndY(ycoordinate);
            leftline.setStroke(Color.BLACK);
            leftline.setStrokeWidth(1.5);




            semicircle.setCenterX(xcoordinate+right+10);
            semicircle.setCenterY(ycoordinate);
            semicircle.setRadiusX(10);
            semicircle.setRadiusY(10);
            semicircle.setStartAngle(0);
            semicircle.setLength(180);
            semicircle.centerXProperty().bind(leftline.endXProperty().add(10));
            semicircle.setStroke(Color.BLACK);
            semicircle.setType(ArcType.OPEN);
            semicircle.setFill(Color.WHITE);
            semicircle.setStrokeWidth(1.5);
            semicircle.setFill(null);



            rightline.setStartX((leftline.getEndX())+20);
            rightline.setStartY(ycoordinate);
            rightline.setEndX((leftline.getEndX()+left+20));
            rightline.setEndY(ycoordinate);
            rightline.startXProperty().bind(leftline.endXProperty().add(20));
            rightline.endXProperty().bind(rightline.startXProperty().add(right));
            rightline.setStroke(Color.BLACK);
            rightline.setStrokeWidth(1.5);



            smallline.startXProperty().bind(leftline.startXProperty());
            smallline.endXProperty().bind(leftline.startXProperty());
            smallline.setStartY(leftline.getStartY()-6);
            smallline.setEndY(leftline.getStartY()+6);
            smallline.setStrokeWidth(2.5);
            smallline.setStroke(Color.RED);


        }
    }


    // This class creates vertical lines with semi-circle by taking the starting point,top line length and bottom line length.

    class VerticalLineWithCircle extends Pane{
        Line topline = new Line();
        Line bottomline =new Line();
        Arc semicircle =new Arc();
        Line smallline = new Line();

        VerticalLineWithCircle(int xcoordinate , int ycoordinate , int bottom , int top){
            topline.setStartY(xcoordinate);
            topline.setEndY(xcoordinate+top);
            topline.setStartX(ycoordinate);
            topline.setEndX(ycoordinate);
            topline.setStroke(Color.BLACK);
            topline.setStrokeWidth(1.5);




            semicircle.setCenterY(xcoordinate+bottom+10);
            semicircle.setCenterX(ycoordinate);
            semicircle.setRadiusX(10);
            semicircle.setRadiusY(10);
            semicircle.setStartAngle(90);
            semicircle.setLength(180);
            semicircle.centerYProperty().bind(topline.endYProperty().add(10));;
            semicircle.setStroke(Color.BLACK);
            semicircle.setType(ArcType.OPEN);
            semicircle.setFill(null);
            semicircle.setStrokeWidth(1.5);



            bottomline.setStartY((topline.getEndY())+20);
            bottomline.setStartX(ycoordinate);
            bottomline.setEndY((topline.getEndY()+bottom+20));
            bottomline.setEndX(ycoordinate);
            bottomline.setStroke(Color.BLACK);
            bottomline.setStrokeWidth(1.5);



            smallline.startYProperty().bind(bottomline.endYProperty());
            smallline.endYProperty().bind(bottomline.endYProperty());
            smallline.setStartX(bottomline.getStartX()-6);
            smallline.setEndX(bottomline.getStartX()+6);
            smallline.setStrokeWidth(2.5);
            smallline.setStroke(Color.RED);


        }



    }

    // This class creates disconnector by taking the center of it.

    class Disconnector extends Pane{

    	Circle circle= new Circle();
    	Line line = new Line();

    	Disconnector(int centerx,int centery){

        	circle.setCenterX(centerx);
        	circle.setCenterY(centery);
        	circle.setRadius(10);
        	circle.setFill(Color.WHITE);
        	circle.setStrokeWidth(1);

        	line.setStartX(centerx);
        	line.setEndX(centerx);
        	line.setStartY(centery-9);
        	line.setEndY(centery+9);

        	circle.setStroke(Color.CHOCOLATE);

        	line.setStroke(Color.CHOCOLATE);


        }

    }



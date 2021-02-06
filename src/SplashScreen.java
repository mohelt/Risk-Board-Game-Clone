import javax.swing.*;
import java.awt.*;

public class SplashScreen {
	
	//variables 
    JFrame frameSplash;
    //adds an image
    JLabel image=new JLabel(new ImageIcon("./src/images/risk.jpg"));
    
    //adds a text
    JLabel text=new JLabel("Risk By WhyNotBot");
    
    //adds the progress bar
    JProgressBar progressBar=new JProgressBar();
    
    //adds a message
    JLabel message=new JLabel();
    
    
    SplashScreen()
    {
    	//creates splash screen
        createGUIForApp();
        addImageToJFrame();
        addTextToJPanel();
        addProgressBarToJFrame();
        addMessageToJFrame();
        runningPBar();
        
        //makes sure the splash screen correctly disappears
        frameSplash.dispose();
    }
    public void createGUIForApp(){
    	
    	//creates a new jframe with correct sizes and looks
        frameSplash=new JFrame();
        frameSplash.getContentPane().setLayout(null);
        frameSplash.setUndecorated(true);
        frameSplash.setSize(600,400);
        frameSplash.setLocationRelativeTo(null);
        frameSplash.getContentPane().setBackground(Color.BLACK);
        frameSplash.setVisible(true);

    }
    
    public void addImageToJFrame(){
    	//adds image to the splash screen
        image.setSize(600,200);
        frameSplash.add(image);
    }
    public void addTextToJPanel()
    {
    	//adds text to the splash screen in correct font and size
        text.setFont(new Font("Helvetica",Font.BOLD,35));
        text.setBounds(170,220,600,40);
        text.setForeground(Color.WHITE);
        frameSplash.add(text);
    }
    public void addMessageToJFrame()
    {
    	//adds message to the splash screen in correct font and size
        message.setBounds(250,320,200,40);
        message.setForeground(Color.WHITE);
        message.setFont(new Font("Helvetica",Font.BOLD,35));
        frameSplash.add(message);
    }
    public void addProgressBarToJFrame(){
    	//creates progress bar with correct size and colors
        progressBar.setBounds(100,280,400,30);
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setBackground(Color.WHITE);
        progressBar.setForeground(Color.BLACK);
        progressBar.setValue(0);
        frameSplash.add(progressBar);
    }
    public void runningPBar(){
        int i=0;

        while( i<=100)
        {
            try{
            	//makes the splash screen appear on screen for a certain amount of time
                Thread.sleep(25);
                progressBar.setValue(i);
                
                //displays loading message
                message.setText("Loading...");
                i++;
                
                //deletes the jframe when the value of i reaches 100
                if(i==100 )
                    frameSplash.dispose();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}

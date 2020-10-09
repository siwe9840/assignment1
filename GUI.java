import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
public class GUI extends JFrame{
	/*--------------------------------
  Have NOT tested with the other files 
	  
  ----------------------------------------*/
  private final SocketClient socketClient;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
		
	//Labels
	
	
	
	
	private JLabel Port_label = new JLabel("Port #: ", SwingConstants.LEFT);
	

	
	
	
	//Buttons
	public static JButton SubmitButton = new JButton("SUBMIT REQUEST");
	public static JButton Clear= new JButton("CLEAR");
	public static JButton Connect= new JButton("Connect");
	public static JButton Disconnect= new JButton("Disconnect ");


	
	//TextFields
	
	public JLabel IP_label = new JLabel("IP Address: ", SwingConstants.LEFT);
	
	public static JTextField IPtxt = new JTextField();
	
	public JLabel Request_label = new JLabel("Request: ", SwingConstants.LEFT);

	public static JTextField PORTtxt  = new JTextField();

	public JLabel Response_label = new JLabel("Response: ", SwingConstants.LEFT);

	public static JTextField REQUESTtxt = new JTextField();

	public static JTextField RESPONSEtxt = new JTextField();
	
	public static JTextField ISBNtxt = new JTextField();
	public static JTextField TITLEtxt = new JTextField(); 
	public static JTextField PUBLISHERtxt = new JTextField();
	public static JTextField AUTHORtxt = new JTextField(); 
	public static JTextField YEARtxt = new JTextField(); 

	public JLabel ISBNlabel = new JLabel("ISBN: ", SwingConstants.LEFT);
	public JLabel TITLElabel = new JLabel("TITLE: ", SwingConstants.LEFT);
	public JLabel PUBLISHERlabel = new JLabel("PUBLISHER: ", SwingConstants.LEFT);
	public JLabel AUTHORlabel = new JLabel("AUTHOR: ", SwingConstants.LEFT);
	public JLabel YEARlabel = new JLabel("YEAR: ", SwingConstants.LEFT);

	
    public GUI() {
       // setTitle("CP372 Assignment 1 App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300,300);
		setVisible(true);
		
		layoutView();
	
        socketClient = new SocketClient();
        
    }
	public void settingFonts() {
		
		//this.go.setFont(new Font("Verdana", Font.PLAIN, 18));
		
		//this.Port_label.setFont(new Font("Verdana", Font.PLAIN, 18));

		




		
	}
	public void addElements() {
	SubmitButton.setBackground(Color.GREEN);
	SubmitButton.setOpaque(true);
	
	
	this.add(IP_label);
	this.add(IPtxt);
	this.add(Port_label);



	this.add(PORTtxt);

	this.add(Request_label);
	this.add(REQUESTtxt);
	
	
	
	this.add(ISBNlabel);
	this.add(ISBNtxt);
	this.add(TITLElabel);
	this.add(TITLEtxt);
	this.add(AUTHORlabel);
	this.add(AUTHORtxt);
	this.add(PUBLISHERlabel);
	this.add(PUBLISHERtxt);
	this.add(YEARlabel);
	this.add(YEARtxt);

	
	
	this.add(SubmitButton);
	this.add(Clear);
	this.add(Response_label);
	this.add(RESPONSEtxt);	
	this.add(Connect);
	this.add(Disconnect);
		
	

		
	}
	
	public void SubmitButtonHandler(ActionEvent e) {
		System.out.println("Submit pressed");
		System.out.println(REQUESTtxt.getText());
		  String ISBN = ISBNtxt.getText().trim();
          // Handle Title
          String TITLE = TITLEtxt.getText().trim();
          // Handle Author
          String AUTHOR = AUTHORtxt.getText().trim();
          // Handle Publisher
          String PUBLISHER = PUBLISHERtxt.getText().trim();
          // Handle Year
          int YEAR = 0;
          if (YEARtxt.getText().length() > 0)
              try {
                   YEAR = Integer.parseInt(YEARtxt.getText());
              } catch (NumberFormatException exception) {
                  JOptionPane.showMessageDialog(this, "Invalid Year", "Error", JOptionPane.ERROR_MESSAGE);
                  return;
              }
      
        	//String[] reqData = {ISBN, TITLE, AUTHOR, PUBLISHER, YEAR};
        
          //send request and update response box with server response
		if(REQUESTtxt.getText().toLowerCase().trim().equals( "submit")) {
		
			try {
				System.out.println("submit type");
				RESPONSEtxt.setText(socketClient.sendMessage(Request.SUBMIT, ISBN, TITLE, AUTHOR, PUBLISHER, YEAR, true ));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(REQUESTtxt.getText().toLowerCase().trim() == "get") {
			try {
				RESPONSEtxt.setText(socketClient.sendMessage(Request.GET, ISBN, TITLE, AUTHOR, PUBLISHER, YEAR, true ));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(REQUESTtxt.getText().toLowerCase().trim() == "remove") {
			try {
				RESPONSEtxt.setText(socketClient.sendMessage(Request.REMOVE, ISBN, TITLE, AUTHOR, PUBLISHER, YEAR, true ));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(REQUESTtxt.getText().toLowerCase().trim() == "update") {
			try {
				RESPONSEtxt.setText(socketClient.sendMessage(Request.UPDATE, ISBN, TITLE, AUTHOR, PUBLISHER, YEAR, true ));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		
	}	
	
	public void ClearButtonHandler(ActionEvent e) {
		IPtxt.setText("");
		ISBNtxt.setText("");
		TITLEtxt.setText("");
		AUTHORtxt.setText("");
		PUBLISHERtxt.setText("");
		YEARtxt.setText("");

	}
	
	public void ConnectButtonHandler(ActionEvent e){
		socketClient.connect(IPtxt.getText(), Integer.parseInt(PORTtxt.getText()));
		
	}
	
	public void DisconnectButtonHandler(ActionEvent e) {
		try {
			socketClient.stopConnection();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}

	
	public void layoutView() {
		this.setLayout(new GridLayout(0,2,4,1));
		this.settingFonts();
		this.addElements();
		

		
		SubmitButton.addActionListener(this::SubmitButtonHandler);
		Clear.addActionListener(this::ClearButtonHandler);
		Connect.addActionListener(this::ConnectButtonHandler);
		Disconnect.addActionListener(this::DisconnectButtonHandler);
		this.setTitle("Server GUI");
	
		
		
	}
	
	
	
	
	

}

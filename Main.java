import java.awt.BorderLayout;  
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;





public class Main {
	// This is the main file with the UI
	public static DrawingPanel drawingPanel = new DrawingPanel();
	public static GUI guiPanel = new GUI(); 
	
	public static void main(String[] args) throws Exception{
		SwingUtilities.invokeLater(new Runnable() {
		    @Override
		    public void run() {
		    	//set up frame
				JFrame frame = new JFrame();
				frame.setTitle("WaferMap");
				frame.setSize(1286, 829);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);
				frame.setVisible(true);
				
				//set up panel and layout
				JPanel panel = new JPanel();
				panel.setLayout(new BorderLayout(2,2));
				
				//create panel objects to fill frame
				
				
			    
			    		//GUI Panel Information
						
							JLabel currentXLabel = new JLabel("X: 0");
							JLabel currentYLabel = new JLabel("Y: 0");
							
							currentXLabel.setPreferredSize(new Dimension(100,40));
							currentYLabel.setPreferredSize(new Dimension(100,40));
							
							MouseListener listener = new MouseAdapter() {
								@Override
								public void mouseClicked(MouseEvent arg0) {
									String lastClickedX = Integer.toString(drawingPanel.getLastClickedX());
									String lastClickedY = Integer.toString(drawingPanel.getLastClickedY());
									
									currentXLabel.setText("x = "+lastClickedX);
									currentYLabel.setText("y = "+lastClickedY);
								}
							};
							//Adding MouseListeners
							
							drawingPanel.addMouseListener(listener);
				
							
							
							//Creating GUI Components
							
							//TextField for Graph Size X
							JNumberTextField textfieldWaferSizeX = new JNumberTextField();
							textfieldWaferSizeX.setPreferredSize(new Dimension(100, 30));
							
							JLabel labelWaferSizeX = new JLabel("Wafer Size X");
							labelWaferSizeX.setPreferredSize(new Dimension(100,20));
							

							
							//Graph Size Label
							JLabel labelGraphSize = new JLabel("Graph Size: 0");
							labelGraphSize.setPreferredSize(new Dimension(100,40));
							
							
							
							//Button for updating
						    JButton buttonDotSize = new JButton("Enter");
							buttonDotSize.setPreferredSize(new Dimension(100, 30));
							
							//Dot size X textfield
							JNumberTextField dotSizeTextFieldX = new JNumberTextField();
							dotSizeTextFieldX.setPreferredSize(new Dimension(100, 30));
							
							JLabel dotSizeX = new JLabel("Dot Size X");
							dotSizeX.setPreferredSize(new Dimension(100,20));
							
							//Dot size Y textfield
							JNumberTextField dotSizeTextFieldY = new JNumberTextField();
							dotSizeTextFieldY.setPreferredSize(new Dimension(100, 30));
							
							JLabel dotSizeY = new JLabel("Dot Size Y");
							dotSizeY.setPreferredSize(new Dimension(100,20));
							
							//Dot size Editable Labels
							JLabel dotSizeLabelX = new JLabel("Die Size: 0");
							dotSizeLabelX.setPreferredSize(new Dimension(100,40));
							
							JLabel dotSizeLabelY = new JLabel("Die Size: 0");
							dotSizeLabelY.setPreferredSize(new Dimension(100,40));
							
							String colors[]={"Red","Blue","Green","Gray","White"};        

							JComboBox<?> colorChooser=new JComboBox<Object>(colors);
							colorChooser.setPreferredSize(new Dimension(100,40));
							
						    JLabel colorLabel = new JLabel("color selected: null");
							colorLabel.setPreferredSize(new Dimension(130,60));
							JCheckBox checkbox = new JCheckBox("Make Wafer Round");
						    checkbox.setMnemonic(KeyEvent.VK_C);
						    //chinButton.setSelected(true);
						    checkbox.addItemListener(new ItemListener() {
					            public void itemStateChanged(ItemEvent e) {
					            	if(e.getStateChange()==1) {
					            		drawingPanel.setSelected(true);
					            	}
					            	else {
					            		drawingPanel.setSelected(false);
					            	}
					            }
					        });

							JButton buttonSave = new JButton("File Save");
							buttonSave.setPreferredSize(new Dimension(100, 30));
							
							JButton buttonOpen = new JButton("File Open");
							buttonOpen.setPreferredSize(new Dimension(100, 30));
							
							JButton buttonSocket = new JButton("Socket Read");
							buttonOpen.setPreferredSize(new Dimension(100, 30));
						    
							JTextField saveName = new JTextField();
							saveName.setPreferredSize(new Dimension(100, 30));
							
							colorChooser.addActionListener (new ActionListener () {
							    public void actionPerformed(ActionEvent e) {
								    if(colorChooser.getItemAt(colorChooser.getSelectedIndex()) == "Blue") {
										drawingPanel.setColor(1);
									}
									else if(colorChooser.getItemAt(colorChooser.getSelectedIndex()) == "Red") {
										drawingPanel.setColor(2);
									}
									else if(colorChooser.getItemAt(colorChooser.getSelectedIndex()) == "Gray") {
										drawingPanel.setColor(3);
									}	
									else if(colorChooser.getItemAt(colorChooser.getSelectedIndex()) == "White") {
										drawingPanel.setColor(4);
									}
									else if(colorChooser.getItemAt(colorChooser.getSelectedIndex()) == "Green") {
										drawingPanel.setColor(5);
									}
									
									
									colorLabel.setText("color selected: "+colorChooser.getItemAt(colorChooser.getSelectedIndex()));
							    }
							});
							
							buttonSave.addActionListener(new ActionListener () {
								@Override
								public void actionPerformed(ActionEvent e) {
									// Serialization 
									try
							        {    
										JFileChooser chooser = new JFileChooser(  );
									    chooser.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
									    chooser.showSaveDialog( null );
									    System.out.println( chooser.getSelectedFile() );
									    
										String name = saveName.getText()+".sav";
							            //Saving of object in a file 
										FileOutputStream saveFile=new FileOutputStream(chooser.getSelectedFile() + "\\" + name);

										// Create an ObjectOutputStream to put objects into save file.
										ObjectOutputStream save = new ObjectOutputStream(saveFile);
										
										FileWriter writer = new FileWriter(saveName.getText()+ ".txt");
										
										save.writeObject(drawingPanel.graphx);
										save.writeObject(drawingPanel.sizeX);
										save.writeObject(drawingPanel.sizeY);
										save.writeObject(drawingPanel.graphy);
										save.writeObject(drawingPanel.arry);
										writer.write("Color, X Value, Y Value, True or False \n");
										save.writeObject(drawingPanel.arryBlue);
										for (int i=0; i<drawingPanel.graphx ; i++)
									    {
											for(int j=0; j<drawingPanel.graphy ; j++) {
											if(drawingPanel.arryBlue[j][i]) {
												writer.write("Blue, ["+(j+1)+"], ["+i+"]," + drawingPanel.arryBlue[j][i]+"\n");
											}
											else if(drawingPanel.arryGreen[j][i]) {
												writer.write("Green, ["+(j+1)+"], ["+i+"]," + drawingPanel.arryGreen[j][i]+"\n");
											}
											else if(drawingPanel.arryGray[j][i]) {
												writer.write("Gray, ["+(j+1)+"], ["+i+"]," + drawingPanel.arryGray[j][i]+"\n");
											}
											else if(drawingPanel.arryWhite[j][i]) {
												writer.write("White, ["+(j+1)+"], ["+i+"]," + drawingPanel.arryWhite[j][i]+"\n");
											}
											else if(drawingPanel.arry[j][i]) {
												writer.write("Red, ["+(j+1)+"], ["+i+"]," + drawingPanel.arry[j][i]+"\n");
											}
											else {
												writer.write("None, ["+(j+1)+"]["+i+"]," + drawingPanel.arryBlue[j][i]+"\n");
											}
											}
									    }
										save.writeObject(drawingPanel.arryGreen);
										save.writeObject(drawingPanel.arryGray);
										save.writeObject(drawingPanel.arryWhite);
										save.writeObject(drawingPanel.arryCircle);
										writer.close();
										save.close();
										System.out.println("Saved");
							        } 
							          
							        catch(IOException ex) 
							        { 
							            System.out.println("IOException is caught"); 
							        } 
								}
								
							});
							//This saves all of the necessary components in a file with the specified file name
							buttonOpen.addActionListener(new ActionListener () {
								@Override
								public void actionPerformed(ActionEvent e) {
									// Serialization 
									try
							        {    
										String name = saveName.getText()+".sav";
							        	FileInputStream saveFile = new FileInputStream(name);

							        	// Create an ObjectInputStream to get objects from save file.
							        	ObjectInputStream save = new ObjectInputStream(saveFile);
							            int graphSizeX = (Integer) save.readObject();
							            //String str1 = Integer.toString(graphSizeX);
							            int dotSizeX = (Integer) save.readObject();
							          //  String str2 = Integer.toString(dotSizeX);
							            int dotSizeY = (Integer) save.readObject();
							          //  String str3 = Integer.toString(dotSizeY);
							            int graphSizeY = (Integer) save.readObject();
							            boolean [][] array = (boolean[][]) save.readObject();
							            boolean [][] arrayBlue = (boolean[][]) save.readObject();
							            boolean [][] arrayGreen = (boolean[][]) save.readObject();
							            boolean [][] arrayGray = (boolean[][]) save.readObject();
							            boolean [][] arrayWhite = (boolean[][]) save.readObject();
							            boolean [][] arrayCircle = (boolean[][]) save.readObject();
							            
							           
							            
//									    labelGraphSize.setText("Graph Size: " + str1);
//										dotSizeLabelX.setText("Die Size X: " + str2);
//										dotSizeLabelY.setText("Die Size Y: " + str3);
							           System.out.println(graphSizeX);
							           System.out.println(graphSizeY);
							           System.out.println(dotSizeX);
							           System.out.println(dotSizeY);
							           //drawingPanel=dp1;
							           save.close();
							           //Color Chooser If Statements
							           drawingPanel.setGraph(graphSizeX, graphSizeY, array, arrayBlue, arrayGreen, arrayGray, arrayWhite, arrayCircle);
										//repaint
									    drawingPanel.setDotSize(dotSizeX,dotSizeY);
									    

							            System.out.println("Object has been deserialized "); 
							        } 
							          
							        catch(IOException ex) 
							        { 
							            System.out.println("IOException is caught"); 
							        } 
							          
							        catch(ClassNotFoundException ex) 
							        { 
							            System.out.println("ClassNotFoundException is caught"); 
							        } 
								}
								
							});
							
							
							guiPanel.add(buttonSave);
							guiPanel.add(saveName);
							//Button Action Performed
							buttonDotSize.addActionListener(new ActionListener () {
								@Override
								public void actionPerformed(ActionEvent e) {
									int graphSizeX = textfieldWaferSizeX.getInt();
									String str1 = Integer.toString(graphSizeX);
									
									int dotSizeX = dotSizeTextFieldX.getInt();
									String str2 = Integer.toString(dotSizeX);
									
									int dotSizeY = dotSizeTextFieldY.getInt();
									String str3 = Integer.toString(dotSizeY);
									int graphSizeY=0;
									
									
									System.out.println(str1);
							           System.out.println(str2);
							           System.out.println(str3);
									labelGraphSize.setText("Graph Size: " + str1);
									dotSizeLabelX.setText("Die Size X: " + str2);
									dotSizeLabelY.setText("Die Size Y: " + str3);
									
									
									//Regular Input
									if(graphSizeX>dotSizeY) {
									graphSizeY=graphSizeX/dotSizeY;
									graphSizeX=graphSizeX/dotSizeX;
									}
									
									drawingPanel.setGraph(graphSizeX, graphSizeY);
									//repaint
								    drawingPanel.setDotSize(dotSizeX,dotSizeY);
										
									
									//Color Chooser If Statements
									if(colorChooser.getItemAt(colorChooser.getSelectedIndex()) == "Blue") {
										drawingPanel.setColor(1);
									}
									else if(colorChooser.getItemAt(colorChooser.getSelectedIndex()) == "Red") {
										drawingPanel.setColor(2);
									}
									else if(colorChooser.getItemAt(colorChooser.getSelectedIndex()) == "Gray") {
										drawingPanel.setColor(3);
									}	
									else if(colorChooser.getItemAt(colorChooser.getSelectedIndex()) == "White") {
										drawingPanel.setColor(4);
									}
									else if(colorChooser.getItemAt(colorChooser.getSelectedIndex()) == "Green") {
										drawingPanel.setColor(5);
									}
									
									 
									
									colorLabel.setText("color selected: "+colorChooser.getItemAt(colorChooser.getSelectedIndex()));
								}
								
						});
							buttonSocket.addActionListener(new ActionListener () {
								@Override
								public void actionPerformed(ActionEvent e) {
									
								//creating server and client connection
//									Thread t1 = new Thread(new Server());
//									t1.start();								
									Thread t2 = new Thread(new Client("127.0.0.1"));
									t2.start();	
								}	
								});
						    
						    
							//JButton buttonClient = new JButton("Run Client");
							//buttonClient.setPreferredSize(new Dimension(120, 40));
							
						//	buttonClient.addActionListener(new ActionListener () {
							//	@Override
								//public void actionPerformed(ActionEvent e) {
									
									
								//}
							//});
							
							JNumberTextField textfieldColorX = new JNumberTextField();
							textfieldColorX.setPreferredSize(new Dimension(100, 30));
							JNumberTextField textfieldColorY = new JNumberTextField();
							textfieldColorY.setPreferredSize(new Dimension(100, 30));
							JButton buttonColor = new JButton();
							buttonColor.setPreferredSize(new Dimension(100, 30));
							buttonColor.addActionListener(new ActionListener () {
								@Override
								public void actionPerformed(ActionEvent e) {
									int colorX= textfieldColorX.getInt();
									//String str1 = Integer.toString(colorX);
									int colorY= textfieldColorY.getInt();
									//String str2 = Integer.toString(colorY);
									
									drawingPanel.colorDie(colorX, colorY);
								}	
								});
							
							
							//Adding GUI Components
							guiPanel.add(textfieldWaferSizeX);
							guiPanel.add(labelWaferSizeX);
							guiPanel.add(dotSizeTextFieldX);
							guiPanel.add(dotSizeX);
							guiPanel.add(dotSizeTextFieldY);
							guiPanel.add(dotSizeY);
							guiPanel.add(buttonDotSize);
							guiPanel.add(labelGraphSize);
							guiPanel.add(dotSizeLabelX);
							guiPanel.add(dotSizeLabelY);
							guiPanel.add(currentXLabel);
							guiPanel.add(currentYLabel);
							guiPanel.add(colorLabel);
							guiPanel.add(colorChooser);
							guiPanel.add(checkbox);
							guiPanel.add(buttonSave);
							guiPanel.add(saveName);
							guiPanel.add(buttonOpen);
							guiPanel.add(buttonSocket);
							guiPanel.add(textfieldColorX);
							guiPanel.add(textfieldColorY);
							guiPanel.add(buttonColor);
							
							
							
				// add to main panel
				panel.add(guiPanel, BorderLayout.EAST);
				panel.add(drawingPanel, BorderLayout.WEST);
				
				//setup frame
				frame.getContentPane().add(panel);
				frame.setVisible(true);
				frame.pack();
				
		    }
		});
		
		
		
		
	}
	
	
}


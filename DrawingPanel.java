import java.awt.Color;  
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.Random;

import javax.swing.JPanel;


import java.awt.Dimension;

@SuppressWarnings("serial")
public class DrawingPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener, Serializable{
	

	
	int spacing = 1;
	
	public double mx = -100;
	public double my = -100;
	public int sizeX = 40;
	public int sizeY = 40;
	
	public int graphx = 2000;
	public int graphy = graphx;
	
	public int spacingX = 90;
	public int spacingY = 5;
	
	public int minusX = spacingX + 160;
	public int minusY = spacingY;
	
//Different Color Arrays
	boolean[][] arry = new boolean[graphx][graphy];
	boolean[][] arryBlue = new boolean[graphx][graphy];
	boolean[][] arryGreen = new boolean[graphx][graphy];
	boolean[][] arryGray = new boolean[graphx][graphy];
	boolean[][] arryWhite = new boolean[graphx][graphy];
	boolean[][] arryCircle = new boolean[graphx][graphy];
	
	public int lastClickedX = 0;
	public int lastClickedY = 0;
	
	public Random rand = new Random();
	public float r = rand.nextFloat();
	public float g = rand.nextFloat();
	public float b = rand.nextFloat();
	Color randomColor = new Color(r, g, b);
	Color colorBoxColor;
	
    public double zoomFactor = 1;
    public double prevZoomFactor = 1;
    public boolean zoomer;
    public boolean dragger;
    public boolean released;
    public double xOffset = 0;
    public double yOffset = 0;
    public int xDiff;
    public int yDiff;
    public Point startPoint;
	public boolean repainter;
	public boolean clicked;
	public boolean selected;
	public AffineTransform at = new AffineTransform();
    
	DrawingPanel() {
		addMouseMotionListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
		this.setPreferredSize(new Dimension(1080,800));
		GUI gui = new GUI();
		graphx = gui.getGraphNum();
		graphy = gui.getGraphNum();
		
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		at = g2.getTransform();
		setBackground(Color.DARK_GRAY);
		if (dragger) {
	        at.translate(xOffset + xDiff, yOffset + yDiff);
	        at.scale(zoomFactor, zoomFactor);
	        System.out.println(xOffset+" "+xDiff+" "+yOffset+" "+yDiff);
            if (released) {
            	System.out.println("released3");
                xOffset += xDiff;
                yOffset += yDiff;
                dragger=false;
                xDiff=0;
                yDiff=0;
            }

        }
		else if(clicked)
		{
			System.out.println(xOffset+" "+inBoxX()+1+" "+yOffset+" "+inBoxY()+1);
			at.translate(xOffset+xDiff,yOffset+yDiff);
	        at.scale(zoomFactor, zoomFactor);
	        clicked=false;
			
		}
		else if (zoomer) {
            

            double xRel = MouseInfo.getPointerInfo().getLocation().getX() - getLocationOnScreen().getX();
            double yRel = MouseInfo.getPointerInfo().getLocation().getY() - getLocationOnScreen().getY();

            double zoomDiv = zoomFactor / prevZoomFactor;

            xOffset = (zoomDiv) * (xOffset) + (1 - zoomDiv) * xRel;
            yOffset = (zoomDiv) * (yOffset) + (1 - zoomDiv) * yRel;

           at.translate(xOffset+xDiff, yOffset+yDiff);
            at.scale(zoomFactor, zoomFactor);
            prevZoomFactor = zoomFactor;
            zoomer=false;
        }
        

         g2.transform(at);

        
        for(int i = 0 ; i < graphx ; i++) {
            for(int j = 0; j < graphy ; j++) {
            	
                g.setColor(Color.white);
                if(selected) {
                if(((i-graphx/2)*(i-graphx/2))+((j-graphx/2)*(j-graphx/2))<=graphx/2*graphx/2) {
	                if(arry[i][j]==true) {
	                	
	                    g.setColor(Color.red);
	                }
	                if(arryBlue[i][j]==true) {
	                    g.setColor(Color.blue);
	                }
	                if(arryGreen[i][j]==true) {
	                    g.setColor(Color.green);
	                }
	                if(arryGray[i][j]==true) {
	                    g.setColor(Color.DARK_GRAY);
	                }
	                if(arryWhite[i][j]==true) {
	                    g.setColor(Color.white);
	                }
	                g.fillRect(spacing+i*sizeX, spacing+j*sizeY+sizeY, sizeX-2*spacing, sizeY-2*spacing);
                }
                }
                else {
                	if(arry[i][j]==true) {
	                    g.setColor(Color.red);
	                }
	                if(arryBlue[i][j]==true) {
	                    g.setColor(Color.blue);
	                }
	                if(arryGreen[i][j]==true) {
	                    g.setColor(Color.green);
	                }
	                if(arryGray[i][j]==true) {
	                    g.setColor(Color.DARK_GRAY);
	                }
	                if(arryWhite[i][j]==true) {
	                    g.setColor(Color.white);
	                }
	                g.fillRect(spacing+i*sizeX, spacing+j*sizeY+sizeY, sizeX-2*spacing, sizeY-2*spacing);
                }
            	
            }
        }
        g2.transform(at);
	}
	public Color getColor() {
		Color realColor = randomColor;
		realColor = colorBoxColor;
		return realColor;
	}
	
	
	public void setColor(int num) {
		if (num == 1) {
			colorBoxColor = Color.blue;
		}
		else if(num==2) {
			colorBoxColor = Color.red;
		}
		else if(num==3) {
			colorBoxColor = Color.DARK_GRAY;
		}
		else if(num==4) {
			colorBoxColor = Color.white;
		}
		else if(num==5) {
			colorBoxColor = Color.green;
		}
	}
	
	public void setSelected(boolean tof) {
		selected=tof;
	}
	public void setGraph(int x, int y) {
		graphx = x;
		graphy = y;
//		repaint();
		//boolean[][] arryDimension = new boolean[x][x];
	}
	public void setGraph(int x, int y, boolean array[][], boolean arrayBlue[][], boolean arrayGreen[][], boolean arrayGray[][], boolean arrayWhite[][], boolean arrayCircle[][]) {
		graphx = x;
		graphy = y;
		arry = array;
		arryBlue = arrayBlue;
		arryGreen = arrayGreen;
		arryGray = arrayGray;
		arryWhite = arrayWhite;
		arryCircle = arrayCircle;
//		repaint();
	}
	//repainted
	public void setDotSize(int dotSizeX, int dotSizeY) {
		sizeX = dotSizeX;
		sizeY = dotSizeY;
		int gcd = 1;
		for(int i = 1; i <= sizeX && i <= sizeY; i++)
        {
            if(sizeX%i==0 && sizeY%i==0)
                gcd = i;
        }
        int sizeReducedX = sizeX/gcd;
        int sizeReducedY = sizeY/gcd;
        
        sizeX = sizeReducedX;
        sizeY = sizeReducedY;
        
		int reducedX = sizeX;
		int reducedY = sizeY;
		while(sizeX*graphx < 1080 && sizeY*graphy <768) {
			if(sizeX*graphx < 1080 && sizeY*graphy <768) {
			sizeX+=reducedX;
			sizeY+=reducedY;
		}
		}
			sizeX-=1;
			sizeY-=1;
			System.out.println("repaint from setDotSize");
		repaint();
	}
	public void setLastClickedX(int x) {
		lastClickedX = x;
	}
	public void setLastClickedY(int y) {
		lastClickedY = y;
	}
	public int getLastClickedX() {
		return lastClickedX;
	}
	public int getLastClickedY() {
		return lastClickedY;
	}
	public void colorDie(int x, int y) {
		x=x-1;
		y=y-1;
		if(getColor()==Color.red) {
				arry[x][y] = true;
			
			}
			else if(getColor()==Color.blue) {

					arryBlue[x][y] = true;
			
			}
			else if(getColor()==Color.green) {

					arryGreen[x][y] = true;
			
			}
			else if(getColor()==Color.DARK_GRAY) {

					arryGray[x][y] = true;
			
			}
			else if(getColor()==Color.white) {

					arryWhite[x][y] = true;
			
			}
		repaint();
	}
//repainted
	@Override
	public void mouseClicked(MouseEvent arg0) {
		System.out.println("the mouse was clicked");
		lastClickedX = inBoxX()+1;
		lastClickedY = inBoxY()+1;
		System.out.println(lastClickedX);
		
		if(getColor()==Color.red) {
		if(inBoxX() != -1 && inBoxY() != -1) {
			arry[inBoxX()][inBoxY()] = true;
		}
		}
		else if(getColor()==Color.blue) {
			if(inBoxX() != -1 && inBoxY() != -1) {
				arryBlue[inBoxX()][inBoxY()] = true;
		}
		}
		else if(getColor()==Color.green) {
			if(inBoxX() != -1 && inBoxY() != -1) {
				arryGreen[inBoxX()][inBoxY()] = true;
		}
		}
		else if(getColor()==Color.DARK_GRAY) {
			if(inBoxX() != -1 && inBoxY() != -1) {
				arryGray[inBoxX()][inBoxY()] = true;
		}
		}
		else if(getColor()==Color.white) {
			if(inBoxX() != -1 && inBoxY() != -1) {
				arryWhite[inBoxX()][inBoxY()] = true;
		}
		}
		
		setLastClickedX(lastClickedX);
		setLastClickedY(lastClickedY);
		clicked=true;
		System.out.println("repaint from mouseclicked");
		repaint();
		
	}
	

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		released = false;
        startPoint = MouseInfo.getPointerInfo().getLocation();
		System.out.println(startPoint);
		System.out.println(mx);
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		released = true;
		System.out.println("released");
	}

//repainted
	@Override
	public void mouseDragged(MouseEvent e) {
        Point curPoint = e.getLocationOnScreen();
        xDiff = curPoint.x - startPoint.x;
        yDiff = curPoint.y - startPoint.y;
        dragger = true;
        System.out.println("repaint from mousedragged");
        repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//System.out.println("the mouse was moved");
		
		mx = (e.getX()-(xOffset + xDiff));
		my = (e.getY()-(yOffset + yDiff));
		mx/=zoomFactor;
		my/=zoomFactor;
	}
	public int inBoxX() {
			for(int i = 0 ; i < graphx ; i++) {
			
				for(int j = 0; j < graphy ; j++) {
					
					if (mx >= (spacing+i*sizeX) && mx < (spacing+i*sizeX+sizeX-2*spacing) && my >= (spacing+j*sizeY+sizeY) && my < (spacing+j*sizeY+sizeY+sizeY-2*spacing)) {
						
						return i;
						
					}
				}
			}
		return -1;
		
		}
		
	public int inBoxY() {
			for(int i = 0 ; i < graphx ; i++) {
				
				for(int j = 0; j < graphy ; j++) {
					
					if (mx >= spacing+i*sizeX && mx < spacing+i*sizeX+sizeX-2*spacing && my >= spacing+j*sizeY+sizeY && my < spacing+j*sizeY+sizeY+sizeY-2*spacing) {
						
						return j;
						
					}
				}
			}
			return -1;
		}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
        zoomer = true;
        
        //Zoom in
        if (e.getWheelRotation() < 0) {
            zoomFactor *= 1.1;
            
        }
        //Zoom out
        if (e.getWheelRotation() > 0) {
            zoomFactor /= 1.1;
           
        }
        System.out.println("repaint from mousewheelmoved");
        repaint();
		
	}
	public String toString() {
		return "Graph Diameter: "+graphx+" Die Size X: "+sizeX+" Die Size Y: "+sizeY;
		
	}
	
}


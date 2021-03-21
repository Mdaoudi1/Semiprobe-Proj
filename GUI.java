import java.awt.Dimension; 
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUI extends JPanel{
	private int graphSize;
	private boolean getGraphTOF = false;
	
	public GUI() {
		super();
		graphSize=0;
		this.setPreferredSize(new Dimension(200,800));
		
	}
	public boolean getTOF() {
		return getGraphTOF;
	}
	public int getGraphNum() {
		return graphSize;
	}

}

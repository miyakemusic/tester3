package testers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.miyake.demo.entities.MyTesterEntity;

import testers.fip.Fip500Panel;
import testers.fip.FipCoreSim;
import testers.olts.OltsCoreSim;
import testers.otdr.OtdrCalculator;
import testers.otdr.OtdrCalculatorSim;
import testers.otdr.OtdrCoreSim;


public class TesterStartup/* extends JFrame*/ {

	private RestClient restClient;
	private JLabel status;
	
	
	public static void main(String[] arg) {
		new TesterStartup(arg[0], arg[1], arg[2]);//.setVisible(true);;
	}
	
	public TesterStartup(String host, String username, String password) {
		MyHttpClient http = new MyHttpClient("http://" + host + ":8080");		
		RestClient restClient = new RestClient(http);

		try {
			MyTesterEntity entity = restClient.signin(username, password);
			TesterDevFrame testerMain = null;//new MT1000A(restClient, entity);
			
			OtdrCoreSim otdrCore = new OtdrCoreSim() {
				@Override
				public OtdrCalculator createCalculator() {
					return new OtdrCalculatorSim();
				}
			};
			OltsCoreSim oltsCore = new OltsCoreSim();
			FipCoreSim fipCore = new FipCoreSim("Microscope-SM.jpg", "Microscope-SM_fail.jpg");
			MT1000APanel mt1000aPanel = new MT1000APanel(restClient, otdrCore, oltsCore, fipCore);
			PortPanel portPanel = new PortPanel(mt1000aPanel.getPorts()) {
				@Override
				protected void onSelect(String name) {
					otdrCore.setSelectedPort(name);
					oltsCore.setSelectedPort(name);
					fipCore.setSelectedPort(name);
				}

				@Override
				protected String portImage() {
					return "portimage2.bmp";
				}
			};
									
			if (entity.getTester() == 1 || entity.getTester() == 13) {
				testerMain = new MT1000ADevFrame(restClient, entity, mt1000aPanel, portPanel);
			}		
			else if (entity.getTester() == 7) {
				Fip500Panel fip500 = new Fip500Panel(restClient);
				testerMain = new FIP500Frame(restClient, entity, fip500, portPanel);
			}
			 			
			testerMain.setVisible(true);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}

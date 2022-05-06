package testers;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import com.miyake.demo.entities.MyTesterEntity;

public class MT1000ADevFrame extends TesterDevFrame {
	public static final String SM1310_1550 = "1310/1550";
	public static final String SM1650 = "1650";
	public static final String OPM = "OPM";
	public static final String FIP = "FIP";
//	private OtdrCoreSim otdrCore;
//	private OltsCoreSim oltsCore;
//	private FipCoreSim fipCore;
	private MainApplicationPanel mainApp;	
	private PortPanel portPanel;

	public MT1000ADevFrame(RestClient restClient, MyTesterEntity entity, MainApplicationPanel mainPanel, PortPanel portPanel) {
		super(restClient, entity, mainPanel, portPanel);
//
//		
//		application("OTDR", new OtdrPane(new OtdrCoreSim() {
//			@Override
//			public OtdrCalculator createCalculator() {
//				return new OtdrCalculatorSim();
//			}
//		})).
//		application("OLTS", new OltsPane(new OltsCoreSim())).
//		application("FIP", new SingleFipTester());
		
		
		
//		otdrCore = new OtdrCoreSim() {
//			@Override
//			public OtdrCalculator createCalculator() {
//				return new OtdrCalculatorSim();
//			}
//		};
//		oltsCore = new OltsCoreSim();
//		fipCore = new FipCoreSim("Microscope-SM.jpg", "Microscope-SM_fail.jpg");
//		mt1000aPanel = new MT1000APanel(restClient, otdrCore, oltsCore, fipCore);
		
		this.mainApp = mainPanel;
		this.portPanel = portPanel;
	}

	@Override
	protected void configurePort(List<Port> ports) {
		ports.add(new Port(MT1000ADevFrame.FIP, 978, 27));
		ports.add(new Port(MT1000ADevFrame.OPM, 308, 29));
		ports.add(new Port(MT1000ADevFrame.SM1650, 535, 39));
		ports.add(new Port(MT1000ADevFrame.SM1310_1550, 670, 39));
	}

	@Override
	protected String portImage() {
//		return this.getClass().getResource("portimage2.bmp").getFile();
		return "portimage2.bmp";
	}

	@Override
	protected String backgroundImage() {
		//return this.getClass().getResource("mt1000A.bmp").getFile();
		return "mt1000A.bmp";
	}

	@Override
	protected int mainHeight() {
		return 458;
	}
	@Override
	protected int mainWidth() {
		return 802;
	}

	@Override
	protected int offsetY() {
		return 104;
	}

	@Override
	protected int offsetX() {
		return 130;
	}

//	@Override
//	protected Component createAppPanel(RestClient restClient) {
//		return mt1000aPanel;
//	}
//
//	@Override
//	protected Component getPortPanel() {
////		PortPanel portPanel = new PortPanel(mt1000aPanel.getPorts()) {
////			@Override
////			protected void onSelect(String name) {
////				otdrCore.setSelectedPort(name);
////				oltsCore.setSelectedPort(name);
////				fipCore.setSelectedPort(name);
////			}
////
////			@Override
////			protected String portImage() {
//////				return this.getClass().getResource("portimage2.bmp").getFile();
////				return "portimage2.bmp";
////			}
////		};
//		return portPanel;
//	}
}

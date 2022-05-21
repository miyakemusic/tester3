package testers.debug;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

import com.miyake.demo.entities.MyTesterEntity;
import com.miyake.demo.jsonobject.ProjectJson;
import com.miyake.demo.jsonobject.TestPlan2;
import com.miyake.demo.jsonobject.TestPlan2Element;
import com.miyake.demo.jsonobject.TestResult;
import com.miyake.demo.jsonobject.TestResultJson;

import testers.MyHttpClient;
import testers.RestClient;

public class DebugTesterPane extends JPanel {

	public DebugTesterPane(String host, Account account) {
		
		MyHttpClient http = new MyHttpClient("http://" + host + ":8080");		
		RestClient restClient = new RestClient(http);
		
		this.setLayout(new FlowLayout());
		this.setBorder(new EtchedBorder());
		try {
			MyTesterEntity entity = restClient.signin(account.account, account.password);
			ProjectJson[] projects = restClient.projectList();
			
			JComboBox<ProjectJson> projectCombo = new JComboBox<>();
			for (ProjectJson project : projects) {
				projectCombo.addItem(project);
			}		
			this.add(projectCombo);
			
			JComboBox<ProjectJson> equipmentCombo = new JComboBox<>();
			this.add(equipmentCombo);
			projectCombo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					equipmentCombo.removeAllItems();
					ProjectJson[] equipments = restClient.equipmentList( ((ProjectJson)projectCombo.getSelectedItem()).id );
					for (ProjectJson json : equipments) {
						equipmentCombo.addItem(json);
					}
				}
			});
			
			projectCombo.setSelectedIndex(0);
			
			equipmentCombo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

				}
			});
			
			JLabel status = new JLabel("Test Status");
			
			JButton runButton = new JButton("Run");
			this.add(runButton);
			runButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					runTest(restClient, (ProjectJson)equipmentCombo.getSelectedItem(), status);
				}
				
			});
			JButton loginButton = new JButton("Login");
			this.add(loginButton);
			loginButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						restClient.signin(account.account, account.password);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			JButton logoutButton = new JButton("Logout");
			this.add(logoutButton);
			logoutButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					restClient.signout();
				}
			});	
			
			this.add(status);
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
	}

	private String v = "0";
	protected void runTest(RestClient restClient, ProjectJson obj, JLabel status) {
		
		TestPlan2 testPlan = restClient.equipmentTest(obj.id);
		
		new Thread() {
			@Override
			public void run() {
				for (TestPlan2Element e : testPlan.getElements()) {
					v = String.valueOf(Math.random() * 100);
					if (e.getCriteria().contains("==0")) {
						v = "0";
					}
					
					restClient.uploadResult(new TestResultJson(e.getPorttest(), v));
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							status.setText(e.getPorttest() + "@" + v);
						}
					});
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}.start();
	}

}

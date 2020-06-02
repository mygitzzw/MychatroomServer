package webserver.view;

/*
 * 类名：ServerFrame
 * 描述：服务器窗口
 */

import webserver.model.ThreadInToHashMap;
import webserver.model.WebServer;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Iterator;
import javax.swing.*;

// The Server UI.
public class ServerBuildFrame extends JFrame implements ActionListener {
	public static ServerBuildFrame serverJframe;
	public JTabbedPane tpServer/*主标签*/;

	// 服务器信息面板
	public JPanel pnlServer/**/, pnlServerInfo/**/,jPanel_list;
	public JLabel lblNumber, lblServerName, lblIP, lblPort, lblLog;
	public JTextField txtNumber, txtServerName, txtIP, txtPort;
	public JButton btnStop, btnSaveLog;
	public static TextArea taLog;

	// 用户信息面板
	public JPanel pnlUser;
	public JLabel lblUser;
	public JList lstUser;
	public JScrollPane spUser;

	// 关于本软件
	JPanel pnlAbout;
	JLabel lblVersionNo, lblAbout;

	public static void main(String[] args) {

		 serverJframe = new ServerBuildFrame();
		serverJframe.taLog.append("尊敬的管理员，欢迎使用Webchat服务器！" +"\r\n");
		new WebServer(serverJframe);  //开启服务器
	}
	public  ServerBuildFrame() {
		super("WebChat 聊天服务器");
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();// 在屏幕居中显示
		Dimension fra = this.getSize();
		if (fra.width > scr.width) {
			fra.width = scr.width;
		}
		if (fra.height > scr.height) {
			fra.height = scr.height;
		}
		this.setLocation((scr.width - fra.width) / 2,
				(scr.height - fra.height) / 2);

		// 服务器信息
		pnlServerInfo = new JPanel(new GridLayout(14, 1));
		pnlServerInfo.setBackground(new Color(120, 130, 203));
		pnlServerInfo.setFont(new Font("宋体", 0, 12));
		pnlServerInfo.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(""),
				BorderFactory.createEmptyBorder(1, 1, 1, 1)));

		lblNumber = new JLabel("当前在线人数:");
		lblNumber.setForeground(Color.YELLOW);
		lblNumber.setFont(new Font("宋体", 0, 12));
		txtNumber = new JTextField("0人",10);
		txtNumber.setBackground(Color.decode("#d6f4f2"));
		txtNumber.setFont(new Font("宋体", 0, 12));
		txtNumber.setEditable(false);

		lblServerName = new JLabel("服务器名称:");
		lblServerName.setForeground(Color.YELLOW);
		lblServerName.setFont(new Font("宋体", 0, 12));
		txtServerName = new JTextField(10);
		txtServerName.setBackground(Color.decode("#d6f4f2"));
		txtServerName.setFont(new Font("宋体", 0, 12));
		txtServerName.setEditable(false);

		lblIP = new JLabel("服务器IP:");
		lblIP.setForeground(Color.YELLOW);
		lblIP.setFont(new Font("宋体", 0, 12));
		txtIP = new JTextField(10);
		txtIP.setBackground(Color.decode("#d6f4f2"));
		txtIP.setFont(new Font("宋体", 0, 12));
		txtIP.setEditable(false);

		lblPort = new JLabel("服务器端口:");
		lblPort.setForeground(Color.YELLOW);
		lblPort.setFont(new Font("宋体", 0, 12));
		txtPort = new JTextField("8888",10);
		txtPort.setBackground(Color.decode("#d6f4f2"));
		txtPort.setFont(new Font("宋体", 0, 12));
		txtPort.setEditable(false);

		btnStop = new JButton("关闭服务器(C)");
		btnStop.addActionListener((e) -> closeServer());
		btnStop.setBackground(Color.ORANGE);
		btnStop.setFont(new Font("宋体", 0, 12));

		pnlServerInfo.setBounds(5, 5, 100, 400);
		pnlServerInfo.add(lblNumber);
		pnlServerInfo.add(txtNumber);
		pnlServerInfo.add(lblServerName);
		pnlServerInfo.add(txtServerName);
		pnlServerInfo.add(lblIP);
		pnlServerInfo.add(txtIP);
		pnlServerInfo.add(lblPort);
		pnlServerInfo.add(txtPort);

		// 服务器面板
		pnlServer = new JPanel();
		pnlServer.setLayout(null);
		pnlServer.setBackground(new Color(180, 160, 203));

		lblLog = new JLabel("[服务器日志]");
		lblLog.setForeground(Color.YELLOW);
		lblLog.setFont(new Font("宋体", 0, 12));
		taLog = new TextArea(20, 50);
		taLog.setFont(new Font("宋体", 0, 12));

		btnSaveLog = new JButton("保存日志(S)");
		btnSaveLog.addActionListener((e) -> saveLog());
		btnSaveLog.setBackground(Color.ORANGE);
		btnSaveLog.setFont(new Font("宋体", 0, 12));

		lblLog.setBounds(110, 5, 100, 30);
		taLog.setBounds(110, 35, 450, 370);
		btnStop.setBounds(200, 410, 120, 30);
		btnSaveLog.setBounds(320, 410, 120, 30);

		//
		pnlServer.add(pnlServerInfo);
		pnlServer.add(lblLog);
		pnlServer.add(taLog);
		pnlServer.add(btnStop);
		pnlServer.add(btnSaveLog);

		// 用户面板
		pnlUser = new JPanel();
		pnlUser.setLayout(null);
		pnlUser.setBackground(new Color(52, 130, 203));
		pnlUser.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createTitledBorder(""),
				BorderFactory.createEmptyBorder(1, 1, 1, 1)));

		lblUser = new JLabel("[在线用户列表]");
		lblUser.setFont(new Font("宋体", 0, 12));
		lblUser.setForeground(Color.YELLOW);

		jPanel_list = new JPanel(new GridLayout(30, 1, 4, 4));


		lstUser = new JList();
		lstUser.setFont(new Font("宋体", 0, 12));
		lstUser.setVisibleRowCount(17);
		lstUser.setFixedCellWidth(180);
		lstUser.setFixedCellHeight(18);
		spUser = new JScrollPane();
		spUser.setBackground(Color.cyan);
		spUser.setFont(new Font("宋体", 0, 12));
		spUser.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		spUser.getViewport().setView(lstUser);

		pnlUser.setBounds(50, 5, 300, 400);
		lblUser.setBounds(50, 10, 100, 30);
		spUser.setBounds(50, 35, 200, 360);

		pnlUser.add(lblUser);
		pnlUser.add(spUser);

		// 软件信息
		pnlAbout = new JPanel();
		pnlAbout.setLayout(null);
		pnlAbout.setBackground(new Color(150, 130, 100));
		pnlAbout.setFont(new Font("宋体", 0, 14));

		lblVersionNo = new JLabel("Version No. v1.51");
		lblVersionNo.setFont(new Font("宋体", 0, 14));
		lblVersionNo.setForeground(Color.black);

		lblAbout = new JLabel();
		lblAbout.setFont(new Font("Consolas", 0, 14));
		lblAbout.setText("Long.Luo Created @8th, March, 2012" + "at Shenzhen, China");
		lblAbout.setForeground(Color.black);

		lblVersionNo.setBounds(110, 5, 100, 30);
		lblAbout.setBounds(110, 35, 400, 50);

		pnlAbout.add(lblVersionNo);
		pnlAbout.add(lblAbout);

		// 主标签面板
		tpServer = new JTabbedPane(JTabbedPane.TOP);
		tpServer.setBackground(Color.CYAN);
		tpServer.setFont(new Font("宋体", 0, 14));

		tpServer.add("服务器管理", pnlServer);
		tpServer.add("在线用户", pnlUser);
		tpServer.add("关于本软件", pnlAbout);

		this.getContentPane().add(tpServer);
		setVisible(true);


	}

	protected void closeServer() {
		Iterator<String> iterator = ThreadInToHashMap.hashMap.keySet().iterator();
		while (iterator.hasNext()){
			try {
				ThreadInToHashMap.getClientThreas(iterator.next()).s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.dispose();
		System.exit(0);
	}

	protected void saveLog() {
		try {
			FileOutputStream fileoutput = new FileOutputStream("log.txt", true);
			String temp = taLog.getText();
			fileoutput.write(temp.getBytes());
			fileoutput.close();
			JOptionPane.showMessageDialog(null, "记录保存在log.txt");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void actionPerformed(ActionEvent evt) {
	}

	// 服务器窗口

}

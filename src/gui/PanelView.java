package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import components.interact;

import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class PanelView extends JFrame {

	private JPanel contentPane;
	private JTextField textGpr_0;
	private JTextField textGpr_1;
	private JTextField textGpr_2;
	private JTextField textGpr_3;
	private JTextField textIxr_1;
	private JTextField textIxr_2;
	private JTextField textIxr_3;
	private JTextField textPC;
	private JTextField textMAR;
	private JTextField textMBR;
	private JTextField textIR;
	private JTextField textMFR;
	private JTextField textPriv;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTextField textField_13;
	private JTextField textField_14;
	private JTextField textField_15;
	private JTextField textHalt;
	private JTextField textRun;
	private JTextArea textArea;
	private JTextField textField_17;
	private JScrollPane scrollPane;

	private interact cpu;		
	
	String op_bit0;
	String op_bit1;
	String op_bit2;
	String op_bit3;
	String op_bit4;
	String op_bit5;
	String gpr_bit1;
	String gpr_bit0;
	String ixr_bit1;
	String ixr_bit0;
	String i;
	String ip_add_4_bit;
	String ip_add_3_bit;
	String ip_add_2_bit;
	String ip_add_1_bit;
	String ip_add_0_bit;
	String instruction, operation, address, gpr, ixr;
	
	private int pro1flag1 = 1; 
	private int pro2flag1 = 1;
	private int typeFlag = 1;
	private JTextField textField_16;
	private JTextField textField_18;
	private JTextField textField_19;
	private JTextField textField_20;
	private JTextField textField_21;
	//status: 1 represent now the program1 need 20 numbers, 
	// 0 represent now the program1 need the compare number or is executing instructions now
	// 2 represent already has the result

	/**
	 * Main method to Launch the GUI
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelView frame = new PanelView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the all components on GUI.
	 * Listen to the actions: mouse_clicking, key_pressed, etc. 
	 */
	public PanelView() {
		//Set the main Frame
		setTitle("Machine Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 800);
		contentPane = new JPanel();
		contentPane.setBackground(Color.CYAN);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//For GUI interacts with CPU components
		cpu = new interact();
		
		
		//Labels for GPR, IXR
		JLabel lblGpr_0 = new JLabel("GPR 0");
		lblGpr_0.setBounds(20, 20, 41, 16);
		contentPane.add(lblGpr_0);
		
		JLabel lblGpr_1 = new JLabel("GPR 1");
		lblGpr_1.setBounds(20, 40, 41, 16);
		contentPane.add(lblGpr_1);
		
		JLabel lblGpr_2 = new JLabel("GPR 2");
		lblGpr_2.setBounds(20, 60, 41, 16);
		contentPane.add(lblGpr_2);
		
		JLabel lblGpr_3 = new JLabel("GPR 3");
		lblGpr_3.setBounds(20, 80, 41, 16);
		contentPane.add(lblGpr_3);
		
		JLabel lblIxr_1 = new JLabel("IXR 1");
		lblIxr_1.setBounds(20, 120, 41, 16);
		contentPane.add(lblIxr_1);
		
		JLabel lblIxr_2 = new JLabel("IXR 2");
		lblIxr_2.setBounds(20, 140, 41, 16);
		contentPane.add(lblIxr_2);
		
		JLabel lblIxr_3 = new JLabel("IXR 3");
		lblIxr_3.setBounds(20, 160, 41, 16);
		contentPane.add(lblIxr_3);
		
		//Set text box to show data of GPR0  
		textGpr_0 = new JTextField();
		
		//When user type any key
		textGpr_0.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c)) {
					e.consume();
				}
			}
			//
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textGpr_0.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<16) {
						textGpr_0.setEditable(true);
					} else {
						textGpr_0.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textGpr_0.setEditable(true);
					} else {
						textGpr_0.setEditable(false);
					}
					}
			}
		});
		textGpr_0.setBounds(70, 20, 160, 16);
		contentPane.add(textGpr_0);
		textGpr_0.setColumns(10);
		
		//Set text box to show data of GPR1
		textGpr_1 = new JTextField();
		textGpr_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c)) {
					e.consume();
					}
				}
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textGpr_1.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<16) {
						textGpr_1.setEditable(true);
					} else {
						textGpr_1.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textGpr_1.setEditable(true);
					} else {
						textGpr_1.setEditable(false);
					}
					}
			}
		});
		textGpr_1.setColumns(10);
		textGpr_1.setBounds(70, 40, 160, 16);
		contentPane.add(textGpr_1);
		
		//Set text box to show data of GPR2
		textGpr_2 = new JTextField();
		textGpr_2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c)) {
					e.consume();
					}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textGpr_2.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<16) {
						textGpr_2.setEditable(true);
					} else {
						textGpr_2.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textGpr_2.setEditable(true);
					} else {
						textGpr_2.setEditable(false);
					}
					}
			}
		});
		textGpr_2.setColumns(10);
		textGpr_2.setBounds(70, 60, 160, 16);
		contentPane.add(textGpr_2);
		
		//Set text box to show data of GPR3
		textGpr_3 = new JTextField();
		textGpr_3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c)) {
					e.consume();
					}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textGpr_3.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<16) {
						textGpr_3.setEditable(true);
					} else {
						textGpr_3.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textGpr_3.setEditable(true);
					} else {
						textGpr_3.setEditable(false);
					}
					}
			}
		});
		textGpr_3.setColumns(10);
		textGpr_3.setBounds(70, 80, 160, 16);
		contentPane.add(textGpr_3);
		
		//Set text box to show data of IXR1
		textIxr_1 = new JTextField();
		textIxr_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c)) {
					e.consume();
					}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textIxr_1.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<16) {
						textIxr_1.setEditable(true);
					} else {
						textIxr_1.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textIxr_1.setEditable(true);
					} else {
						textIxr_1.setEditable(false);
					}
					}
			}
		});
		textIxr_1.setColumns(10);
		textIxr_1.setBounds(70, 120, 160, 16);
		contentPane.add(textIxr_1);
		
		//Set text box to show data of IXR2
		textIxr_2 = new JTextField();
		textIxr_2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c)) {
					e.consume();
					}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textIxr_2.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<16) {
						textIxr_2.setEditable(true);
					} else {
						textIxr_2.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textIxr_2.setEditable(true);
					} else {
						textIxr_2.setEditable(false);
					}
					}
			}
		});
		textIxr_2.setColumns(10);
		textIxr_2.setBounds(70, 140, 160, 16);
		contentPane.add(textIxr_2);
		
		//Set text box to show data of IXR3
		textIxr_3 = new JTextField();
		textIxr_3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c)) {
					e.consume();
					}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textIxr_3.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<16) {
						textIxr_3.setEditable(true);
					} else {
						textIxr_3.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textIxr_3.setEditable(true);
					} else {
						textIxr_3.setEditable(false);
					}
					}
			}
		});
		textIxr_3.setColumns(10);
		textIxr_3.setBounds(70, 160, 160, 16);
		contentPane.add(textIxr_3);
		
		//when LD button for GPR0 is clicked
		JButton btnGpr_0 = new JButton("LD");
		btnGpr_0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Get instruction data from each text box  
				op_bit0 = textField.getText();
				op_bit1 = textField_1.getText();
				op_bit2 = textField_2.getText();
				op_bit3 = textField_3.getText();
				op_bit4 = textField_4.getText();
				op_bit5 = textField_5.getText();
				gpr_bit1 = textField_6.getText();
				gpr_bit0 = textField_7.getText();
				ixr_bit1 = textField_8.getText();
				ixr_bit0 = textField_9.getText();
				i = textField_10.getText();
				ip_add_4_bit = textField_11.getText();
				ip_add_3_bit = textField_12.getText();
				ip_add_2_bit = textField_13.getText();
				ip_add_1_bit = textField_14.getText();
				ip_add_0_bit = textField_15.getText();
				
				address =  ip_add_4_bit + ip_add_3_bit + ip_add_2_bit + ip_add_1_bit + ip_add_0_bit;
				ixr = ixr_bit1 + ixr_bit0;
				gpr = gpr_bit1 + gpr_bit0;
				operation = op_bit0 +  op_bit1 + op_bit2 + op_bit3 + op_bit4 + op_bit5;
				instruction = operation + gpr + ixr + i + address;
				
				//Call this function to load data into GPR0
				load_Gpr0();
			}
		});
		btnGpr_0.setBounds(240, 20, 25, 15);
		contentPane.add(btnGpr_0);
		
		//when LD button for GPR1 is clicked
		JButton btnGpr_1 = new JButton("LD");
		btnGpr_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op_bit0 = textField.getText();
				op_bit1 = textField_1.getText();
				op_bit2 = textField_2.getText();
				op_bit3 = textField_3.getText();
				op_bit4 = textField_4.getText();
				op_bit5 = textField_5.getText();
				gpr_bit1 = textField_6.getText();
				gpr_bit0 = textField_7.getText();
				ixr_bit1 = textField_8.getText();
				ixr_bit0 = textField_9.getText();
				i = textField_10.getText();
				ip_add_4_bit = textField_11.getText();
				ip_add_3_bit = textField_12.getText();
				ip_add_2_bit = textField_13.getText();
				ip_add_1_bit = textField_14.getText();
				ip_add_0_bit = textField_15.getText();
				
				address =  ip_add_4_bit + ip_add_3_bit + ip_add_2_bit + ip_add_1_bit + ip_add_0_bit;
				ixr = ixr_bit1 + ixr_bit0;
				gpr = gpr_bit1 + gpr_bit0;
				operation = op_bit0 +  op_bit1 + op_bit2 + op_bit3 + op_bit4 + op_bit5;
				instruction = operation + gpr + ixr + i + address;
				
				load_Gpr1();
			}
		});
		btnGpr_1.setBounds(240, 41, 25, 15);
		contentPane.add(btnGpr_1);
		
		//when LD button for GPR1 is clicked
		JButton btnGpr_2 = new JButton("LD");
		btnGpr_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op_bit0 = textField.getText();
				op_bit1 = textField_1.getText();
				op_bit2 = textField_2.getText();
				op_bit3 = textField_3.getText();
				op_bit4 = textField_4.getText();
				op_bit5 = textField_5.getText();
				gpr_bit1 = textField_6.getText();
				gpr_bit0 = textField_7.getText();
				ixr_bit1 = textField_8.getText();
				ixr_bit0 = textField_9.getText();
				i = textField_10.getText();
				ip_add_4_bit = textField_11.getText();
				ip_add_3_bit = textField_12.getText();
				ip_add_2_bit = textField_13.getText();
				ip_add_1_bit = textField_14.getText();
				ip_add_0_bit = textField_15.getText();
				
				address =  ip_add_4_bit + ip_add_3_bit + ip_add_2_bit + ip_add_1_bit + ip_add_0_bit;
				ixr = ixr_bit1 + ixr_bit0;
				gpr = gpr_bit1 + gpr_bit0;
				operation = op_bit0 +  op_bit1 + op_bit2 + op_bit3 + op_bit4 + op_bit5;
				instruction = operation + gpr + ixr + i + address;
				
				load_Gpr2();
			}
		});
		btnGpr_2.setBounds(240, 60, 25, 15);
		contentPane.add(btnGpr_2);
		
	    //when LD button for GPR3 is clicked
		JButton btnGpr_3 = new JButton("LD");
		btnGpr_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op_bit0 = textField.getText();
				op_bit1 = textField_1.getText();
				op_bit2 = textField_2.getText();
				op_bit3 = textField_3.getText();
				op_bit4 = textField_4.getText();
				op_bit5 = textField_5.getText();
				gpr_bit1 = textField_6.getText();
				gpr_bit0 = textField_7.getText();
				ixr_bit1 = textField_8.getText();
				ixr_bit0 = textField_9.getText();
				i = textField_10.getText();
				ip_add_4_bit = textField_11.getText();
				ip_add_3_bit = textField_12.getText();
				ip_add_2_bit = textField_13.getText();
				ip_add_1_bit = textField_14.getText();
				ip_add_0_bit = textField_15.getText();
				
				address =  ip_add_4_bit + ip_add_3_bit + ip_add_2_bit + ip_add_1_bit + ip_add_0_bit;
				ixr = ixr_bit1 + ixr_bit0;
				gpr = gpr_bit1 + gpr_bit0;
				operation = op_bit0 +  op_bit1 + op_bit2 + op_bit3 + op_bit4 + op_bit5;
				instruction = operation + gpr + ixr + i + address;
				
				load_Gpr3();
			}
		});
		btnGpr_3.setBounds(240, 80, 25, 15);
		contentPane.add(btnGpr_3);
		
	    //when LD button for IXR1 is clicked
		JButton btnIxr_1 = new JButton("LD");
		btnIxr_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op_bit0 = textField.getText();
				op_bit1 = textField_1.getText();
				op_bit2 = textField_2.getText();
				op_bit3 = textField_3.getText();
				op_bit4 = textField_4.getText();
				op_bit5 = textField_5.getText();
				gpr_bit1 = textField_6.getText();
				gpr_bit0 = textField_7.getText();
				ixr_bit1 = textField_8.getText();
				ixr_bit0 = textField_9.getText();
				i = textField_10.getText();
				ip_add_4_bit = textField_11.getText();
				ip_add_3_bit = textField_12.getText();
				ip_add_2_bit = textField_13.getText();
				ip_add_1_bit = textField_14.getText();
				ip_add_0_bit = textField_15.getText();
				
				address =  ip_add_4_bit + ip_add_3_bit + ip_add_2_bit + ip_add_1_bit + ip_add_0_bit;
				ixr = ixr_bit1 + ixr_bit0;
				gpr = gpr_bit1 + gpr_bit0;
				operation = op_bit0 +  op_bit1 + op_bit2 + op_bit3 + op_bit4 + op_bit5;
				instruction = operation + gpr + ixr + i + address;
				
				load_Ixr1();
			}
		});
		btnIxr_1.setBounds(240, 121, 25, 15);
		contentPane.add(btnIxr_1);
		
		//when LD button for IXR2 is clicked
		JButton btnIxr_2 = new JButton("LD");
		btnIxr_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op_bit0 = textField.getText();
				op_bit1 = textField_1.getText();
				op_bit2 = textField_2.getText();
				op_bit3 = textField_3.getText();
				op_bit4 = textField_4.getText();
				op_bit5 = textField_5.getText();
				gpr_bit1 = textField_6.getText();
				gpr_bit0 = textField_7.getText();
				ixr_bit1 = textField_8.getText();
				ixr_bit0 = textField_9.getText();
				i = textField_10.getText();
				ip_add_4_bit = textField_11.getText();
				ip_add_3_bit = textField_12.getText();
				ip_add_2_bit = textField_13.getText();
				ip_add_1_bit = textField_14.getText();
				ip_add_0_bit = textField_15.getText();
				
				address =  ip_add_4_bit + ip_add_3_bit + ip_add_2_bit + ip_add_1_bit + ip_add_0_bit;
				ixr = ixr_bit1 + ixr_bit0;
				gpr = gpr_bit1 + gpr_bit0;
				operation = op_bit0 +  op_bit1 + op_bit2 + op_bit3 + op_bit4 + op_bit5;
				instruction = operation + gpr + ixr + i + address;
				
				load_Ixr2();
			}
		});
		btnIxr_2.setBounds(240, 141, 25, 15);
		contentPane.add(btnIxr_2);
		
		//when LD button for IXR3 is clicked
		JButton btnIxr_3 = new JButton("LD");
		btnIxr_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op_bit0 = textField.getText();
				op_bit1 = textField_1.getText();
				op_bit2 = textField_2.getText();
				op_bit3 = textField_3.getText();
				op_bit4 = textField_4.getText();
				op_bit5 = textField_5.getText();
				gpr_bit1 = textField_6.getText();
				gpr_bit0 = textField_7.getText();
				ixr_bit1 = textField_8.getText();
				ixr_bit0 = textField_9.getText();
				i = textField_10.getText();
				ip_add_4_bit = textField_11.getText();
				ip_add_3_bit = textField_12.getText();
				ip_add_2_bit = textField_13.getText();
				ip_add_1_bit = textField_14.getText();
				ip_add_0_bit = textField_15.getText();
				
				address =  ip_add_4_bit + ip_add_3_bit + ip_add_2_bit + ip_add_1_bit + ip_add_0_bit;
				ixr = ixr_bit1 + ixr_bit0;
				gpr = gpr_bit1 + gpr_bit0;
				operation = op_bit0 +  op_bit1 + op_bit2 + op_bit3 + op_bit4 + op_bit5;
				instruction = operation + gpr + ixr + i + address;
				
				load_Ixr3();
			}
		});
		btnIxr_3.setBounds(240, 161, 25, 15);
		contentPane.add(btnIxr_3);
		
		//Labels for PC, MAR, MBR, IR, MFR, Privileged
		JLabel lblPC = new JLabel("PC");
		lblPC.setBounds(335, 20, 25, 16);
		contentPane.add(lblPC);
		
		JLabel lblMAR = new JLabel("MAR");
		lblMAR.setBounds(335, 40, 30, 16);
		contentPane.add(lblMAR);
		
		JLabel lblMBR = new JLabel("MBR");
		lblMBR.setBounds(335, 61, 30, 15);
		contentPane.add(lblMBR);
		
		JLabel lblIR = new JLabel("IR");
		lblIR.setBounds(335, 80, 25, 16);
		contentPane.add(lblIR);
		
		JLabel lblMFR = new JLabel("MFR");
		lblMFR.setBounds(443, 101, 30, 16);
		contentPane.add(lblMFR);
		
		JLabel lblPriv = new JLabel("Privileged");
		lblPriv.setBounds(407, 120, 66, 16);
		contentPane.add(lblPriv);
		
		//Set a text box to show data of PC
		textPC = new JTextField();	
		textPC.setColumns(10);
		textPC.setBounds(415, 20, 120, 16);
		contentPane.add(textPC);
		
		//Set a text box to show data of MAR
		textMAR = new JTextField();
		textMAR.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c)) {
					e.consume();
					}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textMAR.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<12) {
						textMAR.setEditable(true);
					} else {
						textMAR.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textMAR.setEditable(true);
					} else {
						textMAR.setEditable(false);
					}
					}
			}
		});
		textMAR.setColumns(10);
		textMAR.setBounds(415, 40, 120, 16);
		contentPane.add(textMAR);
		
		//Set a text box to show data of MBR
		textMBR = new JTextField();
		textMBR.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c)) {
					e.consume();
					}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textMBR.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<16) {
						textMBR.setEditable(true);
					} else {
						textMBR.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textMBR.setEditable(true);
					} else {
						textMBR.setEditable(false);
					}
					}
			}
		});
		textMBR.setColumns(10);
		textMBR.setBounds(377, 60, 160, 16);
		contentPane.add(textMBR);
		
		//Set a text box to show data of IR
		textIR = new JTextField();
		textIR.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c)) {
					e.consume();
					}
			}
		});
		textIR.setColumns(10);
		textIR.setBounds(375, 80, 160, 16);
		contentPane.add(textIR);
		
		//Set a text box to show data of MFR
		textMFR = new JTextField();
		textMFR.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c)) {
					e.consume();
					}
			}
		});
		textMFR.setColumns(10);
		textMFR.setBounds(485, 101, 50, 16);
		contentPane.add(textMFR);
		
		//Set a text box to show data of Privileged
		textPriv = new JTextField();
		textPriv.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(!Character.isDigit(c)) {
					e.consume();
					}
			}
		});
		textPriv.setColumns(10);
		textPriv.setBounds(515, 120, 20, 16);
		contentPane.add(textPriv);
		
		//when LD button for PC is clicked
		JButton btnPC = new JButton("LD");
		btnPC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op_bit0 = textField.getText();
				op_bit1 = textField_1.getText();
				op_bit2 = textField_2.getText();
				op_bit3 = textField_3.getText();
				op_bit4 = textField_4.getText();
				op_bit5 = textField_5.getText();
				gpr_bit1 = textField_6.getText();
				gpr_bit0 = textField_7.getText();
				ixr_bit1 = textField_8.getText();
				ixr_bit0 = textField_9.getText();
				i = textField_10.getText();
				ip_add_4_bit = textField_11.getText();
				ip_add_3_bit = textField_12.getText();
				ip_add_2_bit = textField_13.getText();
				ip_add_1_bit = textField_14.getText();
				ip_add_0_bit = textField_15.getText();
				
				address =  ip_add_4_bit + ip_add_3_bit + ip_add_2_bit + ip_add_1_bit + ip_add_0_bit;
				ixr = ixr_bit1 + ixr_bit0;
				gpr = gpr_bit1 + gpr_bit0;
				operation = op_bit0 +  op_bit1 + op_bit2 + op_bit3 + op_bit4 + op_bit5;
				instruction = operation + gpr + ixr + i + address;
				
				load_PC();
			}
		});
		btnPC.setBounds(545, 20, 25, 15);
		contentPane.add(btnPC);
		
		//when LD button for MAR is clicked
		JButton btnMAR = new JButton("LD");
		btnMAR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op_bit0 = textField.getText();
				op_bit1 = textField_1.getText();
				op_bit2 = textField_2.getText();
				op_bit3 = textField_3.getText();
				op_bit4 = textField_4.getText();
				op_bit5 = textField_5.getText();
				gpr_bit1 = textField_6.getText();
				gpr_bit0 = textField_7.getText();
				ixr_bit1 = textField_8.getText();
				ixr_bit0 = textField_9.getText();
				i = textField_10.getText();
				ip_add_4_bit = textField_11.getText();
				ip_add_3_bit = textField_12.getText();
				ip_add_2_bit = textField_13.getText();
				ip_add_1_bit = textField_14.getText();
				ip_add_0_bit = textField_15.getText();
				
				address =  ip_add_4_bit + ip_add_3_bit + ip_add_2_bit + ip_add_1_bit + ip_add_0_bit;
				ixr = ixr_bit1 + ixr_bit0;
				gpr = gpr_bit1 + gpr_bit0;
				operation = op_bit0 +  op_bit1 + op_bit2 + op_bit3 + op_bit4 + op_bit5;
				instruction = operation + gpr + ixr + i + address;
				
				load_MAR();
			}
		});
		btnMAR.setBounds(545, 40, 25, 15);
		contentPane.add(btnMAR);
		
		//when LD button for MBR is clicked
		JButton btnMBR = new JButton("LD");
		btnMBR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op_bit0 = textField.getText();
				op_bit1 = textField_1.getText();
				op_bit2 = textField_2.getText();
				op_bit3 = textField_3.getText();
				op_bit4 = textField_4.getText();
				op_bit5 = textField_5.getText();
				gpr_bit1 = textField_6.getText();
				gpr_bit0 = textField_7.getText();
				ixr_bit1 = textField_8.getText();
				ixr_bit0 = textField_9.getText();
				i = textField_10.getText();
				ip_add_4_bit = textField_11.getText();
				ip_add_3_bit = textField_12.getText();
				ip_add_2_bit = textField_13.getText();
				ip_add_1_bit = textField_14.getText();
				ip_add_0_bit = textField_15.getText();
				
				address =  ip_add_4_bit + ip_add_3_bit + ip_add_2_bit + ip_add_1_bit + ip_add_0_bit;
				ixr = ixr_bit1 + ixr_bit0;
				gpr = gpr_bit1 + gpr_bit0;
				operation = op_bit0 +  op_bit1 + op_bit2 + op_bit3 + op_bit4 + op_bit5;
				instruction = operation + gpr + ixr + i + address;
				
				load_MBR();
			}
		});
		btnMBR.setBounds(545, 60, 25, 15);
		contentPane.add(btnMBR);
		
		
		//Buttons for instruction 0-15
		//User cannot click these buttons for now. 
		JButton btnNewButton = new JButton("15");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (typeFlag == 1) {
					textField.setText("1");
					typeFlag = 0;
				}
				else {
					textField.setText("0");
					typeFlag = 1;
				}
			}
		});
		btnNewButton.setBounds(20, 250, 30, 45);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("14");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (typeFlag == 1) {
					textField_1.setText("1");
					typeFlag = 0;
				}
				else {
					textField_1.setText("0");
					typeFlag = 1;
				}
			}
		});
		btnNewButton_1.setBounds(50, 250, 30, 45);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("13");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (typeFlag == 1) {
					textField_2.setText("1");
					typeFlag = 0;
				}
				else {
					textField_2.setText("0");
					typeFlag = 1;
				}
			}
		});
		btnNewButton_2.setBounds(80, 250, 30, 45);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("12");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (typeFlag == 1) {
					textField_3.setText("1");
					typeFlag = 0;
				}
				else {
					textField_3.setText("0");
					typeFlag = 1;
				}
			}
		});
		btnNewButton_3.setBounds(110, 250, 30, 45);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("11");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (typeFlag == 1) {
					textField_4.setText("1");
					typeFlag = 0;
				}
				else {
					textField_4.setText("0");
					typeFlag = 1;
				}
			}
		});
		btnNewButton_4.setBounds(140, 250, 30, 45);
		contentPane.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("10");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (typeFlag == 1) {
					textField_5.setText("1");
					typeFlag = 0;
				}
				else {
					textField_5.setText("0");
					typeFlag = 1;
				}
			}
		});
		btnNewButton_5.setBounds(170, 250, 30, 45);
		contentPane.add(btnNewButton_5);
		
		JButton btnNewButton_5_1 = new JButton("9");
		btnNewButton_5_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (typeFlag == 1) {
					textField_6.setText("1");
					typeFlag = 0;
				}
				else {
					textField_6.setText("0");
					typeFlag = 1;
				}
			}
		});
		btnNewButton_5_1.setBounds(215, 250, 30, 45);
		contentPane.add(btnNewButton_5_1);
		
		JButton btnNewButton_5_2 = new JButton("8");
		btnNewButton_5_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (typeFlag == 1) {
					textField_7.setText("1");
					typeFlag = 0;
				}
				else {
					textField_7.setText("0");
					typeFlag = 1;
				}
			}
		});
		btnNewButton_5_2.setBounds(245, 250, 30, 45);
		contentPane.add(btnNewButton_5_2);
		
		JButton btnNewButton_5_2_1 = new JButton("7");
		btnNewButton_5_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (typeFlag == 1) {
					textField_8.setText("1");
					typeFlag = 0;
				}
				else {
					textField_8.setText("0");
					typeFlag = 1;
				}
			}
		});
		btnNewButton_5_2_1.setBounds(290, 250, 30, 45);
		contentPane.add(btnNewButton_5_2_1);
		
		JButton btnNewButton_5_2_2 = new JButton("6");
		btnNewButton_5_2_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (typeFlag == 1) {
					textField_9.setText("1");
					typeFlag = 0;
				}
				else {
					textField_9.setText("0");
					typeFlag = 1;
				}
			}
		});
		btnNewButton_5_2_2.setBounds(320, 250, 30, 45);
		contentPane.add(btnNewButton_5_2_2);
		
		JButton btnNewButton_5_2_3 = new JButton("5");
		btnNewButton_5_2_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (typeFlag == 1) {
					textField_10.setText("1");
					typeFlag = 0;
				}
				else {
					textField_10.setText("0");
					typeFlag = 1;
				}
			}
		});
		btnNewButton_5_2_3.setBounds(365, 250, 30, 45);
		contentPane.add(btnNewButton_5_2_3);
		
		JButton btnNewButton_5_2_4 = new JButton("4");
		btnNewButton_5_2_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (typeFlag == 1) {
					textField_11.setText("1");
					typeFlag = 0;
				}
				else {
					textField_11.setText("0");
					typeFlag = 1;
				}
			}
		});
		btnNewButton_5_2_4.setBounds(410, 250, 30, 45);
		contentPane.add(btnNewButton_5_2_4);
		
		JButton btnNewButton_5_2_4_1 = new JButton("3");
		btnNewButton_5_2_4_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (typeFlag == 1) {
					textField_12.setText("1");
					typeFlag = 0;
				}
				else {
					textField_12.setText("0");
					typeFlag = 1;
				}
			}
		});
		btnNewButton_5_2_4_1.setBounds(440, 250, 30, 45);
		contentPane.add(btnNewButton_5_2_4_1);
		
		JButton btnNewButton_5_2_4_2 = new JButton("2");
		btnNewButton_5_2_4_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (typeFlag == 1) {
					textField_13.setText("1");
					typeFlag = 0;
				}
				else {
					textField_13.setText("0");
					typeFlag = 1;
				}
			}
		});
		btnNewButton_5_2_4_2.setBounds(470, 250, 30, 45);
		contentPane.add(btnNewButton_5_2_4_2);
		
		JButton btnNewButton_5_2_4_3 = new JButton("1");
		btnNewButton_5_2_4_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (typeFlag == 1) {
					textField_14.setText("1");
					typeFlag = 0;
				}
				else {
					textField_14.setText("0");
					typeFlag = 1;
				}
			}
		});
		btnNewButton_5_2_4_3.setBounds(500, 250, 30, 45);
		contentPane.add(btnNewButton_5_2_4_3);
		
		JButton btnNewButton_5_2_4_4 = new JButton("0");
		btnNewButton_5_2_4_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (typeFlag == 1) {
					textField_15.setText("1");
					typeFlag = 0;
				}
				else {
					textField_15.setText("0");
					typeFlag = 1;
				}
			}
		});
		btnNewButton_5_2_4_4.setBounds(530, 250, 30, 45);
		contentPane.add(btnNewButton_5_2_4_4);
		
		//Labels for Operation, GPR, IXR, I, Address
		JLabel lblNewLabel = new JLabel("Operation");
		lblNewLabel.setBounds(79, 307, 66, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("GPR");
		lblNewLabel_1.setBounds(235, 307, 30, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("IXR");
		lblNewLabel_2.setBounds(310, 307, 25, 16);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("I");
		lblNewLabel_3.setBounds(380, 307, 9, 16);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Address");
		lblNewLabel_4.setBounds(460, 307, 65, 16);
		contentPane.add(lblNewLabel_4);
		
		//when Load button is clicked
		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op_bit0 = textField.getText();
				op_bit1 = textField_1.getText();
				op_bit2 = textField_2.getText();
				op_bit3 = textField_3.getText();
				op_bit4 = textField_4.getText();
				op_bit5 = textField_5.getText();
				gpr_bit1 = textField_6.getText();
				gpr_bit0 = textField_7.getText();
				ixr_bit1 = textField_8.getText();
				ixr_bit0 = textField_9.getText();
				i = textField_10.getText();
				ip_add_4_bit = textField_11.getText();
				ip_add_3_bit = textField_12.getText();
				ip_add_2_bit = textField_13.getText();
				ip_add_1_bit = textField_14.getText();
				ip_add_0_bit = textField_15.getText();
				
				address =  ip_add_4_bit + ip_add_3_bit + ip_add_2_bit + ip_add_1_bit + ip_add_0_bit;
				ixr = ixr_bit1 + ixr_bit0;
				gpr = gpr_bit1 + gpr_bit0;
				operation = op_bit0 +  op_bit1 + op_bit2 + op_bit3 + op_bit4 + op_bit5;
				instruction = operation + gpr + ixr + i + address;
				
				load();
				
				
			}
		});
		btnLoad.setBounds(215, 342, 75, 30);
		contentPane.add(btnLoad);
		
		//when Store button is clicked
		JButton btnStore = new JButton("Store");
		btnStore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op_bit0 = textField.getText();
				op_bit1 = textField_1.getText();
				op_bit2 = textField_2.getText();
				op_bit3 = textField_3.getText();
				op_bit4 = textField_4.getText();
				op_bit5 = textField_5.getText();
				gpr_bit1 = textField_6.getText();
				gpr_bit0 = textField_7.getText();
				ixr_bit1 = textField_8.getText();
				ixr_bit0 = textField_9.getText();
				i = textField_10.getText();
				ip_add_4_bit = textField_11.getText();
				ip_add_3_bit = textField_12.getText();
				ip_add_2_bit = textField_13.getText();
				ip_add_1_bit = textField_14.getText();
				ip_add_0_bit = textField_15.getText();
				
				address =  ip_add_4_bit + ip_add_3_bit + ip_add_2_bit + ip_add_1_bit + ip_add_0_bit;
				ixr = ixr_bit1 + ixr_bit0;
				gpr = gpr_bit1 + gpr_bit0;
				operation = op_bit0 +  op_bit1 + op_bit2 + op_bit3 + op_bit4 + op_bit5;
				instruction = operation + gpr + ixr + i + address;
				
				store();
			}
		});
		btnStore.setBounds(89, 342, 75, 30);
		contentPane.add(btnStore);
		
		//when IPL button is clicked
		JButton btnNewButton_6 = new JButton("IPL");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IPL();
			}
		});
		btnNewButton_6.setBounds(310, 342, 65, 30);
		contentPane.add(btnNewButton_6);
		
		//when SS button is clicked
		JButton btnNewButton_7 = new JButton("SS");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SS(0);
			}
		});
		btnNewButton_7.setBounds(170, 323, 30, 45);
		contentPane.add(btnNewButton_7);
		
		//Text box for operation bit0
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textField.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<1) {
						textField.setEditable(true);
					} else {
						textField.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textField.setEditable(true);
					} else {
						textField.setEditable(false);
					}
					}
			}
		});
		textField.setBounds(22, 215, 25, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		
		//Text box for operation bit1
		textField_1 = new JTextField();
		textField_1.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textField_1.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<1) {
						textField_1.setEditable(true);
					} else {
						textField_1.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textField_1.setEditable(true);
					} else {
						textField_1.setEditable(false);
					}
					}
			}
		});
		textField_1.setColumns(10);
		textField_1.setBounds(52, 215, 25, 26);
		contentPane.add(textField_1);
		
		//Text box for operation bit2
		textField_2 = new JTextField();
		textField_2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textField_2.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<1) {
						textField_2.setEditable(true);
					} else {
						textField_2.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textField_2.setEditable(true);
					} else {
						textField_2.setEditable(false);
					}
					}
			}
		});
		textField_2.setColumns(10);
		textField_2.setBounds(82, 215, 25, 26);
		contentPane.add(textField_2);
		
		//Text box for operation bit3
		textField_3 = new JTextField();
		textField_3.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textField_3.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<1) {
						textField_3.setEditable(true);
					} else {
						textField_3.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textField_3.setEditable(true);
					} else {
						textField_3.setEditable(false);
					}
					}
			}
		});
		textField_3.setColumns(10);
		textField_3.setBounds(112, 215, 25, 26);
		contentPane.add(textField_3);
		
		//Text box for operation bit4
		textField_4 = new JTextField();
		textField_4.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textField_4.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<1) {
						textField_4.setEditable(true);
					} else {
						textField_4.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textField_4.setEditable(true);
					} else {
						textField_4.setEditable(false);
					}
					}
			}
		});
		textField_4.setColumns(10);
		textField_4.setBounds(142, 215, 25, 26);
		contentPane.add(textField_4);

		
		//Text box for operation bit5
		textField_5 = new JTextField();
		textField_5.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textField_5.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<1) {
						textField_5.setEditable(true);
					} else {
						textField_5.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textField_5.setEditable(true);
					} else {
						textField_5.setEditable(false);
					}
					}
			}
		});
		textField_5.setColumns(10);
		textField_5.setBounds(172, 215, 25, 26);
		contentPane.add(textField_5);
		
		//Text box for GPR bit1
		textField_6 = new JTextField();
		textField_6.addKeyListener(new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			String input = textField_6.getText();
			int length = input.length();
			char c = e.getKeyChar();
			if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
				if(length<1) {
					textField_6.setEditable(true);
				} else {
					textField_6.setEditable(false);
				} 
			} else {
				if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
					textField_6.setEditable(true);
				} else {
					textField_6.setEditable(false);
				}
				}
		}
	});
		textField_6.setColumns(10);
		textField_6.setBounds(218, 215, 25, 26);
		contentPane.add(textField_6);
		
		//Text box for GPR bit0
		textField_7 = new JTextField();
		textField_7.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textField_7.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<1) {
						textField_7.setEditable(true);
					} else {
						textField_7.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textField_7.setEditable(true);
					} else {
						textField_7.setEditable(false);
					}
					}
			}
		});
		textField_7.setColumns(10);
		textField_7.setBounds(248, 215, 25, 26);
		contentPane.add(textField_7);
		
		//Text box for IXR bit1
		textField_8 = new JTextField();
		textField_8.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textField_8.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<1) {
						textField_8.setEditable(true);
					} else {
						textField_8.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textField_8.setEditable(true);
					} else {
						textField_8.setEditable(false);
					}
					}
			}
		});
		textField_8.setColumns(10);
		textField_8.setBounds(292, 215, 25, 26);
		contentPane.add(textField_8);
		
		//Text box for IXR bit0
		textField_9 = new JTextField();
		textField_9.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textField_9.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<1) {
						textField_9.setEditable(true);
					} else {
						textField_9.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textField_9.setEditable(true);
					} else {
						textField_9.setEditable(false);
					}
					}
			}
		});
		textField_9.setColumns(10);
		textField_9.setBounds(322, 215, 25, 26);
		contentPane.add(textField_9);
		
		//Text box for I
		textField_10 = new JTextField();
		textField_10.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textField_10.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<1) {
						textField_10.setEditable(true);
					} else {
						textField_10.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textField_10.setEditable(true);
					} else {
						textField_10.setEditable(false);
					}
					}
			}
		});
		textField_10.setColumns(10);
		textField_10.setBounds(367, 215, 25, 26);
		contentPane.add(textField_10);
		
		//Text box for address bit4
		textField_11 = new JTextField();
		textField_11.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textField_11.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<1) {
						textField_11.setEditable(true);
					} else {
						textField_11.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textField_11.setEditable(true);
					} else {
						textField_11.setEditable(false);
					}
					}
			}
		});
		textField_11.setColumns(10);
		textField_11.setBounds(412, 215, 25, 26);
		contentPane.add(textField_11);
		
		//Text box for address bit3
		textField_12 = new JTextField();
		textField_12.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textField_12.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<1) {
						textField_12.setEditable(true);
					} else {
						textField_12.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textField_12.setEditable(true);
					} else {
						textField_12.setEditable(false);
					}
					}
			}
		});
		textField_12.setColumns(10);
		textField_12.setBounds(442, 215, 25, 26);
		contentPane.add(textField_12);
		
		//Text box for address bit2
		textField_13 = new JTextField();
		textField_13.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textField_13.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<1) {
						textField_13.setEditable(true);
					} else {
						textField_13.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textField_13.setEditable(true);
					} else {
						textField_13.setEditable(false);
					}
					}
			}
		});
		textField_13.setColumns(10);
		textField_13.setBounds(472, 215, 25, 26);
		contentPane.add(textField_13);
		
		//Text box for address bit1
		textField_14 = new JTextField();
		textField_14.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textField_14.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<1) {
						textField_14.setEditable(true);
					} else {
						textField_14.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textField_14.setEditable(true);
					} else {
						textField_14.setEditable(false);
					}
					}
			}
		});
		textField_14.setColumns(10);
		textField_14.setBounds(502, 215, 25, 26);
		contentPane.add(textField_14);
		
		//Text box for address bit0
		textField_15 = new JTextField();
		textField_15.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String input = textField_15.getText();
				int length = input.length();
				char c = e.getKeyChar();
				if(e.getKeyChar()>= '0' && e.getKeyChar()<='1') {
					if(length<1) {
						textField_15.setEditable(true);
					} else {
						textField_15.setEditable(false);
					} 
				} else {
					if(e.getExtendedKeyCode()==KeyEvent.VK_BACK_SPACE || e.getExtendedKeyCode()==KeyEvent.VK_DELETE) {
						textField_15.setEditable(true);
					} else {
						textField_15.setEditable(false);
					}
					}
			}
		});
		textField_15.setColumns(10);
		textField_15.setBounds(532, 215, 25, 26);
		contentPane.add(textField_15);
		
		//Labels for Halt, Run
		JLabel lblNewLabel_5 = new JLabel("Halt");
		lblNewLabel_5.setBounds(20, 336, 30, 16);
		contentPane.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Run");
		lblNewLabel_6.setBounds(20, 368, 35, 16);
		contentPane.add(lblNewLabel_6);
		
		//Show if program is halted
		textHalt = new JTextField();
		textHalt.setBackground(Color.WHITE);
		textHalt.setBounds(50, 331, 25, 26);
		contentPane.add(textHalt);
		textHalt.setColumns(10);
		
		//Show if program is running
		textRun = new JTextField();
		textRun.setColumns(10);
		textRun.setBackground(Color.GREEN);
		textRun.setBounds(50, 363, 25, 26);
		contentPane.add(textRun);
		
		JButton btnNewButton_8 = new JButton("Load Program 1");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadProgram1();
			}
		});
		btnNewButton_8.setBounds(20, 396, 142, 29);
		contentPane.add(btnNewButton_8);
		
		JButton btnNewButton_9 = new JButton("Input");
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String data = textField_17.getText();
				input(data);
			}
		});
		btnNewButton_9.setBounds(23, 426, 117, 29);
		contentPane.add(btnNewButton_9);
		
		JButton btnNewButton_10 = new JButton("Get answer");
		btnNewButton_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runProgram1ToTheEnd();
			}
		});
		btnNewButton_10.setBounds(172, 467, 117, 29);
		contentPane.add(btnNewButton_10);
		
		JButton btnNewButton_11 = new JButton("Run single instruction");
		btnNewButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SS(1);
			}
		});
		btnNewButton_11.setBounds(6, 467, 171, 29);
		contentPane.add(btnNewButton_11);
		
		textField_17 = new JTextField();
		textField_17.setBounds(145, 426, 130, 26);
		contentPane.add(textField_17);
		textField_17.setColumns(10);
		
		textArea = new JTextArea();
		textArea.setEditable (false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(32, 508, 516, 250);
		contentPane.add(scrollPane);
		
		JLabel lblNewLabel_7 = new JLabel("Keyboard");
		lblNewLabel_7.setBounds(335, 160, 61, 16);
		contentPane.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Printer");
		lblNewLabel_8.setBounds(335, 185, 61, 16);
		contentPane.add(lblNewLabel_8);
		
		textField_16 = new JTextField();
		textField_16.setBounds(407, 155, 130, 26);
		contentPane.add(textField_16);
		textField_16.setColumns(10);
		
		textField_18 = new JTextField();
		textField_18.setBounds(407, 180, 130, 26);
		contentPane.add(textField_18);
		textField_18.setColumns(10);
		
		JButton btnNewButton_12 = new JButton("Load Program 2");
		btnNewButton_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadProgram2();
			}
		});
		btnNewButton_12.setBounds(299, 396, 141, 29);
		contentPane.add(btnNewButton_12);
		
		JButton btnNewButton_13 = new JButton("Input");
		btnNewButton_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				program2_input();
			}
		});
		btnNewButton_13.setBounds(443, 396, 117, 29);
		contentPane.add(btnNewButton_13);
		
		textField_19 = new JTextField();
		textField_19.setBounds(310, 426, 247, 39);
		contentPane.add(textField_19);
		textField_19.setColumns(10);
		
		JButton btnNewButton_14 = new JButton("Run single instruction");
		btnNewButton_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SS(2);
			}
		});
		btnNewButton_14.setBounds(290, 467, 171, 29);
		contentPane.add(btnNewButton_14);
		
		JButton btnNewButton_15 = new JButton("Get answer");
		btnNewButton_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runProgram2ToTheEnd();
			}
		});
		btnNewButton_15.setBounds(465, 467, 117, 29);
		contentPane.add(btnNewButton_15);
		
		textField_20 = new JTextField();
		textField_20.setBounds(460, 326, 130, 26);
		contentPane.add(textField_20);
		textField_20.setColumns(10);
		
		textField_21 = new JTextField();
		textField_21.setBounds(460, 363, 130, 26);
		contentPane.add(textField_21);
		textField_21.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Fr1");
		lblNewLabel_9.setBounds(387, 331, 61, 16);
		contentPane.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Fr2");
		lblNewLabel_10.setBounds(387, 368, 61, 16);
		contentPane.add(lblNewLabel_10);
	}
	
	// get the current status of CPU and display it after each click
	public void display() {
		textGpr_0.setText(Integer.toBinaryString(cpu.get_number(1)));
        textGpr_1.setText(Integer.toBinaryString(cpu.get_number(2)));
        textGpr_2.setText(Integer.toBinaryString(cpu.get_number(3)));
        textGpr_3.setText(Integer.toBinaryString(cpu.get_number(4)));
        textIxr_1.setText(Integer.toBinaryString(cpu.get_number(5)));
        textIxr_2.setText(Integer.toBinaryString(cpu.get_number(6)));
        textIxr_3.setText(Integer.toBinaryString(cpu.get_number(7)));
        textPC.setText(Integer.toBinaryString(cpu.get_number(8)));
        textMAR.setText(Integer.toBinaryString(cpu.get_number(9)));
        textMBR.setText(Integer.toBinaryString(cpu.get_number(10)));
        textIR.setText(Integer.toBinaryString(cpu.get_number(11)));
        textMFR.setText(Integer.toBinaryString(cpu.get_number(12)));
        textField_16.setText(Integer.toString(cpu.get_number(13)));
        textField_18.setText(Integer.toString(cpu.get_number(14)));
        textField_20.setText(Double.toString(cpu.CPU.FRs.readFloat(0)));
        textField_20.setText(Double.toString(cpu.CPU.FRs.readFloat(1)));
	}
	
	// give the input of load instruction to the CPU and execute and check whether something went wrong or not. Halt if something went worng.
	public void load() {
        int halt = cpu.Load_button(instruction);
        display();
        if (halt == 1) {
        	textHalt.setBackground(Color.RED);
        	textRun.setBackground(Color.WHITE);
        }
	}
	
	// give the input of store instruction to the CPU and execute and check whether something went wrong or not. Halt if something went worng.
	public void store() {
        int halt = cpu.Store_button(instruction);
        display();
        if (halt == 1) {
        	textHalt.setBackground(Color.RED);
        	textRun.setBackground(Color.WHITE);
        }
	}
	
	// call the initial function of CPU which restart the machine
	public void IPL() {
		cpu.IPL_button();
		display();
        textHalt.setBackground(Color.WHITE);
       	textRun.setBackground(Color.GREEN);
	}
	
	// call the run single step function including all the operations of executing an instruction
	public void SS(int flag) {
		if (pro1flag1 == 2) { // print result
			textArea.setText(textArea.getText() + "\nThe 20 numbers are:\n");
			for (int i = 0; i < 20; i++) {
				textArea.setText(textArea.getText() + cpu.pro1_20numbers(i) + " ");
			}
			System.out.println(cpu.CPU.device.printer);
			textArea.setText(textArea.getText() + "\nThe closest number to " + cpu.pro1target() + " is in device1 printer, which is " + cpu.printer() + "\n");
			return;
		}
		int halt = cpu.SS_button();
		display();
		if (halt == 1) {
        	textHalt.setBackground(Color.RED);
        	textRun.setBackground(Color.WHITE);
        }
		if (flag == 1) {
			textArea.setText(textArea.getText() + "execute the instruction " + Integer.toBinaryString(cpu.get_number(11)) + ", then the PC is " + cpu.get_number(8) + "\n");
			if (cpu.get_number(11) == 0) {
				pro1flag1 = 2;
			}
		}
		else if (flag == 2) {
			textArea.setText(textArea.getText() + "execute the instruction " + Integer.toBinaryString(cpu.get_number(11)) + ", then the PC is " + cpu.get_number(8) + "\n");
		}
		else if (flag == 3) {
			textArea.setText(textArea.getText() + "\nexecute the instruction " + Integer.toBinaryString(cpu.get_number(11)) + ", then the PC is " + cpu.get_number(8) + "\n");
			textArea.setText(textArea.getText() + "Address2: " + cpu.CPU.cache.readCache(1009) + " Address4: " + cpu.CPU.cache.readCache(1014) + "\n");
			textArea.setText(textArea.getText() + "pointer2: " + cpu.CPU.cache.readCache(1017) + " pointer1: " + cpu.CPU.cache.readCache(1016) + "\n");
			textArea.setText(textArea.getText() + "Sentence number: " + cpu.CPU.cache.readCache(1019) + " Word number: " + cpu.CPU.cache.readCache(1018) + "\n");
			textArea.setText(textArea.getText() + "letter of pointer2: " + (char) cpu.CPU.cache.readCache(cpu.CPU.cache.readCache(1017)+8) + "   letter of pointer1: " + (char) cpu.CPU.cache.readCache(cpu.CPU.cache.readCache(1016)+8) + "\n");
		}
	}
	
	// load the input to the Gpr0
	public void load_Gpr0() {
		cpu.LD_button(instruction, 1);
		display();
	}
	
	// load the input to the Gpr1
	public void load_Gpr1() {
		cpu.LD_button(instruction, 2);
		display();
	}
	
	// load the input to the Gpr2
	public void load_Gpr2() {
		cpu.LD_button(instruction, 3);
		display();
	}
	
	// load the input to the Gpr3
	public void load_Gpr3() {
		cpu.LD_button(instruction, 4);
		display();
	}
	
	// load the input to the Ixr1
	public void load_Ixr1() {
		cpu.LD_button(instruction, 5);
		display();
	}
	
	// load the input to the Ixr2
	public void load_Ixr2() {
		cpu.LD_button(instruction, 6);
		display();
	}
	
	// load the input to the Ixr3
	public void load_Ixr3() {
		cpu.LD_button(instruction, 7);
		display();
	}
	
	// load the input to the PC
	public void load_PC() {
		cpu.LD_button(instruction, 8);
		display();
	}
	
	// load the input to the MAR
	public void load_MAR() {
		cpu.LD_button(instruction, 9);
		display();
	}
	
	// load the input to the MBR
	public void load_MBR() {
		cpu.LD_button(instruction, 10);
		display();
	}
	
	// put the data to the memory and limit the number within 0-65535
	public void input(String data) {
		if (pro1flag1 == 2) { // already have the result
			return;
		}
		int number = Integer.parseInt(data);
		if (number < 0) {
			textArea.setText(textArea.getText() + "the number should be Non-negtive, please write again\n");
			return;
		}
		else if (number > 65535) {
			textArea.setText(textArea.getText() + "the number should be less than 65536, please write again\n");
			return;
		}
		
		if (pro1flag1 == 0) {	//input the number to compare
			cpu.program1input(data);
			SS(1);
			SS(1);
			textArea.setText(textArea.getText() + "get compare number:" + data + "\nplease click the button Run single instruction \nOr click button Get answer to get the result directly" +"\n");
			return;
		}
		
		int next = cpu.program1input(data);	// put 20 number to the memory
		for (int i = 0; i < 7; i++) {
			SS(1);
		}
		if (next == 1) {
			textArea.setText(textArea.getText() + "getnumber:" + data + "\n\n");
		}
		else {
			textArea.setText(textArea.getText() + "getnumber:" + data + "\n\n");
			textArea.setText(textArea.getText() + "\nNow the memory has 20 numbers:\n");
			for (int i = 0; i < 20; i++) {
				textArea.setText(textArea.getText() + cpu.pro1_20numbers(i) + " ");
			}
			textArea.setText(textArea.getText() + "\nPlease enter another number to compare and get the closest number\n\n");
			pro1flag1 = 0;
		}
	}
	
	// initial or restart the program 1
	public void loadProgram1() {
		pro1flag1 = 1;
		cpu.loadprogram1();
		textArea.setText("Now the program1 has been loaded to the memory.\nPlease write 20 numbers in the textfield above the input button and press input button for each number\n then write the last number to compare and get the closest number from the 20 numbers\n\n");
		display();
        textHalt.setBackground(Color.WHITE);
       	textRun.setBackground(Color.GREEN);
       	SS(1);
	}
	
	// This function will run the program1 to the end and print the answer
	public void runProgram1ToTheEnd() {
		if (pro1flag1 == 1) {
			return;
		}
		do {
			SS(1);
		} while (pro1flag1 != 2);
		SS(1);
	}
	
	// initial or restart the program 2
	public void loadProgram2() {
		pro2flag1 = 1;
		cpu.loadprogram2();
		textArea.setText("Now the program2 has been loaded to the memory.\nPlease write 6 sentences in the textfield above the input button and press input button\n then write the target word to compare and detect the word in sentences and its location\n\n");
		display();
        textHalt.setBackground(Color.WHITE);
       	textRun.setBackground(Color.GREEN);
	}
	
	// program2 read 6 sentences or target word
	public void program2_input() {
		if (pro2flag1 == 1) {
			String sentences = textField_19.getText();
			char[] c = sentences.toCharArray();
			for (int i = 0; i < c.length; i++) {
				cpu.CPU.device.inputKeyboard(c[i] - 0);
				if (c[i] == '.') {
					i++;
				}
			}
			while (cpu.get_number(8) != 9) {
				SS(2);
			}
			textArea.setText(textArea.getText() + "\nNow the memory has 6 sentences(part of them is showing below):\n");
			for (int i = 1108; i <= cpu.CPU.cache.readCache(1009); i++) {
				textArea.setText(textArea.getText() + (char) cpu.CPU.cache.readCache(i));
			}
			textArea.setText(textArea.getText() + "\nAddress2: " + cpu.CPU.cache.readCache(1009) + " Address4: " + cpu.CPU.cache.readCache(1014) + "\n");
			textArea.setText(textArea.getText() + "\nPlease input another word to compare and detect in these sentences\n\n");
			pro2flag1 = 2;
		}
		else if (pro2flag1 == 2) {
			String word = textField_19.getText();
			char[] c = word.toCharArray();
			cpu.CPU.device.keyboardClear();
			for (int i = 0; i < c.length; i++) {
				cpu.CPU.device.inputKeyboard(c[i] - 0);
			}
			cpu.CPU.device.inputKeyboard(32);
			while (cpu.get_number(8) != 10) {
				SS(2);
			}
			cpu.CPU.cache.writeCache(1014, cpu.CPU.cache.readCache(1014) - 1);
			textArea.setText(textArea.getText() + "\nNow the memory has the target word:\n");
			for (int i = 2000; i < cpu.CPU.cache.readCache(1014); i++) {
				textArea.setText(textArea.getText() + (char) cpu.CPU.cache.readCache(i+8));
			}
			textArea.setText(textArea.getText() + "\nAddress2: " + cpu.CPU.cache.readCache(1009) + " Address4: " + cpu.CPU.cache.readCache(1014) + "\n");
			pro2flag1 = 3;
		}
	}
	
	// This function will run the program2 to the end and print the answer
	public void runProgram2ToTheEnd() {
		if (pro2flag1 != 3) {
			return;
		}
		while (cpu.get_number(8) != 121 && cpu.get_number(8) != 126) {
			SS(3);
		}
		pro2flag1 = 4;
		if (cpu.get_number(8) == 121) {
			textArea.setText(textArea.getText() + "\nThe word has been found.\nPlease click the Run single instruction button for three times\nto output the result to the Printer(1--which means word has been found)\n and the sentence number and the word number location\n");
		}
		else if (cpu.get_number(8) == 126) {
			textArea.setText(textArea.getText() + "\nThe word has not been found.\nYou can click the Run single instruction button\nto output the result to the Printer(0--which means word has not been found)\n");
		}
	}
}
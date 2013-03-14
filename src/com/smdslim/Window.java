package com.smdslim;

import javax.swing.*;

import com.smdslim.actions.Actions;
import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

public class Window extends JFrame {

	JPanel mainPanel = new JPanel();
	JTabbedPane databaseTabs = new JTabbedPane();
	JTabbedPane queryTabs = new JTabbedPane();
//	JTextField filterTableTF = new JTextField("filter tables", 10);
//	JButton filterApplyBtn = new JButton("apply");
	JPanel queryPanel;
	String currentDatabase;
	boolean connected = false;
	JButton connectBtn;
	JLabel connectionStateLabel;

	public Window(String title, int width, int height, boolean resizable) {

		mainPanel.setLayout(new GridBagLayout());

		/* текстовое поле фильтрации таблиц */
//		addComponent(mainPanel, filterTableTF, 0, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
		
		/*кнопка применения фильтра*/
//		addComponent(mainPanel, filterApplyBtn, 1, 0, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
		
		/*поле ввода хоста*/
		JTextField serverAddressTF = new JTextField("localhost", 10);
		addComponent(mainPanel, serverAddressTF, 0, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);

		/*поле ввода пользователя*/		
		JTextField userNameTF = new JTextField("root", 10);
		addComponent(mainPanel, userNameTF, 0, 2, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
		
		/*поле ввода пароля*/
		JTextField passwordTF = new JTextField("", 10);
		addComponent(mainPanel, passwordTF, 0, 3, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
		
		/*кнопка соединения*/
		connectBtn = new JButton("connect");
		addComponent(mainPanel, connectBtn, 0, 4, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE);
		
		/*вешаем слушатель событий на кнопку соединения*/
		Actions action  = new Actions();
		connectBtn.addActionListener(action);
		
		/*вкладки запросов*/
		
		
		/*добавляем все во фрейм*/
		add(mainPanel);
		
		/*рисуем окно*/
		setUpWindow(title, width, height, resizable);
	}

	private void addComponent(JPanel panel, JComponent component, int xPos, int yPos, int componentWidth, int componentHeight, int place, int stretch) {
		
		GridBagConstraints gridContraint = new GridBagConstraints();
		
		gridContraint.gridx = xPos;
		gridContraint.gridy = yPos;
		gridContraint.gridwidth = componentWidth;
		gridContraint.gridheight = componentHeight;
		gridContraint.insets = new Insets(5, 5, 5, 5);
		gridContraint.anchor = place;
		gridContraint.fill = stretch;
		
		panel.add(component, gridContraint);
		
	}

	private void drawQueryResult() {
		String[] columnNames = { "First", "Second", "Third" };
		Object[][] data = { { "one", "two", 3 }, { "four", 5, 9 } };
		JTable table = new JTable(data, columnNames);
		table.setBorder(BorderFactory.createLineBorder(Color.black));
		table.setBounds(10, 150, 700, 300);
		queryPanel.add(table);
	}

	private void setUpWindow(String title, int width, int height, boolean resizable) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setResizable(resizable);
		setLocationRelativeTo(null);
		setVisible(true);
		pack();
	}
}

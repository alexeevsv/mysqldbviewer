package com.smdslim;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

public class Window extends JFrame {

    private final JTextField dbNameTF;
    JPanel mainPanel = new JPanel();
    JTabbedPane databaseTabs = new JTabbedPane();
    JTabbedPane queryTabs = new JTabbedPane();
    JTextField filterTableTF = new JTextField("filter tables");
    JPanel queryPanel;
    JButton filterApplyBtn = new JButton("apply");
    String currentDatabase;
    boolean connected = false;
    JButton connectBtn;
    JLabel connectionStateLabel;
    private final JTextField serverAddressTF;
    private final JTextField userNameTF;
    private final JTextField passwordTF;
    private final JTextArea queryTA;
    private DataBaseConnection dataBaseConnection;

    public Window(String title, int width, int height, boolean resizable) {
        setUpWindow(title, width, height, resizable);

        /*главная панель*/
        mainPanel.setLayout(null);
        mainPanel.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                System.out.println(e.getX() + ";" + e.getY());
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
        add(mainPanel);

        /*текстовое поле фильтрации таблиц*/
        filterTableTF.setBounds(10, 10, 100, 18);
        mainPanel.add(filterTableTF);

        /*кнопка применения фильтра*/
        filterApplyBtn.setBounds(120, 10, 80, 18);
        mainPanel.add(filterApplyBtn);

        /*вкладки смены баз данных*/
        JPanel dbViewPanel = new JPanel();
        JPanel dbSetUpPanel = new JPanel();
        dbViewPanel.setLayout(null);
        dbSetUpPanel.setLayout(null);
        dbSetUpPanel.setBounds(10, 10, 200, 400);
        dbSetUpPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        dbViewPanel.add(dbSetUpPanel);
        /*поле ввода имени сервера*/
        serverAddressTF = new JTextField("localhost");
        serverAddressTF.setBounds(10, 10, 100, 18);
        dbSetUpPanel.add(serverAddressTF);
        /*поле ввода пользователя*/
        userNameTF = new JTextField("root");
        userNameTF.setBounds(10, 50, 100, 18);
        dbSetUpPanel.add(userNameTF);
        /*поле ввода пароля*/
        passwordTF = new JTextField("");
        passwordTF.setBounds(10, 90, 100, 18);
        dbSetUpPanel.add(passwordTF);
         /*поле ввода имени БД*/
        dbNameTF = new JTextField("asd");
        dbNameTF.setBounds(10, 130, 100, 18);
        dbSetUpPanel.add(dbNameTF);
        /*кнопка соединения*/
        connectBtn = new JButton("connect");
        connectBtn.setBounds(10, 170, 100, 18);
        connectBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == connectBtn) {
                    System.out.println("test");
                    connected = true;
                    try {
                        dataBaseConnection = new DataBaseConnection(serverAddressTF.getText(), userNameTF.getText(), passwordTF.getText(), dbNameTF.getText());
                    } catch (ClassNotFoundException e1) {
                        connected = false;
                    } catch (SQLException e1) {
                        connected = false;
                    }
                    connectionStateLabel.setText(Boolean.toString(connected));
                }
            }
        });
        dbSetUpPanel.add(connectBtn);

        databaseTabs.addTab("select db", dbViewPanel);
        databaseTabs.setBounds(10, 40, 250, 550);
        mainPanel.add(databaseTabs);

        /*вкладки смены запросов*/
        queryPanel = new JPanel();
        queryPanel.setLayout(null);
        queryPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        /*поле ввода запроса*/
        //TODO: запрос для тестов. убрать
        String q = "select \n" +
                "convert(AES_DECRYPT(surname, '2c5ed410984ecf73dc45f481d5a42b4d') using utf8) as surname,\n" +
                "convert(AES_DECRYPT(firstname, '2c5ed410984ecf73dc45f481d5a42b4d') using utf8) as name\n" +
                "\n" +
                "from asd_users;";
        queryTA = new JTextArea(q);
        queryTA.setWrapStyleWord(true);
        queryTA.setLineWrap(true);
        queryTA.setBounds(10, 10, 700, 100);
        queryPanel.add(queryTA);

        /*кнопка выполнения запроса*/
        JButton queryBtn = new JButton("ok");
        queryBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (connected && dataBaseConnection != null) {
                    try {
                        dataBaseConnection.query(queryTA.getText());
//                        System.out.println(dataBaseConnection.tableHeaders.toString());
//                        System.out.println(dataBaseConnection.queryResult.toString());
                        drawQueryResult();
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(queryPanel, e1.getMessage());
                    }
                }
            }
        });
        queryBtn.setBounds(10, 120, 80, 18);
        queryPanel.add(queryBtn);

        queryTabs.addTab("query1", queryPanel);
        queryTabs.setBounds(275, 10, 725, 580);
        mainPanel.add(queryTabs);

        /*панель статуса*/
        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(null);
        statusPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        statusPanel.setBounds(275, 595, 725, 20);
        mainPanel.add(statusPanel);

        /*текст статуса соединение с БД*/
        JLabel connectionStateTitleLabel = new JLabel("Connection: ");
        connectionStateTitleLabel.setBounds(2, 2, 80, 15);
        statusPanel.add(connectionStateTitleLabel);

        /*статус соединения с БД*/
        connectionStateLabel = new JLabel(Boolean.toString(connected));
        connectionStateLabel.setBounds(90, 2, 50, 15);
        statusPanel.add(connectionStateLabel);
    }

    private void drawQueryResult() {
        String[] columnNames = {"First", "Second", "Third"};
        Object[][] data = {
                {"one", "two", 3},
                {"four", 5, 9}
        };
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
    }
}

package com.smdslim.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Actions implements ActionListener {

	public String action;

	public void actionPerformed(ActionEvent e) {

		action = e.getActionCommand();

		switch (action) {
		case "connect":
			/*соединение к базе данных*/
			System.out.println("connecting to database ...");
			break;
		}

	}

}

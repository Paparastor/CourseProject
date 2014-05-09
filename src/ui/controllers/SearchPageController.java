package ui.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ui.pages.SearchPage;

public class SearchPageController implements ActionListener, DocumentListener {

	private SearchPage page;
	
	private JList<String> list;

	private ArrayList<Integer> actualIndexes;
	private JTable table;

	public void fillAll() {

	}

	public SearchPageController(SearchPage page, JTable table) {
		this.page = page;
		this.table = table;
		fillAll();
	}

	// OK and Cancel options handling
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "OK") {
			table.changeSelection(actualIndexes.get(list.getSelectedIndex()), 0, false, false);
		}
		page.dispose();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		list = page.getList();
		DefaultListModel<String> listModel = (DefaultListModel<String>) list
				.getModel();
		actualIndexes = new ArrayList<Integer>();
		listModel.clear();
		String searchParameterValue = page.getSearchTextField().getText();
		for (int i = 0; i < table.getModel().getRowCount(); i++) {
			String temp = (String) table.getModel().getValueAt(i, 2);
			if (temp.contains(searchParameterValue)) {
				listModel.addElement(temp);
				actualIndexes.add(i);
			}
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub

	}

}

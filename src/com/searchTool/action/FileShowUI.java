package com.searchTool.action;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.searchTool.dao.impl.Databasefunc;

/**
 * SearchView
 * 
 * view plain
 */

public class FileShowUI extends javax.swing.JFrame {

	// finding file name
	private String name;

	private JTextField jtf;

	private File startPath;

	// keep the familiar words and path
	private Hashtable<String, List<String>> Log = new Hashtable<String, List<String>>();

	public FileShowUI() {
		JFileChooser jf = new JFileChooser();
		jf.setFileSelectionMode(JFileChooser.SAVE_DIALOG
				| JFileChooser.DIRECTORIES_ONLY);
		jf.showDialog(null, null);
		startPath = jf.getSelectedFile();
		// put all similar words into the hashtable
		this.search(startPath.getPath());
		// set the name of window
		this.setTitle("My file search tool");
		// set size of window
		this.setSize(600, 500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLayout(null);
		// create a panel
		JPanel p1 = new JPanel();
		p1.setBounds(0, 0, 600, 500);
		p1.setBackground(Color.LIGHT_GRAY);
		// create a text area
		final JTextArea jta = new JTextArea(24, 48);
		jtf = new JTextField(15);
		// create a label
		JLabel keyword = new JLabel("Keyword:");
		// create a button
		JButton bsearch = new JButton("Search");

		// add the label to the panel
		p1.add(keyword);
		// add the text field to the panel
		p1.add(jtf);
		// add the button to the panel
		p1.add(bsearch);
		// add the text area to the panel
		p1.add(jta);

		this.add(p1);
		this.add(p1, BorderLayout.NORTH);
		this.add(jta, BorderLayout.CENTER);

		// create a scroll pane
		JScrollPane pane = new JScrollPane(jta);
		pane.setSize(new Dimension(550, 400));
		p1.add(pane);

		// add listener for search button
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jta.setText("");
				// get search name
				name = jtf.getText();
				// from name to get key
				Stemmer stm= new Stemmer();
				String fkey = stm.getKey(curfname);
				if ("".equals(fkey)) {
					fkey = name;
				}
				// ftable.contains(fkey)
				if (Log.get(fkey) != null) {
					for (String curPath : Log.get(fkey)) {
						jta.append("File:" + curPath + "\n");
					}
				} else {
					jta.append("There is no '" + name
							+ "' file in this path.\n");
				}
			}// end of actionPerformed
		};// end of ActionListener
		bsearch.addActionListener(al);

		this.setVisible(true);
	}// end of class

	/**
	 * search File
	 */
	public void search(String fileName) {
		if (fileName == null || "".equals(fileName)) {
			return;
		}// end if

		// create a file object
		File file = new File(fileName);
		// get all files from this path
		File[] fileList = file.listFiles();

		if (null == fileList || fileList.length == 0) {
			return;
		} else {
			for (int i = 0; i < fileList.length; i++) {
				// if it is file
				if (fileList[i].isFile()) {
					// get file path
					String filePath = fileList[i].getAbsolutePath();
					String curfname = filePath.substring(filePath
							.lastIndexOf("\\") + 1, filePath.lastIndexOf("."));
					// get familiar name of key
					Stemmer stm= new Stemmer();
					String fkey = stm.getKey(curfname);
					if ("".equals(fkey)) {
						fkey = curfname;
					}
					// defines a list that saves the file path
					List<String> arrayList = Log.get(fkey);
					if (arrayList == null) {
						arrayList = new ArrayList<String>();
					}
					arrayList.add(filePath);
					Log.put(fkey, arrayList);
				}
				// if it is a directory
				if (fileList[i].isDirectory()) {
					// get path
					String path = fileList[i].getAbsolutePath();
					this.search(path);
				}// end of if

			}// end of for
		}// end of if
	}// end of search

	public static void main(String[] args) {
		new FileShowUI();
	}

}
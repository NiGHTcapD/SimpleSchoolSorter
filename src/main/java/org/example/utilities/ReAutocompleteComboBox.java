package org.example.utilities;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import java.awt.*;


public class ReAutocompleteComboBox extends JComboBox implements JComboBox.KeySelectionManager
{
	KeySelectionManager keySelectionManager = createDefaultKeySelectionManager();

	public ReAutocompleteComboBox(){

	}

	public ReAutocompleteComboBox(int columns, Object[] items, boolean upper) {
		super(new DefaultComboBoxModel(items));
		setEditable(true);

		// Get the length of the longest string in the array
		int limit = 1;
		for (Object item : items) {
			limit = Math.max(limit, item.toString().length());
		}

		setKeySelectionManager(keySelectionManager);
		JTextField tf = getTextField();
		if (tf != null) {
			tf.setColumns(columns);
			tf.setDocument(new ACDocument(limit, upper, setupCharList(items)));
		}

		Object popup = this.getUI().getAccessibleChild(this, 0);
		Component c = ((Container) popup).getComponent(0);
		if (c instanceof JScrollPane) {
			JScrollPane spane = (JScrollPane) c;
			JScrollBar scrollBar = spane.getVerticalScrollBar();
			scrollBar.setPreferredSize(new Dimension(40, scrollBar
				.getPreferredSize().height));
		}
	}

	public JTextField getTextField() {
		if (getEditor() == null)
			return null;
		return (JTextField) getEditor().getEditorComponent();
	}

	private String setupCharList(Object[] items) {
		// Preset the list with the first string
		StringBuilder list = new StringBuilder(items[0].toString());

		// Loop through the characters in each string and add the characters if
		// they are not yet in the list
		for (int i = 1; i < items.length; i++) {
			String s = items[i].toString();
			for (int j = 0; j < s.length(); j++) {
				char c = s.charAt(j); // Next char in the string

				boolean found = false;
				for (int k = 0; !found && k < list.length(); k++) {
					if (c == list.charAt(k)) // Does it equal a char in the list
						found = true;
				}
				
				// Not in the list so add it
				if (!found)
					list.append(c);
			}
		}

		return list.toString();
	}

	public int selectionForKey(char aKey, ComboBoxModel aModel) {
//System.out.println(new StackTrace("AutocompleteCombobox key="+aKey).getStringStackTrace());
		return keySelectionManager.selectionForKey(aKey, aModel);
//		long now = new java.util.Date().getTime();
//		if (searchFor != null && aKey == KeyEvent.VK_BACK_SPACE
//			&& searchFor.length() > 0) {
//			searchFor = searchFor.substring(0, searchFor.length() - 1);
//		} else {
//			if (lap + 1000 < now)
//				searchFor = "" + aKey;
//			else
//				searchFor = searchFor + aKey;
//		}
//		lap = now;
//		return searchForString(searchFor, aModel);
	}

	/**
     * Search for the string in model list. Get the index of the 1st string that
     * starts with the string to search for.
     * 
     * @param s
     *            String to search for
     * @return -1 if not found; otherwise the index of the first matching
     */
	private int searchForString(String s) {
		return searchForString(s, this.getModel(), false);
	}

	/**
     * Search for the string in model list. Get the index of the 1st string that
     * starts with the string to search for or optionally an exact match.
     * 
     * @param s
     *            String to search for
     * @param aModel
     *            Model to search in
     * @param exact true to find an exact match; false to find
     * @return -1 if not found; otherwise the index of the first matching
     */
	private int searchForString(String s, ComboBoxModel aModel, boolean exact){
		s = s.toLowerCase();

		String current;
		for (int i = 0; i < aModel.getSize(); i++) {
			current = aModel.getElementAt(i).toString().toLowerCase();
			if (exact){
				if (current.toLowerCase().equals(s))
					return i;
			} else {
				if (current.toLowerCase().startsWith(s))
					return i;
			}
		}

		return -1;

	}

	public void reset() {
		getTextField().setText("");
		setSelectedIndex(0);
		showPopup();
	}

	/**
     * Test if the text field data exactly matches any of the strings in the
     * data model
     * 
     * @return true if there is an exact match; false otherwise
     */
	public boolean verifyText() {
		return (searchForString(getTextField().getText(), this.getModel(), true) != -1);
	}

	/**
	 * Autocomplete Text field document
	 * @author mlempert
	 *
	 */
	public class ACDocument extends TextFieldLimit {
		boolean processing = false;

		public ACDocument(int limit, boolean upper, String charList) {
			super(limit, upper, charList);
		}

		public void insertString(int offset, String str, AttributeSet a)
			throws BadLocationException {
			if (str == null)
				return;

			// See if the new string data inserted with the current string data
            // matches any of the possible selections.
			char[] strdata = toUppercase 
				? str.toUpperCase().toCharArray()
				: toLowercase 
					? str.toLowerCase().toCharArray() 
					: str.toCharArray();
			char[] data = getText(0, getLength()).toCharArray();
			char[] newdata = new char[data.length + str.length()];
			System.arraycopy(data, 0, newdata, 0, data.length);
			System.arraycopy(strdata, 0, newdata, offset, str.length());
			String newtext = new String(newdata);
			int firstMatch = searchForString(newtext);
			// If no match for the string then exit without inserting
			if (firstMatch < 0)
				return;

			// Insert the new string data
			super.insertString(offset, str, a);

			// If there is an exact match, select it
			// Else select the first to match the start of the newtext string
			//      hilight all text after newtext in the selected string
			//      Example: newtext=M selected string=MRS, RS is hilighted
			if (!processing && str.length() != 0) {
				processing = true;

				ComboBoxModel aModel = getModel();
				
				// Look for an exact match 
				int idx = searchForString(newtext, aModel, true);
				if (idx >= 0) {
					setSelectedIndex(idx);
				} else {
					String current=aModel.getElementAt(firstMatch).toString();
					JTextField tf = getTextField();
					tf.setText(current);
					tf.setSelectionStart(newtext.length());
					tf.setSelectionEnd(current.length());
					
					if (!isPopupVisible())
						setPopupVisible(true);
				}

				processing = false;
			}
		}
	}

}
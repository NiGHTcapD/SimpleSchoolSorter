/*
 * Created on June 25, 2003
 */
package org.example.utilities;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


/**
 * DOCUMENT ME!
 *
 * @author mlempert 
 */
public class TextFieldLimit extends PlainDocument {
    /** Lowercase alphabetic characters */
    public static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    /** Uppercase alphabetic characters */
    public static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /** Numeric characters */
    public static final String NUMERIC = "0123456789";
    /** Alpha characters */
    public static final String ALPHA = LOWERCASE + UPPERCASE;
    /** Alpha/Numeric characters */
    public static final String ALPHA_NUMERIC = ALPHA + NUMERIC;
    /** Standard data entry characters */
	public static final String STANDARD = UPPERCASE + NUMERIC + " .-";
    /** Characters for Yes/No data entry */
    public static final String YN = "YN";
    /** Characters allowed for E-mail addresses */
    public static final String EMAIL = ALPHA_NUMERIC + "_.@-";
	/** Phone number data entry characters */
	public static final String PHONE = NUMERIC + " ()-";
	/** Date data entry characters */
	public static final String DATE = NUMERIC + "/";
	
	private static final String[][] allPredefined = { 
		{"LOWERCASE", LOWERCASE },
		{"UPPERCASE", UPPERCASE },
		{"NUMERIC", NUMERIC },
		{"ALPHA", ALPHA },
		{"ALPHA_NUMERIC", ALPHA_NUMERIC },
		{"STANDARD", STANDARD },
		{"YN", YN },
		{"EMAIL", EMAIL },
		{"PHONE", PHONE },
		{"DATE", DATE},
		}; 

	protected int limit;
    protected boolean toUppercase;
    protected boolean toLowercase = false;
    protected String charList;
    
    public static String getPredefinedCharList(String key) {
		String list = null;
		for (int i = 0; i < allPredefined.length && list == null; i++) {
			if (allPredefined[i][0].equals(key))
				list = allPredefined[i][1];
		}
		return list;
	}

    /**
     * DOCUMENT ME!
     *
     * @param limit Set the number of characters allowed in the field
     * @param upper true to force uppercase conversion; false for no conversion 
     * @param charList Set the allowed list of characters in the field
     */
    public TextFieldLimit(int limit, boolean upper, String charList) {
        this.limit = limit;
        this.toUppercase = upper;
        this.charList = charList;

        if (charList != null) {
            if (charList.equals(UPPERCASE)) {
                toUppercase = true;
            }

            if (charList.equals(LOWERCASE)) {
                toLowercase = true;
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param limit Set the number of characters allowed in the field
     * @param upper true to force uppercase conversion; false for no conversion
     */
    public TextFieldLimit(int limit, boolean upper) {
        this(limit, upper, null);
    }

    /**
     * Default uppercase conversion to false
     *
     * @param limit Set the number of characters allowed in the field
     * @param charList Set the allowed list of characters in the field
     */
    public TextFieldLimit(int limit, String charList) {
        // If charList is set to UPPERCASE, force uppercase.
        this(limit, (charList.equals(UPPERCASE)), charList);
    }

    /**
     * Default uppercase conversion to false
     *
     * @param limit Set the number of characters allowed in the field
     */
    public TextFieldLimit(int limit) {
        this(limit, false);
    }

    /**
     * Test if a character is valid for this Document
     * @param c
     * @return true character is valid; false otherwise
     */
    public boolean isValidChar(char c){
    	if (toUppercase)
			c = Character.toUpperCase(c);
		if (toLowercase)
			c = Character.toLowerCase(c);

        return charList == null || charList.indexOf(c) >= 0;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param offset
     *            DOCUMENT ME!
     * @param str
     *            DOCUMENT ME!
     * @param attr
     *            DOCUMENT ME!
     * @throws BadLocationException
     *             DOCUMENT ME!
     */
    public void insertString(int offset, String str, AttributeSet attr)
        throws BadLocationException {
        if (str == null) {
            return;
        }

        // If the input string can't fit and there is room remaining then
        // truncate it. If no more room then just return
        if ((getLength() + str.length()) > limit) {
			if (limit - getLength() == 0)
				return;
			
			str = str.substring(0, limit - getLength());
		}

        if (toUppercase) {
            str = str.toUpperCase();
        } else if (toLowercase) {
            str = str.toLowerCase();
        }

        boolean update = true;

        if (charList != null) {
            // If any character of the string is not allowed, then don't allow
            for (int i = 0; i < str.length(); i++) {
                if (!charList.contains(str.substring(i, i + 1))) {
                    update = false;
                    break;
                }
            }
        }

        if (update) {
            super.insertString(offset, str, attr);
        }
    }
}

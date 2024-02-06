package org.example.utilities;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public  class FilterComboBox<E> extends JComboBox<E> {
    private List<E> items;

    public FilterComboBox() {
        super();
        initKeyFilter();
        this.setEditable(true);
    }

    public FilterComboBox(List<E> items) {
        this();
        setItems(items);
    }

    private void initKeyFilter() {
        final JTextField textfield = (JTextField) this.getEditor().getEditorComponent();
        textfield.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(() -> comboFilter(textfield.getText()));
            }
        });
    }

    public void setItems(List<E> items) {
        this.items = items;

        DefaultComboBoxModel<E> model = (DefaultComboBoxModel<E>) this.getModel();
        model.removeAllElements();
        model.addAll(items);
    }

    public void comboFilter(String enteredText) {
        String enteredTextLower = enteredText.toLowerCase();

        if (!this.isPopupVisible()) {
            this.showPopup();
        }

        List<E> filterArray= items.stream()
                .filter(item -> item.toString().toLowerCase().contains(enteredTextLower))
                .toList();

        if (filterArray.size() > 0) {
            setItems(filterArray);

            JTextField textfield = (JTextField) this.getEditor().getEditorComponent();
            textfield.setText(enteredText);
        }
        else{
            setItems(items);
        }
    }

}
/**
 Copyright 2019 Indigo A.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package com.indicode.util.xml_substitutor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class InputWindow {
    public static JFrame frame;
    public InputWindow() {

        frame = new JFrame();
        frame.setTitle("XML Substitutor by IndiCode");
        frame.add(panel);
        frame.pack();
        frame.setSize(frame.getWidth() * 2, frame.getHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private JTextField dirName;
    private JTextField xmlsubField;
    private JButton buildButton;
    private JProgressBar progressBar;
    private JButton findDirectoryButton;
    private JButton helpButton;
    private JPanel panel;


    private void createUIComponents() {
        helpButton = new JButton();
        helpButton.addActionListener((e) -> {
            JFrame frame = new JFrame("Help - XML Substitutor");
            JTextArea ta = new JTextArea("Place the directory you wish to use in the directory field. Inside this directory there should be a 'content' folder, along with any others you define. Other folders can be used for resources that aren't already part of your content(EX: website header). Outpus are built at 'directory/result'.\n--------------------\nThe XML tag is the tag used to signify a substitution. Usage is '<tag [(to keep the root tag) stays_after_sub=\"true\"]src=\"resources/my_cool_xml_insert.xml\"/>'");
            ta.setEditable(false);
            ta.setLineWrap(true);
            ta.setWrapStyleWord(true);
            frame.add(new JScrollPane(ta));
            frame.pack();
            frame.setSize(frame.getHeight() * 6, frame.getHeight() * 2);
            frame.setVisible(true);
        });
        xmlsubField = new JTextField();
        xmlsubField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {changedUpdate(e);}

            @Override
            public void removeUpdate(DocumentEvent e) {changedUpdate(e);}

            @Override
            public void changedUpdate(DocumentEvent e) {
                xmlsubField.setToolTipText("Usage Example: <" + xmlsubField.getText() + " src=\"resources/my_cool_xml_insert.xml\"/>");
            }
        });
        findDirectoryButton = new JButton();
        findDirectoryButton.addActionListener((e) -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Choose parent directory");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                dirName.setText(chooser.getSelectedFile().getPath());
            } else {

            }
        });
        buildButton = new JButton();
        buildButton.addActionListener((e) -> {
            MainKt.replace(dirName.getText(), xmlsubField.getText(), progressBar);
        });
    }

}

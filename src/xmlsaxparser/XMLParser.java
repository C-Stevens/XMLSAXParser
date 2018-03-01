/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlsaxparser;

import java.io.File;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Colin
 */

public class XMLParser {
    
    protected static class XMLHandler extends DefaultHandler {
        private TreeView<String> tree;
        private TreeItem<String> rootNode, currentNode;
        public XMLHandler() {
            this.tree = new TreeView<String>();
        }
        
        public TreeView getTreeView() {
            return this.tree;
        }
        
        @Override
        public void startElement(String namespaceURI, String lName, String qName, Attributes attribs) throws SAXException {
            System.out.println("Starting element with qname: "+qName); //DEBUG
            
            String tagName;
            if(lName == "") { // If localName is empty, use the qualifiedName
                tagName = qName;
            } else {
                tagName = lName;
            }
            TreeItem<String> newNode = new TreeItem<String>(tagName);
            if(this.tree.getRoot() == null) { // If the tree doesn't exist, we need to set the root element first
                tree.setRoot(newNode);
            }
            
            if(currentNode == null) {
                rootNode = newNode;
            } else { // Add a leaf
                currentNode.getChildren().add(newNode);
            }
            currentNode = newNode; // Descend the tree
        }
        
        @Override
        public void endElement(String namespaceURI, String sName, String qName) throws SAXException {
            System.out.println("Ending element with qName: "+qName); //DEBUG
            // Since our element is ending, move back up the tree one element for next iteration
            currentNode = currentNode.getParent();
        }
        
        @Override
        public void characters(char buffer[], int offset, int length) throws SAXException {
            String elementString = new String(buffer, offset, length).trim();
            if(elementString.equals("")) { // Avoid adding children with empty values
                return;
            }
            TreeItem<String> valueNode = new TreeItem<String>(elementString);
            currentNode.getChildren().add(valueNode);
            System.out.println("Current string value: "+elementString); //DEBUG
        }
    }
    
    static TreeView createTreeViewFromXML(File file) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        XMLHandler handler = new XMLHandler();
        try {
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(file, handler);
        } catch(Exception e) {
            System.out.println("An exception occured!"); //DEBUG
            //TODO handle exception
        }
        
        return handler.getTreeView();
    }
}

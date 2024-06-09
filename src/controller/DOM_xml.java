package controller;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.Account;

public class DOM_xml{
	public static void saveToXML(Account user) throws ParserConfigurationException, TransformerException, IOException, SAXException {
        File xmlFile = new File("users.xml");

        DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
        Document document;
        if (xmlFile.exists()) {
            document = dBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();
        } else {
            document = dBuilder.newDocument();
            Element root = document.createElement("users");
            document.appendChild(root);
        }

        Element root = document.getDocumentElement();
        Element userElement = document.createElement("user");

        Element userName = document.createElement("name");
        userName.appendChild(document.createTextNode(user.getName()));
        userElement.appendChild(userName);

        Element userEmail = document.createElement("email");
        userEmail.appendChild(document.createTextNode(user.getEmail()));
        userElement.appendChild(userEmail);

        Element userPass = document.createElement("password");
        userPass.appendChild(document.createTextNode(user.getPass()));
        userElement.appendChild(userPass);

        Element userAddress = document.createElement("address");
        userAddress.appendChild(document.createTextNode(user.getAddress()));
        userElement.appendChild(userAddress);

        root.appendChild(userElement);

        TransformerFactory tfFactory = TransformerFactory.newInstance();
        Transformer transformer = tfFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource Dsource = new DOMSource(document);

        StreamResult streamResult = new StreamResult(xmlFile);
        transformer.transform(Dsource, streamResult);
    }
	
	public static boolean validateLogin(String name, String pass) {
		try {
            File file = new File("users.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("user");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String storedUsername = element.getElementsByTagName("name").item(0).getTextContent();
                String storedPassword = element.getElementsByTagName("password").item(0).getTextContent();

                if (name.equals(storedUsername) && pass.equals(storedPassword)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
	}
	
	public static boolean updatePass(String email, String pass) {
		File xmlFile = new File("users.xml");

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList users = doc.getElementsByTagName("user");

            for (int i = 0; i < users.getLength(); i++) {
                Element user = (Element) users.item(i);
                String xmlEmail = user.getElementsByTagName("email").item(0).getTextContent();

                if (xmlEmail.equals(email)) {
                    user.getElementsByTagName("password").item(0).setTextContent(pass);
                    
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    DOMSource source = new DOMSource(doc);
                    StreamResult result = new StreamResult(xmlFile);
                    transformer.transform(source, result);

                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
		return false;
	}
	
	public static boolean checkEmail(String email) {
		try {
            File file = new File("users.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("user");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String storedEmail = element.getElementsByTagName("email").item(0).getTextContent();

                if (storedEmail.equals(email)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
	}
	
	public static boolean checkAccount(String name, String email) {
		try {
            File file = new File("users.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("user");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String storedUsername = element.getElementsByTagName("name").item(0).getTextContent();
                String storedEmail = element.getElementsByTagName("email").item(0).getTextContent();

                if (name.equals(storedUsername) && email.equals(storedEmail)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
	}
}

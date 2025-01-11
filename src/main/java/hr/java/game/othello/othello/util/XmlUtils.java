package hr.java.game.othello.othello.util;

import hr.java.game.othello.othello.model.BoardState;
import javafx.scene.control.Button;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlUtils {

    public static final String GAME_MOVES_XML_FILE_PATH = "xml/game_moves.xml";

    public static void saveGameMovesToXml(List<Button[][]> gameMoves) {

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();
            Element rootElement = document.createElement("GameMoves");
            document.appendChild(rootElement);

            for (Button[][] gameMove : gameMoves) {
                Element gameMoveElement = document.createElement("GameMove");

                for (int row = 0; row < BoardState.NUMBER_OF_ROWS; row++) {
                    for (int col = 0; col < BoardState.NUMBER_OF_COLUMNS; col++) {
                        Element moveElement = document.createElement("Move");
                        moveElement.setTextContent(gameMove[row][col].getStyle());
                        moveElement.setAttribute("row", String.valueOf(row));
                        moveElement.setAttribute("col", String.valueOf(col));
                        gameMoveElement.appendChild(moveElement);
                    }
                }
                rootElement.appendChild(gameMoveElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source= new DOMSource(document);
            StreamResult result = new StreamResult(new FileWriter(GAME_MOVES_XML_FILE_PATH));
            transformer.transform(source, result);
        } catch (ParserConfigurationException | IOException | TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Button[][]> readGameMovesFromXml() {

        List<Button[][]> gameMoves = new ArrayList<>();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(new File(GAME_MOVES_XML_FILE_PATH));
            document.getDocumentElement().normalize();

            Element rootElement = document.getDocumentElement();
            NodeList gameMoveNodes = rootElement.getElementsByTagName("GameMove");
            for (int i = 0; i < gameMoveNodes.getLength(); i++) {
                Node gameMoveNode = gameMoveNodes.item(i);
                if (gameMoveNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element gameMoveElement = (Element) gameMoveNode;
                    Button[][] gameMove = new Button[BoardState.NUMBER_OF_ROWS][BoardState.NUMBER_OF_COLUMNS];
                    NodeList moveNodes = gameMoveElement.getElementsByTagName("Move");

                    for (int j = 0; j < moveNodes.getLength(); j++) {
                        Node moveNode = moveNodes.item(j);
                        if (moveNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element moveElement = (Element) moveNode;

                            int row = Integer.parseInt(moveElement.getAttribute("row"));
                            int col = Integer.parseInt(moveElement.getAttribute("col"));
                            String style = moveElement.getTextContent();

                            Button button = new Button();
                            button.setStyle(style);
                            gameMove[row][col] = button;
                        }
                    }
                    gameMoves.add(gameMove);
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }

        return gameMoves;
    }
}

package fsams;

import fsams.grid.Grid;
import fsams.grid.Tile;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Handles file input and output for saving and loading building layouts.
 * @author FSAMS Team
 */
public class ProjectIO {

    /**
     * Saves a file representing the building layout.
     * @param grid the grid that displays the building layout.
     * @param fileName the name of the file to save.
     * @throws ParserConfigurationException indicates error parsing the file.
     * @throws TransformerConfigurationException indicates a serious configuration error
     * @throws TransformerException specifies an exceptional condition that occurred during the transformation process.
     */
    public static void saveProject(Grid grid, String fileName) 
        throws ParserConfigurationException, TransformerConfigurationException, TransformerException
    {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        File file = new File(fileName);
        if(file.exists()) {
            file.delete();
        }
        
        // Grid
        Element rootNode = doc.createElement("grid");
        doc.appendChild(rootNode);
        
        for(int grid_y=0; grid_y<Grid.grid_height; grid_y++) {
            Element rowNode = doc.createElement("row");
            rootNode.appendChild(rowNode);
            for(int grid_x=0; grid_x<Grid.grid_width; grid_x++) {
                Element tileNode = doc.createElement("tile");
                rowNode.appendChild(tileNode);
                Tile t = grid.getTiles()[grid_x][grid_y];
                if(t.getWallD()) tileNode.setAttribute("wallD", "true");
                if(t.getWallL()) tileNode.setAttribute("wallL", "true");
                if(t.getWallU()) tileNode.setAttribute("wallU", "true");
                if(t.getWallR()) tileNode.setAttribute("wallR", "true");
                if(t.getDoorD()) tileNode.setAttribute("doorD", "true");
                if(t.getDoorL()) tileNode.setAttribute("doorL", "true");
                if(t.getDoorU()) tileNode.setAttribute("doorU", "true");
                if(t.getDoorR()) tileNode.setAttribute("doorR", "true");
                if(t.getFireSensor()) tileNode.setAttribute("sensor", "true");
                if(t.getFireAlarm()) tileNode.setAttribute("alarm", "true");
                if(t.getSprinkler()) tileNode.setAttribute("sprinkler", "true");
                if(t.getHumanAgent()) tileNode.setAttribute("person", "true");
                if(t.getIntruder()) tileNode.setAttribute("intruder", "true");
                if(t.getEquipment()) tileNode.setAttribute("equipment", "true");
                if(t.getFire()) tileNode.setAttribute("fire", "true");
                if(t.getExit()) tileNode.setAttribute("exit", "true");
                if(t.getCamera()) tileNode.setAttribute("camera", "true");
            }
        }
        
        transformer.transform(new DOMSource(doc), new StreamResult(file));
    }
    
    /**
     * Opens a file representing a building layout and places the layout onto the grid.
     * @param grid the grid used to display the building layout.
     * @param fileName the name of the file to open.
     * @throws ParserConfigurationException indicates error parsing the file.
     * @throws SAXException indicates an error in XML parsing.
     * @throws IOException indicates an error in opening the file.
     */
    public static void openProject(Grid grid, String fileName) throws ParserConfigurationException, SAXException, IOException {
        File file = new File(fileName);
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);

        NodeList node_list = doc.getElementsByTagName("grid");
        if(node_list.getLength()<1) throw new IllegalArgumentException("Missing <grid> in project file.");
        
        Element gridNode = (Element)node_list.item(0);
        NodeList row_list = gridNode.getElementsByTagName("row"); 
        if(row_list.getLength()!=Grid.grid_height) 
            throw new IllegalArgumentException("Expceted "+Grid.grid_height+" <row>s, but found "+row_list.getLength()+" in project file.");
        for(int y=0; y<row_list.getLength(); y++) {
            Element rowNode = (Element)row_list.item(y);
            NodeList tile_list = rowNode.getElementsByTagName("tile");
            if(tile_list.getLength()!=Grid.grid_height) 
                throw new IllegalArgumentException("Expceted "+Grid.grid_width+" <tiles>s in each <row>, but found "+tile_list.getLength()+" in project file.");
            for(int x=0; x<tile_list.getLength(); x++) {
                Element tileNode = (Element)tile_list.item(x);
                Tile t = grid.getTiles()[x][y];
                t.setWallD(tileNode.getAttribute("wallD").equalsIgnoreCase("true"));
                t.setWallD(tileNode.getAttribute("wallD").equalsIgnoreCase("true"));
                t.setWallL(tileNode.getAttribute("wallL").equalsIgnoreCase("true"));
                t.setWallU(tileNode.getAttribute("wallU").equalsIgnoreCase("true"));
                t.setWallR(tileNode.getAttribute("wallR").equalsIgnoreCase("true"));
                t.setDoorD(tileNode.getAttribute("doorD").equalsIgnoreCase("true"));
                t.setDoorL(tileNode.getAttribute("doorL").equalsIgnoreCase("true"));
                t.setDoorU(tileNode.getAttribute("doorU").equalsIgnoreCase("true"));
                t.setDoorR(tileNode.getAttribute("doorR").equalsIgnoreCase("true"));
                t.setFireSensor(tileNode.getAttribute("sensor").equalsIgnoreCase("true"));
                t.setFireAlarm(tileNode.getAttribute("alarm").equalsIgnoreCase("true"));
                t.setSprinkler(tileNode.getAttribute("sprinkler").equalsIgnoreCase("true"));
                t.setHumanAgent(tileNode.getAttribute("person").equalsIgnoreCase("true"));
                t.setIntruder(tileNode.getAttribute("intruder").equalsIgnoreCase("true"));
                t.setEquipment(tileNode.getAttribute("equipment").equalsIgnoreCase("true"));
                t.setFire(tileNode.getAttribute("fire").equalsIgnoreCase("true"));
                t.setExit(tileNode.getAttribute("exit").equalsIgnoreCase("true"));
                t.setCamera(tileNode.getAttribute("camera").equalsIgnoreCase("true"));
            }
        }
    }
}

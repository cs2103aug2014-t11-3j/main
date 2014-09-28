package todologApp;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class FileStorage implements Storage{
	private final String DEFAULT_FILE_NAME = "store.xml";
	private static Document _document;
	private static File _file;
	@Override
	public LinkedList<Task> load(){
		SAXReader reader = new SAXReader();
		try {
			_document = reader.read(DEFAULT_FILE_NAME);
		} catch (DocumentException e) {
		}	
		return parseDoc(_document);
		
	}

	private LinkedList<Task> parseDoc(Document document) {
		Element root = document.getRootElement();
		LinkedList<Task> tasks = new LinkedList<Task>();
		for (int i = 0; i< root.nodeCount(); i++) {
			Node taskNode = root.node(i);
			Task task = parseNodeToTask(taskNode);
			tasks.add(task);
		}
		return tasks;
	}

	private Task parseNodeToTask(Node taskNode) {
		return null;
		
	}

	public Document createDocument() {
		Document document = DocumentHelper.createDocument();
        Element root = document.addElement( "root" );
        return document;
	}
	public void writeDocument(Document document) throws IOException {
		XMLWriter writer = new XMLWriter(new FileWriter(_file));
		writer.write(document);
		writer.close();
	}
	@Override
	public void init() {
		_file = new File(DEFAULT_FILE_NAME);
		_document = createDocument();
		try {
			writeDocument(_document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void store(LinkedList<Task> tasks) {
		
	}
	
}

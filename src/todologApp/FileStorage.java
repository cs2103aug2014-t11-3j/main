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
	private Document _document;
	private File _file;
	
	public FileStorage() {
		_file = new File(DEFAULT_FILE_NAME);
	}
	public FileStorage(String fileName) {
		_file = new File(fileName);
	}
	
	@Override
	public LinkedList<Task> load(){
		SAXReader reader = new SAXReader();
		try {
			_document = reader.read(_file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}	
		try {
			return parseDoc(_document);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	private LinkedList<Task> parseDoc(Document document) throws Exception {
		Element root = document.getRootElement();
		LinkedList<Task> tasks = new LinkedList<Task>();
		for (int i = 0; i< root.nodeCount(); i++) {
			Node taskNode = root.node(i);
			if (taskNode instanceof Element) {
				Task task = parseElementToTask((Element) taskNode);
				tasks.add(task);
			} else {
				throw new Exception();
			}
			
		}
		return tasks;
	}
	private Task parseElementToTask(Element taskNode) {
		String taskTypeString = taskNode.attributeValue("type");
		TaskType taskType = parseTaskType(taskTypeString);
		Task task;
		switch (taskType) {
			case TIMED:
				task = parseIntoTimed(taskNode);
			case DEADLINE:
				task = parseIntoDeadline(taskNode);
			default:
				task = parseIntoFloating(taskNode);
		}
		
		return task;
	}
	private Task parseIntoFloating(Element taskNode) {
		String name = taskNode.attributeValue("name");
		boolean status = Boolean.parseBoolean(taskNode.attributeValue("status"));
		return new Task(TaskType.FLOATING, name, status);
	}
	private Task parseIntoDeadline(Element taskNode) {
		// TODO Auto-generated method stub
		return null;
	}
	private Task parseIntoTimed(Element taskNode) {
		// TODO Auto-generated method stub
		return null;
	}
	private static TaskType parseTaskType(String taskTypeString) {
		switch (taskTypeString) {
			case "DEADLINE" :
				return TaskType.DEADLINE;
			case "TIMED" :
				return TaskType.TIMED;
			default: return TaskType.FLOATING;
		}
	}
	public Document createBlankDocument() {
		Document document = DocumentHelper.createDocument();
        return document;
	}
	public void writeDocument(Document document) throws IOException {
		XMLWriter writer = new XMLWriter(new FileWriter("store.xml"));
		writer.write(document);
		writer.close();
	}
	@Override
	public void init() {
		_document = createBlankDocument();
		try {
			writeDocument(_document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void store(LinkedList<Task> tasks) throws IOException{
		Document newDocument = DocumentHelper.createDocument();
		Element root = newDocument.addElement("root");
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			Element taskElement = root.addElement("task")
					.addAttribute("type",task.getTaskType().toString())
					.addAttribute("name",task.getTaskName())
					.addAttribute("status", String.valueOf(task.getTaskStatus()))
//					.addAttribute("startdate",task.getStartDate())
//					.addAttribute("starttime",task.getStartTime())
//					.addAttribute("enddate",task.getEndDate())
//					.addAttribute("endtime",task.getEndTime())
					;
		}
		writeDocument(newDocument);
		
	}
	
}

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
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

public class FileStorage implements Storage{
	private final String DEFAULT_FILE_NAME = "store.xml";
	private Document _document;
	private File _file;
	
	public FileStorage() {
		_file = new File(DEFAULT_FILE_NAME);
		_document = createBlankDocument();
		init();
	}
	public FileStorage(String fileName) {
		_file = new File(fileName);
		_document = createBlankDocument();
		init();
	}
	
	@Override
	public void init() {
		if (!_file.exists()) {
			try {
				_file.createNewFile();
				writeDocument(_document);
			} catch (IOException e) {
				//return feedback of IO error
			}
		}
		
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
			//unsupported file or file corrupted
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
		String taskTypeString = taskNode.element("type").getText();
		TaskType taskType = parseTaskType(taskTypeString);
		Task task;
		switch (taskType) {
			case TIMED:
				task = parseIntoTimed(taskNode);
				break;
			case DEADLINE:
				task = parseIntoDeadline(taskNode);
				break;
			default:
				task = parseIntoFloating(taskNode);
				break;
		}
		
		return task;
	}
	private Task parseIntoFloating(Element taskNode) {
		String name = taskNode.element("name").getText();
		boolean status = Boolean.parseBoolean(taskNode.element("status").getText());
		return new Task(TaskType.FLOATING, name, status);
	}
	
	private Task parseIntoDeadline(Element taskNode) {
		String name = taskNode.element("name").getText();
		String endStr = taskNode.element("end").getText();
		DateTime end = ISODateTimeFormat.dateTime().parseDateTime(endStr);
		boolean status = Boolean.parseBoolean(taskNode.element("status").getText());
		return new Task(TaskType.DEADLINE, name, end, status);
	}
	
	private Task parseIntoTimed(Element taskNode) {
		String name = taskNode.element("name").getText();
		String startStr = taskNode.element("start").getText();
		String endStr = taskNode.element("end").getText();
		DateTime start = ISODateTimeFormat.dateTime().parseDateTime(startStr);
		DateTime end = ISODateTimeFormat.dateTime().parseDateTime(endStr);
		boolean status = Boolean.parseBoolean(taskNode.element("status").getText());
		return new Task(TaskType.TIMED, name, start, end, status);
	}
	private static TaskType parseTaskType(String taskTypeString) {
		switch (taskTypeString) {
			case "DEADLINE" :
				return TaskType.DEADLINE;
			case "TIMED" :
				return TaskType.TIMED;
			case "FLOATING" :
				return TaskType.FLOATING;
			default:
				return TaskType.FLOATING;
		}
	}
	private Document createBlankDocument() {
		Document document = DocumentHelper.createDocument();
		document.addElement("root");
        return document;
	}
	private void writeDocument(Document document) throws IOException {
		XMLWriter writer = new XMLWriter(new FileWriter(_file));
		writer.write(document);
		writer.close();
	}
	

	@Override
	public void store(LinkedList<Task> tasks) throws IOException{
		Document newDocument = DocumentHelper.createDocument();
		Element root = newDocument.addElement("root");
		for (int i = 0; i < tasks.size(); i++) {
			Task task = tasks.get(i);
			switch (task.getTaskType()) {
				case FLOATING:
					addFloatingTaskToRoot(root, task);
					break;
				case TIMED:
					addTimedTaskToRoot(root, task);
					break;
				case DEADLINE:
					addDeadlineTaskToRoot(root, task);
					break;
				default:
					break;
			}
		}
		writeDocument(newDocument);
		
	}
	private void addFloatingTaskToRoot(Element root, Task task) {
		Element newTask = root.addElement("task");
		newTask.addElement("type").setText(task.getTaskType().toString());
		newTask.addElement("name").setText(task.getTaskName());
		newTask.addElement("status").setText(String.valueOf(task.getTaskStatus()));
	}
	
	private void addDeadlineTaskToRoot(Element root, Task task) {
		Element newTask = root.addElement("task");
		newTask.addElement("type").setText(task.getTaskType().toString());
		newTask.addElement("name").setText(task.getTaskName());
		newTask.addElement("end").setText(task.getEnd());
		newTask.addElement("status").setText(String.valueOf(task.getTaskStatus()));
	}
	
	private void addTimedTaskToRoot(Element root, Task task) {
		Element newTask = root.addElement("task");
		newTask.addElement("type").setText(task.getTaskType().toString());
		newTask.addElement("name").setText(task.getTaskName());
		newTask.addElement("start").setText(task.getStart());
		newTask.addElement("end").setText(task.getEnd());
		newTask.addElement("status").setText(String.valueOf(task.getTaskStatus()));
		
	}
	public Document getDocument() {
		return _document;
	}
	public File getFile() {
		return _file;
	}
	
}

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

public class FileStorage implements Storage {
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
				// return feedback of IO error
			}
		}

	}

	@Override
	public LinkedList<Task> load() {
		SAXReader reader = new SAXReader();
		try {
			_document = reader.read(_file);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		try {
			return parseDoc(_document);
		} catch (Exception e) {
			// unsupported file or file corrupted
		}
		return null;

	}

	private LinkedList<Task> parseDoc(Document document) throws Exception {
		Element root = document.getRootElement();
		LinkedList<Task> tasks = new LinkedList<Task>();
		for (int i = 0; i < root.nodeCount(); i++) {
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
		String name = taskNode.attributeValue("name");
		boolean status = Boolean
				.parseBoolean(taskNode.attributeValue("status"));
		return new Task(TaskType.FLOATING, name, status);
	}

	private Task parseIntoDeadline(Element taskNode) {
		String name = taskNode.attributeValue("name");
		String endDay = taskNode.attributeValue("endday");
		int endTime = Integer.parseInt(taskNode.attributeValue("endtime"));
		boolean status = Boolean
				.parseBoolean(taskNode.attributeValue("status"));
		return new Task(TaskType.DEADLINE, name, endDay, endTime, status);
	}

	private Task parseIntoTimed(Element taskNode) {
		String name = taskNode.attributeValue("name");
		String startDay = taskNode.attributeValue("startday");
		String endDay = taskNode.attributeValue("endday");
		int startTime = Integer.parseInt(taskNode.attributeValue("starttime"));
		int endTime = Integer.parseInt(taskNode.attributeValue("endtime"));
		boolean status = Boolean
				.parseBoolean(taskNode.attributeValue("status"));
		return new Task(TaskType.TIMED, name, startDay, endDay, startTime,
				endTime, status);
	}

	private static TaskType parseTaskType(String taskTypeString) {

		return TaskType.FLOATING;

	}

	public Document createBlankDocument() {
		Document document = DocumentHelper.createDocument();
		document.addElement("root");
		return document;
	}

	public void writeDocument(Document document) throws IOException {
		XMLWriter writer = new XMLWriter(new FileWriter("store.xml"));
		writer.write(document);
		writer.close();
	}

	@Override
	public void store(LinkedList<Task> tasks) throws IOException {
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
		root.addElement("task")
				.addAttribute("type", task.getTaskType().toString())
				.addAttribute("name", task.getTaskName())
				.addAttribute("status", String.valueOf(task.getTaskStatus()));
	}

	private void addDeadlineTaskToRoot(Element root, Task task) {
		root.addElement("task")
				.addAttribute("type", task.getTaskType().toString())
				.addAttribute("name", task.getTaskName())
				.addAttribute("endday", task.getEndDay())
				.addAttribute("endtime", String.valueOf(task.getEndTime()))
				.addAttribute("status", String.valueOf(task.getTaskStatus()));
	}

	private void addTimedTaskToRoot(Element root, Task task) {
		root.addElement("task")
				.addAttribute("type", task.getTaskType().toString())
				.addAttribute("name", task.getTaskName())
				.addAttribute("startday", task.getStartDay())
				.addAttribute("endday", task.getEndDay())
				.addAttribute("starttime", String.valueOf(task.getStartTime()))
				.addAttribute("endtime", String.valueOf(task.getEndTime()))
				.addAttribute("status", String.valueOf(task.getTaskStatus()));

	}

}

package todologApp;

//import javax.swing.ImageIcon;
//import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ShowMessageDialog {
	private static Task _task;
	
	public ShowMessageDialog( Task task){
		_task=task;
	}
	public void execute()
	  {
		String message = generateMessage();
	    // create a jframe
	    //JFrame frame = new JFrame("JOptionPane showMessageDialog example");   
	    // show a joptionpane dialog using showMessageDialog
	    JOptionPane.showMessageDialog(null,message,"Reminder",JOptionPane.WARNING_MESSAGE);
	    
	  }
	public String generateMessage(){
		String name = _task.getTaskName();
		String msg;
		if (_task.getTaskType()==TaskType.DEADLINE){
			int dueDate= _task.getEndTime();
			msg = "You have "+name+ " due tomorrow at "+dueDate;
		}
		else if(_task.getTaskType()==TaskType.TIMED){
			String startDate = _task.getStartTimeStr();
			String endDate=_task.getEndTimeStr();
			msg="you have "+name+" from "+startDate+" to "+endDate;
		}
		else{
			msg="sorry for the wrong reminder";
		}
		return msg;
	}

}

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
			String dueDate= _task.getEndTimeStr();
			msg = "You have "+name+ " due tomorrow at "+dueDate;
		}
		else if(_task.getTaskType()==TaskType.TIMED){
			String startTime = _task.getStartTimeStr();
			String endTime=_task.getEndTimeStr();
			int endDate= _task.getEndDate();
			int endMonth=_task.getEndMonth();
			int endYear=_task.getEndYear();
			String endDay= " "+endDate+"/"+endMonth+"/"+endYear;
			int startDate= _task.getStartDate();
			int startMonth=_task.getStartMonth();
			int startYear=_task.getStartYear();
			String startDay=" "+startDate+"/"+startMonth+"/"+startYear;
			msg="you have "+name+" from"+startDay+" at "+startTime+" to"+endDay+" at "+endTime; 
		}
		else{
			msg="sorry for the wrong reminder";
		}
		return msg;
	}

}

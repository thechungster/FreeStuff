import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.restfb.exception.FacebookNetworkException;


public class FBTracker {
	
	private String phoneNumber;
	private String textMessage;
	private ArrayList<FBGroup> groupArrayList;
	
	public FBTracker(ArrayList<FBGroup> groupArray, String number)	{
		groupArrayList = groupArray;
		phoneNumber = number;
		textMessage = "";
	}
	
	public void getFreeAndText() throws FileNotFoundException, IOException, FacebookNetworkException, NullPointerException	{

		for(FBGroup currentGroup : groupArrayList)	{
			textMessage += currentGroup.checkDuplicates(phoneNumber);
		}
		
		if (textMessage.length() > 5) {
			GoogleMail mailMsg = new GoogleMail();
			mailMsg.addMsg(textMessage, phoneNumber);
		}
		
	}
	
	
	
	
}

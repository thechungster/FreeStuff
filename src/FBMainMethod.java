import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import com.restfb.exception.FacebookNetworkException;

/*
 * HOW TO: Add/Remove
 * ADD: Create an FB Group and then add to grouparray. then in fb trackers add the group array and phone number
 * REMOVE: Remove from groupArrays 
 * 		
 * 
 * 
 * 
 */

public class FBMainMethod {

	public static void main(String[] args) throws IOException,
			InterruptedException {
		while (true) {
			try {
				start();
			} catch (NullPointerException e) {
				
			}
			Thread.sleep(5000);
		}

	}

	public static void start() throws IOException, NullPointerException {

		ArrayList<FBGroup> groupArray = new ArrayList<FBGroup>();
		FBGroup group_1 = new FBGroup(new PrivateInfo().GROUP_ID_1);
		FBGroup group_2 = new FBGroup(new PrivateInfo().GROUP_ID_2);
		groupArray.add(group_1);
		groupArray.add(group_2);

		ArrayList<FBTracker> allFBTrackers = new ArrayList<FBTracker>();

		allFBTrackers.add(new FBTracker(groupArray,
				new PrivateInfo().PHONE_NUMBER_1));
		allFBTrackers.add(new FBTracker(groupArray,
				new PrivateInfo().PHONE_NUMBER_2));

		for (FBTracker current : allFBTrackers) {
			try {
				current.getFreeAndText();
			}
			// no internet
			catch (FacebookNetworkException e) {
				break;
			}
		}

	}

}

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.exception.FacebookNetworkException;
import com.restfb.types.Post;

public class FBGroup {

	private String groupNumber;
	private Connection<Post> group;
	private FacebookClient fbClient;

	@SuppressWarnings({ "deprecation" })
	public FBGroup(String groupNumber) throws FacebookNetworkException {
		try {
			this.groupNumber = groupNumber;
			fbClient = new DefaultFacebookClient(
					new PrivateInfo().MY_ACCESS_TOKEN);
			group = fbClient.fetchConnection(groupNumber + "/feed", Post.class);
		} catch (FacebookNetworkException e) {
			return;
		}
	}

	// check for "free"
	public String checkDuplicates(String phoneNumber)
			throws FileNotFoundException, IOException, NullPointerException {
		String freeThings = "";
		String line = "";
		try {
			new FileOutputStream("free" + phoneNumber + ".txt", true).close();
			for (int i = 0; i < 5; i++) {
				String postMessage = "";
				int feelFreeCount = 0;
				int freeCount = 0;
				postMessage = group.getData().get(i).getMessage(); // get the //
				boolean add = true;													// post
				// has no message in the post, such as a picture only
				if (postMessage == null) {
					continue;
				}
				String linkToPost = group.getData().get(i).getActions().get(0).getLink(); // get the link to the post
				String[] msgArr = postMessage.split("\\W+"); // split special
																// characters
				for (String message : msgArr) {
					message = message.replaceAll("\\s+", "");

					if (message.toLowerCase().equals("free")) {
						freeCount++;
					}
				}
				
				String[] linkArray = linkToPost.split("/");
				linkToPost = "facebook.com/groups/" + linkArray[3] + "/"
						+ linkArray[5];
				// To stop "feel free" false positives
				if (postMessage.toLowerCase().contains("feel free")) {
					feelFreeCount++;
				}
				if (postMessage.toLowerCase().contains("free")) {
					BufferedReader br = new BufferedReader(new FileReader(
							"free" + phoneNumber + ".txt"));
					while ((line = br.readLine()) != null) {
						if (line.contains(linkToPost)
								|| freeCount == feelFreeCount) {
							add = false;
							continue;
						}
					}
					if (freeCount == feelFreeCount) {
						add = false;
					}
					if (add) {
						writeTo(linkToPost, phoneNumber);
						freeThings += postMessage + '\n';
						freeThings += "LINK: " + linkToPost + '\n';
						freeThings += '\n';
						freeThings += "--------------------------------------------------";
						freeThings += '\n';
						System.out.println(freeThings);
					}
					br.close();
				}

			}
		} catch (IndexOutOfBoundsException E) {
			System.out.println("THERES NOTHING HERE");
			return freeThings;
		}
		return freeThings;
	}

	private void writeTo(String message, String phoneNumber) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				"free" + phoneNumber + ".txt", true)));
		for (String msg : message.split("\n")) {
			out.println(msg);
		}
		out.close();
		return;
	}

}

import java.util.Collections;
import java.util.Date;
import java.util.TreeSet;
import java.util.TreeSet;

public class TopicManager {
	// We maintain two sorted lists, where min(list1) > max(list2)
	private TreeSet<Topic> top20Topics = new TreeSet<Topic>(Collections.reverseOrder());
	private TreeSet<Topic> restOfTopics = new TreeSet<Topic>(Collections.reverseOrder());
	
	// Note: Singleton
	private static TopicManager tm;
	
	private TopicManager() { }

	public static TopicManager getInstance( ) {
		if (tm==null) {
			tm = new TopicManager();
		}
		return tm;
	}
	
	public TreeSet<Topic> getTopics() {
		return top20Topics;
	}
	public void addTopic(String str) {
		addTopic(new Topic(str));
	}
	public void addTopic(String str, int votes, long time) {
		addTopic(new Topic(str, votes, new Date(time)));
	}
	public void addTopic(Topic topic) {
		if (top20Topics.size() < 20) {
			top20Topics.add(topic);
		} else {
			restOfTopics.add(topic);
		}
		checkListsAndReorderIfNeeded();
	}
	
	// For testing purposes.
	public void reset() {
		top20Topics.clear();
		restOfTopics.clear();
	}
	
	protected void checkListsAndReorderIfNeeded() {
		if (listsNeedReordering()) {
			reorderLists();
		}
	}
	private boolean listsNeedReordering() {
		return (!top20Topics.isEmpty() &&
				!restOfTopics.isEmpty() && 
				(top20Topics.last().compareTo(restOfTopics.first()) < 0));
	}
	private void reorderLists() {
		Topic toRest = top20Topics.pollLast();
		Topic toTop = restOfTopics.pollFirst();
		top20Topics.add(toTop);
		restOfTopics.add(toRest);
	}
	
	public void upvoteTopic(Topic topic) {
		top20Topics.remove(topic);
		topic.voteUp();
		top20Topics.add(topic);
		checkListsAndReorderIfNeeded();
	}
	public void downvoteTopic(Topic topic) {
		top20Topics.remove(topic);
		topic.voteDown();
		top20Topics.add(topic);
		checkListsAndReorderIfNeeded();
	}
	
}

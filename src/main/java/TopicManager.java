import java.util.Collections;
import java.util.Date;
import java.util.TreeSet;

public class TopicManager {
	// We maintain two sorted lists, where min(list1) > max(list2) and size(list1) <= 20
	// This makes accessing and returning the top 20 items easier
	private TreeSet<Topic> top20Topics = new TreeSet<Topic>(Collections.reverseOrder());
	private TreeSet<Topic> restOfTopics = new TreeSet<Topic>(Collections.reverseOrder());
	
	// Note: This is a singleton
	// Following the concept of a single database,
	// I felt that it would be safer to only allow one TM instance
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
	
	// We modify the lists in two ways:
	// (1) Addition, and (2) Voting
	// To maintain ordering across both lists, we do this check every time.
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
	
	// From what I read, modifying an object already in a TreeSet
	// does not update its position, so I'm sticking to remove->add
	public void upvoteTopic(Topic topic) {
		// It may be possible to inject a topic with false votes
		// e.g. a topic("hi", 0, <timeA>) exists in the list, and we receive topic("hi", 100, <timeA>)
		// We would update the list with topic("hi", 101, <timeA>)
		// For this context, in my effort to keep it simple, I'll stick to TreeSet because it maintains ordering.
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

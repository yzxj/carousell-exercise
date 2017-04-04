import java.util.Collections;
import java.util.PriorityQueue;

public class TopicManager {
	private PriorityQueue<Topic> top20TopicsMinHeap = new PriorityQueue<Topic>();
	private PriorityQueue<Topic> restOfTopicsMaxHeap = new PriorityQueue<Topic>(Collections.reverseOrder());
	
	// Note: Singleton
	private static TopicManager tm;
	
	private TopicManager() { }

	public static TopicManager getInstance( ) {
		if (tm==null) {
			tm = new TopicManager();
		}
		return tm;
	}
	
	public void addTopic(Topic topic) {
		if (top20TopicsMinHeap.size() < 20) {
			top20TopicsMinHeap.offer(topic);
		} else {
			restOfTopicsMaxHeap.offer(topic);
		}
		if (heapsNeedSwap()) {
			swapHeapHeads();
		}
	}
	
	public boolean heapsNeedSwap() {
		return (top20TopicsMinHeap.peek().compareTo(restOfTopicsMaxHeap.peek()) < 0);
	}
	
	public void swapHeapHeads() {
		Topic toRest = top20TopicsMinHeap.poll();
		Topic toTop = restOfTopicsMaxHeap.poll();
		top20TopicsMinHeap.offer(toTop);
		restOfTopicsMaxHeap.offer(toRest);
	}
	
	public void upvoteTopic(Topic topic) {
		top20TopicsMinHeap.remove(topic);
		topic.voteUp();
		top20TopicsMinHeap.offer(topic);
	}
	
	public void downvoteTopic(Topic topic) {
		top20TopicsMinHeap.remove(topic);
		topic.voteDown();
		top20TopicsMinHeap.offer(topic);
	}
	
}

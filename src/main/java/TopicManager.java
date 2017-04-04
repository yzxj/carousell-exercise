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
	
	public PriorityQueue<Topic> getTop20() {
		return top20TopicsMinHeap;
	}
	public void addTopic(String str) {
		addTopic(new Topic(str));
	}
	public void addTopic(Topic topic) {
		if (top20TopicsMinHeap.size() < 20) {
			top20TopicsMinHeap.offer(topic);
		} else {
			restOfTopicsMaxHeap.offer(topic);
		}
		checkHeapsAndSwapIfNeeded();
	}
	
	// For testing purposes.
	public void reset() {
		top20TopicsMinHeap.clear();
		restOfTopicsMaxHeap.clear();
	}
	
	// We maintain two queues, where the min(minHeap) > max(maxHeap)
	protected void checkHeapsAndSwapIfNeeded() {
		if (heapsNeedSwap()) {
			swapHeapHeads();
		}
	}
	private boolean heapsNeedSwap() {
		return (!top20TopicsMinHeap.isEmpty() &&
				!restOfTopicsMaxHeap.isEmpty() && 
				(top20TopicsMinHeap.peek().compareTo(restOfTopicsMaxHeap.peek()) < 0));
	}
	private void swapHeapHeads() {
		Topic toRest = top20TopicsMinHeap.poll();
		Topic toTop = restOfTopicsMaxHeap.poll();
		top20TopicsMinHeap.offer(toTop);
		restOfTopicsMaxHeap.offer(toRest);
	}
	
	// PQ removal is O(n), but in our case only the top 20 are modifiable so we use a minHeap to limit n to 20.
	public void upvoteTopic(Topic topic) {
		top20TopicsMinHeap.remove(topic);
		topic.voteUp();
		top20TopicsMinHeap.offer(topic);
		checkHeapsAndSwapIfNeeded();
	}
	public void downvoteTopic(Topic topic) {
		top20TopicsMinHeap.remove(topic);
		topic.voteDown();
		top20TopicsMinHeap.offer(topic);
		checkHeapsAndSwapIfNeeded();
	}
	
}

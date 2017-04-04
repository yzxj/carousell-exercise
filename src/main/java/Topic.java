import java.util.Date;

public class Topic implements Comparable<Topic> {
	private String content;
	private int upvotes;
	private Date timeCreated;
	
	public Topic(String content) {
		this(content, 0, new Date());
	}
	
	public Topic(String content, int upvotes, Date timeCreated) {
		this.content = content;
		this.upvotes = upvotes;
		this.timeCreated = timeCreated;
	}
	
	public void voteUp() {
		this.upvotes += 1;
	}
	 
	public void voteDown() {
		this.upvotes -= 1;
	}
	
	public String getContent() {
		return content;
	}

	public int getUpvotes() {
		return upvotes;
	}

	public Date getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((timeCreated == null) ? 0 : timeCreated.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Topic other = (Topic) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (timeCreated == null) {
			if (other.timeCreated != null)
				return false;
		} else if (!timeCreated.equals(other.timeCreated))
			return false;
		return true;
	}

	@Override
	public int compareTo(Topic other) {
		int otherVotes = other.getUpvotes();
		Date otherTime = other.getTimeCreated();
		if (this.upvotes != otherVotes) {
			if (this.upvotes < otherVotes) {
				return -1;
			} else {
				return 1;
			}
		} else {
			if (this.timeCreated.before(otherTime)) {
				return -1;
			} else if (this.timeCreated.after(otherTime)) {
				return 1;
			} else {
				return 0;
			}
		}
	}
	
	
}

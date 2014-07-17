
/*
 * Authors: Tobias Andersson, Carl Frendin, Jonas Haglund
 */

public interface HeapObject {

	public int getKey();
	
	public void setPosition(int currentPosition);
	
	public int getPosition();
	
	public void setKey(int newKey);
}

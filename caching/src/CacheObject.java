public class CacheObject implements Comparable<CacheObject> {
	private int objectNumber;
	private int nextAccess;

	public CacheObject(int objectNumber, int nextAccess) {
		this.objectNumber = objectNumber;
		this.nextAccess = nextAccess;
	}

	public int getNextAccess() {
		return nextAccess;
	}

	public void setNextAccess(int nextAccess) {
		this.nextAccess = nextAccess;
	}

	public int getObjectNumber() {
		return objectNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof CacheObject))
			return false;
		if (objectNumber == ((CacheObject) o).getObjectNumber() && nextAccess == ((CacheObject) o).getNextAccess())
			return true;
		return false;

	}

	@Override
	public int compareTo(CacheObject object) {
		if (nextAccess < object.getNextAccess())
			return 1;
		if (nextAccess == object.getNextAccess() && objectNumber == object.getObjectNumber())
			return 0;
		return -1;
	}

	public String toString() {
		return "[" + objectNumber + ", " + nextAccess + "]";
	}

}

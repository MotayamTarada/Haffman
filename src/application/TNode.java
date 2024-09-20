package application;

public class TNode<T extends Comparable<T>> {
	T data;
	TNode<T> left;
	TNode<T> right;

	public TNode(T data) {
		this.data = data;
		this.left = null;
		this.right = null;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public TNode<T> getLeft() {
		return left;
	}

	public void setLeft(TNode<T> left) {
		this.left = left;
	}

	public TNode<T> getRight() {
		return right;
	}

	public void setRight(TNode<T> right) {
		this.right = right;
	}

	public boolean isLeaf() {

		if (left == null && right == null) {
			return true;
		}
		return false;
	}

	public boolean hasLeft() {
		if (left != null) {
			return true;
		}
		return false;
	}

	public boolean hasRight() {
		if (right != null) {
			return true;
		}
		return false;
	}

	public boolean isFull() {
		return true;
	}

	public boolean isComplete() {
		return true;
	}

	@Override
	public String toString() {
		return " data=" + data;
	}
	   public int compareTo(TNode<T> otherNode) {
	        return this.data.compareTo(otherNode.getData());
	    }
}

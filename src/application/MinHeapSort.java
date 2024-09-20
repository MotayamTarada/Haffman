package application;

public class MinHeapSort<T extends Comparable<T>> {
    private TNode<T>[] heap;
    private int size;
    private int maxsize;

    private static final int FRONT = 1;

    public MinHeapSort(int maxsize) {
        this.maxsize = maxsize;
        this.size = 0;
        heap = new TNode[maxsize + 1];
    }

    private int parent(int pos) {
        return pos / 2;
    }

    private int leftChild(int pos) {
        return 2 * pos;
    }

    private int rightChild(int pos) {
        return (2 * pos) + 1;
    }

    private boolean isLeaf(int pos) {
        return pos > (size / 2) && pos <= size;
    }

    private void swap(int fpos, int spos) {
        TNode<T> tmp = heap[fpos];
        heap[fpos] = heap[spos];
        heap[spos] = tmp;
    }

    private void minHeapify(int pos) {
        if (!isLeaf(pos)) {
            int leftChildPos = leftChild(pos);
            int rightChildPos = rightChild(pos);
            int smallest = pos;

            // Check if the left child exists and is smaller than the current smallest
            if (leftChildPos <= size && heap[leftChildPos] != null && heap[leftChildPos].getData().compareTo(heap[smallest].getData()) < 0) {
                smallest = leftChildPos;
            }

            // Check if the right child exists and is smaller than the current smallest
            if (rightChildPos <= size && heap[rightChildPos] != null && heap[rightChildPos].getData().compareTo(heap[smallest].getData()) < 0) {
                smallest = rightChildPos;
            }

            // If the smallest node is not the current parent, swap and recursively call minHeapify
            if (smallest != pos) {
                swap(pos, smallest);
                minHeapify(smallest);
            }
        }
    }

    public void insert(TNode<Character> z) {
        if (size >= maxsize) {
            System.out.println("Heap is full!");
            return;
        }

        heap[++size] = (TNode<T>) z;
        int current = size;

        while (current > 1 && heap[current].compareTo(heap[parent(current)]) < 0) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public void buildHeap(T[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        this.heap = (TNode<T>[]) new TNode[array.length + 1];
        this.maxsize = array.length;
        this.size = array.length;
        System.arraycopy(array, 0, this.heap, 1, array.length); // Copy from index 0
        for (int i = size / 2; i >= 1; i--) {
            minHeapify(i);
        }
    }

    public TNode<T> removeMin() {
        if (size == 0) {
            return null; // Heap is empty
        }
        TNode<T> popped = heap[FRONT];
        heap[FRONT] = heap[size--];
        minHeapify(FRONT);
        return popped;
    }

    public void printHeap() {
        for (int i = 1; i <= size / 2; i++) {
            if (heap[i] != null && heap[2 * i] != null && heap[2 * i + 1] != null) {
                System.out.print(" PARENT : " + heap[i] + " LEFT CHILD : " + heap[2 * i]
                        + " RIGHT CHILD :" + heap[2 * i + 1]);
                System.out.println();
            }
        }
    }

    public void clear() {
        size = 0;
    }

    public int length() {
        return this.size;
    }

    public TNode<T> getNode(int index) {
        if (index >= 1 && index <= size) {
            return heap[index];
        } else {
            return null; // Return null if index is out of bounds
        }
    }

    public TNode<T>[] heapSortDescending() {
        TNode<T>[] sortedArray = (TNode<T>[]) new TNode[size];
        int originalSize = size;

        for (int i = 0; i < originalSize; i++) {
            sortedArray[i] = removeMin();
        }

        return sortedArray;
    }

    public static TNode<Character> buildTree(char[] charArray) {
        if (charArray == null || charArray.length == 0) {
            return null;
        }
        return buildTreeHelper(charArray, 0);
    }

    private static TNode<Character> buildTreeHelper(char[] charArray, int index) {
        TNode<Character> root = null;

        // Base case: If index is within the array bounds
        if (index < charArray.length) {
            root = new TNode<>(charArray[index]);
            // Recursively build left and right subtrees
            root.left = buildTreeHelper(charArray, 2 * index + 1);
            root.right = buildTreeHelper(charArray, 2 * index + 2);
        }

        return root;
    }

	public int getSize() {
		return size;
	}
}

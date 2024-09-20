package application;

public class MinHeap<T extends Comparable<T>> {
    private T[] heap;
    private int size;
    private int maxsize;

    public T[] getHeap() {
		return heap;
	}

	public void setHeap(T[] heap) {
		this.heap = heap;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getMaxsize() {
		return maxsize;
	}

	public void setMaxsize(int maxsize) {
		this.maxsize = maxsize;
	}

	public static int getFront() {
		return FRONT;
	}

	private static final int FRONT = 1;

    public MinHeap(int maxsize) {
        this.maxsize = maxsize;
        this.size = 0;
        heap = (T[]) new Comparable[maxsize + 1];
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
        T tmp = heap[fpos];
        heap[fpos] = heap[spos];
        heap[spos] = tmp;
    }

    private void minHeapify(int pos) {
        if (!isLeaf(pos)) {
            int swapPos = pos;
            if (rightChild(pos) <= size) {
                swapPos = (heap[leftChild(pos)].compareTo(heap[rightChild(pos)]) < 0) ? leftChild(pos) : rightChild(pos);
            } else {
                swapPos = leftChild(pos);
            }

            if (heap[pos].compareTo(heap[leftChild(pos)]) > 0 || heap[pos].compareTo(heap[rightChild(pos)]) > 0) {
                swap(pos, swapPos);
                minHeapify(swapPos);
            }
        }
    }

    public void insert(T element) {
        if (size >= maxsize) {
            return;
        }

        heap[++size] = element;
        int current = size;

        while (heap[current].compareTo(heap[parent(current)]) < 0) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public T removeMin() {
        T popped = heap[FRONT];
        heap[FRONT] = heap[size--];
        minHeapify(FRONT);
        return popped;
    }

    public void buildHeap(T[] array) {
        if (array == null || array.length == 0) {
            return;
        }
        this.heap = (T[]) new Comparable[array.length + 1];
        this.maxsize = array.length;
        this.size = array.length;
        System.arraycopy(array, 0, this.heap, 1, array.length);
        for (int i = size / 2; i >= 1; i--) {
            minHeapify(i);
        }
    }

    public void printHeap() {
        for (int i = 1; i <= size / 2; i++) {
            System.out.print(" PARENT : " + heap[i] + " LEFT CHILD : " + heap[2 * i]
                    + " RIGHT CHILD :" + heap[2 * i + 1]);
            System.out.println();
        }
    }
    
    public void clear() {
        this.size = 0;
    }


    public void add(T element) {
        if (size >= maxsize) {
            System.out.println("Heap is full!");
            return;
        }

        heap[++size] = element;
        int current = size;

        while (current > 1 && heap[current].compareTo(heap[parent(current)]) < 0) {
            swap(current, parent(current));
            current = parent(current);
        }
    }


}

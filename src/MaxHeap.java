/*
 * Copyright (c) 2024 Gengrui (Edward) Zhang; web: https://gengruizhang.com
 * This code is for educational purposes, demonstrating the implementation of a max-heap
 * data structure and its associated operations.
 */
import java.util.Arrays;

public class MaxHeap {
    int[] arr;
    int heap_size;

    public MaxHeap(int[] array) {
        this.arr = array;
        this.heap_size = array.length;
        // buildHeap(); // Uncomment if you want to build a valid max-heap upon initialization
    }

    /**
     * The maxHeapify function maintains max-heap property, which is for every node i, A[Parent(i)] >= A[i]
     * Given an array and an index, it ensures that the subtree rooted at that index is a valid max-heap.
     * The max-heap property requires that every parent node's value is greater than or equal to the values
     * of its children. Starting from the given node i, this process continues until the subtree satisfies
     * the max-heap property or until leaf nodes are reached.
     */
    private void maxHeapify(int index) {
        int largest = index;
        // The lecture used examples that assume array index starts at 1; in code, array index starts at 0
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;

        // Check if left child is larger than root
        if (leftChild < heap_size && arr[leftChild] > arr[largest]) {
            largest = leftChild;
        }

        // Check if right child is larger than the largest so far
        if (rightChild < heap_size && arr[rightChild] > arr[largest]) {
            largest = rightChild;
        }

        // If the largest is not root, swap and continue heapifying
        if (largest != index) {
            swap(index, largest);
            maxHeapify(largest);
        }
    }

    // Build a max heap from the array by calling maxHeapify in a bottom-up manner
    private void buildHeap() {
        // Start from the last non-leaf node and heapify each one
        for (int i = (heap_size / 2) - 1; i >= 0; i--) {
            maxHeapify(i);
        }
    }

    // Heap sort function to sort the array
    public void heapSort() {
        buildHeap();

        // One by one extract the largest element from the heap
        for (int i = heap_size - 1; i >= 1; i--) {
            // Move the current root (largest element) to the end
            swap(0, i);

            // Reduce the size of the heap
            heap_size--;

            // Call maxHeapify on the reduced heap
            maxHeapify(0);
        }
    }

    public int heapMaximum() {
        if (heap_size <= 0) {
            throw new IllegalStateException("Heap is empty.");
        }
        return arr[0]; // The maximum element is at the root
    }

    // Function to increase the key at index i to a new value
    public void heapIncreaseKey(int index, int newValue) {
        if (newValue < arr[index]) {
            throw new IllegalArgumentException("New value is smaller than current value.");
        }

        arr[index] = newValue;

        // Bubble up to maintain max-heap property
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (arr[index] > arr[parentIndex]) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }


    // Insert a new element into the heap using heapIncreaseKey
    public void heapInsert(int key) {
        // Create a new array with an additional space
        arr = Arrays.copyOf(arr, heap_size + 1);
        arr[heap_size] = Integer.MIN_VALUE; // Set to the smallest value
        heap_size++; // Increase the heap size

        // Use heapIncreaseKey to set the new value and maintain the heap property
        heapIncreaseKey(heap_size - 1, key);
    }


    // Insert a new element into the heap by directly bubbling up;
    // The bubble-up is similar to heapIncreaseKey but avoids calling it.
    public void heapInsertDirectBubbleUp(int key) {
        // Remember array has a fixed size, so first we need to create a new array with a larger size
        arr = Arrays.copyOf(arr, heap_size + 1);
        arr[heap_size] = key; // Add the new key at the end
        heap_size++; // Increase the heap size

        // Maintain the max-heap property by bubbling up
        int currentIndex = heap_size - 1;
        while (currentIndex > 0) {
            int parentIndex = (currentIndex - 1) / 2;
            if (arr[currentIndex] > arr[parentIndex]) {
                swap(currentIndex, parentIndex);
                currentIndex = parentIndex;
            } else {
                break;
            }
        }
    }

    // Exchange two elements in the heap
    private void swap(int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public void printArray() {
        System.out.println(Arrays.toString(arr));
    }

    public static void main(String[] args) {
        int[] array = {5, 8, 4, 1, 7, 2, 6};

        MaxHeap maxHeap = new MaxHeap(array);

        System.out.println("Original array:");
        maxHeap.printArray();

        // Perform max-heapify on the root
        maxHeap.maxHeapify(0);
        System.out.println("After performing max-heapify on array[0]:");
        maxHeap.printArray();

        // Build the max-heap
        maxHeap.buildHeap();
        System.out.println("After building a max-heap:");
        maxHeap.printArray();

        // Increase key of the last element
        maxHeap.heapIncreaseKey(array.length-1, 100);
        System.out.println("After updating the key of the last element:");
        maxHeap.printArray();

        // Insert a new element using heapIncreaseKey
        maxHeap.heapInsert(9);
        System.out.println("After inserting a new element (calling heapIncreaseKey()):");
        maxHeap.printArray();

        // Insert a new element using direct bubble up
        maxHeap.heapInsertDirectBubbleUp(11);
        System.out.println("After inserting a new element (directly bubbling up):");
        maxHeap.printArray();

        // Peek at the maximum value
        int max = maxHeap.heapMaximum();
        System.out.println("The max element is: "+ max);

        // Perform heap sort
        maxHeap.heapSort();
        System.out.println("Sorted array using Heap Sort:");
        maxHeap.printArray();
    }
}

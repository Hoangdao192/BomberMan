package Utils;

import java.util.ArrayList;

public class MinHeap<T extends Object & Comparable<T>> {
    private ArrayList<T> heap;

    public MinHeap()
    {
        heap = new ArrayList<>();
    }

    public void add(T item) {
        heap.add(item);
        sortUp(item);
    }

    public void removeRoot() {
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        if (heap.size() > 0) {
            sortDown(heap.get(0));
        }
    }

    public int size() {
        return heap.size();
    }

    public T getRoot() {
        if (heap.size() == 0) {
            return null;
        }
        return heap.get(0);
    }

    public boolean contains(T other) {
        for (int i = 0; i < heap.size(); ++i) {
            if (other.equals(heap.get(i))) {
                return true;
            }
        }
        return false;
    }

    public void set(int index, T object) {
        if (index < heap.size()) {
            heap.set(index, object);
            sortDown(object);
            sortUp(object);
        }
    }

    public int indexOf(T object) {
        for (int i = 0; i < heap.size(); ++i) {
            if (object.equals(heap.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private void sortDown(T item) {
        while (true) {
            //  Lấy index của item
            int itemIndex = 0;
            if (heap.contains(item)) {
                itemIndex = heap.indexOf(item);
            }
            T swapItem = null;
            //  Lấy index của item con
            int leftChildIndex = itemIndex * 2 + 1;
            int rightChildIndex = itemIndex * 2 + 2;
            if (leftChildIndex >= heap.size() && rightChildIndex >= heap.size()) {
                return;
            }
            //  Không có nhánh con
            if (heap.get(leftChildIndex) == null && heap.get(rightChildIndex) == null) {
                System.out.println("false");
                break;
            }
            //  Chỉ có nhánh phải
            if (rightChildIndex < heap.size() && leftChildIndex >= heap.size()) {
                swapItem = heap.get(rightChildIndex);
            }
            //  Chỉ có nhánh trái
            else if (leftChildIndex < heap.size() && rightChildIndex >= heap.size()) {
                swapItem = heap.get(leftChildIndex);
            }
            //  Có cả hai nhánh
            else {
                T leftChild = heap.get(leftChildIndex);
                T rightChild = heap.get(rightChildIndex);
                //  Nếu nhánh trái nhỏ hơn
                if (leftChild.compareTo(rightChild) < 0) {
                    swapItem = leftChild;
                }
                //  Nếu nhánh phải nhỏ hơn hoặc hai nhánh bằng nhau
                else {
                    swapItem = rightChild;
                }
            }
            if (swapItem == null) {
                break;
            }
            if (item.compareTo(swapItem) > 0) {
                swap(item, swapItem);
            } else {
                break;
            }

        }
    }

    private void sortUp(T item) {
        while (true) {
            //  Lấy index của item trong mảng
            int itemIndex = 0;
            if (heap.contains(item)) {
                itemIndex = heap.indexOf(item);
            }
            //  Lấy index của item cha
            int parentIndex = (itemIndex - 1) / 2;
            if (parentIndex < 0 || heap.size() == 1) {
                return;
            }
            //  So sánh và đổi chỗ với item cha nếu item này nhỏ hơn item cha
            T parentItem = heap.get(parentIndex);
            if (item.compareTo(parentItem) < 0) {
                swap(item, parentItem);
            } else {
                break;
            }
        }
    }

    private void swap(T first, T second) {
        int firstIndex = 0;
        int secondIndex = 0;
        try {
            firstIndex = heap.indexOf(first);
            secondIndex = heap.indexOf(second);
        } catch (Exception e) {
            return;
        }

        T temp = first;
        heap.set(firstIndex, second);
        heap.set(secondIndex, temp);
    }

    public ArrayList<T> getHeap() {
        ArrayList<T> ret = new ArrayList<>();
        for (int i = 0; i < heap.size(); ++i) {
            ret.add(heap.get(i));
        }
        return ret;
    }
}

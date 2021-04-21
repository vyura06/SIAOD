import java.util.ArrayList;
import java.util.List;

public class WeightingFairQueue<E> {
    private static class WeightNode<E> {
        int weight;
        Queue<E> queue;

        public WeightNode(int weight, Queue<E> queue) {
            this.weight = weight;
            this.queue = queue;
        }

        @Override
        public String toString() {
            return "{weight=" + weight + "=" + queue + '}';
        }
    }

    private final Queue<E> outputQueue  = new Queue<>();
    private final Queue<E> priorityQueue = new Queue<>();
    private final List<WeightNode<E>> weightQueues = new ArrayList<>();
    private int index = 0;

    public WeightingFairQueue() {
    }

    public int size() {
        int s = outputQueue.size() + priorityQueue.size();
        for (WeightNode<E> weightNode : weightQueues)
            s += weightNode.queue.size();
        return s;
    }

    public boolean contains(Object o) {
        if (outputQueue.contains(o))
            return true;
        if (priorityQueue.contains(o))
            return true;
        for (WeightNode<E> weightNode : weightQueues)
            if (weightNode.queue.contains(o))
                return true;
        return false;
    }
    public boolean remove(Object o) {
        if (outputQueue.remove(o))
            return true;
        if (priorityQueue.remove(o))
            return true;
        for (WeightNode<E> weightNode : weightQueues)
            if (weightNode.queue.remove(o))
                return true;
        return false;
    }
    public void clear() {
        outputQueue.clear();
        priorityQueue.clear();
        for (WeightNode<E> weightNode : weightQueues)
            weightNode.queue.clear();
        index = 0;
    }

    private void checkOutputQueue() {
        if (outputQueue.isEmpty()) {
            if (priorityQueue.isEmpty()) {
                searchElementsInWeightQueues();
            } else {
                outputQueue.offer(priorityQueue.remove());
            }
        }
    }
    private void searchElementsInWeightQueues() {
        for (int i = 0; i < weightQueues.size(); i++) {
            WeightNode<E> node = weightQueues.get(index);
            boolean add = false;
            for (int j = 0; j < node.weight && !node.queue.isEmpty(); j++) {
                outputQueue.offer(node.queue.remove());
                add = true;
            }
            nextIndex();
            if (add)
                break;
        }
    }
    private void nextIndex() {
        if (++index >= weightQueues.size())
            index = 0;
    }

    public void offer(E e) {
        priorityQueue.offer(e);
    }
    public E poll() {
        checkOutputQueue();
        return outputQueue.poll();
    }
    public E peek() {
        checkOutputQueue();
        return outputQueue.peek();
    }

    public int sizeWQ() {
        return weightQueues.size();
    }
    public void createNewWQ(int weight) {
        createNewWQ(weight, new Queue<>());
    }
    public void createNewWQ(int weight, Queue<E> queue) {
        if (weight < 1)
            throw new IllegalArgumentException();
        if (queue == null)
            throw new NullPointerException();
        weightQueues.add(new WeightNode<>(weight, queue));
    }

    public void removeWQ(int index) {
        weightQueues.remove(index);
        if (this.index > index)
            this.index--;
    }
    public void offerInWQ(E e, int index) {
        weightQueues.get(index).queue.offer(e);
    }

    @Override
    public String toString() {
        return "WeightingFairQueue{outputQueue=" + outputQueue +
                "\nmaxPriorityQueue=" + priorityQueue +
                "\nweightQueues=" + weightQueues + '}';
    }
}
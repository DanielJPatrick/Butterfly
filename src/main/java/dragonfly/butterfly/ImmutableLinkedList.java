package dragonfly.butterfly;


import java.io.Serializable;
import java.lang.reflect.Array;

public final class ImmutableLinkedList<V> implements Serializable, Cloneable {
    private final Class valueType;
    private final Comparator<ImmutableLinkedListNode<V>> comparator;
    private final ImmutableLinkedListNode<V> startNode;
    private final int length;

    public ImmutableLinkedList() {
        this.valueType = Object.class;
        this.comparator = this.createDefaultComparator();
        this.startNode = null;
        this.length = 0;
    }

    public ImmutableLinkedList(final Class<V> valueType) {
        this.valueType = valueType;
        this.comparator = this.createDefaultComparator();
        this.startNode = null;
        this.length = 0;
    }

    public ImmutableLinkedList(final Comparator<ImmutableLinkedListNode<V>> comparator) {
        this.valueType = Object.class;
        this.comparator = comparator;
        this.startNode = null;
        this.length = 0;
    }

    public ImmutableLinkedList(final Class<V> valueType, final Comparator<ImmutableLinkedListNode<V>> comparator) {
        this.valueType = valueType;
        this.comparator = comparator;
        this.startNode = null;
        this.length = 0;
    }

    ImmutableLinkedList(final Comparator<ImmutableLinkedListNode<V>> comparator, final ImmutableLinkedListNode<V> startNode) {
        this.valueType = Object.class;
        this.comparator = comparator;
        this.startNode = startNode;
        this.length = this.calculateLength();
    }

    ImmutableLinkedList(final Class<V> valueType, final Comparator<ImmutableLinkedListNode<V>> comparator, final ImmutableLinkedListNode<V> startNode) {
        this.valueType = valueType;
        this.comparator = comparator;
        this.startNode = startNode;
        this.length = this.calculateLength();
    }

    public ImmutableLinkedList(final V... values) {
        this.valueType = Object.class;
        this.comparator = this.createDefaultComparator();
        this.startNode = this.create(values);
        this.length = this.calculateLength();
    }

    public ImmutableLinkedList(final Class<V> valueType, final V... values) {
        this.valueType = valueType;
        this.comparator = this.createDefaultComparator();
        this.startNode = this.create(values);
        this.length = this.calculateLength();
    }

    public ImmutableLinkedList(final Comparator<ImmutableLinkedListNode<V>> comparator, final V... values) {
        this.valueType = Object.class;
        this.comparator = comparator;
        this.startNode = this.create(values);
        this.length = this.calculateLength();
    }

    public ImmutableLinkedList(final Class<V> valueType, final Comparator<ImmutableLinkedListNode<V>> comparator, final V... values) {
        this.valueType = valueType;
        this.comparator = comparator;
        this.startNode = this.create(values);
        this.length = this.calculateLength();
    }

    private final Comparator<ImmutableLinkedListNode<V>> createDefaultComparator() {
        return new Comparator<ImmutableLinkedListNode<V>>() {
            @Override
            public final int compare(final ImmutableLinkedListNode<V> o1, final ImmutableLinkedListNode<V> o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                } else if (o1 == null) {
                    return -1;
                } else if (o2 == null) {
                    return 1;
                }
                if (o1.equals(o2)) {
                    return 0;
                } else {
                    if(o1.hashCode() >= o2.hashCode()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        };
    }

    @SuppressWarnings("unchecked")
    private final ImmutableLinkedListNode<V> create(final V[] values) {
        return this.create(null, values, values.length - 1);
    }

    private final ImmutableLinkedListNode<V> create(final ImmutableLinkedListNode<V> startNode, final V[] values, final int currentIndex) {
        if(currentIndex >= 0 && currentIndex < values.length) {
            return this.create(new ImmutableLinkedListNode<V>(values[currentIndex], startNode), values, currentIndex - 1);
        } else {
            return startNode;
        }
    }

    private final int calculateLength() {
        return calculateLength(this.startNode, 0);
    }

    private final int calculateLength(final ImmutableLinkedListNode<V> currentNode, final int currentLength) {
        if(currentNode != null) {
            return calculateLength(currentNode.next(), currentLength + 1);
        } else {
            return currentLength;
        }
    }

    @SuppressWarnings("unchecked")
    public final ImmutableLinkedList<V> set(final ImmutableLinkedListNode<V> startNode) {
        return new ImmutableLinkedList<V>(this.valueType, this.comparator, startNode);
    }

    @SuppressWarnings("unchecked")
    public final ImmutableLinkedList<V> set(final ImmutableLinkedListNode<V> startNode, final Comparator<ImmutableLinkedListNode<V>> comparator) {
        return new ImmutableLinkedList<V>(this.valueType, comparator, startNode);
    }

    @SuppressWarnings("unchecked")
    public final ImmutableLinkedList<V> set(final V... values) {
        return new ImmutableLinkedList<V>(this.valueType, this.comparator, values);
    }

    @SuppressWarnings("unchecked")
    public final ImmutableLinkedList<V> set(final Comparator<ImmutableLinkedListNode<V>> comparator, final V... values) {
        return new ImmutableLinkedList<V>(this.valueType, comparator, values);
    }

    @SuppressWarnings("unchecked")
    public ImmutableLinkedList<V> set(Comparator<ImmutableLinkedListNode<V>> comparator) {
        return new ImmutableLinkedList<V>(this.valueType, comparator, this.startNode);
    }

    private final ImmutableLinkedListNode<V> update(final ImmutableLinkedListNode<V> currentNode, final int currentIndex, final int indexToAddAt, final ImmutableLinkedListNode<V> newNode) {
        if(currentNode != null) {
            if (currentIndex == indexToAddAt) {
                return newNode;
            } else {
                return new ImmutableLinkedListNode<V>(currentNode.value(), this.update(currentNode.next(), currentIndex + 1, indexToAddAt, newNode));
            }
        } else if(currentIndex == indexToAddAt) {
            return newNode;
        } else {
            return null;
        }
    }

    public final ImmutableLinkedList<V> prepend(final V value) {
        return this.set(new ImmutableLinkedListNode<V>(value, this.startNode));
    }

    public final ImmutableLinkedList<V> append(final V value) {
        return this.add(value, this.length);
    }

    public final ImmutableLinkedList<V> add(final V value, final int indexToAddAt) {
        if(indexToAddAt == 0) {
            return this.set(new ImmutableLinkedListNode<V>(value, this.startNode));
        } else if(indexToAddAt == this.length()) {
            return this.set(this.update(this.startNode, 0, indexToAddAt, new ImmutableLinkedListNode<V>(value, null)));
        } else {
            return this.set(this.update(this.startNode, 0, indexToAddAt, new ImmutableLinkedListNode<V>(value, this.get(indexToAddAt))));
        }
    }

    public final ImmutableLinkedList<V> remove(final int indexOfNode) {
        if (indexOfNode >= 0 && indexOfNode < this.length()) {
            if (indexOfNode == 0) {
                return this.set(this.get(1));
            } else {
                return this.set(this.update(this.startNode, 0, indexOfNode - 1, new ImmutableLinkedListNode<V>(this.get(indexOfNode - 1).value(), this.get(indexOfNode + 1))));
            }
        } else {
            return this;
        }
    }

    public final ImmutableLinkedList<V> remove(final ImmutableLinkedListNode<V> nodeToRemove) {
        return this.remove(this.indexOf(nodeToRemove));
    }

    public final ImmutableLinkedList<V> removeAll() {
        return this.set((ImmutableLinkedListNode<V>)null);
    }

    public final ImmutableLinkedListNode<V> get(final int indexOfNode) {
        if(this.length > 0 && indexOfNode >= 0 && indexOfNode < length) {
            return this.get(indexOfNode, this.startNode, 0);
        } else {
            return null;
        }
    }

    private final ImmutableLinkedListNode<V> get(final int indexOfNode, final ImmutableLinkedListNode<V> currentNode, final int currentIndex) {
        if(currentNode != null) {
            if(currentIndex == indexOfNode) {
                return currentNode;
            } else {
                return this.get(indexOfNode, currentNode.next(), currentIndex + 1);
            }
        } else {
            return null;
        }
    }

    public final ImmutableLinkedList<V> replace(final int indexOfNode, final ImmutableLinkedListNode<V> newNode) {
        return this.set(this.update(this.startNode, 0, indexOfNode, newNode));
    }

    public final ImmutableLinkedList<V> replace(final int indexOfNode, final V newValue) {
        return this.set(this.update(this.startNode, 0, indexOfNode, new ImmutableLinkedListNode<V>(newValue, this.get(indexOfNode).next())));
    }

    public final ImmutableLinkedList<V> replace(final ImmutableLinkedListNode<V> nodeToReplace, final ImmutableLinkedListNode<V> newNode) {
        return this.set(this.update(this.startNode, 0, this.indexOf(nodeToReplace), newNode));
    }

    public final ImmutableLinkedList<V> replace(final ImmutableLinkedListNode<V> nodeToReplace, final V newValue) {
        return this.set(this.update(this.startNode, 0, this.indexOf(nodeToReplace), new ImmutableLinkedListNode<V>(newValue, nodeToReplace.next())));
    }

    public final int indexOf(final ImmutableLinkedListNode<V> nodeToFindIndex) {
        return this.indexOf(nodeToFindIndex, this.startNode, 0, this.comparator);
    }

    public final int indexOf(final ImmutableLinkedListNode<V> nodeToFindIndex, final Comparator<ImmutableLinkedListNode<V>> comparator) {
        return this.indexOf(nodeToFindIndex, this.startNode, 0, comparator);
    }

    private final int indexOf(final ImmutableLinkedListNode<V> nodeToFindIndex, final ImmutableLinkedListNode<V> currentNode, final int currentIndex, final Comparator<ImmutableLinkedListNode<V>> comparator) {
        if(currentNode != null) {
            if(comparator.compare(nodeToFindIndex, currentNode) == 0) {
                return currentIndex;
            } else {
                return this.indexOf(nodeToFindIndex, currentNode.next(), currentIndex + 1, comparator);
            }
        } else {
            return -1;
        }
    }

    public final int indexOf(final V valueToFindIndex) {
        return this.indexOf(valueToFindIndex, this.startNode, 0, new Comparator<V>() {
            @Override
            public int compare(V o1, V o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                } else if (o1 == null) {
                    return -1;
                } else if (o2 == null) {
                    return 1;
                }

                if(o1.equals(o2)) {
                    return 0;
                } else {
                    if (o1.hashCode() >= o2.hashCode()) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        });
    }

    public final int indexOf(final V valueToFindIndex, final Comparator<V> comparator) {
        return this.indexOf(valueToFindIndex, this.startNode, 0, comparator);
    }

    private final int indexOf(final V valueToFindIndex, final ImmutableLinkedListNode<V> currentNode, final int currentIndex, final Comparator<V> comparator) {
        if(currentNode != null) {
            if(comparator.compare(valueToFindIndex, currentNode.value()) == 0) {
                return currentIndex;
            } else {
                return this.indexOf(valueToFindIndex, currentNode.next(), currentIndex + 1, comparator);
            }
        } else {
            return -1;
        }
    }

    public final int indexOf(final ImmutableLinkedList<V> subListToFindIndex) {
        return this.indexOf(subListToFindIndex, 0, this.startNode, 0, this.comparator);
    }

    public final int indexOf(final ImmutableLinkedList<V> subListToFindIndex, final Comparator<ImmutableLinkedListNode<V>> comparator) {
        return this.indexOf(subListToFindIndex, 0, this.startNode, 0, comparator);
    }

    private final int indexOf(final ImmutableLinkedList<V> subListToFindIndex, final int subListIndex, final ImmutableLinkedListNode<V> currentNode, final int currentIndex, final Comparator<ImmutableLinkedListNode<V>> comparator) {
        if(subListToFindIndex != null) {
            if (currentNode != null) {
                if (comparator.compare(subListToFindIndex.get(subListIndex), currentNode) == 0) {
                    return this.indexOf(subListToFindIndex, subListIndex + 1, currentNode.next(), currentIndex, comparator);
                } else {
                    return this.indexOf(subListToFindIndex, 0, this.get(currentIndex + 1), currentIndex + 1, comparator);
                }
            }
            if (subListIndex == subListToFindIndex.length) {
                return currentIndex;
            }
        }
        return -1;
    }

    public final boolean contains(final ImmutableLinkedListNode<V> nodeToFind) {
        return this.indexOf(nodeToFind) != -1;
    }

    public final boolean contains(final ImmutableLinkedListNode<V> nodeToFind, final Comparator<ImmutableLinkedListNode<V>> comparator) {
        return this.indexOf(nodeToFind, comparator) != -1;
    }

    public final boolean contains(final V valueToFind) {
        return this.indexOf(valueToFind) != -1;
    }

    public final boolean contains(final V valueToFind, final Comparator<V> comparator) {
        return this.indexOf(valueToFind, comparator) != -1;
    }

    public final boolean contains(final ImmutableLinkedList<V> subListToFind) {
        return this.indexOf(subListToFind) != -1;
    }

    public final boolean contains(final ImmutableLinkedList<V> subListToFind, final Comparator<ImmutableLinkedListNode<V>> comparator) {
        return this.indexOf(subListToFind, comparator) != -1;
    }

    public final ImmutableLinkedList<V> subList(final int startIndex, final int endIndex) {
        return this.set(this.get(startIndex)).replace(this.get(endIndex), new ImmutableLinkedListNode<V>(this.get(endIndex).value(), null));
    }

    public final ImmutableLinkedList<V> mergeStart(final ImmutableLinkedList<V> immutableLinkedListToMerge) {
        return this.merge(immutableLinkedListToMerge, 0);
    }

    public final ImmutableLinkedList<V> mergeEnd(final ImmutableLinkedList<V> immutableLinkedListToMerge) {
        return this.merge(immutableLinkedListToMerge, this.length - 1);
    }

    public final ImmutableLinkedList<V> merge(final ImmutableLinkedList<V> immutableLinkedListToMerge, final int mergeIndex) {
        if(immutableLinkedListToMerge != null && immutableLinkedListToMerge.length > 0) {
            if (mergeIndex == 0) {
                return immutableLinkedListToMerge.replace(immutableLinkedListToMerge.get(immutableLinkedListToMerge.length - 1), new ImmutableLinkedListNode<V>(immutableLinkedListToMerge.get(immutableLinkedListToMerge.length - 1).value(), this.startNode));
            } else if (mergeIndex == this.length - 1) {
                return this.replace(this.get(this.length - 1), new ImmutableLinkedListNode<V>(this.get(this.length - 1).value(), immutableLinkedListToMerge.startNode));
            } else {
                final ImmutableLinkedList<V> temp = this.replace(this.get(mergeIndex), new ImmutableLinkedListNode<V>(this.get(mergeIndex).value(), immutableLinkedListToMerge.startNode));
                return temp.replace(temp.get(temp.length - 1), new ImmutableLinkedListNode<V>(temp.get(temp.length - 1).value(), this.get(mergeIndex + 1)));
            }
        } else {
            return this;
        }
    }

    public final ImmutableLinkedListNode[] toArray() {
        return this.toArray(this, new ImmutableLinkedListNode[this.length], false, 0);
    }

    public final ImmutableLinkedListNode[] toArray(final boolean reverseOrder) {
        return this.toArray(this, new ImmutableLinkedListNode[this.length], reverseOrder, 0);
    }

    private final ImmutableLinkedListNode[] toArray(final ImmutableLinkedList<V> immutableLinkedList, final ImmutableLinkedListNode[] array, final boolean reverseOrder, final int currentIndex) {
        if(currentIndex < immutableLinkedList.length) {
            if(reverseOrder) {
                array[currentIndex] = immutableLinkedList.get(immutableLinkedList.length - 1 - currentIndex);
            } else {
                array[currentIndex] = immutableLinkedList.get(currentIndex);
            }
            immutableLinkedList.toArray(immutableLinkedList, array, reverseOrder, currentIndex + 1);
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    public final V[] values() {
        return this.values(this, (V[])Array.newInstance(this.valueType, this.length), false, 0);
    }

    @SuppressWarnings("unchecked")
    public final V[] values(final boolean reverseOrder) {
        return this.values(this, (V[])Array.newInstance(this.valueType, this.length), reverseOrder, 0);
    }

    private final V[] values(final ImmutableLinkedList<V> immutableLinkedList, final V[] array, final boolean reverseOrder, final int currentIndex) {
        if(currentIndex < immutableLinkedList.length) {
            if(reverseOrder) {
                array[currentIndex] = immutableLinkedList.get(immutableLinkedList.length - 1 - currentIndex).value();
            } else {
                array[currentIndex] = immutableLinkedList.get(currentIndex).value();
            }
            immutableLinkedList.values(immutableLinkedList, array, reverseOrder, currentIndex + 1);
        }
        return array;
    }

    @Override
    public final boolean equals(final Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof ImmutableLinkedList)) {
            return false;
        }
        if(!(this.startNode == null && ((ImmutableLinkedList)obj).startNode == null)) {
            if (this.startNode == null || ((ImmutableLinkedList)obj).startNode == null) {
                return false;
            } else {
                if ((!this.startNode.equals(((ImmutableLinkedList)obj).startNode))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public final int hashCode() {
        return this.getClass().hashCode() + (this.startNode != null ? this.startNode.hashCode() : 0);
    }

    @Override
    public final Object clone() {
        return Utils.copy(this);
    }

    @Override
    public final String toString() {
        return new StringBuffer()
                .append("{")
                .append((this.startNode != null ? this.startNode.toString() : "null"))
                .append("}")
                .toString();
    }


    public final Class valueType() {
        return this.valueType;
    }

    public final ImmutableLinkedListNode<V> startNode() {
        return this.startNode;
    }

    public final int length() {
        return this.length;
    }

    public final Comparator<ImmutableLinkedListNode<V>> comparator() {
        return this.comparator;
    }
}
package dragonfly.butterfly;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Set;


public final class ImmutableLinkedSet<V> implements Serializable, Cloneable {
    private final Class valueType;
    private final ImmutableLinkedSetNode<V> startNode;
    private final int length;
    private final Comparator<V> comparator;

    public ImmutableLinkedSet() {
        this.valueType = Object.class;
        this.startNode = null;
        this.length = 0;
        this.comparator = this.createDefaultComparator();
    }

    public ImmutableLinkedSet(final Class<V> valueType) {
        this.valueType = valueType;
        this.startNode = null;
        this.length = 0;
        this.comparator = this.createDefaultComparator();
    }

    public ImmutableLinkedSet(final Comparator<V> comparator) {
        this.valueType = Object.class;
        this.startNode = null;
        this.length = 0;
        this.comparator = comparator;
    }

    public ImmutableLinkedSet(final Class<V> valueType, final Comparator<V> comparator) {
        this.valueType = valueType;
        this.startNode = null;
        this.length = 0;
        this.comparator = comparator;
    }

    ImmutableLinkedSet(final ImmutableLinkedSetNode<V> startNode) {
        final ImmutableLinkedSet<V> tempImmutableLinkedSet = this.create(new ImmutableLinkedSet<V>(), startNode);
        this.valueType = tempImmutableLinkedSet.valueType;
        this.startNode = tempImmutableLinkedSet.startNode;
        this.length = tempImmutableLinkedSet.length;
        this.comparator = tempImmutableLinkedSet.comparator;
    }

    ImmutableLinkedSet(final Class<V> valueType, final ImmutableLinkedSetNode<V> startNode) {
        final ImmutableLinkedSet<V> tempImmutableLinkedSet = this.create(new ImmutableLinkedSet<V>(valueType), startNode);
        this.valueType = tempImmutableLinkedSet.valueType;
        this.startNode = tempImmutableLinkedSet.startNode;
        this.length = tempImmutableLinkedSet.length;
        this.comparator = tempImmutableLinkedSet.comparator;
    }

    ImmutableLinkedSet(final ImmutableLinkedSetNode<V> startNode, final Comparator<V> comparator) {
        final ImmutableLinkedSet<V> tempImmutableLinkedSet = this.create(new ImmutableLinkedSet<V>(comparator), startNode);
        this.valueType = tempImmutableLinkedSet.valueType;
        this.startNode = tempImmutableLinkedSet.startNode;
        this.length = tempImmutableLinkedSet.length;
        this.comparator = tempImmutableLinkedSet.comparator;
    }

    ImmutableLinkedSet(final Class<V> valueType, final ImmutableLinkedSetNode<V> startNode, final Comparator<V> comparator) {
        final ImmutableLinkedSet<V> tempImmutableLinkedSet = this.create(new ImmutableLinkedSet<V>(valueType, comparator), startNode);
        this.valueType = tempImmutableLinkedSet.valueType;
        this.startNode = tempImmutableLinkedSet.startNode;
        this.length = tempImmutableLinkedSet.length;
        this.comparator = tempImmutableLinkedSet.comparator;
    }

    public ImmutableLinkedSet(final V... values) {
        final ImmutableLinkedSet<V> tempImmutableLinkedSet = this.create(new ImmutableLinkedSet<V>(), values, 0);
        this.valueType = tempImmutableLinkedSet.valueType;
        this.startNode = tempImmutableLinkedSet.startNode;
        this.length = tempImmutableLinkedSet.length;
        this.comparator = tempImmutableLinkedSet.comparator;
    }

    public ImmutableLinkedSet(final Class<V> valueType, final V... values) {
        final ImmutableLinkedSet<V> tempImmutableLinkedSet = this.create(new ImmutableLinkedSet<V>(valueType), values, 0);
        this.valueType = tempImmutableLinkedSet.valueType;
        this.startNode = tempImmutableLinkedSet.startNode;
        this.length = tempImmutableLinkedSet.length;
        this.comparator = tempImmutableLinkedSet.comparator;
    }

    public ImmutableLinkedSet(final Comparator<V> comparator, final V... values) {
        final ImmutableLinkedSet<V> tempImmutableLinkedSet = this.create(new ImmutableLinkedSet<V>(comparator), values, 0);
        this.valueType = tempImmutableLinkedSet.valueType;
        this.startNode = tempImmutableLinkedSet.startNode;
        this.length = tempImmutableLinkedSet.length;
        this.comparator = tempImmutableLinkedSet.comparator;
    }

    public ImmutableLinkedSet(final Class<V> valueType, final Comparator<V> comparator, final V... values) {
        final ImmutableLinkedSet<V> tempImmutableLinkedSet = this.create(new ImmutableLinkedSet<V>(valueType, comparator), values, 0);
        this.valueType = tempImmutableLinkedSet.valueType;
        this.startNode = tempImmutableLinkedSet.startNode;
        this.length = tempImmutableLinkedSet.length;
        this.comparator = tempImmutableLinkedSet.comparator;
    }

    @SuppressWarnings("unchecked")
    public ImmutableLinkedSet(final Set<V> values) {
        final ImmutableLinkedSet<V> tempImmutableLinkedSet = this.create(new ImmutableLinkedSet<V>(), (V[])values.toArray(), 0);
        this.valueType = tempImmutableLinkedSet.valueType;
        this.startNode = tempImmutableLinkedSet.startNode;
        this.length = tempImmutableLinkedSet.length;
        this.comparator = tempImmutableLinkedSet.comparator;
    }

    @SuppressWarnings("unchecked")
    public ImmutableLinkedSet(final Class<V> valueType, final Set<V> values) {
        final ImmutableLinkedSet<V> tempImmutableLinkedSet = this.create(new ImmutableLinkedSet<V>(valueType), (V[])values.toArray(), 0);
        this.valueType = tempImmutableLinkedSet.valueType;
        this.startNode = tempImmutableLinkedSet.startNode;
        this.length = tempImmutableLinkedSet.length;
        this.comparator = tempImmutableLinkedSet.comparator;
    }

    @SuppressWarnings("unchecked")
    public ImmutableLinkedSet(final Set<V> values, final Comparator<V> comparator) {
        final ImmutableLinkedSet<V> tempImmutableLinkedSet = this.create(new ImmutableLinkedSet<V>(comparator), (V[])values.toArray(), 0);
        this.valueType = tempImmutableLinkedSet.valueType;
        this.startNode = tempImmutableLinkedSet.startNode;
        this.length = tempImmutableLinkedSet.length;
        this.comparator = tempImmutableLinkedSet.comparator;
    }

    @SuppressWarnings("unchecked")
    public ImmutableLinkedSet(final Class<V> valueType, final Set<V> values, final Comparator<V> comparator) {
        final ImmutableLinkedSet<V> tempImmutableLinkedSet = this.create(new ImmutableLinkedSet<V>(valueType, comparator), (V[])values.toArray(), 0);
        this.valueType = tempImmutableLinkedSet.valueType;
        this.startNode = tempImmutableLinkedSet.startNode;
        this.length = tempImmutableLinkedSet.length;
        this.comparator = tempImmutableLinkedSet.comparator;
    }

    private ImmutableLinkedSet(final boolean createOnlyConstructor, final Class<V> valueType, final ImmutableLinkedSetNode<V> startNode, final Comparator<V> comparator) {
        this.valueType = valueType;
        this.startNode = startNode;
        this.length = this.calculateLength(this.startNode);
        this.comparator = comparator;
    }

    private final Comparator<V> createDefaultComparator() {
        return new Comparator<V>() {
            @Override
            public final int compare(final V o1, final V o2) {
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
    private final ImmutableLinkedSet<V> create(final ImmutableLinkedSet<V> immutableLinkedSet, final V[] values, final int currentIndex) {
        if(currentIndex >= 0 && currentIndex < values.length) {
            return this.create(immutableLinkedSet.add(values[currentIndex]), values, currentIndex + 1);
        } else {
            return immutableLinkedSet;
        }
    }

    @SuppressWarnings("unchecked")
    private final ImmutableLinkedSet<V> create(final ImmutableLinkedSet<V> immutableLinkedSet, final ImmutableLinkedSetNode<V> startNode) {
        return this.create(immutableLinkedSet, new ImmutableLinkedSet<V>(true, immutableLinkedSet.valueType, startNode, immutableLinkedSet.comparator), this.calculateLength(startNode) - 1);
    }

    @SuppressWarnings("unchecked")
    private final ImmutableLinkedSet<V> create(final ImmutableLinkedSet<V> filteredImmutableLinkedSet, final ImmutableLinkedSet<V> rawImmutableLinkedSet, final int currentIndex) {
        if(currentIndex >= 0 && currentIndex < rawImmutableLinkedSet.length) {
            ImmutableLinkedSetNode<V> existingNodeWithValue = filteredImmutableLinkedSet.get(rawImmutableLinkedSet.get(currentIndex).value());
            if(existingNodeWithValue == null) {
                return this.create(new ImmutableLinkedSet<V>(true, filteredImmutableLinkedSet.valueType, new ImmutableLinkedSetNode<V>(rawImmutableLinkedSet.get(currentIndex).value(),
                        filteredImmutableLinkedSet.startNode), filteredImmutableLinkedSet.comparator), rawImmutableLinkedSet, currentIndex - 1);
            }
            else {
                return this.create(filteredImmutableLinkedSet, rawImmutableLinkedSet, currentIndex - 1);
            }
        } else {
            return filteredImmutableLinkedSet;
        }
    }

    private final int calculateLength(final ImmutableLinkedSetNode<V> startNode) {
        if(startNode != null) {
            return calculateLength(startNode.next(), 1);
        } else {
            return 0;
        }
    }

    private final int calculateLength(final ImmutableLinkedSetNode<V> currentNode, final int currentLength) {
        if(currentNode != null) {
            return calculateLength(currentNode.next(), currentLength + 1);
        } else {
            return currentLength;
        }
    }

    @SuppressWarnings("unchecked")
    public final ImmutableLinkedSet<V> set(final ImmutableLinkedSetNode<V> startNode) {
        return new ImmutableLinkedSet<V>(this.valueType, startNode, this.comparator);
    }

    @SuppressWarnings("unchecked")
    public final ImmutableLinkedSet<V> set(final ImmutableLinkedSetNode<V> startNode, final Comparator<V> comparator) {
        return new ImmutableLinkedSet<V>(this.valueType, startNode, comparator);
    }

    @SuppressWarnings("unchecked")
    public final ImmutableLinkedSet<V> set(final Set<V> values) {
        return new ImmutableLinkedSet<V>(this.valueType, values, this.comparator);
    }

    @SuppressWarnings("unchecked")
    public final ImmutableLinkedSet<V> set(final Set<V> values, final Comparator<V> comparator) {
        return new ImmutableLinkedSet<V>(this.valueType, values, comparator);
    }

    public ImmutableLinkedSet<V> set(Comparator<V> comparator) {
        return this.set(this.startNode, comparator);
    }

    private final ImmutableLinkedSetNode<V> update(final ImmutableLinkedSetNode<V> currentNode, final ImmutableLinkedSetNode<V> nodeToReplace, final ImmutableLinkedSetNode<V> newNode) {
        if(currentNode != null) {
            if (nodeToReplace != null && this.comparator.compare(currentNode.value(), nodeToReplace.value()) == 0) {
                if (newNode.next() == null) {
                    return new ImmutableLinkedSetNode<V>(newNode.value(), null);
                } else {
                    return new ImmutableLinkedSetNode<V>(newNode.value(), this.update(newNode.next(), null, null));
                }
            } else {
                if (currentNode.next() == null) {
                    return new ImmutableLinkedSetNode<V>(currentNode.value(), null);
                } else {
                    return new ImmutableLinkedSetNode<V>(currentNode.value(), this.update(currentNode.next(), nodeToReplace, newNode));
                }
            }
        } else {
            return null;
        }
    }

    public final ImmutableLinkedSet<V> add(final V value) {
        return this.set(new ImmutableLinkedSetNode<V>(value, this.startNode));
    }

    public final ImmutableLinkedSet<V> remove(final int indexOfNode) {
        if(this.startNode != null && this.comparator.compare(this.startNode.value(), this.get(indexOfNode).value()) == 0) {
            return this.set(this.startNode.next());
        } else {
            return this.remove(this.get(indexOfNode).value(), this.startNode, null, null, null);
        }
    }

    public final ImmutableLinkedSet<V> remove(final ImmutableLinkedSetNode<V> nodeToRemove) {
        if(this.startNode != null && this.comparator.compare(this.startNode.value(), nodeToRemove.value()) == 0) {
            return this.set(this.startNode.next());
        } else {
            return this.remove(nodeToRemove.value(), this.startNode, null, null, null);
        }
    }

    public final ImmutableLinkedSet<V> remove(final V valueToRemove) {
        if(this.startNode != null && this.comparator.compare(this.startNode.value(), valueToRemove) == 0) {
            return this.set(this.startNode.next());
        } else {
            return this.remove(valueToRemove, this.startNode, null, null, null);
        }
    }

    private final ImmutableLinkedSet<V> remove (final V valueToRemove, final ImmutableLinkedSetNode<V> currentNode, final Integer nextNodeCount, final ImmutableLinkedSetNode<V> previousNode, final ImmutableLinkedSetNode<V> nextNode) {
        if(currentNode != null) {
            if(currentNode.next() != null && this.comparator.compare(currentNode.next().value(), valueToRemove) == 0) {
                return this.remove(valueToRemove, currentNode.next(), 2, currentNode, null);
            } else {
                if (nextNodeCount != null) {
                    if (nextNodeCount - 1 == 0) {
                        return this.remove(valueToRemove, currentNode.next(), nextNodeCount - 1, previousNode, currentNode);
                    } else if(nextNodeCount - 1 < 0) {
                        return this.set(this.update(this.startNode, previousNode, new ImmutableLinkedSetNode<V>(previousNode.value(), nextNode)));
                    } else {
                        return this.remove(valueToRemove, currentNode.next(), nextNodeCount - 1, previousNode, nextNode);
                    }
                } else {
                    return this.remove(valueToRemove, currentNode.next(), nextNodeCount, previousNode, nextNode);
                }
            }
        } else {
            if (previousNode != null) {
                return this.set(this.update(this.startNode, previousNode, new ImmutableLinkedSetNode<V>(previousNode.value(), nextNode)));
            } else {
                return this;
            }
        }
    }

    public final ImmutableLinkedSet<V> removeAll() {
        return this.set((ImmutableLinkedSetNode<V>)null);
    }

    public final ImmutableLinkedSetNode<V> get(final int indexOfNode) {
        if(this.length > 0 && indexOfNode >= 0 && indexOfNode < length) {
            return this.get(indexOfNode, this.startNode, 0);
        } else {
            return null;
        }
    }

    private final ImmutableLinkedSetNode<V> get(final int indexOfNode, ImmutableLinkedSetNode<V> currentNode, final int currentIndex) {
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

    public final ImmutableLinkedSetNode<V> get(final V valueOfNode) {
        return this.get(valueOfNode, this.startNode);
    }

    private final ImmutableLinkedSetNode<V> get(final V valueOfNode, final ImmutableLinkedSetNode<V> currentNode) {
        if(currentNode != null) {
            if(this.comparator.compare(currentNode.value(), valueOfNode) == 0) {
                return currentNode;
            } else {
                return this.get(valueOfNode, currentNode.next());
            }
        } else {
            return null;
        }
    }

    public final ImmutableLinkedSet<V> replace(final int indexOfNode, final ImmutableLinkedSetNode<V> newNode) {
        return this.set(this.update(this.startNode, this.get(indexOfNode), newNode));
    }

    public final ImmutableLinkedSet<V> replace(final int indexOfNode, final V newValue) {
        return this.set(this.update(this.startNode, this.get(indexOfNode), new ImmutableLinkedSetNode<V>(newValue, this.get(indexOfNode).next())));
    }

    public final ImmutableLinkedSet<V> replace(final ImmutableLinkedSetNode<V> nodeToReplace, final ImmutableLinkedSetNode<V> newNode) {
        return this.set(this.update(this.startNode, nodeToReplace, newNode));
    }

    public final ImmutableLinkedSet<V> replace(final ImmutableLinkedSetNode<V> nodeToReplace, final V newValue) {
        return this.set(this.update(this.startNode, nodeToReplace, new ImmutableLinkedSetNode<V>(newValue, nodeToReplace.next())));
    }

    public final ImmutableLinkedSet<V> replace(final V valueOfNode, final V newValue) {
        return this.set(this.update(this.startNode, this.get(valueOfNode), new ImmutableLinkedSetNode<V>(newValue, this.get(valueOfNode).next())));
    }

    public final int indexOf(final ImmutableLinkedSetNode<V> nodeToFindIndex) {
        return this.indexOf(nodeToFindIndex.value(), this.startNode, 0, this.comparator);
    }

    public final int indexOf(final ImmutableLinkedSetNode<V> nodeToFindIndex, final Comparator<V> comparator) {
        return this.indexOf(nodeToFindIndex.value(), this.startNode, 0, comparator);
    }

    public final int indexOf(final V valueToFindIndex) {
        return this.indexOf(valueToFindIndex, this.startNode, 0, this.comparator);
    }

    public final int indexOf(final V valueToFindIndex, final Comparator<V> comparator) {
        return this.indexOf(valueToFindIndex, this.startNode, 0, comparator);
    }

    private final int indexOf(final V valueToFindIndex, final ImmutableLinkedSetNode<V> currentNode, final int currentIndex, final Comparator<V> comparator) {
        if(currentNode != null) {
            if (comparator.compare(currentNode.value(), valueToFindIndex) == 0) {
                return currentIndex;
            } else {
                return this.indexOf(valueToFindIndex, currentNode.next(), currentIndex + 1, comparator);
            }
        } else {
            return -1;
        }
    }

    public final int indexOf(final ImmutableLinkedSet<V> subsetToFindIndex) {
        return this.indexOf(subsetToFindIndex, 0, this.startNode, 0, this.comparator);
    }

    public final int indexOf(final ImmutableLinkedSet<V> subsetToFindIndex, final Comparator<V> comparator) {
        return this.indexOf(subsetToFindIndex, 0, this.startNode, 0, comparator);
    }

    private final int indexOf(final ImmutableLinkedSet<V> subsetToFindIndex, final int subsetIndex, final ImmutableLinkedSetNode<V> currentNode, final int currentIndex, final Comparator<V> comparator) {
        if(subsetToFindIndex != null) {
            if (currentNode != null) {
                if (comparator.compare(subsetToFindIndex.get(subsetIndex).value(), currentNode.value()) == 0) {
                    return this.indexOf(subsetToFindIndex, subsetIndex + 1, currentNode.next(), currentIndex, comparator);
                } else {
                    return this.indexOf(subsetToFindIndex, 0, this.get(currentIndex + 1), currentIndex + 1, comparator);
                }
            }
            if (subsetIndex == subsetToFindIndex.length) {
                return currentIndex;
            }
        }
        return -1;
    }

    public final boolean contains(final ImmutableLinkedSetNode<V> nodeToFind) {
        return this.indexOf(nodeToFind) != -1;
    }

    public final boolean contains(final ImmutableLinkedSetNode<V> nodeToFind, final Comparator<V> comparator) {
        return this.indexOf(nodeToFind, comparator) != -1;
    }

    public final boolean contains(final V valueToFind) {
        return this.indexOf(valueToFind) != -1;
    }

    public final boolean contains(final V valueToFind, final Comparator<V> comparator) {
        return this.indexOf(valueToFind, comparator) != -1;
    }

    public final boolean contains(final ImmutableLinkedSet<V> subsetToFind) {
        return this.indexOf(subsetToFind) != -1;
    }

    public final boolean contains(final ImmutableLinkedSet<V> subsetToFind, final Comparator<V> comparator) {
        return this.indexOf(subsetToFind, comparator) != -1;
    }

    public final ImmutableLinkedSet<V> subset(final int startIndex, final int endIndex) {
        return this.set(this.get(startIndex)).replace(this.get(endIndex), new ImmutableLinkedSetNode<V>(this.get(endIndex).value(), null));
    }

    public final ImmutableLinkedSet<V> mergeStart(final ImmutableLinkedSet<V> immutableLinkedListToMerge) {
        return this.merge(immutableLinkedListToMerge, 0);
    }

    public final ImmutableLinkedSet<V> mergeEnd(final ImmutableLinkedSet<V> immutableLinkedListToMerge) {
        return this.merge(immutableLinkedListToMerge, this.length - 1);
    }

    public final ImmutableLinkedSet<V> merge(final ImmutableLinkedSet<V> immutableLinkedSetToMerge, final int mergeIndex) {
        if(immutableLinkedSetToMerge != null && immutableLinkedSetToMerge.length > 0) {
            if (mergeIndex == 0) {
                return immutableLinkedSetToMerge.replace(immutableLinkedSetToMerge.get(immutableLinkedSetToMerge.length - 1), new ImmutableLinkedSetNode<V>(immutableLinkedSetToMerge.get(immutableLinkedSetToMerge.length - 1).value(), this.startNode));
            } else if (mergeIndex == this.length - 1) {
                return this.replace(this.get(this.length - 1), new ImmutableLinkedSetNode<V>(this.get(this.length - 1).value(), this.update(immutableLinkedSetToMerge.startNode, null, null)));
            } else {
                final ImmutableLinkedSet<V> temp = this.replace(this.get(mergeIndex), new ImmutableLinkedSetNode<V>(this.get(mergeIndex).value(), immutableLinkedSetToMerge.startNode));
                return temp.replace(temp.get(temp.length - 1), new ImmutableLinkedSetNode<V>(temp.get(temp.length - 1).value(), this.get(mergeIndex + 1)));
            }
        } else {
            return this;
        }
    }

    public final ImmutableLinkedSetNode[] toArray() {
        return this.toArray(this, new ImmutableLinkedSetNode[this.length], false, 0);
    }

    public final ImmutableLinkedSetNode[] toArray(final boolean reverseOrder) {
        return this.toArray(this, new ImmutableLinkedSetNode[this.length], reverseOrder, 0);
    }

    private final ImmutableLinkedSetNode[] toArray(final ImmutableLinkedSet<V> immutableLinkedSet, final ImmutableLinkedSetNode[] array, final boolean reverseOrder, final int currentIndex) {
        if(currentIndex < immutableLinkedSet.length) {
            if(reverseOrder) {
                array[currentIndex] = immutableLinkedSet.get(immutableLinkedSet.length - 1 - currentIndex);
            } else {
                array[currentIndex] = immutableLinkedSet.get(currentIndex);
            }
            immutableLinkedSet.toArray(immutableLinkedSet, array, reverseOrder, currentIndex + 1);
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    public final V[] getValues() {
        return this.getValues(this, (V[])Array.newInstance(this.valueType, this.length), false, 0);
    }

    @SuppressWarnings("unchecked")
    public final V[] getValues(final boolean reverseOrder) {
        return this.getValues(this, (V[])Array.newInstance(this.valueType, this.length), reverseOrder, 0);
    }

    private final V[] getValues(final ImmutableLinkedSet<V> immutableLinkedSet, final V[] array, final boolean reverseOrder, final int currentIndex) {
        if(currentIndex < immutableLinkedSet.length) {
            if(reverseOrder) {
                array[currentIndex] = immutableLinkedSet.get(immutableLinkedSet.length - 1 - currentIndex).value();
            } else {
                array[currentIndex] = immutableLinkedSet.get(currentIndex).value();
            }
            immutableLinkedSet.getValues(immutableLinkedSet, array, reverseOrder, currentIndex + 1);
        }
        return array;
    }

    @Override
    public final boolean equals(final Object obj) {
        if(obj != null && obj instanceof ImmutableLinkedSet) {
            if(!(this.startNode == null && ((ImmutableLinkedSet)obj).startNode == null)) {
                if (this.startNode == null || ((ImmutableLinkedSet) obj).startNode == null) {
                    return false;
                } else {
                    if ((!this.startNode.equals(((ImmutableLinkedSet) obj).startNode))) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
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

    public final ImmutableLinkedSetNode<V> startNode() {
        return this.startNode;
    }

    public final int length() {
        return this.length;
    }

    public final Comparator<V> comparator() {
        return this.comparator;
    }
}
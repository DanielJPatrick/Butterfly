package dragonfly.butterfly;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Map;


public final class ImmutableLinkedMap<K, V> implements Serializable, Cloneable {
    private final Class keyType;
    private final Class valueType;
    private final Comparator<K> comparator;
    private final ImmutableLinkedMapNode<K, V> startNode;
    private final int length;

    public ImmutableLinkedMap() {
        this.keyType = Object.class;
        this.valueType = Object.class;
        this.comparator = this.createDefaultComparator();
        this.startNode = null;
        this.length = 0;
    }

    public ImmutableLinkedMap(final Class<K> keyType, final Class<V> valueType) {
        this.keyType = keyType;
        this.valueType = valueType;
        this.comparator = this.createDefaultComparator();
        this.startNode = null;
        this.length = 0;
    }

    public ImmutableLinkedMap(final Comparator<K> comparator) {
        this.keyType = Object.class;
        this.valueType = Object.class;
        this.comparator = comparator;
        this.startNode = null;
        this.length = 0;
    }

    public ImmutableLinkedMap(final Class<K> keyType, final Class<V> valueType, final Comparator<K> comparator) {
        this.keyType = keyType;
        this.valueType = valueType;
        this.comparator = comparator;
        this.startNode = null;
        this.length = 0;
    }

    ImmutableLinkedMap(final ImmutableLinkedMapNode<K, V> startNode) {
        this.keyType = Object.class;
        this.valueType = Object.class;
        this.comparator = this.createDefaultComparator();
        this.startNode = this.verify(startNode);
        this.length = this.calculateLength();
    }

    ImmutableLinkedMap(final Class<K> keyType, final Class<V> valueType, final ImmutableLinkedMapNode<K, V> startNode) {
        this.keyType = keyType;
        this.valueType = valueType;
        this.comparator = this.createDefaultComparator();
        this.startNode = this.verify(startNode);
        this.length = this.calculateLength();
    }

    ImmutableLinkedMap(final Comparator<K> comparator, final ImmutableLinkedMapNode<K, V> startNode) {
        this.keyType = Object.class;
        this.valueType = Object.class;
        this.comparator = comparator;
        this.startNode = this.verify(startNode);
        this.length = this.calculateLength();
    }

    ImmutableLinkedMap(final Class<K> keyType, final Class<V> valueType, final Comparator<K> comparator, final ImmutableLinkedMapNode<K, V> startNode) {
        this.keyType = keyType;
        this.valueType = valueType;
        this.comparator = comparator;
        this.startNode = this.verify(startNode);
        this.length = this.calculateLength();
    }

     /*

    public ImmutableLinkedMap(final Map<K, V> entries) {
        final ImmutableLinkedMap<K, V> tempImmutableLinkedMap = this.create(new ImmutableLinkedMap<K, V>(), entries, 0);
        this.keyType = tempImmutableLinkedMap.keyType;
        this.valueType = tempImmutableLinkedMap.valueType;
        this.startNode = tempImmutableLinkedMap.startNode;
        this.length = tempImmutableLinkedMap.length;
        this.comparator = tempImmutableLinkedMap.comparator;
    }

    public ImmutableLinkedMap(final Class<K> keyType, final Class<V> valueType, final Map<K, V> entries) {
        final ImmutableLinkedMap<K, V> tempImmutableLinkedMap = this.create(new ImmutableLinkedMap<K, V>(keyType, valueType), entries, 0);
        this.keyType = tempImmutableLinkedMap.keyType;
        this.valueType = tempImmutableLinkedMap.valueType;
        this.startNode = tempImmutableLinkedMap.startNode;
        this.length = tempImmutableLinkedMap.length;
        this.comparator = tempImmutableLinkedMap.comparator;
    }

    public ImmutableLinkedMap(final Map<K, V> entries, final Comparator<K> comparator) {
        final ImmutableLinkedMap<K, V> tempImmutableLinkedMap = this.create(new ImmutableLinkedMap<K, V>(comparator), entries, 0);
        this.keyType = tempImmutableLinkedMap.keyType;
        this.valueType = tempImmutableLinkedMap.valueType;
        this.startNode = tempImmutableLinkedMap.startNode;
        this.length = tempImmutableLinkedMap.length;
        this.comparator = tempImmutableLinkedMap.comparator;
    }

    public ImmutableLinkedMap(final Class<K> keyType, final Class<V> valueType, final Map<K, V> entries, final Comparator<K> comparator) {
        final ImmutableLinkedMap<K, V> tempImmutableLinkedMap = this.create(new ImmutableLinkedMap<K, V>(keyType, valueType, comparator), entries, 0);
        this.keyType = tempImmutableLinkedMap.keyType;
        this.valueType = tempImmutableLinkedMap.valueType;
        this.startNode = tempImmutableLinkedMap.startNode;
        this.length = tempImmutableLinkedMap.length;
        this.comparator = tempImmutableLinkedMap.comparator;
    }

*/

    private final Comparator<K> createDefaultComparator() {
        return new Comparator<K>() {
            @Override
            public final int compare(final K o1, final K o2) {
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


    //TODO: create linked nodes first then create map from startNode for efficiency
    @SuppressWarnings("unchecked")
    private final ImmutableLinkedMap<K, V> create(final ImmutableLinkedMap<K, V> immutableLinkedMap, final Map<K, V> entries, final int currentIndex) {
        return this.create(immutableLinkedMap, entries.entrySet().toArray(new Map.Entry[entries.size()]), currentIndex);
    }

    private final ImmutableLinkedMap<K, V> create(final ImmutableLinkedMap<K, V> immutableLinkedMap, final Map.Entry<K, V>[] entries, final int currentIndex) {
        if(currentIndex >= 0 && currentIndex < entries.length) {
            return this.create(immutableLinkedMap.prepend(entries[currentIndex].getKey(), entries[currentIndex].getValue()), entries, currentIndex + 1);
        } else {
            return immutableLinkedMap;
        }
    }

    private final ImmutableLinkedMapNode<K, V> verify(final ImmutableLinkedMapNode<K, V> startNode) {
        return this.verify(startNode, startNode);
    }

    private final ImmutableLinkedMapNode<K, V> verify(final ImmutableLinkedMapNode<K, V> startNode, final ImmutableLinkedMapNode<K, V> currentNode) {
        if(currentNode != null) {
            if (this.get(currentNode.key(), currentNode.next()) == null) {
                return verify(startNode, currentNode.next());
            } else {
                final int length = this.calculateLength(startNode, 0);
                return removeDuplicateKeys(startNode, length, length - 1, null);
            }
        } else {
            return startNode;
        }
    }

    private final ImmutableLinkedMapNode<K, V> removeDuplicateKeys(final ImmutableLinkedMapNode<K, V> originalStartNode, final int originalLength, final int originalCurrentIndex, final ImmutableLinkedMapNode<K, V> modifiedStartNode) {
        if(originalCurrentIndex >= 0 && originalCurrentIndex < originalLength) {
            final ImmutableLinkedMapNode<K, V> originalCurrentNode = this.get(originalCurrentIndex, originalStartNode, 0);
            final ImmutableLinkedMapNode<K, V> previousNodeWithKey = this.get(originalCurrentNode.key(), modifiedStartNode);
            if(previousNodeWithKey == null) {
                return this.removeDuplicateKeys(originalStartNode, originalLength, originalCurrentIndex - 1, new ImmutableLinkedMapNode<K, V>(originalCurrentNode.key(), originalCurrentNode.value(), modifiedStartNode));
            } else {
                return this.removeDuplicateKeys(originalStartNode, originalLength, originalCurrentIndex - 1, modifiedStartNode);
            }
        } else {
            return modifiedStartNode;
        }
    }

    private final int calculateLength() {
        return calculateLength(this.startNode, 0);
    }

    private final int calculateLength(final ImmutableLinkedMapNode<K, V> currentNode, final int currentLength) {
        if(currentNode != null) {
            return calculateLength(currentNode.next(), currentLength + 1);
        } else {
            return currentLength;
        }
    }

    @SuppressWarnings("unchecked")
    public final ImmutableLinkedMap<K, V> set(final ImmutableLinkedMapNode<K, V> startNode) {
        return new ImmutableLinkedMap<K, V>(this.keyType, this.valueType, this.comparator, startNode);
    }

    @SuppressWarnings("unchecked")
    public final ImmutableLinkedMap<K, V> set(final ImmutableLinkedMapNode<K, V> startNode, final Comparator<K> comparator) {
        return new ImmutableLinkedMap<K, V>(this.keyType, this.valueType, comparator, startNode);
    }

    /*
    @SuppressWarnings("unchecked")
    public final ImmutableLinkedMap<K, V> set(final Map<K, V> entries) {
        return new ImmutableLinkedMap<K, V>(this.keyType, this.valueType, entries, this.comparator);
    }

    @SuppressWarnings("unchecked")
    public final ImmutableLinkedMap<K, V> set(final Map<K, V> entries, final Comparator<K> comparator) {
        return new ImmutableLinkedMap<K, V>(this.keyType, this.valueType, entries, comparator);
    }
*/

    @SuppressWarnings("unchecked")
    public ImmutableLinkedMap<K, V> set(Comparator<K> comparator) {
        return new ImmutableLinkedMap<K, V>(this.keyType, this.valueType, comparator, this.startNode);
    }

    private final ImmutableLinkedMapNode<K, V> update(final ImmutableLinkedMapNode<K, V> currentNode, final int currentIndex, final int indexToAddAt, final ImmutableLinkedMapNode<K, V> newNode) {
        if(currentNode != null) {
            if (currentIndex == indexToAddAt) {
                return newNode;
            } else {
                return new ImmutableLinkedMapNode<K, V>(currentNode.key(), currentNode.value(), this.update(currentNode.next(), currentIndex + 1, indexToAddAt, newNode));
            }
        } else if(currentIndex == indexToAddAt) {
            return newNode;
        } else {
            return null;
        }
    }

    public final ImmutableLinkedMap<K, V> prepend(final K key, final V value) {
        return this.set(new ImmutableLinkedMapNode<K, V>(key, value, this.startNode));
    }

    public final ImmutableLinkedMap<K, V> append(final K key, final V value) {
        return this.add(key, value, this.length);
    }

    public final ImmutableLinkedMap<K, V> add(final K key, final V value, final int indexToAddAt) {
        if(indexToAddAt == 0) {
            return this.set(new ImmutableLinkedMapNode<K, V>(key, value, this.startNode));
        } else if(indexToAddAt == this.length()) {
            return this.set(this.update(this.startNode, 0, indexToAddAt, new ImmutableLinkedMapNode<K, V>(key, value, null)));
        } else {
            return this.set(this.update(this.startNode, 0, indexToAddAt, new ImmutableLinkedMapNode<K, V>(key, value, this.get(indexToAddAt))));
        }
    }

    public final ImmutableLinkedMap<K, V> remove(final int indexOfNode) {
        if (indexOfNode >= 0 && indexOfNode < this.length()) {
            if (indexOfNode == 0) {
                return this.set(this.get(1));
            } else {
                return this.set(this.update(this.startNode, 0, indexOfNode - 1, new ImmutableLinkedMapNode<K, V>(this.get(indexOfNode - 1).key(), this.get(indexOfNode - 1).value(), this.get(indexOfNode + 1))));
            }
        } else {
            return this;
        }
    }

    public final ImmutableLinkedMap<K, V> remove(final ImmutableLinkedMapNode<K, V> nodeToRemove) {
        return this.remove(this.indexOf(nodeToRemove));
    }

    public final ImmutableLinkedMap<K, V> remove(final K keyToRemove) {
        return this.remove(this.indexOf(keyToRemove));
    }

    public final ImmutableLinkedMap<K, V> removeAll() {
        return this.set((ImmutableLinkedMapNode<K, V>)null);
    }

    public final ImmutableLinkedMapNode<K, V> get(final int indexOfNode) {
        if(this.length > 0 && indexOfNode >= 0 && indexOfNode < length) {
            return this.get(indexOfNode, this.startNode, 0);
        } else {
            return null;
        }
    }

    private final ImmutableLinkedMapNode<K, V> get(final int indexOfNode, ImmutableLinkedMapNode<K, V> currentNode, final int currentIndex) {
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

    public final ImmutableLinkedMapNode<K, V> get(final K keyOfNode) {
        return this.get(keyOfNode, this.startNode);
    }

    private final ImmutableLinkedMapNode<K, V> get(final K keyOfNode, final ImmutableLinkedMapNode<K, V> currentNode) {
        if(currentNode != null) {
            if(this.comparator.compare(currentNode.key(), keyOfNode) == 0) {
                return currentNode;
            } else {
                return this.get(keyOfNode, currentNode.next());
            }
        } else {
            return null;
        }
    }

    public final ImmutableLinkedMap<K, V> replace(final int indexOfNode, final ImmutableLinkedMapNode<K, V> newNode) {
        return this.set(this.update(this.startNode, 0, indexOfNode, newNode));
    }

    public final ImmutableLinkedMap<K, V> replace(final int indexOfNode, final V newValue) {
        return this.set(this.update(this.startNode, 0, indexOfNode, new ImmutableLinkedMapNode<K, V>(this.get(indexOfNode).key(), newValue, this.get(indexOfNode).next())));
    }

    public final ImmutableLinkedMap<K, V> replace(final ImmutableLinkedMapNode<K, V> nodeToReplace, final ImmutableLinkedMapNode<K, V> newNode) {
        return this.set(this.update(this.startNode, 0, this.indexOf(nodeToReplace), newNode));
    }

    public final ImmutableLinkedMap<K, V> replace(final ImmutableLinkedMapNode<K, V> nodeToReplace, final V newValue) {
        return this.set(this.update(this.startNode, 0, this.indexOf(nodeToReplace), new ImmutableLinkedMapNode<K, V>(nodeToReplace.key(), newValue, nodeToReplace.next())));
    }

    public final ImmutableLinkedMap<K, V> replace(final K keyOfNode, final V newValue) {
        return this.set(this.update(this.startNode, 0, this.indexOf(keyOfNode), new ImmutableLinkedMapNode<K, V>(this.get(keyOfNode).key(), newValue, this.get(keyOfNode).next())));
    }

    public final int indexOf(final ImmutableLinkedMapNode<K, V> nodeToFindIndex) {
        return this.indexOf(nodeToFindIndex.key(), this.startNode, 0, this.comparator);
    }

    public final int indexOf(final ImmutableLinkedMapNode<K, V> nodeToFindIndex, final Comparator<K> comparator) {
        return this.indexOf(nodeToFindIndex.key(), this.startNode, 0, comparator);
    }

    public final int indexOf(final K keyToFindIndex) {
        return this.indexOf(keyToFindIndex, this.startNode, 0, this.comparator);
    }

    public final int indexOf(final K keyToFindIndex, final Comparator<K> comparator) {
        return this.indexOf(keyToFindIndex, this.startNode, 0, comparator);
    }

    private final int indexOf(final K keyToFindIndex, final ImmutableLinkedMapNode<K, V> currentNode, final int currentIndex, final Comparator<K> comparator) {
        if(currentNode != null) {
            if (comparator.compare(currentNode.key(), keyToFindIndex) == 0) {
                return currentIndex;
            } else {
                return this.indexOf(keyToFindIndex, currentNode.next(), currentIndex + 1, comparator);
            }
        } else {
            return -1;
        }
    }

    public final int indexOfValue(final V valueToFindIndex) {
        return this.indexOfValue(valueToFindIndex, this.startNode, 0, new Comparator<V>() {
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

    public final int indexOfValue(final V valueToFindIndex, final Comparator<V> comparator) {
        return this.indexOfValue(valueToFindIndex, this.startNode, 0, comparator);
    }

    private final int indexOfValue(final V valueToFindIndex, final ImmutableLinkedMapNode<K, V> currentNode, final int currentIndex, final Comparator<V> comparator) {
        if(currentNode != null) {
            if(comparator.compare(valueToFindIndex, currentNode.value()) == 0) {
                return currentIndex;
            } else {
                return this.indexOfValue(valueToFindIndex, currentNode.next(), currentIndex + 1, comparator);
            }
        } else {
            return -1;
        }
    }

    public final int indexOf(final ImmutableLinkedMap<K, V> subMapToFindIndex) {
        return this.indexOf(subMapToFindIndex, 0, this.startNode, 0, this.comparator);
    }

    public final int indexOf(final ImmutableLinkedMap<K, V> subMapToFindIndex, final Comparator<K> comparator) {
        return this.indexOf(subMapToFindIndex, 0, this.startNode, 0, comparator);
    }

    private final int indexOf(final ImmutableLinkedMap<K, V> subMapToFindIndex, final int subMapIndex, final ImmutableLinkedMapNode<K, V> currentNode, final int currentIndex, final Comparator<K> comparator) {
        if(subMapToFindIndex != null) {
            if (subMapToFindIndex.get(subMapIndex) != null && currentNode != null) {
                if (comparator.compare(subMapToFindIndex.get(subMapIndex).key(), currentNode.key()) == 0) {
                    return this.indexOf(subMapToFindIndex, subMapIndex + 1, currentNode.next(), currentIndex, comparator);
                } else {
                    return this.indexOf(subMapToFindIndex, 0, this.get(currentIndex + 1), currentIndex + 1, comparator);
                }
            }
            if (subMapIndex == subMapToFindIndex.length) {
                return currentIndex;
            }
        }
        return -1;
    }

    public final boolean contains(final ImmutableLinkedMapNode<K, V> nodeToFind) {
        return this.indexOf(nodeToFind) != -1;
    }

    public final boolean contains(final ImmutableLinkedMapNode<K, V> nodeToFind, final Comparator<K> comparator) {
        return this.indexOf(nodeToFind, comparator) != -1;
    }

    public final boolean contains(final K keyToFind) {
        return this.indexOf(keyToFind) != -1;
    }

    public final boolean contains(final K keyToFind, final Comparator<K> comparator) {
        return this.indexOf(keyToFind, comparator) != -1;
    }

    public final boolean containsValue(final V valueToFind) {
        return this.indexOfValue(valueToFind) != -1;
    }

    public final boolean containsValue(final V valueToFind, final Comparator<V> comparator) {
        return this.indexOfValue(valueToFind, comparator) != -1;
    }

    public final boolean contains(final ImmutableLinkedMap<K, V> subMapToFind) {
        return this.indexOf(subMapToFind) != -1;
    }

    public final boolean contains(final ImmutableLinkedMap<K, V> subMapToFind, final Comparator<K> comparator) {
        return this.indexOf(subMapToFind, comparator) != -1;
    }

    public final ImmutableLinkedMap<K, V> subMap(final int startIndex, final int endIndex) {
        return this.set(this.get(startIndex)).replace(this.get(endIndex), new ImmutableLinkedMapNode<K, V>(this.get(endIndex).key(), this.get(endIndex).value(), null));
    }

    public final ImmutableLinkedMap<K, V> mergeStart(final ImmutableLinkedMap<K, V> immutableLinkedListToMerge) {
        return this.merge(immutableLinkedListToMerge, 0);
    }

    public final ImmutableLinkedMap<K, V> mergeEnd(final ImmutableLinkedMap<K, V> immutableLinkedListToMerge) {
        return this.merge(immutableLinkedListToMerge, this.length - 1);
    }

    public final ImmutableLinkedMap<K, V> merge(final ImmutableLinkedMap<K, V> immutableLinkedMapToMerge, final int mergeIndex) {
        if(immutableLinkedMapToMerge != null && immutableLinkedMapToMerge.length > 0) {
            if (mergeIndex == 0) {
                return immutableLinkedMapToMerge.replace(immutableLinkedMapToMerge.get(immutableLinkedMapToMerge.length - 1), new ImmutableLinkedMapNode<K, V>(immutableLinkedMapToMerge.get(immutableLinkedMapToMerge.length - 1).key(), immutableLinkedMapToMerge.get(immutableLinkedMapToMerge.length - 1).value(), this.startNode));
            } else if (mergeIndex == this.length - 1) {
                return this.replace(this.get(this.length - 1), new ImmutableLinkedMapNode<K, V>(this.get(this.length - 1).key(), this.get(this.length - 1).value(), immutableLinkedMapToMerge.startNode));
            } else {
                final ImmutableLinkedMap<K, V> temp = this.replace(this.get(mergeIndex), new ImmutableLinkedMapNode<K, V>(this.get(mergeIndex).key(), this.get(mergeIndex).value(), immutableLinkedMapToMerge.startNode));
                return temp.replace(temp.get(temp.length - 1), new ImmutableLinkedMapNode<K, V>(temp.get(temp.length - 1).key(), temp.get(temp.length - 1).value(), this.get(mergeIndex + 1)));
            }
        } else {
            return this;
        }
    }

    public final ImmutableLinkedMapNode[] toArray() {
        return this.toArray(this, new ImmutableLinkedMapNode[this.length], false, 0);
    }

    public final ImmutableLinkedMapNode[] toArray(final boolean reverseOrder) {
        return this.toArray(this, new ImmutableLinkedMapNode[this.length], reverseOrder, 0);
    }

    private final ImmutableLinkedMapNode[] toArray(final ImmutableLinkedMap<K, V> immutableLinkedMap, final ImmutableLinkedMapNode[] array, final boolean reverseOrder, final int currentIndex) {
        if(currentIndex < immutableLinkedMap.length) {
            if(reverseOrder) {
                array[currentIndex] = immutableLinkedMap.get(immutableLinkedMap.length - 1 - currentIndex);
            } else {
                array[currentIndex] = immutableLinkedMap.get(currentIndex);
            }
            immutableLinkedMap.toArray(immutableLinkedMap, array, reverseOrder, currentIndex + 1);
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    public final K[] keys() {
        return this.keys(this, (K[])Array.newInstance(this.keyType, this.length), false, 0);
    }

    @SuppressWarnings("unchecked")
    public final K[] keys(final boolean reverseOrder) {
        return this.keys(this, (K[])Array.newInstance(this.keyType, this.length), reverseOrder, 0);
    }

    private final K[] keys(final ImmutableLinkedMap<K, V> immutableLinkedMap, final K[] array, final boolean reverseOrder, final int currentIndex) {
        if(currentIndex < immutableLinkedMap.length) {
            if(reverseOrder) {
                array[currentIndex] = immutableLinkedMap.get(immutableLinkedMap.length - 1 - currentIndex).key();
            } else {
                array[currentIndex] = immutableLinkedMap.get(currentIndex).key();
            }
            immutableLinkedMap.keys(immutableLinkedMap, array, reverseOrder, currentIndex + 1);
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

    private final V[] values(final ImmutableLinkedMap<K, V> immutableLinkedMap, final V[] array, final boolean reverseOrder, final int currentIndex) {
        if(currentIndex < immutableLinkedMap.length) {
            if(reverseOrder) {
                array[currentIndex] = immutableLinkedMap.get(immutableLinkedMap.length - 1 - currentIndex).value();
            } else {
                array[currentIndex] = immutableLinkedMap.get(currentIndex).value();
            }
            immutableLinkedMap.values(immutableLinkedMap, array, reverseOrder, currentIndex + 1);
        }
        return array;
    }

    @Override
    public final boolean equals(final Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof ImmutableLinkedMap)) {
            return false;
        }
        if(!(this.startNode == null && ((ImmutableLinkedMap)obj).startNode == null)) {
            if (this.startNode == null || ((ImmutableLinkedMap)obj).startNode == null) {
                return false;
            } else {
                if ((!this.startNode.equals(((ImmutableLinkedMap)obj).startNode))) {
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

    public final ImmutableLinkedMapNode<K, V> startNode() {
        return this.startNode;
    }

    public final int length() {
        return this.length;
    }

    public final Comparator<K> comparator() {
        return this.comparator;
    }
}
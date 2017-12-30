package dragonfly.butterfly;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

abstract class Utils {

    static final Object copy(Serializable objectToCopy) {
        try {
            final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(objectToCopy);
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        } catch(IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    static final ImmutableLinkedList<Character> toImmutableLinkedList(final String stringToConvert) {
        return toImmutableLinkedList(stringToConvert, new ImmutableLinkedList<Character>(Character.class));
    }

    private static final ImmutableLinkedList<Character> toImmutableLinkedList(final String stringToConvert, final ImmutableLinkedList<Character> characterImmutableLinkedList) {
        if(stringToConvert.length() > characterImmutableLinkedList.length()) {
            return toImmutableLinkedList(stringToConvert, characterImmutableLinkedList.add(new Character(stringToConvert.charAt(characterImmutableLinkedList.length()))));
        } else {
            return characterImmutableLinkedList;
        }
    }

    @SuppressWarnings("unchecked")
    static final ImmutableLinkedSet<ImmutableLinkedList<Character>> toImmutableLinkedSet(final String[] stringsToConvert) {
        return toImmutableLinkedSet(stringsToConvert, new ImmutableLinkedSet<ImmutableLinkedList<Character>>((Class<ImmutableLinkedList<Character>>)(Class<?>)ImmutableLinkedList.class));
    }

    private static final ImmutableLinkedSet<ImmutableLinkedList<Character>> toImmutableLinkedSet(final String[] stringsToConvert, final ImmutableLinkedSet<ImmutableLinkedList<Character>> wordImmutableLinkedSet) {
        if(stringsToConvert.length > wordImmutableLinkedSet.length()) {
            return toImmutableLinkedSet(stringsToConvert, wordImmutableLinkedSet.add(toImmutableLinkedList(stringsToConvert[wordImmutableLinkedSet.length()])));
        } else {
            return wordImmutableLinkedSet;
        }
    }
}

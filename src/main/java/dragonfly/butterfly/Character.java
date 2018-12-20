package dragonfly.butterfly;


import java.io.Serializable;

public final class Character implements Serializable, Cloneable {
    private final char charValue;

    public Character(final char charValue) {
        this.charValue = charValue;
    }

    public static final Character toLowerCase(final Character character) {
        if(character.charValue == 'A') {
            return new Character('a');
        } else if(character.charValue == 'B') {
            return new Character('b');
        } else if(character.charValue == 'C') {
            return new Character('c');
        } else if(character.charValue == 'D') {
            return new Character('d');
        } else if(character.charValue == 'E') {
            return new Character('e');
        } else if(character.charValue == 'F') {
            return new Character('f');
        } else if(character.charValue == 'G') {
            return new Character('g');
        } else if(character.charValue == 'H') {
            return new Character('h');
        } else if(character.charValue == 'I') {
            return new Character('i');
        } else if(character.charValue == 'J') {
            return new Character('j');
        } else if(character.charValue == 'K') {
            return new Character('k');
        } else if(character.charValue == 'L') {
            return new Character('l');
        } else if(character.charValue == 'M') {
            return new Character('m');
        } else if(character.charValue == 'N') {
            return new Character('n');
        } else if(character.charValue == 'O') {
            return new Character('o');
        } else if(character.charValue == 'P') {
            return new Character('p');
        } else if(character.charValue == 'Q') {
            return new Character('q');
        } else if(character.charValue == 'R') {
            return new Character('r');
        } else if(character.charValue == 'S') {
            return new Character('s');
        } else if(character.charValue == 'T') {
            return new Character('t');
        } else if(character.charValue == 'U') {
            return new Character('u');
        } else if(character.charValue == 'V') {
            return new Character('v');
        } else if(character.charValue == 'W') {
            return new Character('w');
        } else if(character.charValue == 'X') {
            return new Character('x');
        } else if(character.charValue == 'Y') {
            return new Character('y');
        } else if(character.charValue == 'Z') {
            return new Character('z');
        } else {
            return character;
        }
    }

    @Override
    public final boolean equals(final Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Character)) {
            return false;
        }
        if(this.charValue == ((Character)obj).charValue) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public final int hashCode() {
        return this.getClass().hashCode() +
                this.charValue;
    }

    @Override
    public final Object clone() {
        return Utils.copy(this);
    }

    @Override
    public final String toString() {
        return String.valueOf(this.charValue);
    }

    public final char getCharValue() {
        return charValue;
    }

    public static final String toString(Character[] characters) {
        return new String(toCharArray(characters));
    }

    public static final char[] toCharArray(final Character[] characters) {
        return toCharArray(characters, new char[characters.length], 0);
    }

    private static final char[] toCharArray(final Character[] characters, final char[] chars, final int currentIndex) {
        if(currentIndex < characters.length) {
            chars[currentIndex] = characters[currentIndex].getCharValue();
            return toCharArray(characters, chars, currentIndex + 1);
        } else {
            return chars;
        }
    }
}

package dragonfly.butterfly;


import java.io.Serializable;

public final class Character implements Serializable, Cloneable {
    private final char value;

    public Character(final char value) {
        this.value = value;
    }

    public static final Character toLowerCase(final Character character) {
        if(character.value == 'A') {
            return new Character('a');
        } else if(character.value == 'B') {
            return new Character('b');
        } else if(character.value == 'C') {
            return new Character('c');
        } else if(character.value == 'D') {
            return new Character('d');
        } else if(character.value == 'E') {
            return new Character('e');
        } else if(character.value == 'F') {
            return new Character('f');
        } else if(character.value == 'G') {
            return new Character('g');
        } else if(character.value == 'H') {
            return new Character('h');
        } else if(character.value == 'I') {
            return new Character('i');
        } else if(character.value == 'J') {
            return new Character('j');
        } else if(character.value == 'K') {
            return new Character('k');
        } else if(character.value == 'L') {
            return new Character('l');
        } else if(character.value == 'M') {
            return new Character('m');
        } else if(character.value == 'N') {
            return new Character('n');
        } else if(character.value == 'O') {
            return new Character('o');
        } else if(character.value == 'P') {
            return new Character('p');
        } else if(character.value == 'Q') {
            return new Character('q');
        } else if(character.value == 'R') {
            return new Character('r');
        } else if(character.value == 'S') {
            return new Character('s');
        } else if(character.value == 'T') {
            return new Character('t');
        } else if(character.value == 'U') {
            return new Character('u');
        } else if(character.value == 'V') {
            return new Character('v');
        } else if(character.value == 'W') {
            return new Character('w');
        } else if(character.value == 'X') {
            return new Character('x');
        } else if(character.value == 'Y') {
            return new Character('y');
        } else if(character.value == 'Z') {
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
        if(this.value == ((Character)obj).value) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public final int hashCode() {
        return this.getClass().hashCode() +
                this.value;
    }

    @Override
    public final Object clone() {
        return Utils.copy(this);
    }

    @Override
    public final String toString() {
        return String.valueOf(this.value);
    }

    public final char value() {
        return value;
    }

    public static final String toString(Character[] characters) {
        return new String(toCharArray(characters));
    }

    public static final char[] toCharArray(final Character[] characters) {
        return toCharArray(characters, new char[characters.length], 0);
    }

    private static final char[] toCharArray(final Character[] characters, final char[] chars, final int currentIndex) {
        if(currentIndex < characters.length) {
            chars[currentIndex] = characters[currentIndex].value();
            return toCharArray(characters, chars, currentIndex + 1);
        } else {
            return chars;
        }
    }
}

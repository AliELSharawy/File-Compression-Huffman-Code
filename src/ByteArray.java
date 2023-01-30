import java.io.Serializable;
import java.util.Arrays;

public class ByteArray implements Serializable {
    private byte[] array;
    public ByteArray(byte[] array) {
        this.array = array.clone();
    }
    public byte[] getArray() {
        return array.clone();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ByteArray byteArray = (ByteArray) o;
        return Arrays.equals(array, byteArray.array);
    }
    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }
}

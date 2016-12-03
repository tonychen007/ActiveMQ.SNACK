import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TestMain {

	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
	
    public static Object[] toArray(List list) {
        // Estimate size of array; be prepared to see more or fewer elements
        Object[] r = new Object[list.size()];
        Iterator it = list.iterator();
        for (int i = 0; i < r.length; i++) {
            if (! it.hasNext()) // fewer elements than expected
                return Arrays.copyOf(r, i);
            r[i] = it.next();
        }
        return it.hasNext() ? finishToArray(r, it) : r;
    }
    
    private static <T> T[] finishToArray(T[] r, Iterator<?> it) {
        int i = r.length;
        while (it.hasNext()) {
            int cap = r.length;
            if (i == cap) {
                int newCap = cap + (cap >> 1) + 1;
                // overflow-conscious code
                if (newCap - MAX_ARRAY_SIZE > 0)
                    newCap = hugeCapacity(cap + 1);
                r = Arrays.copyOf(r, newCap);
            }
            r[i++] = (T)it.next();
        }
        // trim if overallocated
        return (i == r.length) ? r : Arrays.copyOf(r, i);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError
                ("Required array size too large");
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }
    
	public static void main(String[] args) {
		List<Integer> lst = new ArrayList<>(10);		
		lst.add(0,1);
		lst.add(1, 2);
		lst.add(2,3);
		lst.add(3,4);
		lst.add(2, null);
	
		toArray(lst);
	}
}
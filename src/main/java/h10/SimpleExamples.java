package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

public class SimpleExamples {


    @DoNotTouch
    public static ListItem<House> createNeighborhood(House h) {
       ListItem<House> result = new ListItem<>(h, null, null);
       createNeighborhoodHelper(result);
       return result;
    }

    @StudentImplementationRequired
    public static void createNeighborhoodHelper(ListItem<House> p) {
        if (p.key.getHouseNumber() - 2 >= 0 && p.key.getHouseNumber() + 2 <= 100) {
            if (p.prev == null) {
                ListItem<House> prev = new ListItem<>(new House(p.key.getHouseNumber() - 2, House.HouseColor.RED), null, p);
                p.prev = prev;
                createNeighborhoodHelper(prev);
            }
            if (p.next == null) {
                ListItem<House> next = new ListItem<>(new House(p.key.getHouseNumber() + 2, House.HouseColor.GREEN), p, null);
                p.next = next;
                createNeighborhoodHelper(next);
            }
        }
   }

    @DoNotTouch
    public static ListItem<Character> buildAlphabet() {
        ListItem<Character> result = new ListItem<>('z', null, null);
        ListItem<Character> prev = result;
        for (char c = 'y'; c >= 'a'; c--) {
            result.prev = new ListItem<>();
            result = result.prev;
            result.key = c;
            result.next = prev;
            prev = result;
        }
        return result;
    }

    public static void main(String[] args) {
        ListItem<House> neighborhood = createNeighborhood(new House(50, House.HouseColor.RED));

        for (ListItem<House> p = neighborhood; p != null; p = p.prev) {
            System.out.println(p.key.getHouseNumber() + " " + p.key.getColor());
        }
        for (ListItem<House> p = neighborhood; p != null; p = p.next) {
            System.out.println(p.key.getHouseNumber() + " " + p.key.getColor());
        }
    }
}

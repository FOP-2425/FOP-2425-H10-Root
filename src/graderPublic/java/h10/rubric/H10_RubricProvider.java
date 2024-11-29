package h10.rubric;

import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Provides the rubric for H10.
 *
 * @author Nhan Huynh
 */
public abstract class H10_RubricProvider implements RubricProvider {

    /**
     * Defines the subtask H10.1.1 for task H10.1.
     */
    private static final Subtask H10_1_1 = Subtask.builder()
        .description("H10.1.1 | Liste von Spielern erstellen")
        .testClassName("h10.H10_1_1_Tests")
        .criterion("Die Liste wird korrekt erstellt und zurückgegeben. Jedes Listenelement verweist korrekt auf den vorherigen und nächsten Spieler, sofern dieser existiert.", "testResult", JsonParameterSet.class)
        .requirement("Verbindliche Anforderungen: Unerlaubte Verwendung von Datenstrukturen", "testDataStructure")
        .build();

    /**
     * Defines the subtask H10.1.2 for task H10.1.
     */
    private static final Subtask H10_1_2 = Subtask.builder()
        .description("H10.1.2 | Vorkommen der Karte SKIP zählen - iterativ")
        .testClassName("h10.H10_1_2_Tests")
        .criterion("Die Anzahl der Karten des Typs SKIP wird korrekt gezählt und zurückgegeben.", false, "testResult", JsonParameterSet.class)
        .requirement("Verbindliche Anforderungen: Unerlaubte Verwendung von Datenstrukturen", "testDataStructure")
        .requirement("Verbindliche Anforderungen: Unerlaubte Verwendung von Rekursion", "testLoops")
        .build();

    /**
     * Defines the subtask H10.1.3 for task H10.1.
     */
    private static final Subtask H10_1_3 = Subtask.builder()
        .description("H10.1.3 | Vorkommen der Karte SKIP zählen - rekursiv")
        .testClassName("h10.H10_1_3_Tests")
        .criterion("Die Anzahl der Karten des Typs SKIP wird korrekt gezählt und zurückgegeben.", false, "testResult", JsonParameterSet.class)
        .requirement("Verbindliche Anforderungen: Unerlaubte Verwendung von Datenstrukturen", "testDataStructure")
        .requirement("Verbindliche Anforderungen: Unerlaubte Verwendung von Schleifen", "testRecursions")
        .build();

    /**
     * Defines the subtask H10.1.4 for task H10.1.
     */
    private static final Subtask H10_1_4 = Subtask.builder()
        .description("H10.1.4 | Vorkommen der Karte SKIP zählen - mit Iterator")
        .testClassName("h10.H10_1_4_Tests")
        .criterion("Die Anzahl der Karten des Typs SKIP wird korrekt gezählt und zurückgegeben.", "testResult", JsonParameterSet.class)
        .requirement("Verbindliche Anforderungen: Unerlaubte Verwendung von Datenstrukturen", "testDataStructure")
        .requirement("Verbindliche Anforderungen: Unerlaubte Verwendung von Rekursion", "testLoops")
        .build();

    /**
     * Defines the task H10.1.
     */
    private static final Task H10_1 = Task.builder()
        .description("H10.1 | Beispiele mit Klasse ListItem<T>")
        .subtasks(H10_1_1, H10_1_2, H10_1_3, H10_1_4)
        .build();


    /**
     * Defines the subtask H10.2.1 for task H10.2.
     */
    private static final Subtask H10_2_1 = Subtask.builder()
        .description("H10.2.1 | Ist dieses Element bereits in der Liste?")
        .testClassName("h10.H10_2_1_Tests")
        .criterion("Die Methode gibt den Index des ersten Vorkommens des Elements zurück, falls es in der Liste enthalten ist. Andernfalls wird -1 zurückgegeben.", "testResult", JsonParameterSet.class)
        .requirement("Verbindliche Anforderungen: Unerlaubte Verwendung von Datenstrukturen", "testDataStructure")
        .requirement("Verbindliche Anforderungen: Unerlaubte Verwendung von Schleifen", "testRecursions")
        .build();

    /**
     * Defines the subtask H10.2.2 for task H10.2.
     */
    private static final Subtask H10_2_2 = Subtask.builder()
        .description("H10.2.2 | Auf ein Element in der Liste zugreifen")
        .testClassName("h10.H10_2_2_Tests")
        .criterion("Die Methode gibt das Element an der angegebenen Position zurück.", "testPositions", JsonParameterSet.class)
        .criterion("Die Suche wird in der Liste von vorne oder hinten gestartet, je nachdem, welcher Weg kürzer ist.", false, "testPath", JsonParameterSet.class)
        .criterion("Falls die Position nicht existiert, wird eine IndexOutOfBoundsException geworfen.", false, "testException", JsonParameterSet.class)
        .requirement("Verbindliche Anforderungen: Unerlaubte Verwendung von Datenstrukturen", "testDataStructure")
        .requirement("Verbindliche Anforderungen: Unerlaubte Verwendung von Rekursion", "testLoops")
        .build();

    /**
     * Defines the subtask H10.2.3 for task H10.2.
     */
    private static final Subtask H10_2_3 = Subtask.builder()
        .description("H10.2.3 | Ein Element hinzufügen")
        .testClassName("h10.H10_2_3_Tests")
        .criterion("Fall 1: Die Liste ist leer wurde korrekt implementiert.", "testEmptyList")
        .criterion("Fall 2: Neues Element an das Ende der Liste wurde korrekt implementiert.", false, "testEnd", JsonParameterSet.class)
        .criterion("Fall 3: Neues Element an den Anfang der Liste wurde korrekt implementiert.", "testStart", JsonParameterSet.class)
        .criterion("Fall 4: Neues Element in der Mitte der Liste wurde korrekt implementiert.", false, "testMiddle", JsonParameterSet.class)
        .criterion("Die Größe der Liste wird um 1 erhöht.", false, "testSize", JsonParameterSet.class)
        .criterion("Falls die Position nicht existiert, wird eine IndexOutOfBoundsException geworfen.", "testIndexOutOfBoundsException", JsonParameterSet.class)
        .criterion("Falls der übergebene key null ist, wird eine IllegalArgumentException geworfen.", false, "testIllegalArgumentException", JsonParameterSet.class)
        .requirement("Verbindliche Anforderungen: Unerlaubte Verwendung von Datenstrukturen", "testDataStructure")
        .build();

    /**
     * Defines the subtask H10.2.4 for task H10.2.
     */
    private static final Subtask H10_2_4 = Subtask.builder()
        .description("H10.2.4 | Ein Element entfernen")
        .testClassName("h10.H10_2_4_Tests")
        .criterion("Die Fälle 1 und 4 wurden korrekt implementiert.", Map.of(
            "testCase1", List.of(),
            "testCase4", List.of(JsonParameterSet.class)
        ))
        .criterion("Die Fälle 2 und 3 wurden korrekt implementiert.", false, Map.of(
            "testCase2", List.of(JsonParameterSet.class),
            "testCase3", List.of(JsonParameterSet.class)
        ))
        .criterion("Die Größe der Liste wird um 1 verringert.", false, "testSize", JsonParameterSet.class)
        .criterion("Das entfernte Element verweist immernoch auf seine Nachbarn.", "testReference", JsonParameterSet.class)
        .requirement("Verbindliche Anforderungen: Unerlaubte Verwendung von Datenstrukturen", "testDataStructure")
        .build();

    /**
     * Defines the subtask H10.2.5 for task H10.2.
     */
    private static final Subtask H10_2_5 = Subtask.builder()
        .description("H10.2.5 | Alle Elemente entfernen")
        .testClassName("h10.H10_2_5_Tests")
        .criterion("Nach einem Aufruf von clear() ist die Liste leer. Insbesondere sind head und tail auf null gesetzt, und die Größe der Liste ist 0.", "testResult", JsonParameterSet.class)
        .requirement("Verbindliche Anforderungen: Unerlaubte Verwendung von Datenstrukturen", "testDataStructure")
        .build();

    /**
     * Defines the task H10.2.
     */
    private static final Task H10_2 = Task.builder()
        .description("H10.2 | DoublyLinkedList<T>")
        .subtasks(H10_2_1, H10_2_2, H10_2_3, H10_2_4, H10_2_5)
        .build();

    /**
     * Defines the subtask H10.3.1 for task H10.3.
     */
    private static final Subtask H10_3_1 = Subtask.builder()
        .description("H10.3.1 | Das nächste Element zurückgeben")
        .testClassName("h10.H10_3_1_Tests")
        .criterion("Die Methode hasNext() gibt korrekt an, ob es ein nächstes Element gibt.", "testHasNext")
        .criterion("Die Methode next() gibt das nächste Element des Iterators zurück. Der Pointer p zeigt auf das neue Listenelement.", Map.of(
            "testNextEmpty", List.of(),
            "testNextEnd", List.of(),
            "testNextMiddle", List.of()
        ))
        .criterion("Die Methode next() setzt das Attribut calledRemove auf false.", "testCalledRemove")
        .requirement("Verbindliche Anforderungen: Unerlaubte Verwendung von Datenstrukturen", "testDataStructure")
        .build();

    /**
     * Defines the subtask H10.3.2 for task H10.3.
     */
    private static final Subtask H10_3_2 = Subtask.builder()
        .description("H10.3.2 | Das vorherige Element zurückgeben")
        .testClassName("h10.H10_3_2_Tests")
        .criterion("Die Methode hasPrevious() gibt korrekt an, ob es ein vorheriges Element gibt.", false, "testHasPrevious")
        .criterion("Die Methode previous() gibt das vorherige Element des Iterators zurück. Der Pointer p zeigt auf das neue Listenelement.", false, Map.of(
            "testPreviousEmpty", List.of(),
            "testPreviousEnd", List.of(),
            "testNextMiddle", List.of()
        ))
        .criterion("Die Methode previous() setzt das Attribut calledRemove auf false.", false, "testCalledRemove")
        .requirement("Verbindliche Anforderungen: Unerlaubte Verwendung von Datenstrukturen", "testDataStructure")
        .build();

    /**
     * Defines the subtask H10.3.3 for task H10.3.
     */
    private static final Subtask H10_3_3 = Subtask.builder()
        .description("H10.3.3 | Verlierer des Spiels bestimmen")
        .testClassName("h10.H10_3_3_Tests")
        .criterion("Bei einem SKIP wird der nächste Spieler übersprungen.", "testSkip")
        .criterion("Bei einer REVERSE-Karte wird die Richtung des Iterators umgekehrt.", "testReverse")
        .criterion("Wurde im letzten Zug eine DRAW_TWO-Karte gespielt, so muss der nächste Spieler zwei Karten ziehen, sofern er nicht auch eine DRAW_TWO-Karte spielt.", false, Map.of(
            "testDrawTwoLastTurnNoDrawTwo", List.of(),
            "testDrawTwoLastTurnDrawTwo", List.of()
        ))
        .criterion("Wurden in den vorherigen Zügen mehrere DRAW_TWO-Karten gespielt, so erhöht sich die Anzahl der zu ziehenden Karten entsprechend.", false, "testDrawTwoMultiple")
        .criterion("Spieler, die keine Karten mehr auf der Hand haben, werden aus dem Spiel entfernt.", "testNoCards")
        .criterion("Der Spieler, der als letztes Karten in der Hand hat, wird korrekt bestimmt und zurückgegeben.", false, "testLoser", JsonParameterSet.class)
        .requirement("Verbindliche Anforderungen: Unerlaubte Verwendung von Datenstrukturen", "testDataStructure")
        .build();

    /**
     * Defines the task H10.3.
     */
    private static final Task H10_3 = Task.builder()
        .description("H10.3 | Zyklischer Iterator über die DoublyLinkedList")
        .subtasks(H10_3_1, H10_3_2, H10_3_3)
        .build();

    /**
     * Whether the private tests are being graded.
     */
    private final boolean publicTests;

    /**
     * Constructs a new rubric provider.
     *
     * @param publicTests whether the private tests are being graded
     */
    public H10_RubricProvider(boolean publicTests) {
        this.publicTests = publicTests;
    }

    /**
     * Constructs a new public rubric provider.
     */
    public H10_RubricProvider() {
        this(true);
    }

    @Override
    public Rubric getRubric() {
        return Rubric.builder()
            .title("H10 | Doppelt verkette Listen - %s Tests".formatted(publicTests ? "Public" : "Private"))
            .addChildCriteria(
                Stream.of(H10_1, H10_2, H10_3)
                    .map(Task::getCriterion)
                    .toArray(Criterion[]::new)
            )
            .build();
    }
}

package h10;

import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;
import org.tudalgo.algoutils.tutor.general.jagr.RubricUtils;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;

import static h10.util.Rubrics.criterion;

/**
 * Rubric provider (Public Tests) for the H10 package.
 *
 * @author Nhan Huynh
 */
public class H10_RubricProvider implements RubricProvider {

    private static final Criterion H10_1_1 = Criterion.builder()
            .shortDescription("H10.1.1 | Liste von Spielern erstellen")
            .addChildCriteria(
                    criterion(
                            "Die Liste wird korrekt erstellt und zurückgegeben. Jedes Listenelement verweist korrekt auf den vorherigen und nächsten Spieler, sofern dieser existiert.",
                            "h10.H10_1_1_Tests",
                            "testResult",
                            JsonParameterSet.class
                    )
            ).build();

    private static final Criterion H10_1_2 = Criterion.builder()
            .shortDescription("H10.1.2 | Vorkommen der Karte SKIP zählen - iterativ")
            .minPoints(0)
            .maxPoints(1)
            .addChildCriteria(
                    criterion(
                            "Die Anzahl der Karten des Typs SKIP wird korrekt gezählt und zurückgegeben.",
                            "h10.H10_1_2_Tests",
                            "testResult",
                            JsonParameterSet.class
                    ),
                    criterion(
                            "Verbindliche Anforderung nicht erfüllt",
                            -1,
                            "h10.H10_1_2_Tests",
                            "testRequirements"
                    )
            ).build();

    private static final Criterion H10_1_3 = Criterion.builder()
            .shortDescription("H10.1.3 | Vorkommen der Karte SKIP zählen - rekursiv")
            .minPoints(0)
            .maxPoints(1)
            .addChildCriteria(
                    criterion(
                            "Die Anzahl der Karten des Typs SKIP wird korrekt gezählt und zurückgegeben.",
                            "h10.H10_1_3_Tests",
                            "testResult",
                            JsonParameterSet.class
                    ),
                    criterion(
                            "Verbindliche Anforderung nicht erfüllt",
                            -1,
                            "h10.H10_1_3_Tests",
                            "testRequirements"
                    )
            ).build();

    private static final Criterion H10_1_4 = Criterion.builder()
            .shortDescription("H10.1.4 | Vorkommen der Karte SKIP zählen - mit Iterator")
            .minPoints(0)
            .maxPoints(1)
            .addChildCriteria(
                    criterion(
                            "Die Anzahl der Karten des Typs SKIP wird korrekt gezählt und zurückgegeben.",
                            "h10.H10_1_3_Tests",
                            "testResult",
                            JsonParameterSet.class
                    ),
                    criterion(
                            "Verbindliche Anforderung nicht erfüllt",
                            -1,
                            "h10.H10_1_3_Tests",
                            "testRequirements"
                    )
            ).build();

    private static final Criterion H10_1 = Criterion.builder()
            .shortDescription("H10.1 | Beispiele mit Klasse ListItem<T>")
            .addChildCriteria(
                    H10_1_1,
                    H10_1_2,
                    H10_1_3,
                    H10_1_4
            ).build();

    /*
        private static final Criterion H10_1_1 = Criterion.builder()
            .shortDescription("H10.1.1 | Liste von Spielern erstellen")
            .minPoints(0)
            .maxPoints(1)
            .addChildCriteria(
                    criterion( // TODO: PUBLIC TEST
                        "Die Liste wird korrekt erstellt und zurückgegeben. Jedes Listenelement verweist korrekt auf den vorherigen und nächsten Spieler, sofern dieser existiert."
                    )
            )
            .build();

    private static final Criterion H10_1_2 = Criterion.builder()
            .shortDescription("H10.1.2 | Vorkommen der Karte SKIP zählen - iterativ")
            .minPoints(0)
            .maxPoints(1)
            .addChildCriteria(
                    criterion(
                            "Die Anzahl der Karten des Typs SKIP wird korrekt gezählt und zurückgegeben."
                    ),
                    criterion(
                            "Verbindliche Anforderung nicht erfüllt",
                            -1
                    ) // Punktabzug wenn nicht erfüllt
            )
            .build();

    private static final Criterion H10_1_3 = Criterion.builder()
            .shortDescription("H10.1.3 | Vorkommen der Karte SKIP zählen - rekursiv")
            .minPoints(0)
            .maxPoints(1)
            .addChildCriteria(
                    criterion(
                            "Die Anzahl der Karten des Typs SKIP wird korrekt gezählt und zurückgegeben."
                    ),
                    criterion(
                            "Verbindliche Anforderung nicht erfüllt",
                            -1
                    ) // Punktabzug wenn nicht erfüllt
            )
            .build();

    private static final Criterion H10_1_4 = Criterion.builder()
            .shortDescription("H10.1.4 | Vorkommen der Karte SKIP zählen - mit Iterator")
            .minPoints(0)
            .maxPoints(1)
            .addChildCriteria(
                    criterion( // TODO: PUBLIC TEST
                            "Die Anzahl der Karten des Typs SKIP wird korrekt gezählt und zurückgegeben."
                    ) ,
                    criterion(
                            "Verbindliche Anforderung nicht erfüllt",
                            -1
                    ) // Punktabzug wenn nicht erfüllt
            )
            .build();

    private static final Criterion H10_1 = Criterion.builder()
            .shortDescription("H10.1 | Beispiele mit Klasse ListItem<T>")
            .addChildCriteria(
                    H10_1_1,
                    H10_1_2,
                    H10_1_3,
                    H10_1_4
            )
            .build();


    private static final Criterion H10_2_1 = Criterion.builder()
            .shortDescription("H10.2.1 | Ist dieses Element bereits in der Liste?")
            .minPoints(0)
            .maxPoints(1)
            .addChildCriteria(
                    criterion( // TODO: PUBLIC TEST
                            "Die Methode gibt den Index des ersten Vorkommens des Elements zurück, falls es in der Liste enthalten ist. Andernfalls wird -1 zurückgegeben."
                    ),
                    criterion(
                            "Verbindliche Anforderung nicht erfüllt",
                            -1
                    ) // Punktabzug wenn nicht erfüllt
            )
            .build();

    private static final Criterion H10_2_2 = Criterion.builder()
            .shortDescription("H10.2.2 | Auf ein Element in der Liste zugreifen")
            .minPoints(0)
            .maxPoints(3)
            .addChildCriteria(
                    criterion("Die Methode gibt das Element an der angegebenen Position zurück."), // TODO: PUBLIC TEST
                    criterion("Die Suche wird in der Liste von vorne oder hinten gestartet, je nachdem, welcher Weg kürzer ist."),
                    criterion("Falls die Position nicht existiert, wird eine IndexOutOfBoundsException geworfen."),
                    criterion(
                            "Verbindliche Anforderung nicht erfüllt",
                            -3
                    ) // Punktabzug wenn nicht erfüllt
            )
            .build();

    private static final Criterion H10_2_3 = Criterion.builder()
            .shortDescription("H10.2.3 | Ein Element hinzufügen")
            .minPoints(0)
            .maxPoints(7)
            .addChildCriteria(
                    criterion("Fall 1: Die Liste ist leer wurde korrekt implementiert."), // TODO: PUBLIC TEST
                    criterion("Fall 2: Neues Element an das Ende der Liste wurde korrekt implementiert."),
                    criterion("Fall 3: Neues Element an den Anfang der Liste wurde korrekt implementiert."), // TODO: PUBLIC TEST
                    criterion("Fall 4: Neues Element in der Mitte der Liste wurde korrekt implementiert."),
                    criterion("Die Größe der Liste wird um 1 erhöht."),
                    criterion("Falls die Position nicht existiert, wird eine IndexOutOfBoundsException geworfen."), // TODO: PUBLIC TEST
                    criterion("Falls der übergebene key null ist, wird eine IllegalArgumentException geworfen.")
            )
            .build();

    private static final Criterion H10_2_4 = Criterion.builder()
            .shortDescription("H10.2.4 | Ein Element entfernen")
            .minPoints(0)
            .maxPoints(4)
            .addChildCriteria(
                    criterion("Die Fälle 1 und 4 wurden korrekt implementiert."), // TODO: PUBLIC TEST
                    criterion("Die Fälle 2 und 3 wurden korrekt implementiert."),
                    criterion("Die Größe der Liste wird um 1 verringert."),
                    criterion("Das entfernte Element verweist immernoch auf seine Nachbarn.") // TODO: PUBLIC TEST
            )
            .build();

    private static final Criterion H10_2_5 = Criterion.builder()
            .shortDescription("H10.2.5 | Alle Elemente entfernen")
            .minPoints(0)
            .maxPoints(1)
            .addChildCriteria(
                    criterion("Nach einem Aufruf von clear() ist die Liste leer. Insbesondere sind head und tail auf null gesetzt, und die Größe der Liste ist 0.") // TODO: PUBLIC TEST
            )
            .build();

    private static final Criterion H10_2 = Criterion.builder()
            .shortDescription("H10.2 | DoublyLinkedList<T>")
            .addChildCriteria(
                    H10_2_1,
                    H10_2_2,
                    H10_2_3,
                    H10_2_4,
                    H10_2_5
            )
            .build();

    private static final Criterion H10_3_1 = Criterion.builder()
        .shortDescription("H10.3.1 | Das nächste Element zurückgeben")
        .minPoints(0)
        .maxPoints(3)
        .addChildCriteria(
                criterion("Die Methode hasNext() gibt korrekt an, ob es ein nächstes Element gibt."), // TODO: PUBLIC TEST
                criterion("Die Methode next() gibt das nächste Element des Iterators zurück. Der Pointer p zeigt auf das neue Listenelement."), // TODO: PUBLIC TEST
                criterion("Die Methode next() setzt das Attribut calledRemove auf false.") // TODO: PUBLIC TEST
        )
        .build();

    private static final Criterion H10_3_2 = Criterion.builder()
        .shortDescription("H10.3.2 | Das vorherige Element zurückgeben")
        .minPoints(0)
        .maxPoints(3)
            .addChildCriteria(
                    criterion("Die Methode hasPrevious() gibt korrekt an, ob es ein vorheriges Element gibt."),
                    criterion("Die Methode previous() gibt das vorherige Element des Iterators zurück. Der Pointer p zeigt auf das neue Listenelement."),
                    criterion("Die Methode previous() setzt das Attribut calledRemove auf false.")
            )
        .build();

    private static final Criterion H10_3_3 = Criterion.builder()
        .shortDescription("H10.3.3 | Verlierer des Spiels bestimmen")
        .minPoints(0)
        .maxPoints(6)
        .addChildCriteria(
                criterion("Der Spieler, der als letztes Karten in der Hand hat, wird korrekt bestimmt und zurückgegeben."), // TODO: PUBLIC TEST
                criterion("Bei einem SKIP wird der nächste Spieler übersprungen."), // TODO: PUBLIC TEST
                criterion("Bei einer REVERSE-Karte wird die Richtung des Iterators umgekehrt."), // TODO: PUBLIC TEST
                criterion("Wurde im letzten Zug eine DRAW_TWO-Karte gespielt, so muss der nächste Spieler zwei Karten ziehen, sofern er nicht auch eine DRAW_TWO-Karte spielt."),
                criterion("Wurden in den vorherigen Zügen mehrere DRAW_TWO-Karten gespielt, so erhöht sich die Anzahl der zu ziehenden Karten entsprechend."),
                criterion("Spieler, die keine Karten mehr auf der Hand haben, werden aus dem Spiel entfernt.")
        )
        .build();

    private static final Criterion H10_3 = Criterion.builder()
        .shortDescription("H10.3 | Zyklischer Iterator über die DoublyLinkedList")
        .addChildCriteria(
            H10_3_1,
            H10_3_2,
            H10_3_3
        )
        .build();

    public static final Rubric RUBRIC = Rubric.builder()
        .title("H10 | Doppelt verkette Listen")
        .addChildCriteria(
            H10_1,
            H10_2,
            H10_3
        )
        .build();
     */

    private final boolean privateTests;

    public H10_RubricProvider(boolean privateTests) {
        this.privateTests = privateTests;
    }

    @Override
    public Rubric getRubric() {
        return Rubric.builder()
                .title("H10 | Doppelt verkette Listen - %s Tests".formatted(privateTests ? "Private" : "Public"))
                .addChildCriteria(H10_1)
                .build();
    }
}

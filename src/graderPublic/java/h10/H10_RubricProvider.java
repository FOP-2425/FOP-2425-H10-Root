package h10;

import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;
import org.tudalgo.algoutils.tutor.general.jagr.RubricUtils;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;

import static h10.Rubrics.criterion;


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
        )
        .build();

    private static final Criterion H10_1 = Criterion.builder()
        .shortDescription("H10.1 | Beispiele mit Klasse ListItem<T>")
        .addChildCriteria(
            H10_1_1,
            H10_1_2
        ).build();


    public static final Rubric RUBRIC = Rubric.builder()
        .title("H10 | Doppelt verkette Listen - Public Tests")
        .addChildCriteria(
            H10_1
            // H10_2,
            // H10_3
        )
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}

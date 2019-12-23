package day19;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MedicineTest {

    @Test
    void countDistinctMoleculesAfterOneReplacement() {
        String medicineMolecule = "HOHOHO";
        String replacements = """
                H => HO
                H => OH
                O => HH
                """;
        Medicine medicine = new Medicine(medicineMolecule, replacements);

        assertEquals(7, medicine.countDistinctMoleculesAfterOneReplacement());
    }

    @Test
    void minimumNumberOfStepsToGenerateMedicineMolecule() {
        String medicineMolecule = "HOH";
        String replacements = """
                e => H
                e => O
                H => HO
                H => OH
                O => HH
                """;
        Medicine medicine = new Medicine(medicineMolecule, replacements);

        assertEquals(3, medicine.minimumNumberOfStepsToGenerateMedicineMolecule());
    }
}
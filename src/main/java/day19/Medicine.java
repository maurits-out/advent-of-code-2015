package day19;

import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.toUnmodifiableSet;
import static java.util.stream.IntStream.range;

public class Medicine {

    static record Replacement(String leftHandSide, String rightHandSide) {
    }

    private final static Pattern PATTERN = compile("(\\p{Alpha}+) => (\\p{Alpha}+)");

    private final String medicineMolecule;
    private final Set<Replacement> replacements;

    public Medicine(String medicineMolecule, String replacementRules) {
        this.medicineMolecule = medicineMolecule;
        this.replacements = parseReplacements(replacementRules);
    }

    long countDistinctMoleculesAfterOneReplacement() {
        return generateNewMolecules(medicineMolecule, Replacement::leftHandSide, Replacement::rightHandSide)
                .distinct()
                .count();
    }

    int minimumNumberOfStepsToGenerateMedicineMolecule() {
        return minimumNumberOfStepsToGenerateMedicineMolecule(medicineMolecule, 0).orElseThrow();
    }

    private OptionalInt minimumNumberOfStepsToGenerateMedicineMolecule(String molecule, int steps) {
        if (molecule.equals("e")) {
            return OptionalInt.of(steps);
        }
        return generateNewMolecules(molecule, Replacement::rightHandSide, Replacement::leftHandSide)
                .map(s -> minimumNumberOfStepsToGenerateMedicineMolecule(s, steps + 1))
                .filter(OptionalInt::isPresent)
                .mapToInt(OptionalInt::getAsInt)
                .findFirst();
    }

    private Stream<String> generateNewMolecules(String molecule, Function<Replacement, String> targetFunction, Function<Replacement, String> replacementFunction) {
        return replacements.stream()
                .sorted(comparingInt(replacement -> -targetFunction.apply(replacement).length()))
                .flatMap(replacement ->
                        generateNewMolecules(molecule, targetFunction.apply(replacement), replacementFunction.apply(replacement)));
    }

    private Stream<String> generateNewMolecules(String molecule, String target, String replacement) {
        return findReplacementIndices(molecule, target)
                .mapToObj(index -> replace(molecule, index, target, replacement));
    }

    private Set<Replacement> parseReplacements(String inputReplacements) {
        return inputReplacements
                .lines()
                .map(PATTERN::matcher)
                .filter(Matcher::find)
                .map(matcher -> new Replacement(matcher.group(1), matcher.group(2)))
                .collect(toUnmodifiableSet());
    }

    private IntStream findReplacementIndices(String molecule, String target) {
        return range(0, molecule.length())
                .filter(beginIndex -> isReplaceable(molecule, target, beginIndex));
    }

    private boolean isReplaceable(String molecule, String target, int beginIndex) {
        var endIndex = beginIndex + target.length();
        if (endIndex > molecule.length()) {
            return false;
        }
        return target.equals(molecule.substring(beginIndex, endIndex));
    }

    private String replace(String molecule, int index, String target, String replacement) {
        return molecule.substring(0, index)
                + replacement
                + molecule.substring(index + target.length());
    }

    public static void main(String[] args) {
        var molecule = "CRnCaCaCaSiRnBPTiMgArSiRnSiRnMgArSiRnCaFArTiTiBSiThFYCaFArCaCaSiThCaPBSiThSiThCaCaPTiRnPBSiThRnFArArCaCaSiThCaSiThSiRnMgArCaPTiBPRnFArSiThCaSiRnFArBCaSiRnCaPRnFArPMgYCaFArCaPTiTiTiBPBSiThCaPTiBPBSiRnFArBPBSiRnCaFArBPRnSiRnFArRnSiRnBFArCaFArCaCaCaSiThSiThCaCaPBPTiTiRnFArCaPTiBSiAlArPBCaCaCaCaCaSiRnMgArCaSiThFArThCaSiThCaSiRnCaFYCaSiRnFYFArFArCaSiRnFYFArCaSiRnBPMgArSiThPRnFArCaSiRnFArTiRnSiRnFYFArCaSiRnBFArCaSiRnTiMgArSiThCaSiThCaFArPRnFArSiRnFArTiTiTiTiBCaCaSiRnCaCaFYFArSiThCaPTiBPTiBCaSiThSiRnMgArCaF";
        var replacements = """
                Al => ThF
                Al => ThRnFAr
                B => BCa
                B => TiB
                B => TiRnFAr
                Ca => CaCa
                Ca => PB
                Ca => PRnFAr
                Ca => SiRnFYFAr
                Ca => SiRnMgAr
                Ca => SiTh
                F => CaF
                F => PMg
                F => SiAl
                H => CRnAlAr
                H => CRnFYFYFAr
                H => CRnFYMgAr
                H => CRnMgYFAr
                H => HCa
                H => NRnFYFAr
                H => NRnMgAr
                H => NTh
                H => OB
                H => ORnFAr
                Mg => BF
                Mg => TiMg
                N => CRnFAr
                N => HSi
                O => CRnFYFAr
                O => CRnMgAr
                O => HP
                O => NRnFAr
                O => OTi
                P => CaP
                P => PTi
                P => SiRnFAr
                Si => CaSi
                Th => ThCa
                Ti => BP
                Ti => TiTi
                e => HF
                e => NAl
                e => OMg
                """;
        Medicine medicine = new Medicine(molecule, replacements);
        System.out.printf("Number of distinct molecules after one replacement: %d\n", medicine.countDistinctMoleculesAfterOneReplacement());
        System.out.printf("Minimum number of steps to generate medicine molecule: %d\n", medicine.minimumNumberOfStepsToGenerateMedicineMolecule());
    }
}
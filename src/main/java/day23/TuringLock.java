package day23;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static day23.InstructionType.*;
import static java.lang.Integer.parseInt;
import static support.InputLoader.loadInput;

public class TuringLock {

    private static final Pattern HLF_PATTERN = Pattern.compile("^hlf ([ab])$");
    private static final Pattern TPL_PATTERN = Pattern.compile("^tpl ([ab])$");
    private static final Pattern INC_PATTERN = Pattern.compile("^inc ([ab])$");
    private static final Pattern JMP_PATTERN = Pattern.compile("^jmp ([-+]\\d+)$");
    private static final Pattern JIE_PATTERN = Pattern.compile("^jie ([ab]), ([-+]\\d+)$");
    private static final Pattern JIO_PATTERN = Pattern.compile("^jio ([ab]), ([-+]\\d+)$");

    private final Instruction[] instructions;

    public TuringLock(String input) {
        instructions = input
                .lines()
                .map(this::parseLine)
                .toArray(Instruction[]::new);
    }

    Instruction parseLine(String line) {
        var matcher = HLF_PATTERN.matcher(line);
        if (matcher.find()) {
            return new Instruction(HALF, parseRegister(matcher), 1);
        }
        matcher = TPL_PATTERN.matcher(line);
        if (matcher.find()) {
            return new Instruction(TRIPLE, parseRegister(matcher), 1);
        }
        matcher = INC_PATTERN.matcher(line);
        if (matcher.find()) {
            return new Instruction(INCREMENT, parseRegister(matcher), 1);
        }
        matcher = JMP_PATTERN.matcher(line);
        if (matcher.find()) {
            return new Instruction(JUMP, null, parseOffset(matcher, 1));
        }
        matcher = JIE_PATTERN.matcher(line);
        if (matcher.find()) {
            return new Instruction(JUMP_IF_EVEN, parseRegister(matcher), parseOffset(matcher, 2));
        }
        matcher = JIO_PATTERN.matcher(line);
        if (matcher.find()) {
            return new Instruction(JUMP_IF_ONE, parseRegister(matcher), parseOffset(matcher, 2));
        }
        throw new IllegalArgumentException("Could not parse line: " + line);
    }

    int executeAndReturnValueOfB(int initialValueOfA) {
        var state = new State();
        state.updateRegister('a', initialValueOfA);
        while (state.getInstructionPointer() >= 0 && state.getInstructionPointer() < instructions.length) {
            instructions[state.getInstructionPointer()].evaluate(state);
        }
        return state.getRegister('b');
    }

    private int parseOffset(Matcher matcher, int group) {
        return parseInt(matcher.group(group));
    }

    private char parseRegister(Matcher matcher) {
        return matcher.group(1).charAt(0);
    }

    public static void main(String[] args) {
        var input = loadInput("day23-input.txt");
        var turingLock = new TuringLock(input);
        System.out.printf("Value of b after evaluating the instructions (part 1): %d\n", turingLock.executeAndReturnValueOfB(0));
        System.out.printf("Value of b after evaluating the instructions (part 2): %d\n", turingLock.executeAndReturnValueOfB(1));
    }
}

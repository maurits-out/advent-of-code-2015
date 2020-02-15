package day23;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static day23.InstructionType.*;

public class TuringLock {

    private static final Pattern HLF_PATTERN = Pattern.compile("^hlf ([ab])$");
    private static final Pattern TPL_PATTERN = Pattern.compile("^tpl ([ab])$");
    private static final Pattern INC_PATTERN = Pattern.compile("^inc ([ab])$");
    private static final Pattern JMP_PATTERN = Pattern.compile("^jmp ([-+]\\d+)$");
    private static final Pattern JIE_PATTERN = Pattern.compile("^jie ([ab]), ([-+]\\d+)$");
    private static final Pattern JIO_PATTERN = Pattern.compile("^jio ([ab]), ([-+]\\d+)$");

    Instruction[] parseProgram(Stream<String> lines) {
        return lines.map(this::parseLine).toArray(Instruction[]::new);
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
            return new Instruction(JUMP, null, parseOffset(matcher, 0));
        }
        matcher = JIE_PATTERN.matcher(line);
        if (matcher.find()) {
            return new Instruction(JUMP_IF_EVEN, parseRegister(matcher), parseOffset(matcher, 1));
        }
        matcher = JIO_PATTERN.matcher(line);
        if (matcher.find()) {
            return new Instruction(JUMP_IF_ONE, parseRegister(matcher), parseOffset(matcher, 1));
        }
        throw new IllegalArgumentException("Could not parse line: " + line);
    }

    private int parseOffset(Matcher matcher, int group) {
        return Integer.parseInt(matcher.group(group));
    }

    private char parseRegister(Matcher matcher) {
        return matcher.group(0).charAt(0);
    }
}

enum InstructionType {
    HALF,
    TRIPLE,
    INCREMENT,
    JUMP,
    JUMP_IF_EVEN,
    JUMP_IF_ONE
}

final class Instruction {
    private final InstructionType type;
    private final Character register;
    private final Integer offset;

    public Instruction(InstructionType type, Character register, Integer offset) {
        this.type = type;
        this.register = register;
        this.offset = offset;
    }

    void evaluate(State state) {
        switch (type) {
            case HALF -> {
                state.updateRegister(register, state.getRegister(register) / 2);
                state.updateInstructionPointer(offset);
            }
            case TRIPLE -> {
                state.updateRegister(register, state.getRegister(register) * 3);
                state.updateInstructionPointer(offset);
            }
            case INCREMENT -> {
                state.updateRegister(register, state.getRegister(register) + 1);
                state.updateInstructionPointer(offset);
            }
            case JUMP -> state.updateInstructionPointer(offset);
            case JUMP_IF_EVEN -> {
                boolean isEven = state.getRegister(register) % 2 == 0;
                state.updateInstructionPointer(isEven ? offset : 1);
            }
            case JUMP_IF_ONE -> {
                boolean isOne = state.getRegister(register) == 1;
                state.updateInstructionPointer(isOne ? offset : 1);
            }
            default -> throw new IllegalStateException("Unsupported instruction: " + type);
        }
    }
}

final class State {

    private final Map<Character, Integer> registers = new HashMap<>();
    private int instructionPointer = 0;

    public int getRegister(char register) {
        return registers.getOrDefault(register, 0);
    }

    public int getInstructionPointer() {
        return instructionPointer;
    }

    public void updateInstructionPointer(int offset) {
        instructionPointer += offset;
    }

    public void updateRegister(char register, int value) {
        registers.put(register, value);
    }
}

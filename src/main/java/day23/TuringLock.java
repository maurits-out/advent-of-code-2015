package day23;

import java.util.regex.Pattern;

public class TuringLock {

    private static final Pattern HLF_PATTERN = Pattern.compile("^hlf ([ab])$");
    private static final Pattern TPL_PATTERN = Pattern.compile("^tpl ([ab])$");
    private static final Pattern INC_PATTERN = Pattern.compile("^inc ([ab])$");
    private static final Pattern JMP_PATTERN = Pattern.compile("^jmp ([-+]\\d+)$");
    private static final Pattern JIE_PATTERN = Pattern.compile("^jie ([ab]), ([-+]\\d+)$");
    private static final Pattern JIO_PATTERN = Pattern.compile("^jio ([ab]), ([-+]\\d+)$");


}

abstract class Instruction {
    protected final Character register;
    protected final Integer offset;

    public Instruction(Character register, Integer offset) {
        this.register = register;
        this.offset = offset;
    }
}

final class Half extends Instruction {

    Half(Character register) {
        super(register, null);
    }
}

final class Triple extends Instruction {

    Triple(Character register) {
        super(register, null);
    }
}

final class Increment extends Instruction {
    private final char register;

    Increment(char register) {
        this.register = register;
    }
}

final class Jump extends Instruction {
    private final int offset;

    Jump(int offset) {
        this.offset = offset;
    }
}

final class JumpIfEven extends Instruction {
    private final char register;
    private final int offset;

    JumpIfEven(char register, int offset) {
        this.register = register;
        this.offset = offset;
    }
}

final class JumpIfOne extends Instruction {
    private final char register;
    private final int offset;

    JumpIfOne(char register, int offset) {
        this.register = register;
        this.offset = offset;
    }
}

final class State {
}

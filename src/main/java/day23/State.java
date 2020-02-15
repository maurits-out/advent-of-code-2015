package day23;

import java.util.HashMap;
import java.util.Map;

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

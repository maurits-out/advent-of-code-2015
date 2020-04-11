package day23;

final record Instruction(InstructionType type, Character register, Integer offset) {

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

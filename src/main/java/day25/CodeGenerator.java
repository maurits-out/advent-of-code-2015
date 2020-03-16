package day25;

import java.util.function.Supplier;
import java.util.stream.Stream;

public class CodeGenerator {

    private static class CodeSupplier implements Supplier<Code> {

        private long value = 20151125;
        private int row = 2;
        private int column = 1;
        private int offset = 1;

        @Override
        public Code get() {
            value = value * 252533 % 33554393;
            var code = new Code(row, column, value);
            if (row > 1) {
                row = row - 1;
                column = column + 1;
            } else {
                offset++;
                row = 1 + offset;
                column = 1;
            }
            return code;
        }
    }

    private static class Code {
        private final int row;
        private final int column;
        private final long value;

        public Code(int row, int column, long value) {
            this.row = row;
            this.column = column;
            this.value = value;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public long getValue() {
            return value;
        }
    }

    private long findCode() {
        return Stream.generate(new CodeSupplier())
                .filter(code -> code.getRow() == 2947 && code.getColumn() == 3029)
                .findFirst()
                .orElseThrow()
                .getValue();
    }

    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator();
        System.out.printf("Code: %d\n", generator.findCode());
    }
}

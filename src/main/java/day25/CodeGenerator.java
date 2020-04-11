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
                row = offset + 1;
                column = 1;
            }
            return code;
        }
    }

    private static record Code(int row, int column, long value) {
    }

    private long findCode() {
        return Stream.generate(new CodeSupplier())
                .filter(code -> code.row == 2947 && code.column == 3029)
                .findFirst()
                .orElseThrow()
                .value();
    }

    public static void main(String[] args) {
        CodeGenerator generator = new CodeGenerator();
        System.out.printf("Code: %d\n", generator.findCode());
    }
}

package day22;

enum Effect {
    SHIELD(6),
    POISON(6),
    RECHARGE(5);

    private final int timer;

    Effect(int timer) {
        this.timer = timer;
    }

    public int getTimer() {
        return timer;
    }
}

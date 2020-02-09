package day22;

import java.util.Map;

class State {
    private final int playerHitPoints;
    private final int playerArmor;
    private final int playerMana;
    private final int bossHitPoints;
    private final Map<Effect, Integer> effectTimers;

    public State(int playerHitPoints, int playerMana, int playerArmor, int bossHitPoints, Map<Effect, Integer> effectTimers) {
        this.playerHitPoints = playerHitPoints;
        this.playerMana = playerMana;
        this.playerArmor = playerArmor;
        this.bossHitPoints = bossHitPoints;
        this.effectTimers = effectTimers;
    }

    public int getPlayerHitPoints() {
        return playerHitPoints;
    }

    public int getPlayerArmor() {
        return playerArmor;
    }

    public int getPlayerMana() {
        return playerMana;
    }

    public int getBossHitPoints() {
        return bossHitPoints;
    }

    public Map<Effect, Integer> getEffectTimers() {
        return effectTimers;
    }

    public boolean hasEffect(Effect effect) {
        return effectTimers.containsKey(effect);
    }
}

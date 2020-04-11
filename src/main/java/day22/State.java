package day22;

import java.util.Map;

record State(int playerHitPoints, int playerMana, int playerArmor, int bossHitPoints,
             Map<Effect, Integer>effectTimers) {

    public boolean hasEffect(Effect effect) {
        return effectTimers.containsKey(effect);
    }
}

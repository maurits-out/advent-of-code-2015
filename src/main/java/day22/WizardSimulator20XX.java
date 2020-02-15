package day22;

import java.util.HashMap;
import java.util.Map;

import static day22.Difficulty.HARD;
import static day22.Difficulty.NORMAL;
import static day22.Effect.*;
import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.stream.Collectors.toMap;

public class WizardSimulator20XX {

    private static final int MAGIC_MISSILE_COST = 53;
    private static final int MAGIC_MISSILE_DAMAGE = 4;
    private static final int DRAIN_COST = 73;
    private static final int DRAIN_HEALING = 2;
    private static final int DRAIN_DAMAGE = 2;
    private static final int BOSS_DAMAGE = 8;
    private static final int SHIELD_COST = 113;
    private static final int SHIELD_ARMOR_INCREASE = 7;
    private static final int POISON_COST = 173;
    private static final int POISON_DAMAGE_TO_BOSS = 3;
    private static final int RECHARGE_COST = 229;
    private static final int RECHARGE_MANA = 101;

    private final Difficulty difficulty;

    public WizardSimulator20XX(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    private int leastAmountOfManaAndStillWin() {
        var state = new State(50, 500, 0, 55, Map.of());
        return leastAmount(state, 0, MAX_VALUE, true);
    }

    private int leastAmount(State state, int manaSpent, int currentMinimum, boolean playersTurn) {
        if (playerWins(state)) {
            return manaSpent;
        }
        if (bossWins(state) || spentAlreadyMoreThanCurrentMinimum(manaSpent, currentMinimum)) {
            return MAX_VALUE;
        }

        var updatedState = state;

        if (difficulty == HARD && playersTurn) {
            updatedState = playerLoosesOneHitPoint(state);
            if (bossWins(updatedState)) {
                return MAX_VALUE;
            }
        }

        updatedState = applyEffectsAndDecrementTimers(updatedState);

        if (!playersTurn) {
            return leastAmount(bossAttacks(updatedState), manaSpent, currentMinimum, true);
        }

        var updatedMin = currentMinimum;
        if (canCastMagicMissile(updatedState)) {
            updatedMin = min(updatedMin, leastAmount(castMagicMissile(updatedState), manaSpent + MAGIC_MISSILE_COST, updatedMin, false));
        }
        if (canCastDrain(updatedState)) {
            updatedMin = min(updatedMin, leastAmount(castDrain(updatedState), manaSpent + DRAIN_COST, updatedMin, false));
        }
        if (canCastShield(updatedState)) {
            updatedMin = min(updatedMin, leastAmount(castShield(updatedState), manaSpent + SHIELD_COST, updatedMin, false));
        }
        if (canCastPoison(updatedState)) {
            updatedMin = min(updatedMin, leastAmount(castPoison(updatedState), manaSpent + POISON_COST, updatedMin, false));
        }
        if (canCastRecharge(updatedState)) {
            updatedMin = min(updatedMin, leastAmount(castRecharge(updatedState), manaSpent + RECHARGE_COST, updatedMin, false));
        }

        return updatedMin;
    }

    private State playerLoosesOneHitPoint(State state) {
        return new State(
                state.getPlayerHitPoints() - 1,
                state.getPlayerMana(),
                state.getPlayerArmor(),
                state.getBossHitPoints(),
                state.getEffectTimers());
    }

    private boolean spentAlreadyMoreThanCurrentMinimum(int manaSpent, int currentMinimum) {
        return manaSpent >= currentMinimum;
    }

    private State applyEffectsAndDecrementTimers(State state) {

        var bossHitPoints = state.getBossHitPoints();
        if (state.hasEffect(POISON)) {
            bossHitPoints -= POISON_DAMAGE_TO_BOSS;
        }

        var playerMana = state.getPlayerMana();
        if (state.hasEffect(RECHARGE)) {
            playerMana += RECHARGE_MANA;
        }

        var timers = state.getEffectTimers();

        var playerArmor = state.getPlayerArmor();
        if (timers.containsKey(SHIELD) && timers.get(SHIELD) == 1) {
            playerArmor -= SHIELD_ARMOR_INCREASE;
        }

        return new State(state.getPlayerHitPoints(), playerMana, playerArmor, bossHitPoints, decrementTimers(timers));
    }

    private Map<Effect, Integer> decrementTimers(Map<Effect, Integer> effectTimers) {
        return effectTimers
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1)
                .collect(toMap(Map.Entry::getKey, entry -> entry.getValue() - 1));
    }

    private boolean canCastRecharge(State state) {
        return state.getPlayerMana() >= RECHARGE_COST && !state.hasEffect(RECHARGE);
    }

    private State castRecharge(State state) {
        return new State(
                state.getPlayerHitPoints(),
                state.getPlayerMana() - RECHARGE_COST,
                state.getPlayerArmor(),
                state.getBossHitPoints(),
                addEffect(state, RECHARGE));
    }

    private boolean canCastShield(State state) {
        return state.getPlayerMana() >= SHIELD_COST && !state.hasEffect(SHIELD);
    }

    private State castShield(State state) {
        return new State(
                state.getPlayerHitPoints(),
                state.getPlayerMana() - SHIELD_COST,
                state.getPlayerArmor() + SHIELD_ARMOR_INCREASE,
                state.getBossHitPoints(),
                addEffect(state, SHIELD));
    }

    private boolean canCastPoison(State state) {
        return state.getPlayerMana() >= POISON_COST && !state.hasEffect(POISON);
    }

    private State castPoison(State state) {
        return new State(
                state.getPlayerHitPoints(),
                state.getPlayerMana() - POISON_COST,
                state.getPlayerArmor(),
                state.getBossHitPoints(),
                addEffect(state, POISON));
    }

    private boolean canCastDrain(State state) {
        return state.getPlayerMana() >= DRAIN_COST;
    }

    private State castDrain(State state) {
        return new State(
                state.getPlayerHitPoints() + DRAIN_HEALING,
                state.getPlayerMana() - DRAIN_COST,
                state.getPlayerArmor(),
                state.getBossHitPoints() - DRAIN_DAMAGE,
                state.getEffectTimers());
    }

    private boolean canCastMagicMissile(State state) {
        return state.getPlayerMana() >= MAGIC_MISSILE_COST;
    }

    private Map<Effect, Integer> addEffect(State state, Effect effect) {
        var effectTimers = new HashMap<>(state.getEffectTimers());
        effectTimers.put(effect, effect.getTimer());
        return effectTimers;
    }

    private State castMagicMissile(State state) {
        return new State(
                state.getPlayerHitPoints(),
                state.getPlayerMana() - MAGIC_MISSILE_COST,
                state.getPlayerArmor(),
                state.getBossHitPoints() - MAGIC_MISSILE_DAMAGE,
                state.getEffectTimers());
    }

    private State bossAttacks(State state) {
        var damageDealtByBoss = max(BOSS_DAMAGE - state.getPlayerArmor(), 1);
        return new State(
                state.getPlayerHitPoints() - damageDealtByBoss,
                state.getPlayerMana(),
                state.getPlayerArmor(),
                state.getBossHitPoints(),
                state.getEffectTimers());
    }

    private boolean bossWins(State state) {
        return state.getPlayerHitPoints() <= 0;
    }

    private boolean playerWins(State state) {
        return state.getBossHitPoints() <= 0;
    }

    public static void main(String[] args) {
        var normal = new WizardSimulator20XX(NORMAL);
        System.out.printf("Least amount of mana the player can spend and still win the fight (normal, part 1): %4d\n", normal.leastAmountOfManaAndStillWin());

        var hard = new WizardSimulator20XX(HARD);
        System.out.printf("Least amount of mana the player can spend and still win the fight (hard, part 2)  : %4d\n", hard.leastAmountOfManaAndStillWin());
    }
}
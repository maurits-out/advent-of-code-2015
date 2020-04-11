package day21;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.function.ToIntFunction;

import static java.lang.Math.max;
import static java.util.stream.Collectors.partitioningBy;

public class RPGSimulator20XX {

    private final Stats bossStats;
    private final int playerHitPoints;
    private final IntSummaryStatistics statisticsOfWinningConfigurations;
    private final IntSummaryStatistics statisticsOfLossingConfigurations;

    public RPGSimulator20XX(Stats bossStats, int playerHitPoints) {
        this.bossStats = bossStats;
        this.playerHitPoints = playerHitPoints;

        var configurations = createConfigurations().stream().collect(partitioningBy(this::playerWins));
        this.statisticsOfWinningConfigurations = statistics(configurations.get(true));
        this.statisticsOfLossingConfigurations = statistics(configurations.get(false));
    }

    private IntSummaryStatistics statistics(List<Configuration> configurations) {
        return configurations.stream().mapToInt(Configuration::totalCost).summaryStatistics();
    }

    int leastAmountOfGoldToWinTheFight() {
        return statisticsOfWinningConfigurations.getMin();
    }

    int mostAmountOfGoldAndLooseTheFight() {
        return statisticsOfLossingConfigurations.getMax();
    }

    private boolean playerWins(Configuration c) {
        var playerStats = new Stats(playerHitPoints, c.totalDamage(), c.totalArmor());
        return playerWins(playerStats);
    }

    private boolean playerWins(Stats playerStats) {
        var damageDealtByPlayer = max(playerStats.damage() - bossStats.armor(), 1);
        var damageDealtByBoss = max(bossStats.damage() - playerStats.armor(), 1);

        var currentHintPointsPlayer = playerStats.hitPoints();
        var currentHintPointsBoss = bossStats.hitPoints();
        var playersTurn = true;
        while (currentHintPointsPlayer > 0 && currentHintPointsBoss > 0) {
            if (playersTurn) {
                currentHintPointsBoss -= damageDealtByPlayer;
            } else {
                currentHintPointsPlayer -= damageDealtByBoss;
            }
            playersTurn = !playersTurn;
        }
        return currentHintPointsBoss <= 0;
    }

    private List<Configuration> createConfigurations() {
        var shop = new Shop();

        var configurations = new ArrayList<Configuration>();
        for (Item weapon : shop.weapons()) {
            for (Item item : shop.armor()) {
                for (var k = 0; k < shop.rings().size() - 1; k++) {
                    for (var l = k + 1; l < shop.rings().size(); l++) {
                        var items = List.of(weapon, item, shop.rings().get(k), shop.rings().get(l));
                        configurations.add(new Configuration(items));
                    }
                }
            }
        }
        return configurations;
    }

    public static void main(String[] args) {
        var bossStats = new Stats(109, 8, 2);
        var simulator = new RPGSimulator20XX(bossStats, 100);
        System.out.printf("Least amount of gold such that player still wins: %d\n", simulator.leastAmountOfGoldToWinTheFight());
        System.out.printf("Most amount of gold and player looses: %d\n", simulator.mostAmountOfGoldAndLooseTheFight());
    }
}

record Stats(int hitPoints, int damage, int armor) {
}

record Configuration(int totalCost, int totalArmor, int totalDamage) {

    public Configuration(List<Item> items) {
        this(sum(items, Item::cost), sum(items, Item::armor), sum(items, Item::damage));
    }

    private static int sum(List<Item> items, ToIntFunction<Item> property) {
        return items.stream().mapToInt(property).sum();
    }
}

record Shop(List<Item>weapons, List<Item>armor, List<Item>rings) {

    public Shop() {
        this(createWeapons(), createArmor(), createRings());
    }

    private static List<Item> createWeapons() {
        return List.of(
                new Item(8, 4, 0),
                new Item(10, 5, 0),
                new Item(25, 6, 0),
                new Item(40, 7, 0),
                new Item(74, 8, 0));
    }

    private static List<Item> createArmor() {
        return List.of(
                new Item(0, 0, 0),
                new Item(13, 0, 1),
                new Item(31, 0, 2),
                new Item(53, 0, 3),
                new Item(75, 0, 4),
                new Item(102, 0, 5));
    }

    private static List<Item> createRings() {
        return List.of(
                new Item(0, 0, 0),
                new Item(0, 0, 0),
                new Item(25, 1, 0),
                new Item(50, 2, 0),
                new Item(100, 3, 0),
                new Item(20, 0, 1),
                new Item(40, 0, 2),
                new Item(80, 0, 3));
    }
}

record Item(int cost, int damage, int armor) {
}
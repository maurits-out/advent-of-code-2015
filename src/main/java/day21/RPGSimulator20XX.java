package day21;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;

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
        return configurations.stream().mapToInt(Configuration::getTotalCost).summaryStatistics();
    }

    int leastAmountOfGoldToWinTheFight() {
        return statisticsOfWinningConfigurations.getMin();
    }

    int mostAmountOfGoldAndLooseTheFight() {
        return statisticsOfLossingConfigurations.getMax();
    }

    private boolean playerWins(Configuration c) {
        var playerStats = new Stats(playerHitPoints, c.getTotalDamage(), c.getTotalArmor());
        return playerWins(playerStats);
    }

    private boolean playerWins(Stats playerStats) {
        var damageDealtByPlayer = max(playerStats.getDamage() - bossStats.getArmor(), 1);
        var damageDealtByBoss = max(bossStats.getDamage() - playerStats.getArmor(), 1);

        var currentHintPointsPlayer = playerStats.getHitPoints();
        var currentHintPointsBoss = bossStats.getHitPoints();
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
        var weapons = shop.getWeapons();
        var armor = shop.getArmor();
        var rings = shop.getRings();

        ArrayList<Configuration> configurations = new ArrayList<>();
        for (Item weapon : weapons) {
            for (Item item : armor) {
                for (var k = 0; k < rings.size() - 1; k++) {
                    for (var l = k + 1; l < rings.size(); l++) {
                        var items = List.of(weapon, item, rings.get(k), rings.get(l));
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

class Stats {
    private final int hitPoints;
    private final int damage;
    private final int armor;

    public Stats(int hitPoints, int damage, int armor) {
        this.hitPoints = hitPoints;
        this.damage = damage;
        this.armor = armor;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getDamage() {
        return damage;
    }

    public int getArmor() {
        return armor;
    }
}

class Configuration {
    private final int totalCost;
    private final int totalArmor;
    private final int totalDamage;

    public Configuration(List<Item> items) {
        totalCost = items.stream().mapToInt(Item::getCost).sum();
        totalArmor = items.stream().mapToInt(Item::getArmor).sum();
        totalDamage = items.stream().mapToInt(Item::getDamage).sum();
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getTotalArmor() {
        return totalArmor;
    }

    public int getTotalDamage() {
        return totalDamage;
    }
}

class Shop {
    private final List<Item> weapons;
    private final List<Item> armor;
    private final List<Item> rings;

    public Shop() {
        weapons = createWeapons();
        armor = createArmor();
        rings = createRings();
    }

    public List<Item> getWeapons() {
        return weapons;
    }

    public List<Item> getArmor() {
        return armor;
    }

    public List<Item> getRings() {
        return rings;
    }

    private List<Item> createWeapons() {
        return List.of(
                new Item(8, 4, 0),
                new Item(10, 5, 0),
                new Item(25, 6, 0),
                new Item(40, 7, 0),
                new Item(74, 8, 0));
    }

    private List<Item> createArmor() {
        return List.of(
                new Item(0, 0, 0),
                new Item(13, 0, 1),
                new Item(31, 0, 2),
                new Item(53, 0, 3),
                new Item(75, 0, 4),
                new Item(102, 0, 5));
    }

    private List<Item> createRings() {
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

class Item {
    private final int cost;
    private final int damage;
    private final int armor;

    public Item(int cost, int damage, int armor) {
        this.cost = cost;
        this.damage = damage;
        this.armor = armor;
    }

    public int getCost() {
        return cost;
    }

    public int getDamage() {
        return damage;
    }

    public int getArmor() {
        return armor;
    }
}

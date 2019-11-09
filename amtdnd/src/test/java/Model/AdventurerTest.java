package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdventurerTest {
    @Test
    void anAdventurerMustHaveTheRightData(){
        String name = "MonNom";
        Adventurer adventurer = Adventurer.builder()
                .name(name)
                .dex(9)
                .con(234)
                .wis(96)
                .cha(13)
                .inte(42)
                .str(66)
                .gold(66666)
                .klass("Rogue")
                .race("Elf")
                .experience(12345)
                .spendpoints(789)
                .build();
        assertNotNull(adventurer);
        assertEquals(name, adventurer.getName());
        assertEquals(9, adventurer.getDex());
        assertEquals(234, adventurer.getCon());
        assertEquals(96, adventurer.getWis());
        assertEquals(13, adventurer.getCha());
        assertEquals(42, adventurer.getInte());
        assertEquals(66, adventurer.getStr());
        assertEquals(66666, adventurer.getGold());
        assertEquals("Rogue", adventurer.getKlass());
        assertEquals("Elf", adventurer.getRace());
        assertEquals(12345, adventurer.getExperience());
        assertEquals(789, adventurer.getSpendpoints());
    }

    @Test
    void anAdventurerMustHaveTheRightLevel(){
        Adventurer adventurer = Adventurer.builder()
                .name("MonNom")
                .experience(0)
                .build();
        assertEquals(1, adventurer.getLevel());
        adventurer.setExperience(12345);
        assertEquals(4, adventurer.getLevel());
    }

    @Test
    void anAdventurerShouldHaveTheRightRemainingPoints(){
        Adventurer adventurer = Adventurer.builder()
                .name("MonNom")
                .experience(12345)
                .spendpoints(355)
                .build();
        assertEquals(95, adventurer.getremainingpoint());
    }

    @Test
    void anAdventurerShouldUseRemainingPointsToIncreaseStats(){
        Adventurer adventurer = Adventurer.builder()
                .name("MonNom")
                .dex(9)
                .con(234)
                .wis(96)
                .cha(13)
                .inte(42)
                .str(66)
                .experience(12345)
                .spendpoints(355)
                .build();
        adventurer.addCha();
        assertEquals(14, adventurer.getCha());
        assertEquals(356, adventurer.getSpendpoints());
        assertEquals(94, adventurer.getremainingpoint());
        adventurer.addCon();
        adventurer.addCon();
        adventurer.addInt();
        adventurer.addInt();
        adventurer.addInt();
        adventurer.addDex();
        adventurer.addWis();
        adventurer.addWis();
        adventurer.addStr();
        adventurer.addStr();
        assertEquals(236, adventurer.getCon());
        assertEquals(10, adventurer.getDex());
        assertEquals(45, adventurer.getInte());
        assertEquals(98, adventurer.getWis());
        assertEquals(68, adventurer.getStr());
        assertEquals(366, adventurer.getSpendpoints());
        assertEquals(84, adventurer.getremainingpoint());
    }

    @Test
    void itShouldBePossibleToAddExpToAnAdventurer(){
        Adventurer adventurer = Adventurer.builder()
                .name("MonNom")
                .experience(12345)
                .build();
        assertEquals(12345, adventurer.getExperience());
        adventurer.addExp(250);
        assertEquals(12595, adventurer.getExperience());
    }

    @Test
    void itShouldBePossibleToAddGoldToAnAdventurer(){
        Adventurer adventurer = Adventurer.builder()
                .name("MonNom")
                .gold(10000020)
                .build();
        assertEquals(10000020, adventurer.getGold());
        adventurer.addGold(500);
        assertEquals(10000520, adventurer.getGold());
    }
}
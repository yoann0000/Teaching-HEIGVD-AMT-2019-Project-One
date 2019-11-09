import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SQLGenerator {
    public static void main(String... args) throws FileNotFoundException, UnsupportedEncodingException {
        List<String> adventurers = new LinkedList<String>();
        List<String> guilds = new LinkedList<String>();
        List<String> parties = new LinkedList<String>();
        List<String> quests = new LinkedList<String>();
        List<String> races = new LinkedList<String>();
        races.add("Dragonborn");
        races.add("Dwarf");
        races.add("Elf");
        races.add("Gnome");
        races.add("Half-Elf");
        races.add("Half-Orc");
        races.add("Halfling");
        races.add("Human");
        races.add("Tiefling");
        List<String> classes = new LinkedList<String>();
        classes.add("Barbarian");
        classes.add("Bard");
        classes.add("Cleric");
        classes.add("Druid");
        classes.add("Fighter");
        classes.add("Monk");
        classes.add("Paladin");
        classes.add("Ranger");
        classes.add("Rogue");
        classes.add("Sorcerer");
        classes.add("Warlock");
        classes.add("Wizard");

        System.out.println("dnddata.sql");
        PrintWriter writer = new PrintWriter("dnddata.sql", "UTF-8");
        writer.println("USE dnd;");
        writer.flush();
        for(int i = 0; i < Math.pow(2, 9); ++i){
            String g = "Guild" + i;
            guilds.add(g);
            writer.println("INSERT INTO guild (id, reputation) VALUES ('" +
                    g + "', " + new Random().nextInt(Math.abs(999999)) + ");");
            writer.flush();
        }
        for(int i = 0; i < Math.pow(2, 11); ++i){
            String p = "Party" + i;
            parties.add(p);
            writer.println("INSERT INTO party (id, reputation) VALUES ('" +
                    p + "', " + new Random().nextInt(Math.abs(999999)) + ");");
            writer.flush();
        }
        for(int i = 0; i < Math.pow(2, 14); ++i){
            String a = "PNJ" + i;
            adventurers.add(a);
            writer.println("INSERT INTO player (id, password," +
                    "gold, strength, dexterity, constitution, intelligence," +
                    "wisdom, charisma, experience, spendpoints, fkRace, fkClasse, fkGuild) " +
                    "VALUES ('" + a + "', '$2a$10$y8VooRYElJx3rlbYYHbewup.xvVmUpDSPyuOIyXPORKt6RXn7Iuxa', "
                    + new Random().nextInt(Math.abs(999999)) + ", " + 8 + new Random().nextInt(Math.abs(255)) +
                    ", " + 8 + new Random().nextInt(Math.abs(255)) + ", " + 8 + new Random().nextInt(Math.abs(255)) +
                    ", " + 8 + new Random().nextInt(Math.abs(255)) + ", " + 8 + new Random().nextInt(Math.abs(255)) +
                    ", " + 8 + new Random().nextInt(Math.abs(255)) + ", " + new Random().nextInt(Math.abs(999999)) +
                    ", " + new Random().nextInt(Math.abs(9999)) + " , '"
                    + races.get(new Random().nextInt(races.size())) + "', '"
                    + classes.get(new Random().nextInt(classes.size())) + "', '"
                    + guilds.get(new Random().nextInt(guilds.size())) + "');");
            writer.flush();
        }
        for(int i = 0; i < Math.pow(2, 15); ++i){
            String q = "Quest" + i;
            quests.add(q);
            writer.println("INSERT INTO quest (id, description, rewardGold, rewardExp) VALUE ('"
                    + q + "', 'Description succintes', " + new Random().nextInt(Math.abs(9999)) + ", "
                    + new Random().nextInt(Math.abs(9999)) + ");");
            writer.flush();
        }
        writer.close();
        System.out.println("dnddata0.sql");
        writer = new PrintWriter("dnddata0.sql", "UTF-8");
        writer.println("USE dnd;");
        writer.flush();
        for(int i = 0; i < Math.pow(2, 15); ++i) {
            writer.println("INSERT INTO playerParty (fkPlayer, fkParty)" +
                    " VALUES ('" + adventurers.get(new Random().nextInt(adventurers.size())) + "', '"
                    + parties.get(new Random().nextInt(parties.size())) + "');");
            writer.flush();
        }
        writer.close();
        for(int i = 1; i <= 8; ++i) {
            System.out.println("dnddata"+i+".sql");
            writer = new PrintWriter("dnddata"+i+".sql", "UTF-8");
            writer.println("USE dnd;");
            writer.flush();
            for (int j = 0; j < Math.pow(2, 15); ++j) {
                writer.println("INSERT INTO guildPartyQuest (fkGuild, fkParty," +
                        "fkQuest, temps) VALUES ('" + guilds.get(new Random( ).nextInt(guilds.size( ))) + "', '" +
                        parties.get(new Random( ).nextInt(parties.size( ))) + "', '" +
                        quests.get(new Random( ).nextInt(quests.size( ))) + "', NOW());");
                writer.flush( );
            }
            writer.close();
        }
    }
}

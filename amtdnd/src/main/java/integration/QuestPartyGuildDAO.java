package integration;

import Model.Adventurer;
import Model.Guild;
import Model.Party;
import Model.Quest;
import business.IAuthenticationService;
import datastore.exception.DuplicateKeyException;
import datastore.exception.KeyNotFoundException;
import javafx.util.Pair;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.sql.DataSource;
import java.security.KeyException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class QuestPartyGuildDAO {

    @Resource(lookup = "java:/jdbc/dnd")
    DataSource dataSource;

    @EJB
    IAuthenticationService authentificationService;

    public Quest create(Quest quest, Party party, Guild guild) throws DuplicateKeyException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("INSERT INTO guildPartyQuest (fkGuild, fkParty," +
                    "fkQuest, temps) VALUES (?, ?, ?, NOW());");
            statement.setString(1, guild.getName());
            statement.setString(2, party.getName());
            statement.setLong(3, quest.getId());
            statement.execute();
            return quest;
        }catch (SQLException e){
            e.printStackTrace();
            throw new Error(e);
        }finally {
            ConnectionCloser.closeConnection(con);
        }
    }

    public List<Pair<Quest, Party>> getQuestsDoneByGuild(Guild guild) throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT (quest.id, " +
                    "quest.objective, quest.description, quest.rewardGold, quest.rewardExp, " +
                    "party.id, party.reputation FROM quest INNER JOIN guildPartyQuest" +
                    "ON fkQuest = quest.id INNER JOIN party" +
                    "ON fkParty = party.id WHERE fkGuild = ?;");
            statement.setString(1, guild.getName());
            ResultSet rs = statement.executeQuery();
            List<Pair<Quest, Party>> done = new LinkedList<>();
            PartyAdventurerDAO members = new PartyAdventurerDAO();
            while(rs.next()){
                done.add(new Pair(
                        Quest.builder()
                        .id(rs.getLong(1))
                        .objective(rs.getString(2))
                        .description(rs.getString(3))
                        .gold(rs.getInt(4))
                        .exp(rs.getInt(5))
                        .build(),
                        Party.builder()
                        .name(rs.getString(6))
                        .reputation(rs.getInt(7))
                        .members(members.findPartyMembersById(rs.getString(6)))
                        .build()
                ));
            }
            return done;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }

    public List<Pair<Quest, Guild>> getQuestsDoneByParty(Party party) throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT (quest.id, " +
                    "quest.objective, quest.description, quest.rewardGold, quest.rewardExp, " +
                    "guild.id, guild.reputation FROM quest INNER JOIN guildPartyQuest" +
                    "ON fkQuest = quest.id INNER JOIN guild" +
                    "ON fkGuild = guild.id WHERE fkParty = ?;");
            statement.setString(1, party.getName());
            ResultSet rs = statement.executeQuery();
            List<Pair<Quest, Guild>> done = new LinkedList<>();
            GuildAdventurerDAO members = new GuildAdventurerDAO();
            while(rs.next()){
                done.add(new Pair(
                        Quest.builder()
                                .id(rs.getLong(1))
                                .objective(rs.getString(2))
                                .description(rs.getString(3))
                                .gold(rs.getInt(4))
                                .exp(rs.getInt(5))
                                .build(),
                        Guild.builder()
                                .name(rs.getString(6))
                                .reputation(rs.getInt(7))
                                .members(members.findMembersById(rs.getString(6)))
                                .build()
                ));
            }
            return done;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }

    public List<Pair<Party, Guild>> getPartiesAndGuildsWhoHasDoneQuest(Quest quest) throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT (party.id, " +
                    "party.reputation, guild.id, guild.reputation FROM party INNER JOIN guildPartyQuest" +
                    "ON fkParty = party.id INNER JOIN guild" +
                    "ON fkGuild = guild.id WHERE fkQuest = ?;");
            statement.setLong(1, quest.getId());
            ResultSet rs = statement.executeQuery();
            List<Pair<Party, Guild>> png = new LinkedList<>();
            PartyAdventurerDAO pmembers = new PartyAdventurerDAO();
            GuildAdventurerDAO gmembers = new GuildAdventurerDAO();
            while(rs.next()){
                png.add(new Pair(
                        Party.builder()
                                .name(rs.getString(1))
                                .reputation(rs.getInt(2))
                                .members(pmembers.findPartyMembersById(rs.getString(1)))
                                .build(),
                        Guild.builder()
                                .name(rs.getString(3))
                                .reputation(rs.getInt(4))
                                .members(gmembers.findMembersById(rs.getString(3)))
                                .build()
                ));
            }
            return png;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }
}

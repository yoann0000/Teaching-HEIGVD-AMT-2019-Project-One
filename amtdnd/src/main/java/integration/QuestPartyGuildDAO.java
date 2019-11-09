package integration;

import Model.Adventurer;
import Model.Guild;
import Model.Party;
import Model.Quest;
import Model.util.PairPG;
import Model.util.PairQG;
import Model.util.PairQP;
import business.IAuthenticationService;
import datastore.exception.DuplicateKeyException;
import datastore.exception.KeyNotFoundException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class QuestPartyGuildDAO implements IQuestPartyGuildDAO {

    @Resource(lookup = "java:/jdbc/dnd")
    DataSource dataSource;

    @EJB
    IAuthenticationService authentificationService;

    @EJB
    IGuildAdventurerDAO guildAdventurerDAO;

    @EJB
    IPartyAdventurerDAO partyAdventurerDAO;

    public Quest create(Quest quest, Party party, Guild guild) throws DuplicateKeyException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("INSERT INTO guildPartyQuest (fkGuild, fkParty," +
                    "fkQuest, temps) VALUES (?, ?, ?, NOW());");
            statement.setString(1, guild.getName());
            statement.setString(2, party.getName());
            statement.setString(3, quest.getObjective());
            statement.execute();
            return quest;
        }catch (SQLException e) {
            e.printStackTrace( );
            throw new Error(e);
        }finally {
            ConnectionCloser.closeConnection(con);
        }
    }

    public List<PairQP> getQuestsDoneByGuild(Guild guild) throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT quest.id, " +
                    "quest.description, quest.rewardGold, quest.rewardExp, " +
                    "party.id, party.reputation FROM quest INNER JOIN guildPartyQuest " +
                    "ON fkQuest = quest.id INNER JOIN party " +
                    "ON fkParty = party.id WHERE fkGuild = ?;");
            statement.setString(1, guild.getName());
            ResultSet rs = statement.executeQuery();
            List<PairQP> done = new LinkedList<>();
            while(rs.next()){
                done.add(PairQP.builder()
                        .quest(
                                Quest.builder()
                                .objective(rs.getString(1))
                                .description(rs.getString(2))
                                .gold(rs.getInt(3))
                                .exp(rs.getInt(4))
                                .build()
                        ).party(
                                Party.builder()
                                .name(rs.getString(5))
                                .reputation(rs.getInt(6))
                                .members(new LinkedList<>())
                                .build()
                        ).build()
                );
            }
            ConnectionCloser.closeConnection(con);
            for(PairQP qp : done){
                List<Adventurer> members = guildAdventurerDAO.findMembersById(qp.getParty().getName());
                for(Adventurer member : members){
                    qp.getParty().addMember(member);
                }
            }
            return done;
        }catch (SQLException e) {
            e.printStackTrace();
            ConnectionCloser.closeConnection(con);
            throw new Error(e);
        }
    }

    public List<PairQG> getQuestsDoneByParty(Party party) throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT quest.id, " +
                    "quest.description, quest.rewardGold, quest.rewardExp, " +
                    "guild.id, guild.reputation FROM quest INNER JOIN guildPartyQuest " +
                    "ON fkQuest = quest.id INNER JOIN guild " +
                    "ON fkGuild = guild.id WHERE fkParty = ?;");
            statement.setString(1, party.getName());
            ResultSet rs = statement.executeQuery();
            List<PairQG> done = new LinkedList<>();
            while(rs.next()){
                done.add(PairQG.builder()
                        .quest(
                                Quest.builder()
                                .objective(rs.getString(1))
                                .description(rs.getString(2))
                                .gold(rs.getInt(3))
                                .exp(rs.getInt(4))
                                .build())
                        .guild(
                                Guild.builder()
                                .name(rs.getString(5))
                                .reputation(rs.getInt(6))
                                .members(new LinkedList<>())
                                .build()
                        ).build()
                );
            }
            ConnectionCloser.closeConnection(con);
            for(PairQG qg : done){
                List<Adventurer> members = guildAdventurerDAO.findMembersById(qg.getGuild().getName());
                for(Adventurer member : members){
                    qg.getGuild().addMember(member);
                }
            }
            return done;
        }catch (SQLException e) {
            e.printStackTrace();
            ConnectionCloser.closeConnection(con);
            throw new Error(e);
        }
    }

    public List<PairPG> getPartiesAndGuildsWhoHasDoneQuest(Quest quest) throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT party.id, " +
                    "party.reputation, guild.id, guild.reputation FROM party INNER JOIN guildPartyQuest " +
                    "ON fkParty = party.id INNER JOIN guild " +
                    "ON fkGuild = guild.id WHERE fkQuest = ?;");
            statement.setString(1, quest.getObjective());
            ResultSet rs = statement.executeQuery();
            List<PairPG> png = new LinkedList<>();
            while(rs.next()){
                png.add(PairPG.builder()
                        .party(
                                Party.builder()
                                .name(rs.getString(1))
                                .reputation(rs.getInt(2))
                                .members(new LinkedList<>())
                                .build())
                        .guild(
                                Guild.builder()
                                .name(rs.getString(3))
                                .reputation(rs.getInt(4))
                                .members(new LinkedList<>())
                                .build())
                        .build()
                );
            }
            ConnectionCloser.closeConnection(con);
            for(PairPG pg : png){
                List<Adventurer> members = guildAdventurerDAO.findMembersById(pg.getGuild().getName());
                for(Adventurer member : members){
                    pg.getGuild().addMember(member);
                }
                members = partyAdventurerDAO.findPartyMembersById(pg.getParty().getName());
                for(Adventurer member : members){
                    pg.getParty().addMember(member);
                }
            }
            return png;
        }catch (SQLException e) {
            e.printStackTrace();
            ConnectionCloser.closeConnection(con);
            throw new Error(e);
        }
    }
}

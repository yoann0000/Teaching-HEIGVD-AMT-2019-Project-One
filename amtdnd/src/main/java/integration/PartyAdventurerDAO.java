package integration;

import Model.Adventurer;
import Model.Party;
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
public class PartyAdventurerDAO implements IPartyAdventurerDAO{

    @Resource(lookup = "java:/jdbc/dnd")
    DataSource dataSource;

    @EJB
    IAuthenticationService authentificationService;

    public List<Adventurer> findPartyMembersById(String id) {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT player.id, gold, strength," +
                    "dexterity, constitution, intelligence, wisdom, charisma, experience, spendpoints, fkRace," +
                    "fkClasse FROM player INNER JOIN playerParty " +
                    "ON player.id = playerParty.fkPlayer WHERE fkParty = ?;");
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            List<Adventurer> members = new LinkedList<>();
            while(rs.next()){
                members.add(Adventurer.builder().name(rs.getString(1))
                        .gold(rs.getLong(2))
                        .str(rs.getInt(3))
                        .dex(rs.getInt(4))
                        .con(rs.getInt(5))
                        .inte(rs.getInt(6))
                        .wis(rs.getInt(7))
                        .cha(rs.getInt(8))
                        .experience(rs.getInt(9))
                        .spendpoints(rs.getInt(10))
                        .race(rs.getString(11))
                        .klass(rs.getString(12))
                        .build());
            }
            return members;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }

    public List<Party> findPlayerPartiesById(String id) {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT party.id, party.reputation" +
                    " FROM party INNER JOIN playerParty " +
                    "ON party.id = playerParty.fkParty WHERE fkPlayer = ?;");
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            List<Party> parties = new LinkedList<>();
            while(rs.next()){
                parties.add(Party.builder().name(rs.getString(1))
                        .reputation(rs.getInt(2))
                        .members(findPartyMembersById(rs.getString(1)))
                        .build());
            }
            return parties;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }

    public Adventurer create(Adventurer adventurer, Party party) throws DuplicateKeyException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("INSERT INTO playerParty (fkPlayer, fkParty)" +
                    " VALUES (?, ?);");
            statement.setString(1, adventurer.getName());
            statement.setString(2, party.getName());
            statement.execute();
            return adventurer;
        }catch (SQLException e){
            e.printStackTrace();
            throw new DuplicateKeyException(adventurer.getName() + " fait déjà partie de "  +party.getName());
        }finally {
            ConnectionCloser.closeConnection(con);
        }
    }

    public void deleteById(Adventurer adventurer, Party party) throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("DELETE FROM playerParty WHERE fkPlayer = ?" +
                    "AND fkParty = ?;");
            statement.setString(1, adventurer.getName());
            statement.setString(2, party.getName());
            int numberOfDeletedParties = statement.executeUpdate();
            if(numberOfDeletedParties != 1){
                throw new KeyNotFoundException("Could not find party " + party.getName() + " in " + adventurer.getName());
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }
}

package integration;

import Model.Adventurer;
import Model.Guild;
import business.IAuthenticationService;
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
public class GuildAdventurerDAO implements IGuildAdventurerDAO{

    @Resource(lookup = "java:/jdbc/dnd")
    DataSource dataSource;

    @EJB
    IAuthenticationService authentificationService;

    @EJB
    IGuildDAO guildDAO;

    @Override
    public void addGuildToAdventurer(Adventurer adventurer, Guild guild)  throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("UPDATE player SET fkGuild = ? WHERE id = ?;");
            statement.setString(1, guild.getName());
            statement.setString(2, adventurer.getName());
            int numberOfUpdatedAdventurers = statement.executeUpdate();
            if(numberOfUpdatedAdventurers != 1){
                throw new KeyNotFoundException("Could not find adventurer " + adventurer.getName() + " or guild "
                        + guild.getName());
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }

    @Override
    public void removeGuildFromAdventurer(Adventurer adventurer) throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("UPDATE player SET fkGuild = NULL WHERE id = ?;");
            statement.setString(1, adventurer.getName());
            int numberOfUpdatedAdventurers = statement.executeUpdate();
            if(numberOfUpdatedAdventurers != 1){
                throw new KeyNotFoundException("Could not find adventurer " + adventurer.getName());
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }

    public List<Adventurer> findMembersById(String id) {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT player.id, gold, strength," +
                    "dexterity, constitution, intelligence, wisdom, charisma, experience, spendpoints, fkRace," +
                    "fkClasse FROM player WHERE fkGuild = ?;");
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
            e.printStackTrace( );
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }

    @Override
    public Guild findAdventurerGuild(String id) {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT fkGuild FROM player WHERE id = ?;");
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            boolean hasRecord = rs.next();
            if(!hasRecord){
                return null;
            }
            Guild guild = guildDAO.findById(rs.getString(1));
            return guild;
        }catch (SQLException | KeyNotFoundException e) {
            e.printStackTrace( );
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }
}

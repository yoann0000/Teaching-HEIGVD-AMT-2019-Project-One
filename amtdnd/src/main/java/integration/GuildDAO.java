package integration;

import Model.Adventurer;
import Model.Guild;
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
public class GuildDAO implements IGuildDAO {
    @Resource(lookup = "java:/jdbc/dnd")
    DataSource dataSource;

    @EJB
    IAuthenticationService authentificationService;

    @EJB
    IGuildAdventurerDAO guildAdventurerDAO;

    @Override
    public Guild create(Guild entity) throws DuplicateKeyException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("INSERT INTO guild (id, reputation) VALUES (?, 0);");
            statement.setString(1, entity.getName());
            statement.execute();
            return entity;
        }catch (SQLException e){
            e.printStackTrace();
            throw new DuplicateKeyException(entity.getName() + " existe déjà.");
        }finally {
            ConnectionCloser.closeConnection(con);
        }
    }

    @Override
    public List<Guild> findAll(int offset, int limit){
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = null;
            if(limit == 0) {
                statement = con.prepareStatement("SELECT id, reputation FROM guild;");
            }else{
                statement = con.prepareStatement("SELECT id, reputation FROM guild LIMIT ? OFFSET ?;");
                statement.setInt(1, limit);
                statement.setInt(2, offset);
            }
            ResultSet rs = statement.executeQuery();
            List<Guild> guilds = new LinkedList<>();
            while(rs.next()){
                Guild existingGuild = Guild.builder()
                        .name(rs.getString(1))
                        .reputation(rs.getInt(2))
                        .members(new LinkedList<Adventurer>())
                        .build();
                guilds.add(existingGuild);
            }
            ConnectionCloser.closeConnection(con);
            for(Guild g : guilds) {
                List<Adventurer> members = guildAdventurerDAO.findMembersById(g.getName());
                for (Adventurer member : members) {
                    g.addMember(member);
                }
            }
            return guilds;
        }catch (SQLException e) {
            e.printStackTrace();
            ConnectionCloser.closeConnection(con);
            throw new Error(e);
        }
    }

    @Override
    public int nbOfRecord(){
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT COUNT(*) FROM guild;");
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }

    @Override
    public Guild findById(String id) throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT id, reputation FROM guild WHERE id = ?;");
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();

            boolean hasRecord = rs.next();
            if(!hasRecord){
                throw new KeyNotFoundException("Could not find guild " + id);
            }
            Guild existingGuild = Guild.builder()
                    .name(rs.getString(1))
                    .reputation(rs.getInt(2))
                    .members(new LinkedList<Adventurer>())
                    .build();
            ConnectionCloser.closeConnection(con);
            List<Adventurer> members = guildAdventurerDAO.findMembersById(id);
            for(Adventurer member : members){
                existingGuild.addMember(member);
            }
            return existingGuild;
        }catch (SQLException e) {
            e.printStackTrace();
            ConnectionCloser.closeConnection(con);
            throw new Error(e);
        }
    }

    @Override
    public void update(Guild entity) throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("UPDATE guild SET reputation = ? WHERE id = ?;");
            statement.setInt(1, entity.getReputation());
            statement.setString(2, entity.getName());
            int numberOfUpdatedGuilds = statement.executeUpdate();
            if(numberOfUpdatedGuilds != 1){
                throw new KeyNotFoundException("Could not find guild " + entity.getName());
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }

    @Override
    public void deleteById(String id) throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("DELETE FROM guild WHERE id = ?;");
            statement.setString(1, id);
            int numberOfDeletedGuilds = statement.executeUpdate();
            if(numberOfDeletedGuilds != 1){
                throw new KeyNotFoundException("Could not find guild " + id);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }
}

package integration;

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

@Stateless
public class GuildDAO implements IGuildDAO {
    @Resource(lookup = "java:/jdbc/dnd")
    DataSource dataSource;

    @EJB
    IAuthenticationService authentificationService;

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
            throw new Error(e);
        }finally {
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
            GuildAdventurerDAO members = new GuildAdventurerDAO();
            boolean hasRecord = rs.next();
            if(!hasRecord){
                throw new KeyNotFoundException("Could not find guild " + id);
            }
            Guild existingGuild = Guild.builder()
                    .name(rs.getString(1))
                    .reputation(rs.getInt(2))
                    .members(members.findMembersById(id))
                    .build();
            return existingGuild;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
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

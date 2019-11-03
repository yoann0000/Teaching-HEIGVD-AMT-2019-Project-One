package integration;


import Model.Party;
import business.IAuthenticationService;
import datastore.exception.DuplicateKeyException;
import datastore.exception.KeyNotFoundException;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PartyDAO implements IPartyDAO {

    @Resource(lookup = "java:/jdbc/dnd")
    DataSource dataSource;

    @EJB
    IAuthenticationService authentificationService;

    @Override
    public Party create(Party entity) throws DuplicateKeyException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("INSERT INTO party (id, reputation) VALUES (?, 0);");
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
    public Party findById(String id) throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT id, reputation FROM party WHERE id = ?;");
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            boolean hasRecord = rs.next();
            if(!hasRecord){
                throw new KeyNotFoundException("Could not find party " + id);
            }
            Party existingParty = Party.builder()
                    .name(rs.getString(1))
                    .reputation(rs.getInt(2))
                    .build();
            return existingParty;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }

    @Override
    public void update(Party entity) throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("UPDATE party SET reputation = ? WHERE id = ?;");
            statement.setInt(1, entity.getReputation());
            statement.setString(2, entity.getName());
            int numberOfUpdatedParties = statement.executeUpdate();
            if(numberOfUpdatedParties != 1){
                throw new KeyNotFoundException("Could not find party " + entity.getName());
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
            PreparedStatement statement = con.prepareStatement("DELETE FROM party WHERE id = ?;");
            statement.setString(1, id);
            int numberOfDeletedParties = statement.executeUpdate();
            if(numberOfDeletedParties != 1){
                throw new KeyNotFoundException("Could not find party " + id);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }
}

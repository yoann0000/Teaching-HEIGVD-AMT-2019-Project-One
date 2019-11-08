package integration;

import Model.Quest;
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
public class QuestDAO implements IQuestDAO {

    @Resource(lookup = "java:/jdbc/dnd")
    DataSource dataSource;

    @EJB
    IAuthenticationService authentificationService;

    @Override
    public Quest create(Quest entity) throws DuplicateKeyException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("INSERT INTO quest (id, description," +
                    "rewardGold, rewardExp) VALUES (?, ?, ?, ?);");
            statement.setString(1, entity.getObjective());
            statement.setString(2, entity.getDescription());
            statement.setInt(3, entity.getGold());
            statement.setInt(4, entity.getExp());
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
    public Quest findById(String objectif) throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT id, description," +
                           "rewardGold, rewardExp FROM quest WHERE id = ?;");
            statement.setString(1, objectif);
            ResultSet rs = statement.executeQuery();
            boolean hasRecord = rs.next();
            if(!hasRecord){
                throw new KeyNotFoundException("Could not find quest " + objectif);
            }
            Quest existingQuest = Quest.builder()
                    .objective(rs.getString(1))
                    .description(rs.getString(2))
                    .gold(rs.getInt(3))
                    .exp(rs.getInt(4))
                    .build();
            return existingQuest;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }

    @Override
    public void update(Quest entity) throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("UPDATE quest SET " +
                    "description = ?, rewardGold = ?, rewardExp = ? WHERE id = ?;");
            statement.setString(1, entity.getDescription());
            statement.setInt(2, entity.getGold());
            statement.setInt(3, entity.getExp());
            statement.setString(4, entity.getObjective());
            int numberOfUpdatedQuests = statement.executeUpdate();
            if(numberOfUpdatedQuests != 1){
                throw new KeyNotFoundException("Could not find quest " + entity.getObjective());
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }

    @Override
    public void deleteById(String objectif) throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("DELETE FROM quest WHERE id = ?;");
            statement.setString(1, objectif);
            int numberOfDeletedQuests = statement.executeUpdate();
            if(numberOfDeletedQuests != 1){
                throw new KeyNotFoundException("Could not find quest " + objectif);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }
}

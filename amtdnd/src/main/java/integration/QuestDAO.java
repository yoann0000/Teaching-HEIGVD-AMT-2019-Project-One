package integration;

import Model.Guild;
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
            PreparedStatement statement = con.prepareStatement("INSERT INTO quest (id, objective, description," +
                    "rewardGold, rewardExp) VALUES (?, ?, ?, ?, ?);");
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getObjective());
            statement.setString(3, entity.getDescription());
            statement.setInt(4, entity.getGold());
            statement.setInt(5, entity.getExp());
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
    public Quest findById(Long id) throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT id, objective, description," +
                           "rewardGold, rewardExp FROM guild WHERE id = ?;)");
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            boolean hasRecord = rs.next();
            if(!hasRecord){
                throw new KeyNotFoundException("Could not find quest " + id);
            }
            Quest existingQuest = Quest.builder()
                    .id(rs.getLong(1))
                    .objective(rs.getString(2))
                    .description(rs.getString(3))
                    .gold(rs.getInt(4))
                    .exp(rs.getInt(5))
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
            PreparedStatement statement = con.prepareStatement("UPDATE quest SET objective = ?," +
                    "description = ?, rewardGold = ?, rewardExp = ? WHERE id = ?;");
            statement.setString(1, entity.getObjective());
            statement.setString(2, entity.getDescription());
            statement.setInt(3, entity.getGold());
            statement.setInt(4, entity.getExp());
            statement.setLong(5, entity.getId());
            int numberOfUpdatedQuests = statement.executeUpdate();
            if(numberOfUpdatedQuests != 1){
                throw new KeyNotFoundException("Could not find quest n°" + entity.getId());
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }

    @Override
    public void deleteById(Long id) throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("DELETE FROM quest WHERE id = ?;");
            statement.setLong(1, id);
            int numberOfDeletedQuests = statement.executeUpdate();
            if(numberOfDeletedQuests != 1){
                throw new KeyNotFoundException("Could not find quest n°" + id);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }
}

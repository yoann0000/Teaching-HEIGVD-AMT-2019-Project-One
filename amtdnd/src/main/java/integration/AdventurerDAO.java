package integration;

import Model.Adventurer;
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
public class AdventurerDAO implements IAdventurerDAO {

    @Resource(lookup = "java:/jdbc/dnd")
    DataSource dataSource;

    @EJB
    IAuthenticationService authentificationService;

    @Override
    public Adventurer create(Adventurer entity) throws DuplicateKeyException {
        Connection con = null;
        try {
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("INSERT INTO player (id, password," +
                    "gold, strength, dexterity, constitution, intelligence," +
                    "wisdom, charisma, experience, spendpoints, fkRace, fkClasse) " +
                    "VALUES (?, ?, 0, 8, 8, 8, 8, 8, 8, 0, 0, ?, ?);");
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getRace());
            statement.setString(4, entity.getKlass());
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
    public Adventurer findById(String id) throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("SELECT (id, password, " +
                    "gold, strength, dexterity, constitution, intelligence," +
                    "wisdom, charisma, experience, spendpoints, fkRace, fkClasse  FROM player WHERE id = ?;");
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            boolean hasRecord = rs.next();
            if(!hasRecord){
                throw new KeyNotFoundException("Could not find adventurer " + id);
            }
            Adventurer existingAdventurer = Adventurer.builder()
                    .name(rs.getString(1))
                    .password(rs.getString(2))
                    .gold(rs.getLong(3))
                    .str(rs.getInt(4))
                    .dex(rs.getInt(5))
                    .con(rs.getInt(6))
                    .inte(rs.getInt(7))
                    .wis(rs.getInt(8))
                    .cha(rs.getInt(9))
                    .experience(rs.getInt(10))
                    .spendpoints(rs.getInt(11))
                    .race(rs.getString(12))
                    .klass(rs.getString(13))
                    .build();
            return existingAdventurer;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }

    @Override
    public void update(Adventurer entity) throws KeyNotFoundException {
        Connection con = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement("UPDATE player SET password = ?, gold = ?, strength = ?" +
                    ", dexterity = ?, constitution = ?, intelligence = ?, wisdom = ?, charisma = ?" +
                    ", experience = ?, spendpoints = ?, fkRace = ?, fkClasse = ? WHERE id = ?;");
            statement.setString(1, entity.getPassword());
            statement.setLong(2, entity.getGold());
            statement.setInt(3, entity.getStr());
            statement.setInt(4, entity.getDex());
            statement.setInt(5, entity.getCon());
            statement.setInt(6, entity.getInte());
            statement.setInt(7, entity.getWis());
            statement.setInt(8, entity.getCha());
            statement.setInt(9, entity.getExperience());
            statement.setInt(10, entity.getSpendpoints());
            statement.setString(11, entity.getRace());
            statement.setString(12, entity.getKlass());
            statement.setString(13, entity.getName());
            int numberOfUpdatedAdventurers = statement.executeUpdate();
            if(numberOfUpdatedAdventurers != 1){
                throw new KeyNotFoundException("Could not find adventurer " + entity.getName());
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
            PreparedStatement statement = con.prepareStatement("DELETE FROM player WHERE id = ?;");
            statement.setString(1, id);
            int numberOfDeletedAdventurers = statement.executeUpdate();
            if(numberOfDeletedAdventurers != 1){
                throw new KeyNotFoundException("Could not find adventurer " + id);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }
}

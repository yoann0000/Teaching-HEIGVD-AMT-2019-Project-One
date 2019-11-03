package integration;

import Model.Adventurer;
import business.IAuthenticationService;


import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class GuildAdventurerDAO {

    @Resource(lookup = "java:/jdbc/dnd")
    DataSource dataSource;

    @EJB
    IAuthenticationService authentificationService;

    List<Adventurer> findMembersById(String id) {
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
            e.printStackTrace();
            throw new Error(e);
        } finally {
            ConnectionCloser.closeConnection(con);
        }
    }
}

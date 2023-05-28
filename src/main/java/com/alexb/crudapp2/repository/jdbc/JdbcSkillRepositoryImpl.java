package com.alexb.crudapp2.repository.jdbc;

import com.alexb.crudapp2.config.MyDataSource;
import com.alexb.crudapp2.model.Skill;
import com.alexb.crudapp2.repository.SkillRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSkillRepositoryImpl implements SkillRepository {
    private static final MyDataSource dataSource = new MyDataSource();

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<Skill> readSkillsFromFile() {
        List<Skill> skills = new ArrayList<>();
        String sqlQuery = "SELECT * FROM skills";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            while (resultSet.next()) {
                Skill skill = new Skill();
                skill.setId(resultSet.getInt("id"));
                skill.setName(resultSet.getString("name"));
                skills.add(skill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skills;
    }

    @Override
    public Skill getById(Long id) {
        Skill skill = new Skill();
        String sqlQuery = "SELECT * FROM skills WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                skill.setId(resultSet.getLong("id"));
                skill.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skill;
    }

    @Override
    public List<Skill> getAll() {
        return readSkillsFromFile();
    }

    @Override
    public Skill save(Skill skill) {
        String sqlQuery = "INSERT INTO skills (name) VALUES (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, skill.getName());
            preparedStatement.executeUpdate();
            skill.setId(getIdBySkill(connection, skill));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skill;
    }

    private long getIdBySkill(Connection connection, Skill skill) throws SQLException {
        String sqlQuery = "SELECT id FROM skills WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, skill.getName());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("id");
                } else {
                    throw new SQLException("Developer ID not found");
                }
            }
        }
    }

    @Override
    public Skill update(Skill updateSkill) {
        String sqlQuery = "UPDATE skills SET name = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, updateSkill.getName());
            preparedStatement.setLong(2, updateSkill.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateSkill;
    }

    @Override
    public void deleteById(Long idDeletedSkill) {
        String sqlQuery1 = "DELETE FROM skills WHERE id = ?";
        String sqlQuery2 = "DELETE FROM developers_skills WHERE skills_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement1 = connection.prepareStatement(sqlQuery1);
             PreparedStatement preparedStatement2 = connection.prepareStatement(sqlQuery2)) {

            preparedStatement1.setLong(1, idDeletedSkill);
            preparedStatement1.executeUpdate();
            preparedStatement2.setLong(1, idDeletedSkill);
            preparedStatement2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


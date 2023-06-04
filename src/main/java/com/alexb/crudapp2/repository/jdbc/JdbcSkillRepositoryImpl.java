package com.alexb.crudapp2.repository.jdbc;

import com.alexb.crudapp2.config.JdbcUtils;
import com.alexb.crudapp2.entity.SkillEntity;
import com.alexb.crudapp2.repository.SkillRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSkillRepositoryImpl implements SkillRepository {

    @Override
    public SkillEntity getById(Long id) {
        SkillEntity skill = new SkillEntity();
        String sqlQuery = "SELECT * FROM skills WHERE id = ?";
        try (PreparedStatement preparedStatement = JdbcUtils.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                skill.setId(resultSet.getLong("id"));
                skill.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skill;
    }

    @Override
    public List<SkillEntity> getAll() {
        List<SkillEntity> skills = new ArrayList<>();
        String sqlQuery = "SELECT * FROM skills";

        try (PreparedStatement statement = JdbcUtils.prepareStatement(sqlQuery);
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            while (resultSet.next()) {
                SkillEntity skill = new SkillEntity();
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
    public SkillEntity save(SkillEntity skill) {
        String sqlQuery = "INSERT INTO skills (name) VALUES (?)";
        try (PreparedStatement preparedStatement = JdbcUtils.prepareStatementWithKeys(sqlQuery)) {
            preparedStatement.setString(1, skill.getName());
            preparedStatement.executeUpdate();

            long generatedId;
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getLong(1);
            } else {
                throw new RuntimeException("Id could not be assigned");
            }
            skill.setId(generatedId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return skill;
    }

    @Override
    public SkillEntity update(SkillEntity updateSkill) {
        String sqlQuery = "UPDATE skills SET name = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = JdbcUtils.prepareStatementWithKeys(sqlQuery)) {

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

        try (PreparedStatement preparedStatement1 = JdbcUtils.prepareStatement(sqlQuery1);
             PreparedStatement preparedStatement2 = JdbcUtils.prepareStatement(sqlQuery2)) {

            preparedStatement1.setLong(1, idDeletedSkill);
            preparedStatement1.executeUpdate();
            preparedStatement2.setLong(1, idDeletedSkill);
            preparedStatement2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


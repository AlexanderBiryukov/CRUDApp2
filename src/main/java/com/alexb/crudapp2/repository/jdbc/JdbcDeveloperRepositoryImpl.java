package com.alexb.crudapp2.repository.jdbc;

import com.alexb.crudapp2.config.JdbcUtils;
import com.alexb.crudapp2.entity.DeveloperEntity;
import com.alexb.crudapp2.entity.SkillEntity;
import com.alexb.crudapp2.entity.SpecialtyEntity;
import com.alexb.crudapp2.entity.Status;
import com.alexb.crudapp2.repository.DeveloperRepository;

import java.sql.*;
import java.util.*;
import java.util.List;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository {

    public DeveloperEntity settingDeveloperField(ResultSet resultSet) throws SQLException {
        DeveloperEntity developer = new DeveloperEntity();
        developer.setId(resultSet.getLong("developers.id"));
        developer.setFirstName(resultSet.getString("first_name"));
        developer.setLastName(resultSet.getString("last_name"));
        developer.setSpecialty(new SpecialtyEntity(resultSet.getLong("specialty_id"),
                resultSet.getString("specialty")));
        developer.setStatus(Status.valueOf(resultSet.getString("status")));
        developer.setSkills(new ArrayList<>());
        return developer;

    }

    private List<DeveloperEntity> mapResultSetToDeveloper(ResultSet resultSet) throws SQLException {
        DeveloperEntity developer = null;
        HashMap<Long, DeveloperEntity> developersMap = new HashMap<>();
        while (resultSet.next()) {
            long devId = resultSet.getLong("developers.id");
            if (developer == null || developer.getId() != devId) {
                developer = settingDeveloperField(resultSet);
                developersMap.put(devId, developer);
            }
            SkillEntity skill = new SkillEntity();
            skill.setId(resultSet.getLong("skills.id"));
            skill.setName(resultSet.getString("skills_name"));

            DeveloperEntity devTemp = developersMap.get(devId);
            devTemp.getSkills().add(skill);
            developersMap.put(devId, devTemp);
        }
        return new ArrayList<>(developersMap.values());
    }

    @Override
    public List<DeveloperEntity> getAll() {
        List<DeveloperEntity> developers = new ArrayList<>();
        String sqlQuery = """
                select developers.id,
                       developers.first_name,
                       developers.last_name,
                       developers.specialty,
                       developers.specialty_id,
                       developers.status,
                       skills.id,
                       skills.name as skills_name
                from developers
                         join developers_skills
                              on developers.id = developers_skills.developers_id
                         join skills on developers_skills.skills_id = skills.id
                         join specialties on developers.specialty_id = specialties.id
                where status = 'ACTIVE';""";

        try (PreparedStatement statement = JdbcUtils.prepareStatement(sqlQuery);
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            developers = mapResultSetToDeveloper(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developers;
    }

    @Override
    public DeveloperEntity save(DeveloperEntity developer) {
        String sqlQuery = "INSERT INTO developers (first_name, last_name, specialty, status, specialty_id)" +
                " VALUES(?, ?, ?, ?, ?);";
        long generatedId;
        try (PreparedStatement preparedStatement = JdbcUtils.prepareStatementWithKeys(sqlQuery)) {
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setString(3, developer.getSpecialty().getName());
            preparedStatement.setString(4, developer.getStatus().name());
            preparedStatement.setLong(5, developer.getSpecialty().getId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getLong(1);
            } else {
                throw new RuntimeException("Id could not be assigned");
            }
            developer.setId(generatedId);
            saveSkillsDeveloper(developer);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }

    private void saveSkillsDeveloper(DeveloperEntity developer) {
        String sqlQuery = "INSERT INTO developers_skills (developers_id, skills_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = JdbcUtils.prepareStatement(sqlQuery)) {
            for (SkillEntity skill : developer.getSkills()) {
                preparedStatement.setLong(1, developer.getId());
                preparedStatement.setLong(2, skill.getId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DeveloperEntity getById(Long id) {
        DeveloperEntity developer = null;
        String sqlQuery = """
                SELECT developers.id,
                       developers.first_name,
                       developers.last_name,
                       developers.specialty,
                       developers.specialty_id,
                       developers.status,
                       skills.id,
                       skills.name as skills_name
                FROM developers
                         JOIN developers_skills
                              ON developers.id = developers_skills.developers_id
                         JOIN skills on developers_skills.skills_id = skills.id
                         JOIN specialties on developers.specialty_id = specialties.id
                WHERE status = 'ACTIVE' and developers.id = ?;""";
        try (PreparedStatement preparedStatement = JdbcUtils.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (developer == null) {
                    developer = settingDeveloperField(resultSet);
                }
                developer.getSkills().add(new SkillEntity(resultSet.getLong("skills.id"),
                        resultSet.getString("skills_name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return developer;
    }


    @Override
    public DeveloperEntity update(DeveloperEntity updateDeveloper) {
        String sqlQuery = "UPDATE developers " +
                "SET first_name = ?, last_name = ?, specialty = ?, specialty_id = ? " +
                "WHERE id = ?;";

        try (PreparedStatement preparedStatement = JdbcUtils.prepareStatementWithKeys(sqlQuery)
        ) {
            deleteSkillsDeveloper(updateDeveloper.getId());

            preparedStatement.setString(1, updateDeveloper.getFirstName());
            preparedStatement.setString(2, updateDeveloper.getLastName());
            preparedStatement.setString(3, updateDeveloper.getSpecialty().getName());
            preparedStatement.setLong(4, updateDeveloper.getSpecialty().getId());
            preparedStatement.setLong(5, updateDeveloper.getId());
            preparedStatement.executeUpdate();

            saveSkillsDeveloper(updateDeveloper);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateDeveloper;
    }

    private void deleteSkillsDeveloper(long id) {
        String sqlQuery = "DELETE FROM developers_skills WHERE developers_id = ?";

        try (PreparedStatement preparedStatement = JdbcUtils.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long idDeletedDev) {
        String sqlQuery = "UPDATE developers SET status = 'DELETED' WHERE id = " + idDeletedDev;
        try (PreparedStatement preparedStatement = JdbcUtils.prepareStatement(sqlQuery)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


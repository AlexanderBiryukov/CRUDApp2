package com.alexb.crudapp2.repository.jdbc;

import com.alexb.crudapp2.config.MyDataSource;
import com.alexb.crudapp2.model.Developer;
import com.alexb.crudapp2.model.Skill;
import com.alexb.crudapp2.model.Specialty;
import com.alexb.crudapp2.model.Status;
import com.alexb.crudapp2.repository.DeveloperRepository;

import java.sql.*;
import java.util.*;
import java.util.List;

public class JdbcDeveloperRepositoryImpl implements DeveloperRepository {

    private static final MyDataSource dataSource = new MyDataSource();

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<Developer> readDevelopersFromFile() {
        List<Developer> developers = new ArrayList<>();
        String sqlQuery = "SELECT * FROM developers WHERE status = 'ACTIVE'";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {
            while (resultSet.next()) {
                Developer developer = new Developer();
                developer.setId(resultSet.getInt("id"));
                developer.setFirstName(resultSet.getString("first_name"));
                developer.setLastName(resultSet.getString("last_name"));
                developer.setStatus(Status.valueOf(resultSet.getString("status")));
                developer.setSkills(getSkillsForDeveloper(developer.getId()));

                Specialty specialty = new Specialty();
                specialty.setId(resultSet.getLong("specialty_id"));
                specialty.setName(resultSet.getString("specialty"));
                developer.setSpecialty(specialty);

                developers.add(developer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return developers;
    }

    private List<Skill> getSkillsForDeveloper(Long id) {
        List<Skill> listSkills = new ArrayList<>();
        String sqlQuery = "SELECT s.id, s.name FROM skills AS s " +
                "JOIN developers_skills AS ds ON s.id = ds.skills_id " +
                "WHERE ds.developers_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Skill skill = new Skill();
                skill.setId(resultSet.getInt("id"));
                skill.setName(resultSet.getString("name"));
                listSkills.add(skill);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listSkills;
    }


    @Override
    public Developer save(Developer developer) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            saveDeveloper(connection, developer);
            saveSkillsDeveloper(connection, developer);
            developer.setId(getIdByDeveloper(connection, developer));

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }

    private void saveDeveloper(Connection connection, Developer developer) {
        String sqlQuery = "INSERT INTO developers (first_name, last_name, specialty, status, specialty_id)" +
                " VALUES(?, ?, ?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setString(3, developer.getSpecialty().getName());
            preparedStatement.setString(4, developer.getStatus().name());
            preparedStatement.setLong(5, developer.getSpecialty().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveSkillsDeveloper(Connection connection, Developer developer) {
        String sqlQuery = "INSERT INTO developers_skills (developers_id, skills_id) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            long developerId = getIdByDeveloper(connection, developer);
            for (Skill skill : developer.getSkills()) {
                preparedStatement.setLong(1, developerId);
                preparedStatement.setLong(2, skill.getId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private long getIdByDeveloper(Connection connection, Developer developer) throws SQLException {
        String sqlQuery = "SELECT id FROM developers WHERE first_name = ? AND last_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
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
    public Developer getById(Long id) {
        Developer developer = new Developer();
        String sqlQuery = "SELECT * FROM developers WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                developer.setId(resultSet.getInt("id"));
                developer.setFirstName(resultSet.getString("first_name"));
                developer.setLastName(resultSet.getString("last_name"));
                Specialty specialty = new Specialty();
                specialty.setId(resultSet.getLong("specialty_id"));
                specialty.setName(resultSet.getString("specialty"));
                developer.setSpecialty(specialty);
                developer.setStatus(Status.valueOf(resultSet.getString("status")));
                developer.setSkills(getSkillsForDeveloper(developer.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return developer;
    }

    @Override
    public List<Developer> getAll() {
        return readDevelopersFromFile();
    }

    @Override
    public Developer update(Developer updateDeveloper) {
        String sqlQuery = "UPDATE developers " +
                "SET first_name = ?, last_name = ?, specialty = ?, specialty_id = ? " +
                "WHERE id = ?;";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)
        ) {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            Developer developer = getById(updateDeveloper.getId());   // получем из бд данные старого разработчика
            deleteSkillsDeveloper(connection, developer);          // удаляем старые данные о навыках разработчика

            preparedStatement.setString(1, updateDeveloper.getFirstName());
            preparedStatement.setString(2, updateDeveloper.getLastName());
            preparedStatement.setString(3, updateDeveloper.getSpecialty().getName());
            preparedStatement.setLong(4, updateDeveloper.getSpecialty().getId());
            preparedStatement.setLong(5, updateDeveloper.getId());
            preparedStatement.executeUpdate();

            saveSkillsDeveloper(connection, updateDeveloper);      // добавляем новые данные о навыках разработчика

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateDeveloper;
    }

    private void deleteSkillsDeveloper(Connection connection, Developer developer) {
        String sqlQuery = "DELETE FROM developers_skills WHERE developers_id = ? AND skills_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            long id = developer.getId();
            for (Skill skill : developer.getSkills()) {
                preparedStatement.setLong(1, id);
                preparedStatement.setLong(2, skill.getId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long idDeletedDev)  {
        String sqlQuery = "UPDATE developers SET status = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);

            deleteSkillsDeveloper(connection, getById(idDeletedDev)); // удаляем данные о нывыках разработчика

            preparedStatement.setString(1, Status.DELETED.name());
            preparedStatement.setLong(2, idDeletedDev);
            preparedStatement.executeUpdate();    // меняем статус разаработчика на DELETED

            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


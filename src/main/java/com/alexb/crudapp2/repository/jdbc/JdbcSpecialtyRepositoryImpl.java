package com.alexb.crudapp2.repository.jdbc;

import com.alexb.crudapp2.config.MyDataSource;
import com.alexb.crudapp2.model.Skill;
import com.alexb.crudapp2.model.Specialty;
import com.alexb.crudapp2.repository.SpecialtyRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JdbcSpecialtyRepositoryImpl implements SpecialtyRepository {
    private static final MyDataSource dataSource = new MyDataSource();

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private List<Specialty> readSpecialtiesFromFile() {
        List<Specialty> specialties = new ArrayList<>();
        String sql = "SELECT * FROM specialties";

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Specialty specialty = new Specialty();
                specialty.setId(resultSet.getInt("id"));
                specialty.setName(resultSet.getString("name"));
                specialties.add(specialty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialties;
    }

    @Override
    public Specialty getById(Long id) {
        Specialty specialty = new Specialty();
        String sqlQuery = "SELECT * FROM specialties WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                specialty.setId(resultSet.getLong("id"));
                specialty.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialty;
    }

    @Override
    public List<Specialty> getAll() {
        return readSpecialtiesFromFile();
    }

    @Override
    public Specialty save(Specialty specialty) {
        String sqlQuery = "INSERT INTO specialties (name) VALUES (?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, specialty.getName());
            preparedStatement.executeUpdate();
            specialty.setId(getIdBySpecialty(connection, specialty));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialty;
    }

    private long getIdBySpecialty(Connection connection,Specialty specialty) throws SQLException {
        String sqlQuery = "SELECT id FROM specialties WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, specialty.getName());
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
    public Specialty update(Specialty updateSpecialty) {
        String sqlQuery = "UPDATE specialties SET name = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, updateSpecialty.getName());
            preparedStatement.setLong(2, updateSpecialty.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updateSpecialty;
    }

    @Override
    public void deleteById(Long idDeletedSpec) {
        String sqlQuery1 = "DELETE FROM specialties WHERE id = ?";
        String sqlQuery2 = "UPDATE developers SET specialty_id = NULL WHERE specialty_id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement1 = connection.prepareStatement(sqlQuery1);
             PreparedStatement preparedStatement2 = connection.prepareStatement(sqlQuery2)) {

            preparedStatement1.setLong(1, idDeletedSpec);
            preparedStatement1.executeUpdate();
            preparedStatement2.setLong(1, idDeletedSpec);
            preparedStatement2.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package com.alexb.crudapp2.repository.jdbc;

import com.alexb.crudapp2.config.JdbcUtils;
import com.alexb.crudapp2.entity.SpecialtyEntity;
import com.alexb.crudapp2.repository.SpecialtyRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSpecialtyRepositoryImpl implements SpecialtyRepository {

    @Override
    public SpecialtyEntity getById(Long id) {
        SpecialtyEntity specialty = new SpecialtyEntity();
        String sqlQuery = "SELECT * FROM specialties WHERE id = ?";
        try (PreparedStatement preparedStatement = JdbcUtils.prepareStatement(sqlQuery)) {
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
    public List<SpecialtyEntity> getAll() {
        List<SpecialtyEntity> specialties = new ArrayList<>();
        String sql = "SELECT * FROM specialties";

        try (PreparedStatement statement = JdbcUtils.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                SpecialtyEntity specialty = new SpecialtyEntity();
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
    public SpecialtyEntity save(SpecialtyEntity specialty) {
        String sqlQuery = "INSERT INTO specialties (name) VALUES (?)";
        try (PreparedStatement preparedStatement = JdbcUtils.prepareStatementWithKeys(sqlQuery)) {
            preparedStatement.setString(1, specialty.getName());
            preparedStatement.executeUpdate();
            long generatedId;
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                generatedId = generatedKeys.getLong(1);
            } else {
                throw new RuntimeException("Id could not be assigned");
            }
            specialty.setId(generatedId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return specialty;
    }

    @Override
    public SpecialtyEntity update(SpecialtyEntity updateSpecialty) {
        String sqlQuery = "UPDATE specialties SET name = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = JdbcUtils.prepareStatement(sqlQuery)) {
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
        try (PreparedStatement preparedStatement1 = JdbcUtils.prepareStatement(sqlQuery1)) {
            preparedStatement1.setLong(1, idDeletedSpec);
            preparedStatement1.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

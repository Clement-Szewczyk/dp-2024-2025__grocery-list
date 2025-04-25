package com.fges.dao;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GroceryListDAOFactoryTest {

    @Test
    void createDAO_should_return_CSVGroceryListDAO_for_csv_format() {
        // When
        GroceryListDAO dao = GroceryListDAOFactory.createDAO("test.csv", "csv");

        // Then
        assertThat(dao).isInstanceOf(CSVGroceryListDAO.class);
    }

    @Test
    void createDAO_should_return_JSONGroceryListDAO_for_json_format() {
        // When
        GroceryListDAO dao = GroceryListDAOFactory.createDAO("test.json", "json");

        // Then
        assertThat(dao).isInstanceOf(JSONGroceryListDAO.class);
    }

    @Test
    void createDAO_should_return_JSONGroceryListDAO_for_unknown_format() {
        // When
        GroceryListDAO dao = GroceryListDAOFactory.createDAO("test.xyz", "xyz");

        // Then
        assertThat(dao).isInstanceOf(JSONGroceryListDAO.class);
    }

    @Test
    void createDAO_should_be_case_insensitive() {
        // When
        GroceryListDAO dao = GroceryListDAOFactory.createDAO("test.csv", "CSV");

        // Then
        assertThat(dao).isInstanceOf(CSVGroceryListDAO.class);
    }
}
package com.stayhard.repository;

import com.stayhard.domain.entities.User;
import com.stayhard.repository.jdbc.JdbcConnection;
import com.stayhard.repository.jdbc.JdbcUserRepository;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcUserRepositoryIntegrationTest {

    private JdbcConnection jdbcConnection;
    private JdbcUserRepository repository;

    @BeforeEach
    void setUp() throws SQLException {
        String dbName = "testdb_" + System.currentTimeMillis();
        String url = "jdbc:h2:mem:" + dbName + ";DB_CLOSE_DELAY=-1;MODE=PostgreSQL";
        String user = "sa";
        String password = "";

        Connection conn = DriverManager.getConnection(url, user, password);
        JdbcConnection.initializeTables(conn);
        conn.close();

        jdbcConnection = new JdbcConnection(url, user, password);
        repository = new JdbcUserRepository(jdbcConnection);
    }

    @Test
    void save_shouldPersistUser() {
        User user = User.create("john", "john@example.com");

        User saved = repository.save(user);

        assertNotNull(saved.id());
        assertEquals("john", saved.username());
    }

    @Test
    void findById_withExistingUser_shouldReturnUser() {
        User user = repository.save(User.create("john", "john@example.com"));

        Optional<User> found = repository.findById(user.id());

        assertTrue(found.isPresent());
        assertEquals("john", found.get().username());
    }

    @Test
    void findByUsername_shouldReturnUser() {
        repository.save(User.create("john", "john@example.com"));

        Optional<User> found = repository.findByUsername("john");

        assertTrue(found.isPresent());
        assertEquals("john@example.com", found.get().email());
    }

    @Test
    void findAll_shouldReturnAllUsers() {
        repository.save(User.create("john", "john@example.com"));
        repository.save(User.create("jane", "jane@example.com"));

        List<User> users = repository.findAll();

        assertEquals(2, users.size());
    }

    @Test
    void update_shouldModifyUser() {
        User user = repository.save(User.create("john", "john@example.com"));
        User updated = new User(
            user.id(), "johnny", "johnny@example.com",
            user.createdAt(), 5, 50, 10
        );

        User result = repository.update(updated);

        assertEquals("johnny", result.username());
        assertEquals(5, result.level());
    }

    @Test
    void delete_shouldRemoveUser() {
        User user = repository.save(User.create("john", "john@example.com"));

        repository.delete(user.id());

        assertTrue(repository.findById(user.id()).isEmpty());
    }

    @Test
    void existsByUsername_shouldReturnCorrectValue() {
        repository.save(User.create("john", "john@example.com"));

        assertTrue(repository.existsByUsername("john"));
        assertFalse(repository.existsByUsername("unknown"));
    }

    @Test
    void existsByEmail_shouldReturnCorrectValue() {
        repository.save(User.create("john", "john@example.com"));

        assertTrue(repository.existsByEmail("john@example.com"));
        assertFalse(repository.existsByEmail("unknown@example.com"));
    }
}

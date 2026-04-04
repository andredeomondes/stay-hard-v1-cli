package com.stayhard.repository;

import com.stayhard.domain.entities.Habit;
import com.stayhard.domain.entities.User;
import com.stayhard.domain.enums.Priority;
import com.stayhard.domain.enums.Status;
import com.stayhard.repository.jdbc.JdbcConnection;
import com.stayhard.repository.jdbc.JdbcHabitRepository;
import com.stayhard.repository.jdbc.JdbcUserRepository;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcHabitRepositoryIntegrationTest {

    private JdbcConnection jdbcConnection;
    private JdbcHabitRepository repository;
    private JdbcUserRepository userRepository;
    private Long testUserId;

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
        repository = new JdbcHabitRepository(jdbcConnection);
        userRepository = new JdbcUserRepository(jdbcConnection);

        User testUser = userRepository.save(User.create("testuser", "test@example.com"));
        testUserId = testUser.id();
    }

    @Test
    void save_shouldPersistHabit() {
        Habit habit = Habit.create("Exercise", "Daily workout", Priority.HIGH, testUserId);

        Habit saved = repository.save(habit);

        assertNotNull(saved.id());
        assertEquals("Exercise", saved.name());
    }

    @Test
    void findById_withExistingHabit_shouldReturnHabit() {
        Habit habit = repository.save(Habit.create("Exercise", "Daily", Priority.HIGH, testUserId));

        Optional<Habit> found = repository.findById(habit.id());

        assertTrue(found.isPresent());
        assertEquals("Exercise", found.get().name());
    }

    @Test
    void findById_withNonExistingId_shouldReturnEmpty() {
        Optional<Habit> found = repository.findById(999L);

        assertTrue(found.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllHabits() {
        repository.save(Habit.create("Exercise", "Daily", Priority.HIGH, testUserId));
        repository.save(Habit.create("Read", "Books", Priority.MEDIUM, testUserId));

        List<Habit> habits = repository.findAll();

        assertEquals(2, habits.size());
    }

    @Test
    void findByUserId_shouldReturnUserHabits() {
        User anotherUser = userRepository.save(User.create("another", "another@example.com"));
        
        repository.save(Habit.create("Exercise", "Daily", Priority.HIGH, testUserId));
        repository.save(Habit.create("Read", "Books", Priority.MEDIUM, anotherUser.id()));

        List<Habit> userHabits = repository.findByUserId(testUserId);

        assertEquals(1, userHabits.size());
        assertEquals(testUserId, userHabits.get(0).userId());
    }

    @Test
    void update_shouldModifyHabit() {
        Habit habit = repository.save(Habit.create("Exercise", "Daily", Priority.HIGH, testUserId));
        Habit updated = new Habit(
            habit.id(), "Exercise Updated", "New description",
            Priority.CRITICAL, Status.COMPLETED,
            habit.createdAt(), null, habit.deadline(), 1, null, habit.userId()
        );

        Habit result = repository.update(updated);

        assertEquals("Exercise Updated", result.name());
        assertEquals(Priority.CRITICAL, result.priority());
    }

    @Test
    void delete_shouldRemoveHabit() {
        Habit habit = repository.save(Habit.create("Exercise", "Daily", Priority.HIGH, testUserId));

        repository.delete(habit.id());

        assertTrue(repository.findById(habit.id()).isEmpty());
    }

    @Test
    void count_shouldReturnCorrectCount() {
        repository.save(Habit.create("Exercise", "Daily", Priority.HIGH, testUserId));
        repository.save(Habit.create("Read", "Books", Priority.MEDIUM, testUserId));

        int count = repository.count();

        assertEquals(2, count);
    }
}

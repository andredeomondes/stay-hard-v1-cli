-- Stay Hard System - Database Initialization Script
-- Run this script in DBeaver or psql to create the database

-- Create database (run as superuser)
-- CREATE DATABASE stayhard;

-- Connect to stayhard database and run:

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at DATE NOT NULL,
    level INTEGER DEFAULT 1,
    xp INTEGER DEFAULT 0,
    total_habits_completed INTEGER DEFAULT 0
);

-- Habits table
CREATE TABLE IF NOT EXISTS habits (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    priority VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at DATE NOT NULL,
    completed_at TIMESTAMP,
    deadline TIMESTAMP NOT NULL,
    streak INTEGER DEFAULT 0,
    last_completed_date DATE,
    user_id BIGINT REFERENCES users(id)
);

-- Indexes for better performance
CREATE INDEX IF NOT EXISTS idx_habits_user_id ON habits(user_id);
CREATE INDEX IF NOT EXISTS idx_habits_status ON habits(status);
CREATE INDEX IF NOT EXISTS idx_habits_priority ON habits(priority);
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);

-- Sample data (optional)
-- INSERT INTO users (username, email, created_at, level, xp, total_habits_completed)
-- VALUES ('demo', 'demo@example.com', CURRENT_DATE, 1, 0, 0);

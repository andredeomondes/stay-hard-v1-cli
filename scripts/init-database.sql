-- ============================================
-- Script SQL para criar banco de dados Stay Hard
-- Execute no PostgreSQL (pgAdmin ou psql)
-- ============================================

-- 1. Criar banco de dados (se não existir)
-- CREATE DATABASE stayhard;

-- 2. Conectar ao banco stayhard e executar:

-- Criar tabela de usuários
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    days_completed INT DEFAULT 0,
    days_failed INT DEFAULT 0,
    current_streak INT DEFAULT 0,
    max_streak INT DEFAULT 0
);

-- Criar tabela de hábitos
CREATE TABLE IF NOT EXISTS habits (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    priority VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL
);

-- Inserir usuário padrão (se tabela vazia)
INSERT INTO users (name, days_completed, days_failed, current_streak, max_streak)
SELECT 'Player', 0, 0, 0, 0
WHERE NOT EXISTS (SELECT 1 FROM users LIMIT 1);

-- ============================================
-- Configurações de conexão JDBC:
-- 
-- URL: jdbc:postgresql://localhost:5432/stayhard
-- Usuário: postgres
-- Senha: admin
-- ============================================

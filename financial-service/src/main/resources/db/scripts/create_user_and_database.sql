CREATE DATABASE financial_service;

CREATE USER financial_service_user WITH PASSWORD 'financial_service_password';
GRANT ALL PRIVILEGES ON DATABASE financial_service TO financial_service_user;

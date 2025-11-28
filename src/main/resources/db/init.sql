-- Database initialization script for Shashank Project
-- This script creates the user table with the defined schema

-- Enable UUID extension if not already enabled
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Drop the table if it exists (for fresh starts)
DROP TABLE IF EXISTS "user" CASCADE;

-- Create the user table
CREATE TABLE "user" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    mobile_number VARCHAR(20),
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_on TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now(),
    updated_on TIMESTAMP WITH TIME ZONE,
    created_by UUID,
    updated_by UUID,
    
    -- Self-referential foreign keys
    CONSTRAINT fk_user_created_by FOREIGN KEY (created_by) REFERENCES "user"(id) ON DELETE SET NULL,
    CONSTRAINT fk_user_updated_by FOREIGN KEY (updated_by) REFERENCES "user"(id) ON DELETE SET NULL
);

-- Create indexes for commonly queried columns
CREATE INDEX idx_user_email ON "user"(email);
CREATE INDEX idx_user_is_active ON "user"(is_active);
CREATE INDEX idx_user_created_on ON "user"(created_on);

-- Insert a sample admin user (password is BCrypt hash of 'admin123')
INSERT INTO "user" (id, name, email, password, mobile_number, is_active, created_on)
VALUES (
    gen_random_uuid(),
    'Admin User',
    'admin@shashank.com',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZRGdjGj/n3Rtrj/IqRvKvH4LCiGy.',
    '9999999999',
    true,
    now()
);

-- Grant permissions (if needed for application user)
-- GRANT ALL PRIVILEGES ON TABLE "user" TO shashank;
-- GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO shashank;

COMMENT ON TABLE "user" IS 'Stores user information for the Shashank Project';
COMMENT ON COLUMN "user".id IS 'Unique identifier for the user';
COMMENT ON COLUMN "user".name IS 'Full name of the user';
COMMENT ON COLUMN "user".email IS 'Email address (used for login)';
COMMENT ON COLUMN "user".password IS 'BCrypt hashed password';
COMMENT ON COLUMN "user".mobile_number IS 'Phone number';
COMMENT ON COLUMN "user".is_active IS 'Account active status';
COMMENT ON COLUMN "user".created_on IS 'Record creation timestamp';
COMMENT ON COLUMN "user".updated_on IS 'Last update timestamp';
COMMENT ON COLUMN "user".created_by IS 'UUID of user who created this record';
COMMENT ON COLUMN "user".updated_by IS 'UUID of user who last updated this record';


-- Create tables
CREATE TABLE products (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    currency CHAR(3) NOT NULL
);

-- Insert sample data
INSERT INTO products (name, description, price, currency)
VALUES
    ('Smartphone', 'High-end smartphone with 128GB storage.', 699.99, 'USD'),
    ('Laptop', '14-inch laptop with 16GB RAM.', 1199.99, 'USD'),
    ('Headphones', 'Noise-cancelling over-ear headphones.', 199.99, 'USD'),
    ('Coffee Maker', 'Automatic coffee maker with grinder.', 129.99, 'USD');
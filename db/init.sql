CREATE TABLE products (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    price NUMERIC(10, 2) NOT NULL,
    currency CHAR(3) NOT NULL
);

INSERT INTO products (id, name, description, price, currency)
VALUES
    ('123e4567-e89b-12d3-a456-426614174001', 'Smartphone', 'High-end smartphone with 128GB storage.', 699.99, 'USD'),
    ('123e4567-e89b-12d3-a456-426614174002', 'Laptop', '14-inch laptop with 16GB RAM.', 1199.99, 'USD'),
    ('123e4567-e89b-12d3-a456-426614174003', 'Headphones', 'Noise-cancelling over-ear headphones.', 300.00, 'USD'),
    ('123e4567-e89b-12d3-a456-426614174033', 'Headphones2', 'Noise-cancelling over-ear headphones.', 300.00, 'USD'),
    ('123e4567-e89b-12d3-a456-426614174004', 'Coffee Maker', 'Automatic coffee maker with grinder.', 129.99, 'USD');
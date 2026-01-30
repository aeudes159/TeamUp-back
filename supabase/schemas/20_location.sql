CREATE TABLE public.location (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(150),
                          address TEXT,
                          average_price DECIMAL(10,2),
                          picture_url TEXT
);
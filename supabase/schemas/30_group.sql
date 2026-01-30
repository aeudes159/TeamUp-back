CREATE TABLE public.group (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(150) NOT NULL,
                       cover_picture_url TEXT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       is_public BOOLEAN NOT NULL
);

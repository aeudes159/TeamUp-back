CREATE TABLE public.user (
                      id SERIAL PRIMARY KEY,
                      first_name VARCHAR(100),
                      last_name VARCHAR(100),
                      age INT,
                      phone_number VARCHAR(20),
                      address TEXT,
                      profile_picture_url TEXT
);
CREATE TABLE public.rating (
                        id SERIAL PRIMARY KEY,
                        rating_value INT CHECK (rating_value BETWEEN 0 AND 5),
                        user_id INT REFERENCES public.user(id),
                        location_id INT REFERENCES public.location(id),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
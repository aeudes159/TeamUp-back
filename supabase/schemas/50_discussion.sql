CREATE TABLE public.discussion (
                            id SERIAL PRIMARY KEY,
                            group_id INT REFERENCES public.group(id) ON DELETE CASCADE,
                            background_image_url TEXT,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE public.message (
                         id SERIAL PRIMARY KEY,
                         content TEXT,
                         image_url TEXT,
                         sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         sender_id INT REFERENCES public.user(id),
                         discussion_id INT REFERENCES public.discussion(id)
);
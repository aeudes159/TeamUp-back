CREATE TABLE public.post (
                      id SERIAL PRIMARY KEY,
                      content TEXT,
                      image_url TEXT,
                      posted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      author_id INT REFERENCES public.user(id),
                      location_id INT REFERENCES public.location(id),
                      discussion_id INT REFERENCES public.discussion(id)
);
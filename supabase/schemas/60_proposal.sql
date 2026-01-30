CREATE TABLE public.proposal (
                          id SERIAL PRIMARY KEY,
                          discussion_id INT REFERENCES public.discussion(id),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
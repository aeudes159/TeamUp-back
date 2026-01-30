CREATE TABLE public.group_member (
                              group_id INT REFERENCES public.group(id) ON DELETE CASCADE,
                              user_id INT REFERENCES public.user(id) ON DELETE CASCADE,
                              joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              PRIMARY KEY (group_id, user_id)
);
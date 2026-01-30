CREATE TABLE public.proposal_rating (
                                 proposal_id INT REFERENCES public.proposal(id) ON DELETE CASCADE,
                                 rating_id INT REFERENCES public.rating(id) ON DELETE CASCADE,
                                 PRIMARY KEY (proposal_id, rating_id)
);
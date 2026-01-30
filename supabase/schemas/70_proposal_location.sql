CREATE TABLE public.proposal_location (
                                   proposal_id INT REFERENCES public.proposal(id) ON DELETE CASCADE,
                                   location_id INT REFERENCES public.location(id) ON DELETE CASCADE,
                                   PRIMARY KEY (proposal_id, location_id)
);
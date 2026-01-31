create table if not exists public.group_location_history (
    group_id integer references public."group"(id) on delete cascade,
    location_id integer references public.location(id) on delete cascade,
    added_at timestamp default current_timestamp,
    primary key (group_id, location_id)
);

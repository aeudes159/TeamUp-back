-- Sequences
create sequence if not exists poll_id_seq;
create sequence if not exists poll_option_id_seq;
create sequence if not exists poll_vote_id_seq;

-- Poll table
create table public.poll (
    id integer primary key default nextval('poll_id_seq'),
    title varchar(255) not null,
    description text,
    discussion_id integer references public.discussion(id) on delete cascade,
    creator_id integer references public."user"(id) on delete set null,
    created_at timestamp default current_timestamp,
    is_active boolean default true,
    closed_at timestamp default null
);

-- Poll option (each option is a location)
create table public.poll_option (
    id integer primary key default nextval('poll_option_id_seq'),
    poll_id integer references public.poll(id) on delete cascade,
    location_id integer references public.location(id) on delete cascade,
    added_by_user_id integer references public."user"(id) on delete set null,
    created_at timestamp default current_timestamp,
    unique(poll_id, location_id)
);

-- Vote table
create table public.poll_vote (
    id integer primary key default nextval('poll_vote_id_seq'),
    poll_option_id integer references public.poll_option(id) on delete cascade,
    user_id integer references public."user"(id) on delete cascade,
    created_at timestamp default current_timestamp,
    unique(poll_option_id, user_id)
);

-- Indexes
create index idx_poll_discussion on public.poll(discussion_id);
create index idx_poll_option_poll on public.poll_option(poll_id);
create index idx_poll_vote_option on public.poll_vote(poll_option_id);

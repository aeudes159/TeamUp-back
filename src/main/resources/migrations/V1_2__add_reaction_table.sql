create sequence if not exists reaction_id_seq;

create table if not exists public.reaction (
    id integer primary key default nextval('reaction_id_seq'),
    emoji varchar(10) not null,
    user_id integer references public."user"(id) on delete cascade,
    message_id integer references public.message(id) on delete cascade,
    created_at timestamp default current_timestamp,
    unique (user_id, message_id, emoji)
);

create index if not exists idx_reaction_message_id on public.reaction(message_id);
create index if not exists idx_reaction_user_id on public.reaction(user_id);

-- Create comment table for post comments
create sequence if not exists comment_id_seq;

create table if not exists public.comment (
    id integer primary key default nextval('comment_id_seq'),
    content text not null,
    author_id integer not null references public."user"(id) on delete cascade,
    post_id integer not null references public.post(id) on delete cascade,
    created_at timestamp default current_timestamp
);

create index if not exists idx_comment_post_id on public.comment(post_id);
create index if not exists idx_comment_author_id on public.comment(author_id);

-- Extend reactions to support comments
-- First, make message_id nullable
alter table public.reaction alter column message_id drop not null;

-- Add comment_id column
alter table public.reaction add column if not exists comment_id integer references public.comment(id) on delete cascade;

-- Create index for comment reactions
create index if not exists idx_reaction_comment_id on public.reaction(comment_id);

-- Drop the old unique constraint and create a new one that accounts for both message and comment reactions
alter table public.reaction drop constraint if exists reaction_user_id_message_id_emoji_key;

-- Create new unique constraints for message reactions and comment reactions separately
create unique index if not exists idx_reaction_message_unique on public.reaction(user_id, message_id, emoji) where message_id is not null;
create unique index if not exists idx_reaction_comment_unique on public.reaction(user_id, comment_id, emoji) where comment_id is not null;

-- Add check constraint to ensure reaction has either message_id OR comment_id (not both, not neither)
alter table public.reaction add constraint reaction_target_check 
    check ((message_id is not null and comment_id is null) or 
           (message_id is null and comment_id is not null));

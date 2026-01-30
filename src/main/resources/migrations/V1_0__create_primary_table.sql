create sequence if not exists activity_feed_id_seq;
create sequence if not exists discussion_id_seq;
create sequence if not exists group_id_seq;
create sequence if not exists location_id_seq;
create sequence if not exists message_id_seq;
create sequence if not exists post_id_seq;
create sequence if not exists proposal_id_seq;
create sequence if not exists rating_id_seq;
create sequence if not exists user_id_seq;

create table public.activity_feed (
                                      id integer primary key default nextval('activity_feed_id_seq')
);

create table public."group" (
                                id integer primary key default nextval('group_id_seq'),
                                name varchar(150) not null,
                                cover_picture_url text,
                                created_at timestamp default current_timestamp,
                                is_public boolean not null
);

create table public."user" (
                               id integer primary key default nextval('user_id_seq'),
                               first_name varchar(100),
                               last_name varchar(100),
                               age integer,
                               phone_number varchar(20),
                               address text,
                               profile_picture_url text
);

create table public.discussion (
                                   id integer primary key default nextval('discussion_id_seq'),
                                   group_id integer references public."group"(id) on delete set null,
                                   background_image_url text,
                                   created_at timestamp default current_timestamp
);

create table public.location (
                                 id integer primary key default nextval('location_id_seq'),
                                 name varchar(150),
                                 address text,
                                 average_price numeric(10,2),
                                 picture_url text
);

create table public.message (
                                id integer primary key default nextval('message_id_seq'),
                                content text,
                                image_url text,
                                sent_at timestamp default current_timestamp,
                                sender_id integer references public."user"(id) on delete set null,
                                discussion_id integer references public.discussion(id) on delete cascade
);

create table public.post (
                             id integer primary key default nextval('post_id_seq'),
                             content text,
                             image_url text,
                             posted_at timestamp default current_timestamp,
                             author_id integer references public."user"(id) on delete set null,
                             location_id integer references public.location(id) on delete set null,
                             discussion_id integer references public.discussion(id) on delete cascade
);

create table public.proposal (
                                 id integer primary key default nextval('proposal_id_seq'),
                                 discussion_id integer references public.discussion(id) on delete cascade,
                                 created_at timestamp default current_timestamp
);

create table public.rating (
                               id integer primary key default nextval('rating_id_seq'),
                               rating_value integer,
                               user_id integer references public."user"(id) on delete cascade,
                               location_id integer references public.location(id) on delete cascade,
                               created_at timestamp default current_timestamp
);

create table public.activity_feed_post (
                                           activity_feed_id integer references public.activity_feed(id) on delete cascade,
                                           post_id integer references public.post(id) on delete cascade,
                                           primary key (activity_feed_id, post_id)
);

create table public.group_member (
                                     group_id integer references public."group"(id) on delete cascade,
                                     user_id integer references public."user"(id) on delete cascade,
                                     joined_at timestamp default current_timestamp,
                                     primary key (group_id, user_id)
);

create table public.proposal_location (
                                          proposal_id integer references public.proposal(id) on delete cascade,
                                          location_id integer references public.location(id) on delete cascade,
                                          primary key (proposal_id, location_id)
);

create table public.proposal_rating (
                                        proposal_id integer references public.proposal(id) on delete cascade,
                                        rating_id integer references public.rating(id) on delete cascade,
                                        primary key (proposal_id, rating_id)
);

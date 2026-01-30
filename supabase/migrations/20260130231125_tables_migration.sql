create sequence "public"."activity_feed_id_seq";

create sequence "public"."discussion_id_seq";

create sequence "public"."group_id_seq";

create sequence "public"."location_id_seq";

create sequence "public"."message_id_seq";

create sequence "public"."post_id_seq";

create sequence "public"."proposal_id_seq";

create sequence "public"."rating_id_seq";

create sequence "public"."user_id_seq";


  create table "public"."activity_feed" (
    "id" integer not null default nextval('public.activity_feed_id_seq'::regclass)
      );



  create table "public"."activity_feed_post" (
    "activity_feed_id" integer not null,
    "post_id" integer not null
      );



  create table "public"."discussion" (
    "id" integer not null default nextval('public.discussion_id_seq'::regclass),
    "group_id" integer,
    "background_image_url" text,
    "created_at" timestamp without time zone default CURRENT_TIMESTAMP
      );



  create table "public"."group" (
    "id" integer not null default nextval('public.group_id_seq'::regclass),
    "name" character varying(150) not null,
    "cover_picture_url" text,
    "created_at" timestamp without time zone default CURRENT_TIMESTAMP,
    "is_public" boolean not null
      );



  create table "public"."group_member" (
    "group_id" integer not null,
    "user_id" integer not null,
    "joined_at" timestamp without time zone default CURRENT_TIMESTAMP
      );



  create table "public"."location" (
    "id" integer not null default nextval('public.location_id_seq'::regclass),
    "name" character varying(150),
    "address" text,
    "average_price" numeric(10,2),
    "picture_url" text
      );



  create table "public"."message" (
    "id" integer not null default nextval('public.message_id_seq'::regclass),
    "content" text,
    "image_url" text,
    "sent_at" timestamp without time zone default CURRENT_TIMESTAMP,
    "sender_id" integer,
    "discussion_id" integer
      );



  create table "public"."post" (
    "id" integer not null default nextval('public.post_id_seq'::regclass),
    "content" text,
    "image_url" text,
    "posted_at" timestamp without time zone default CURRENT_TIMESTAMP,
    "author_id" integer,
    "location_id" integer,
    "discussion_id" integer
      );



  create table "public"."proposal" (
    "id" integer not null default nextval('public.proposal_id_seq'::regclass),
    "discussion_id" integer,
    "created_at" timestamp without time zone default CURRENT_TIMESTAMP
      );



  create table "public"."proposal_location" (
    "proposal_id" integer not null,
    "location_id" integer not null
      );



  create table "public"."proposal_rating" (
    "proposal_id" integer not null,
    "rating_id" integer not null
      );



  create table "public"."rating" (
    "id" integer not null default nextval('public.rating_id_seq'::regclass),
    "rating_value" integer,
    "user_id" integer,
    "location_id" integer,
    "created_at" timestamp without time zone default CURRENT_TIMESTAMP
      );



  create table "public"."user" (
    "id" integer not null default nextval('public.user_id_seq'::regclass),
    "first_name" character varying(100),
    "last_name" character varying(100),
    "age" integer,
    "phone_number" character varying(20),
    "address" text,
    "profile_picture_url" text
      );


alter sequence "public"."activity_feed_id_seq" owned by "public"."activity_feed"."id";

alter sequence "public"."discussion_id_seq" owned by "public"."discussion"."id";

alter sequence "public"."group_id_seq" owned by "public"."group"."id";

alter sequence "public"."location_id_seq" owned by "public"."location"."id";

alter sequence "public"."message_id_seq" owned by "public"."message"."id";

alter sequence "public"."post_id_seq" owned by "public"."post"."id";

alter sequence "public"."proposal_id_seq" owned by "public"."proposal"."id";

alter sequence "public"."rating_id_seq" owned by "public"."rating"."id";

alter sequence "public"."user_id_seq" owned by "public"."user"."id";

CREATE UNIQUE INDEX activity_feed_pkey ON public.activity_feed USING btree (id);

CREATE UNIQUE INDEX activity_feed_post_pkey ON public.activity_feed_post USING btree (activity_feed_id, post_id);

CREATE UNIQUE INDEX discussion_pkey ON public.discussion USING btree (id);

CREATE UNIQUE INDEX group_member_pkey ON public.group_member USING btree (group_id, user_id);

CREATE UNIQUE INDEX group_pkey ON public."group" USING btree (id);

CREATE UNIQUE INDEX location_pkey ON public.location USING btree (id);

CREATE UNIQUE INDEX message_pkey ON public.message USING btree (id);

CREATE UNIQUE INDEX post_pkey ON public.post USING btree (id);

CREATE UNIQUE INDEX proposal_location_pkey ON public.proposal_location USING btree (proposal_id, location_id);

CREATE UNIQUE INDEX proposal_pkey ON public.proposal USING btree (id);

CREATE UNIQUE INDEX proposal_rating_pkey ON public.proposal_rating USING btree (proposal_id, rating_id);

CREATE UNIQUE INDEX rating_pkey ON public.rating USING btree (id);

CREATE UNIQUE INDEX user_pkey ON public."user" USING btree (id);

alter table "public"."activity_feed" add constraint "activity_feed_pkey" PRIMARY KEY using index "activity_feed_pkey";

alter table "public"."activity_feed_post" add constraint "activity_feed_post_pkey" PRIMARY KEY using index "activity_feed_post_pkey";

alter table "public"."discussion" add constraint "discussion_pkey" PRIMARY KEY using index "discussion_pkey";

alter table "public"."group" add constraint "group_pkey" PRIMARY KEY using index "group_pkey";

alter table "public"."group_member" add constraint "group_member_pkey" PRIMARY KEY using index "group_member_pkey";

alter table "public"."location" add constraint "location_pkey" PRIMARY KEY using index "location_pkey";

alter table "public"."message" add constraint "message_pkey" PRIMARY KEY using index "message_pkey";

alter table "public"."post" add constraint "post_pkey" PRIMARY KEY using index "post_pkey";

alter table "public"."proposal" add constraint "proposal_pkey" PRIMARY KEY using index "proposal_pkey";

alter table "public"."proposal_location" add constraint "proposal_location_pkey" PRIMARY KEY using index "proposal_location_pkey";

alter table "public"."proposal_rating" add constraint "proposal_rating_pkey" PRIMARY KEY using index "proposal_rating_pkey";

alter table "public"."rating" add constraint "rating_pkey" PRIMARY KEY using index "rating_pkey";

alter table "public"."user" add constraint "user_pkey" PRIMARY KEY using index "user_pkey";

alter table "public"."activity_feed_post" add constraint "activity_feed_post_activity_feed_id_fkey" FOREIGN KEY (activity_feed_id) REFERENCES public.activity_feed(id) ON DELETE CASCADE not valid;

alter table "public"."activity_feed_post" validate constraint "activity_feed_post_activity_feed_id_fkey";

alter table "public"."activity_feed_post" add constraint "activity_feed_post_post_id_fkey" FOREIGN KEY (post_id) REFERENCES public.post(id) ON DELETE CASCADE not valid;

alter table "public"."activity_feed_post" validate constraint "activity_feed_post_post_id_fkey";

alter table "public"."discussion" add constraint "discussion_group_id_fkey" FOREIGN KEY (group_id) REFERENCES public."group"(id) ON DELETE CASCADE not valid;

alter table "public"."discussion" validate constraint "discussion_group_id_fkey";

alter table "public"."group_member" add constraint "group_member_group_id_fkey" FOREIGN KEY (group_id) REFERENCES public."group"(id) ON DELETE CASCADE not valid;

alter table "public"."group_member" validate constraint "group_member_group_id_fkey";

alter table "public"."group_member" add constraint "group_member_user_id_fkey" FOREIGN KEY (user_id) REFERENCES public."user"(id) ON DELETE CASCADE not valid;

alter table "public"."group_member" validate constraint "group_member_user_id_fkey";

alter table "public"."message" add constraint "message_discussion_id_fkey" FOREIGN KEY (discussion_id) REFERENCES public.discussion(id) not valid;

alter table "public"."message" validate constraint "message_discussion_id_fkey";

alter table "public"."message" add constraint "message_sender_id_fkey" FOREIGN KEY (sender_id) REFERENCES public."user"(id) not valid;

alter table "public"."message" validate constraint "message_sender_id_fkey";

alter table "public"."post" add constraint "post_author_id_fkey" FOREIGN KEY (author_id) REFERENCES public."user"(id) not valid;

alter table "public"."post" validate constraint "post_author_id_fkey";

alter table "public"."post" add constraint "post_discussion_id_fkey" FOREIGN KEY (discussion_id) REFERENCES public.discussion(id) not valid;

alter table "public"."post" validate constraint "post_discussion_id_fkey";

alter table "public"."post" add constraint "post_location_id_fkey" FOREIGN KEY (location_id) REFERENCES public.location(id) not valid;

alter table "public"."post" validate constraint "post_location_id_fkey";

alter table "public"."proposal" add constraint "proposal_discussion_id_fkey" FOREIGN KEY (discussion_id) REFERENCES public.discussion(id) not valid;

alter table "public"."proposal" validate constraint "proposal_discussion_id_fkey";

alter table "public"."proposal_location" add constraint "proposal_location_location_id_fkey" FOREIGN KEY (location_id) REFERENCES public.location(id) ON DELETE CASCADE not valid;

alter table "public"."proposal_location" validate constraint "proposal_location_location_id_fkey";

alter table "public"."proposal_location" add constraint "proposal_location_proposal_id_fkey" FOREIGN KEY (proposal_id) REFERENCES public.proposal(id) ON DELETE CASCADE not valid;

alter table "public"."proposal_location" validate constraint "proposal_location_proposal_id_fkey";

alter table "public"."proposal_rating" add constraint "proposal_rating_proposal_id_fkey" FOREIGN KEY (proposal_id) REFERENCES public.proposal(id) ON DELETE CASCADE not valid;

alter table "public"."proposal_rating" validate constraint "proposal_rating_proposal_id_fkey";

alter table "public"."proposal_rating" add constraint "proposal_rating_rating_id_fkey" FOREIGN KEY (rating_id) REFERENCES public.rating(id) ON DELETE CASCADE not valid;

alter table "public"."proposal_rating" validate constraint "proposal_rating_rating_id_fkey";

alter table "public"."rating" add constraint "rating_location_id_fkey" FOREIGN KEY (location_id) REFERENCES public.location(id) not valid;

alter table "public"."rating" validate constraint "rating_location_id_fkey";

alter table "public"."rating" add constraint "rating_rating_value_check" CHECK (((rating_value >= 0) AND (rating_value <= 5))) not valid;

alter table "public"."rating" validate constraint "rating_rating_value_check";

alter table "public"."rating" add constraint "rating_user_id_fkey" FOREIGN KEY (user_id) REFERENCES public."user"(id) not valid;

alter table "public"."rating" validate constraint "rating_user_id_fkey";

grant delete on table "public"."activity_feed" to "anon";

grant insert on table "public"."activity_feed" to "anon";

grant references on table "public"."activity_feed" to "anon";

grant select on table "public"."activity_feed" to "anon";

grant trigger on table "public"."activity_feed" to "anon";

grant truncate on table "public"."activity_feed" to "anon";

grant update on table "public"."activity_feed" to "anon";

grant delete on table "public"."activity_feed" to "authenticated";

grant insert on table "public"."activity_feed" to "authenticated";

grant references on table "public"."activity_feed" to "authenticated";

grant select on table "public"."activity_feed" to "authenticated";

grant trigger on table "public"."activity_feed" to "authenticated";

grant truncate on table "public"."activity_feed" to "authenticated";

grant update on table "public"."activity_feed" to "authenticated";

grant delete on table "public"."activity_feed" to "service_role";

grant insert on table "public"."activity_feed" to "service_role";

grant references on table "public"."activity_feed" to "service_role";

grant select on table "public"."activity_feed" to "service_role";

grant trigger on table "public"."activity_feed" to "service_role";

grant truncate on table "public"."activity_feed" to "service_role";

grant update on table "public"."activity_feed" to "service_role";

grant delete on table "public"."activity_feed_post" to "anon";

grant insert on table "public"."activity_feed_post" to "anon";

grant references on table "public"."activity_feed_post" to "anon";

grant select on table "public"."activity_feed_post" to "anon";

grant trigger on table "public"."activity_feed_post" to "anon";

grant truncate on table "public"."activity_feed_post" to "anon";

grant update on table "public"."activity_feed_post" to "anon";

grant delete on table "public"."activity_feed_post" to "authenticated";

grant insert on table "public"."activity_feed_post" to "authenticated";

grant references on table "public"."activity_feed_post" to "authenticated";

grant select on table "public"."activity_feed_post" to "authenticated";

grant trigger on table "public"."activity_feed_post" to "authenticated";

grant truncate on table "public"."activity_feed_post" to "authenticated";

grant update on table "public"."activity_feed_post" to "authenticated";

grant delete on table "public"."activity_feed_post" to "service_role";

grant insert on table "public"."activity_feed_post" to "service_role";

grant references on table "public"."activity_feed_post" to "service_role";

grant select on table "public"."activity_feed_post" to "service_role";

grant trigger on table "public"."activity_feed_post" to "service_role";

grant truncate on table "public"."activity_feed_post" to "service_role";

grant update on table "public"."activity_feed_post" to "service_role";

grant delete on table "public"."discussion" to "anon";

grant insert on table "public"."discussion" to "anon";

grant references on table "public"."discussion" to "anon";

grant select on table "public"."discussion" to "anon";

grant trigger on table "public"."discussion" to "anon";

grant truncate on table "public"."discussion" to "anon";

grant update on table "public"."discussion" to "anon";

grant delete on table "public"."discussion" to "authenticated";

grant insert on table "public"."discussion" to "authenticated";

grant references on table "public"."discussion" to "authenticated";

grant select on table "public"."discussion" to "authenticated";

grant trigger on table "public"."discussion" to "authenticated";

grant truncate on table "public"."discussion" to "authenticated";

grant update on table "public"."discussion" to "authenticated";

grant delete on table "public"."discussion" to "service_role";

grant insert on table "public"."discussion" to "service_role";

grant references on table "public"."discussion" to "service_role";

grant select on table "public"."discussion" to "service_role";

grant trigger on table "public"."discussion" to "service_role";

grant truncate on table "public"."discussion" to "service_role";

grant update on table "public"."discussion" to "service_role";

grant delete on table "public"."group" to "anon";

grant insert on table "public"."group" to "anon";

grant references on table "public"."group" to "anon";

grant select on table "public"."group" to "anon";

grant trigger on table "public"."group" to "anon";

grant truncate on table "public"."group" to "anon";

grant update on table "public"."group" to "anon";

grant delete on table "public"."group" to "authenticated";

grant insert on table "public"."group" to "authenticated";

grant references on table "public"."group" to "authenticated";

grant select on table "public"."group" to "authenticated";

grant trigger on table "public"."group" to "authenticated";

grant truncate on table "public"."group" to "authenticated";

grant update on table "public"."group" to "authenticated";

grant delete on table "public"."group" to "service_role";

grant insert on table "public"."group" to "service_role";

grant references on table "public"."group" to "service_role";

grant select on table "public"."group" to "service_role";

grant trigger on table "public"."group" to "service_role";

grant truncate on table "public"."group" to "service_role";

grant update on table "public"."group" to "service_role";

grant delete on table "public"."group_member" to "anon";

grant insert on table "public"."group_member" to "anon";

grant references on table "public"."group_member" to "anon";

grant select on table "public"."group_member" to "anon";

grant trigger on table "public"."group_member" to "anon";

grant truncate on table "public"."group_member" to "anon";

grant update on table "public"."group_member" to "anon";

grant delete on table "public"."group_member" to "authenticated";

grant insert on table "public"."group_member" to "authenticated";

grant references on table "public"."group_member" to "authenticated";

grant select on table "public"."group_member" to "authenticated";

grant trigger on table "public"."group_member" to "authenticated";

grant truncate on table "public"."group_member" to "authenticated";

grant update on table "public"."group_member" to "authenticated";

grant delete on table "public"."group_member" to "service_role";

grant insert on table "public"."group_member" to "service_role";

grant references on table "public"."group_member" to "service_role";

grant select on table "public"."group_member" to "service_role";

grant trigger on table "public"."group_member" to "service_role";

grant truncate on table "public"."group_member" to "service_role";

grant update on table "public"."group_member" to "service_role";

grant delete on table "public"."location" to "anon";

grant insert on table "public"."location" to "anon";

grant references on table "public"."location" to "anon";

grant select on table "public"."location" to "anon";

grant trigger on table "public"."location" to "anon";

grant truncate on table "public"."location" to "anon";

grant update on table "public"."location" to "anon";

grant delete on table "public"."location" to "authenticated";

grant insert on table "public"."location" to "authenticated";

grant references on table "public"."location" to "authenticated";

grant select on table "public"."location" to "authenticated";

grant trigger on table "public"."location" to "authenticated";

grant truncate on table "public"."location" to "authenticated";

grant update on table "public"."location" to "authenticated";

grant delete on table "public"."location" to "service_role";

grant insert on table "public"."location" to "service_role";

grant references on table "public"."location" to "service_role";

grant select on table "public"."location" to "service_role";

grant trigger on table "public"."location" to "service_role";

grant truncate on table "public"."location" to "service_role";

grant update on table "public"."location" to "service_role";

grant delete on table "public"."message" to "anon";

grant insert on table "public"."message" to "anon";

grant references on table "public"."message" to "anon";

grant select on table "public"."message" to "anon";

grant trigger on table "public"."message" to "anon";

grant truncate on table "public"."message" to "anon";

grant update on table "public"."message" to "anon";

grant delete on table "public"."message" to "authenticated";

grant insert on table "public"."message" to "authenticated";

grant references on table "public"."message" to "authenticated";

grant select on table "public"."message" to "authenticated";

grant trigger on table "public"."message" to "authenticated";

grant truncate on table "public"."message" to "authenticated";

grant update on table "public"."message" to "authenticated";

grant delete on table "public"."message" to "service_role";

grant insert on table "public"."message" to "service_role";

grant references on table "public"."message" to "service_role";

grant select on table "public"."message" to "service_role";

grant trigger on table "public"."message" to "service_role";

grant truncate on table "public"."message" to "service_role";

grant update on table "public"."message" to "service_role";

grant delete on table "public"."post" to "anon";

grant insert on table "public"."post" to "anon";

grant references on table "public"."post" to "anon";

grant select on table "public"."post" to "anon";

grant trigger on table "public"."post" to "anon";

grant truncate on table "public"."post" to "anon";

grant update on table "public"."post" to "anon";

grant delete on table "public"."post" to "authenticated";

grant insert on table "public"."post" to "authenticated";

grant references on table "public"."post" to "authenticated";

grant select on table "public"."post" to "authenticated";

grant trigger on table "public"."post" to "authenticated";

grant truncate on table "public"."post" to "authenticated";

grant update on table "public"."post" to "authenticated";

grant delete on table "public"."post" to "service_role";

grant insert on table "public"."post" to "service_role";

grant references on table "public"."post" to "service_role";

grant select on table "public"."post" to "service_role";

grant trigger on table "public"."post" to "service_role";

grant truncate on table "public"."post" to "service_role";

grant update on table "public"."post" to "service_role";

grant delete on table "public"."proposal" to "anon";

grant insert on table "public"."proposal" to "anon";

grant references on table "public"."proposal" to "anon";

grant select on table "public"."proposal" to "anon";

grant trigger on table "public"."proposal" to "anon";

grant truncate on table "public"."proposal" to "anon";

grant update on table "public"."proposal" to "anon";

grant delete on table "public"."proposal" to "authenticated";

grant insert on table "public"."proposal" to "authenticated";

grant references on table "public"."proposal" to "authenticated";

grant select on table "public"."proposal" to "authenticated";

grant trigger on table "public"."proposal" to "authenticated";

grant truncate on table "public"."proposal" to "authenticated";

grant update on table "public"."proposal" to "authenticated";

grant delete on table "public"."proposal" to "service_role";

grant insert on table "public"."proposal" to "service_role";

grant references on table "public"."proposal" to "service_role";

grant select on table "public"."proposal" to "service_role";

grant trigger on table "public"."proposal" to "service_role";

grant truncate on table "public"."proposal" to "service_role";

grant update on table "public"."proposal" to "service_role";

grant delete on table "public"."proposal_location" to "anon";

grant insert on table "public"."proposal_location" to "anon";

grant references on table "public"."proposal_location" to "anon";

grant select on table "public"."proposal_location" to "anon";

grant trigger on table "public"."proposal_location" to "anon";

grant truncate on table "public"."proposal_location" to "anon";

grant update on table "public"."proposal_location" to "anon";

grant delete on table "public"."proposal_location" to "authenticated";

grant insert on table "public"."proposal_location" to "authenticated";

grant references on table "public"."proposal_location" to "authenticated";

grant select on table "public"."proposal_location" to "authenticated";

grant trigger on table "public"."proposal_location" to "authenticated";

grant truncate on table "public"."proposal_location" to "authenticated";

grant update on table "public"."proposal_location" to "authenticated";

grant delete on table "public"."proposal_location" to "service_role";

grant insert on table "public"."proposal_location" to "service_role";

grant references on table "public"."proposal_location" to "service_role";

grant select on table "public"."proposal_location" to "service_role";

grant trigger on table "public"."proposal_location" to "service_role";

grant truncate on table "public"."proposal_location" to "service_role";

grant update on table "public"."proposal_location" to "service_role";

grant delete on table "public"."proposal_rating" to "anon";

grant insert on table "public"."proposal_rating" to "anon";

grant references on table "public"."proposal_rating" to "anon";

grant select on table "public"."proposal_rating" to "anon";

grant trigger on table "public"."proposal_rating" to "anon";

grant truncate on table "public"."proposal_rating" to "anon";

grant update on table "public"."proposal_rating" to "anon";

grant delete on table "public"."proposal_rating" to "authenticated";

grant insert on table "public"."proposal_rating" to "authenticated";

grant references on table "public"."proposal_rating" to "authenticated";

grant select on table "public"."proposal_rating" to "authenticated";

grant trigger on table "public"."proposal_rating" to "authenticated";

grant truncate on table "public"."proposal_rating" to "authenticated";

grant update on table "public"."proposal_rating" to "authenticated";

grant delete on table "public"."proposal_rating" to "service_role";

grant insert on table "public"."proposal_rating" to "service_role";

grant references on table "public"."proposal_rating" to "service_role";

grant select on table "public"."proposal_rating" to "service_role";

grant trigger on table "public"."proposal_rating" to "service_role";

grant truncate on table "public"."proposal_rating" to "service_role";

grant update on table "public"."proposal_rating" to "service_role";

grant delete on table "public"."rating" to "anon";

grant insert on table "public"."rating" to "anon";

grant references on table "public"."rating" to "anon";

grant select on table "public"."rating" to "anon";

grant trigger on table "public"."rating" to "anon";

grant truncate on table "public"."rating" to "anon";

grant update on table "public"."rating" to "anon";

grant delete on table "public"."rating" to "authenticated";

grant insert on table "public"."rating" to "authenticated";

grant references on table "public"."rating" to "authenticated";

grant select on table "public"."rating" to "authenticated";

grant trigger on table "public"."rating" to "authenticated";

grant truncate on table "public"."rating" to "authenticated";

grant update on table "public"."rating" to "authenticated";

grant delete on table "public"."rating" to "service_role";

grant insert on table "public"."rating" to "service_role";

grant references on table "public"."rating" to "service_role";

grant select on table "public"."rating" to "service_role";

grant trigger on table "public"."rating" to "service_role";

grant truncate on table "public"."rating" to "service_role";

grant update on table "public"."rating" to "service_role";

grant delete on table "public"."user" to "anon";

grant insert on table "public"."user" to "anon";

grant references on table "public"."user" to "anon";

grant select on table "public"."user" to "anon";

grant trigger on table "public"."user" to "anon";

grant truncate on table "public"."user" to "anon";

grant update on table "public"."user" to "anon";

grant delete on table "public"."user" to "authenticated";

grant insert on table "public"."user" to "authenticated";

grant references on table "public"."user" to "authenticated";

grant select on table "public"."user" to "authenticated";

grant trigger on table "public"."user" to "authenticated";

grant truncate on table "public"."user" to "authenticated";

grant update on table "public"."user" to "authenticated";

grant delete on table "public"."user" to "service_role";

grant insert on table "public"."user" to "service_role";

grant references on table "public"."user" to "service_role";

grant select on table "public"."user" to "service_role";

grant trigger on table "public"."user" to "service_role";

grant truncate on table "public"."user" to "service_role";

grant update on table "public"."user" to "service_role";



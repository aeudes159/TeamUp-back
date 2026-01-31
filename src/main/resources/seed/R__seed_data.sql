-- Flyway Repeatable Migration: Seed Data
-- This migration runs when the init-data profile is active
-- It re-runs whenever the checksum changes (content is modified)
--
-- Naming convention: R__ prefix for repeatable migrations
-- Repeatable migrations run after all versioned migrations

-- ============================================
-- Clear existing data (for repeatability)
-- ============================================
TRUNCATE TABLE proposal_rating CASCADE;
TRUNCATE TABLE proposal_location CASCADE;
TRUNCATE TABLE group_location_history CASCADE;
TRUNCATE TABLE group_member CASCADE;
TRUNCATE TABLE activity_feed_post CASCADE;
TRUNCATE TABLE rating CASCADE;
TRUNCATE TABLE proposal CASCADE;
TRUNCATE TABLE post CASCADE;
TRUNCATE TABLE message CASCADE;
TRUNCATE TABLE discussion CASCADE;
TRUNCATE TABLE location CASCADE;
TRUNCATE TABLE activity_feed CASCADE;
TRUNCATE TABLE "group" CASCADE;
TRUNCATE TABLE "user" CASCADE;

-- Reset sequences
ALTER SEQUENCE user_id_seq RESTART WITH 1;
ALTER SEQUENCE group_id_seq RESTART WITH 1;
ALTER SEQUENCE location_id_seq RESTART WITH 1;
ALTER SEQUENCE discussion_id_seq RESTART WITH 1;
ALTER SEQUENCE message_id_seq RESTART WITH 1;
ALTER SEQUENCE post_id_seq RESTART WITH 1;
ALTER SEQUENCE proposal_id_seq RESTART WITH 1;
ALTER SEQUENCE rating_id_seq RESTART WITH 1;
ALTER SEQUENCE activity_feed_id_seq RESTART WITH 1;

-- ============================================
-- Users
-- ============================================
INSERT INTO "user" (id, first_name, last_name, age, phone_number, address, profile_picture_url) VALUES
(nextval('user_id_seq'), 'Jean', 'Dupont', 32, '+33612345678', 'Paris 15ème', 'https://i.pravatar.cc/150?img=1'),
(nextval('user_id_seq'), 'Marie', 'Martin', 28, '+33698765432', 'Paris 11ème', 'https://i.pravatar.cc/150?img=5'),
(nextval('user_id_seq'), 'Thomas', 'Bernard', 35, '+33611223344', 'Lyon', 'https://i.pravatar.cc/150?img=3'),
(nextval('user_id_seq'), 'Sophie', 'Leroy', 29, '+33622334455', 'Paris 9ème', 'https://i.pravatar.cc/150?img=9'),
(nextval('user_id_seq'), 'Pierre', 'Moreau', 41, '+33633445566', 'Marseille', 'https://i.pravatar.cc/150?img=12');

-- ============================================
-- Groups
-- ============================================
INSERT INTO "group" (id, name, cover_picture_url, created_at, is_public) VALUES
(nextval('group_id_seq'), 'Équipe Rouge', 'https://images.unsplash.com/photo-1522071820081-009f0129c71c?w=800', NOW() - INTERVAL '30 days', true),
(nextval('group_id_seq'), 'Équipe Bleue', 'https://images.unsplash.com/photo-1517245386807-bb43f82c33c4?w=800', NOW() - INTERVAL '25 days', true),
(nextval('group_id_seq'), 'Équipe Verte', 'https://images.unsplash.com/photo-1552664730-d307ca884978?w=800', NOW() - INTERVAL '20 days', true),
(nextval('group_id_seq'), 'Direction', 'https://images.unsplash.com/photo-1600880292203-757bb62b4baf?w=800', NOW() - INTERVAL '60 days', false);

-- ============================================
-- Locations
-- ============================================
INSERT INTO location (id, name, address, average_price, picture_url) VALUES
(nextval('location_id_seq'), 'Escape Game Paris', '15 Rue de la Pompe, 75015 Paris', 25.00, 'https://images.unsplash.com/photo-1511578314322-379afb476865?w=800'),
(nextval('location_id_seq'), 'Bois de Boulogne', 'Bois de Boulogne, 75016 Paris', 0.00, 'https://images.unsplash.com/photo-1552674605-db6ffd4facb5?w=800'),
(nextval('location_id_seq'), 'Bureaux Takima', '42 Avenue des Champs-Élysées, 75008 Paris', 0.00, 'https://images.unsplash.com/photo-1542744173-8e7e53415bb0?w=800'),
(nextval('location_id_seq'), 'Restaurant Le Comptoir', '9 Carrefour de l''Odéon, 75006 Paris', 45.00, 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=800'),
(nextval('location_id_seq'), 'Bowling Montparnasse', '73 Avenue du Maine, 75014 Paris', 15.00, 'https://images.unsplash.com/photo-1545232979-8bf68ee9b1af?w=800');

-- ============================================
-- Group Members (join table)
-- ============================================
-- Équipe Rouge members
INSERT INTO group_member (group_id, user_id, joined_at) VALUES
(1, 1, NOW() - INTERVAL '28 days'),
(1, 2, NOW() - INTERVAL '27 days'),
(1, 4, NOW() - INTERVAL '26 days');

-- Équipe Bleue members
INSERT INTO group_member (group_id, user_id, joined_at) VALUES
(2, 3, NOW() - INTERVAL '23 days'),
(2, 5, NOW() - INTERVAL '22 days');

-- Équipe Verte members
INSERT INTO group_member (group_id, user_id, joined_at) VALUES
(3, 1, NOW() - INTERVAL '18 days'),
(3, 3, NOW() - INTERVAL '17 days'),
(3, 4, NOW() - INTERVAL '16 days'),
(3, 5, NOW() - INTERVAL '15 days');

-- Direction members
INSERT INTO group_member (group_id, user_id, joined_at) VALUES
(4, 1, NOW() - INTERVAL '58 days'),
(4, 5, NOW() - INTERVAL '55 days');

-- ============================================
-- Discussions (one per group)
-- ============================================
INSERT INTO discussion (id, group_id, background_image_url, created_at) VALUES
(nextval('discussion_id_seq'), 1, 'https://images.unsplash.com/photo-1557683316-973673baf926?w=800', NOW() - INTERVAL '28 days'),
(nextval('discussion_id_seq'), 2, 'https://images.unsplash.com/photo-1557682250-33bd709cbe85?w=800', NOW() - INTERVAL '23 days'),
(nextval('discussion_id_seq'), 3, 'https://images.unsplash.com/photo-1557682224-5b8590cd9ec5?w=800', NOW() - INTERVAL '18 days'),
(nextval('discussion_id_seq'), 4, 'https://images.unsplash.com/photo-1557682260-96773eb01377?w=800', NOW() - INTERVAL '58 days');

-- ============================================
-- Messages
-- ============================================
-- Équipe Rouge discussion
INSERT INTO message (id, content, image_url, sent_at, sender_id, discussion_id) VALUES
(nextval('message_id_seq'), 'Salut l''équipe ! On se retrouve à 14h ?', NULL, NOW() - INTERVAL '2 days' + INTERVAL '10 hours', 1, 1),
(nextval('message_id_seq'), 'Parfait ! J''apporte les snacks', NULL, NOW() - INTERVAL '2 days' + INTERVAL '10 hours 5 minutes', 2, 1),
(nextval('message_id_seq'), 'Super, on va tout déchirer !', NULL, NOW() - INTERVAL '2 days' + INTERVAL '10 hours 10 minutes', 4, 1),
(nextval('message_id_seq'), 'J''ai réservé la salle de réunion B', NULL, NOW() - INTERVAL '1 day' + INTERVAL '9 hours', 1, 1),
(nextval('message_id_seq'), 'Merci Jean ! À tout à l''heure', NULL, NOW() - INTERVAL '1 day' + INTERVAL '9 hours 15 minutes', 2, 1);

-- Équipe Bleue discussion
INSERT INTO message (id, content, image_url, sent_at, sender_id, discussion_id) VALUES
(nextval('message_id_seq'), 'Qui est dispo pour le prochain team building ?', NULL, NOW() - INTERVAL '3 days' + INTERVAL '14 hours', 3, 2),
(nextval('message_id_seq'), 'Moi je suis libre vendredi', NULL, NOW() - INTERVAL '3 days' + INTERVAL '14 hours 30 minutes', 5, 2),
(nextval('message_id_seq'), 'Vendredi ça me va aussi', NULL, NOW() - INTERVAL '3 days' + INTERVAL '15 hours', 3, 2);

-- Équipe Verte discussion
INSERT INTO message (id, content, image_url, sent_at, sender_id, discussion_id) VALUES
(nextval('message_id_seq'), 'Bravo pour la course d''hier !', NULL, NOW() - INTERVAL '1 day' + INTERVAL '8 hours', 4, 3),
(nextval('message_id_seq'), 'On a fait un super temps', NULL, NOW() - INTERVAL '1 day' + INTERVAL '8 hours 10 minutes', 1, 3),
(nextval('message_id_seq'), 'La prochaine fois on bat le record !', NULL, NOW() - INTERVAL '1 day' + INTERVAL '8 hours 20 minutes', 5, 3);

-- ============================================
-- Posts (activity feed content)
-- ============================================
INSERT INTO post (id, content, image_url, posted_at, author_id, location_id, discussion_id) VALUES
(nextval('post_id_seq'), 'Incroyable session d''escape game avec l''équipe ! On a réussi à sortir en 45 minutes. Prochaine fois on bat le record !', 'https://images.unsplash.com/photo-1511578314322-379afb476865?w=800', NOW() - INTERVAL '2 days', 1, 1, NULL),
(nextval('post_id_seq'), 'Course d''orientation au Bois de Boulogne - 5km parcourus et des fous rires garantis ! L''équipe rouge a encore gagné.', 'https://images.unsplash.com/photo-1552674605-db6ffd4facb5?w=800', NOW() - INTERVAL '3 days', 2, 2, NULL),
(nextval('post_id_seq'), 'Atelier créatif au bureau - brainstorming sur notre prochain projet. Des idées géniales ont émergé !', 'https://images.unsplash.com/photo-1542744173-8e7e53415bb0?w=800', NOW() - INTERVAL '4 days', 3, 3, NULL),
(nextval('post_id_seq'), 'Petit déjeuner d''équipe ce matin. Rien de tel pour bien commencer la semaine !', 'https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=800', NOW() - INTERVAL '5 days', 1, 3, NULL),
(nextval('post_id_seq'), 'Soirée bowling avec toute l''équipe ! Thomas est imbattable', 'https://images.unsplash.com/photo-1545232979-8bf68ee9b1af?w=800', NOW() - INTERVAL '7 days', 4, 5, NULL),
(nextval('post_id_seq'), 'Déjeuner d''équipe au Comptoir - la cuisine était excellente !', 'https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=800', NOW() - INTERVAL '10 days', 5, 4, NULL);

-- ============================================
-- Activity Feeds
-- ============================================
INSERT INTO activity_feed (id) VALUES
(nextval('activity_feed_id_seq')),
(nextval('activity_feed_id_seq'));

-- Link posts to activity feeds
INSERT INTO activity_feed_post (activity_feed_id, post_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6),
(2, 1), (2, 3), (2, 5);

-- ============================================
-- Proposals
-- ============================================
INSERT INTO proposal (id, discussion_id, created_at) VALUES
(nextval('proposal_id_seq'), 1, NOW() - INTERVAL '5 days'),
(nextval('proposal_id_seq'), 2, NOW() - INTERVAL '3 days'),
(nextval('proposal_id_seq'), 3, NOW() - INTERVAL '1 day');

-- Link proposals to locations
INSERT INTO proposal_location (proposal_id, location_id) VALUES
(1, 1), (1, 5),
(2, 2), (2, 4),
(3, 3);

-- ============================================
-- Ratings
-- ============================================
INSERT INTO rating (id, rating_value, user_id, location_id, created_at) VALUES
(nextval('rating_id_seq'), 5, 1, 1, NOW() - INTERVAL '1 day'),
(nextval('rating_id_seq'), 4, 2, 1, NOW() - INTERVAL '1 day'),
(nextval('rating_id_seq'), 5, 3, 2, NOW() - INTERVAL '2 days'),
(nextval('rating_id_seq'), 4, 4, 3, NOW() - INTERVAL '3 days'),
(nextval('rating_id_seq'), 5, 5, 4, NOW() - INTERVAL '5 days'),
(nextval('rating_id_seq'), 3, 1, 5, NOW() - INTERVAL '6 days');

-- Link ratings to proposals
INSERT INTO proposal_rating (proposal_id, rating_id) VALUES
(1, 1), (1, 2),
(2, 3),
(3, 4);

-- ============================================
-- Group Location History
-- ============================================
INSERT INTO group_location_history (group_id, location_id, added_at) VALUES
(1, 1, NOW() - INTERVAL '20 days'),
(1, 3, NOW() - INTERVAL '15 days'),
(2, 2, NOW() - INTERVAL '18 days'),
(2, 4, NOW() - INTERVAL '10 days'),
(3, 5, NOW() - INTERVAL '12 days'),
(4, 3, NOW() - INTERVAL '50 days'),
(4, 4, NOW() - INTERVAL '40 days');

CREATE TABLE public.activity_feed_post (
                                    activity_feed_id INT REFERENCES public.activity_feed(id) ON DELETE CASCADE,
                                    post_id INT REFERENCES public.post(id) ON DELETE CASCADE,
                                    PRIMARY KEY (activity_feed_id, post_id)
);
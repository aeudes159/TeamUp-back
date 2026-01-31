ALTER TABLE rating
    ADD CONSTRAINT chk_rating_value
        CHECK (rating_value >= 0 AND rating_value <= 5);

CREATE OR REPLACE FUNCTION delete_expired_tokens_function() RETURNS TRIGGER AS $$
BEGIN
DELETE FROM tbl_token WHERE expired = true;
RETURN NULL;
END;
$$ LANGUAGE plpgsql;
CREATE TRIGGER delete_expired_tokens_trigger
    AFTER UPDATE ON tbl_token
    FOR EACH ROW EXECUTE FUNCTION delete_expired_tokens_function();
ALTER TABLE address
    ADD customer_id VARCHAR(255);
ALTER TABLE address
    ADD FOREIGN KEY (customer_id) REFERENCES customer (id);
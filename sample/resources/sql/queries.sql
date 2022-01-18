-- :name create-book! :! :n
-- :doc creates a new book record
INSERT INTO books
(isbn, title, author)
VALUES (:isbn, :title, :author)

-- :name update-book! :! :n
-- :doc updates an existing book record
UPDATE books
SET isbn = :isbn, title = :title, author = :author
WHERE id = :id

-- :name get-book :? :1
-- :doc retrieves a book record given the id
SELECT * FROM books
WHERE id = :id

-- :name delete-book! :! :n
-- :doc deletes a book record given the id
DELETE FROM books
WHERE id = :id

-- :name get-books :? :*
-- :doc retrieves all book records
SELECT * FROM books

-- :name create-review! :! :n
-- :doc creates a new review record
INSERT INTO reviews
(book_id, reviewer, published, review)
VALUES (:book_id, :reviewer, :published, :review)

-- :name update-review! :! :n
-- :doc updates an existing review record
UPDATE reviews
SET book_id = :book_id, reviewer = :reviewer, published = :published, review = :review
WHERE id = :id

-- :name get-review :? :1
-- :doc retrieves a review record given the id
SELECT * FROM reviews
WHERE id = :id

-- :name delete-review! :! :n
-- :doc deletes a review record given the id
DELETE FROM reviews
WHERE id = :id

-- :name get-reviews :? :*
-- :doc retrieves all review records
SELECT * FROM reviews
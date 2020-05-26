-- Filtered Publishers
SELECT DISTINCT ON (pu.name) pu.name AS "Publishers" --, bg.*, books.*, authors.*, pu.publisher_id
FROM genres
 LEFT OUTER JOIN book_genres bg ON bg.genre_id = genres.genre_id
 RIGHT OUTER JOIN books ON bg.book_id = books.book_id
 LEFT OUTER JOIN book_authors ba ON ba.book_id = books.book_id
 RIGHT OUTER JOIN authors ON ba.author_id = authors.author_id
 LEFT OUTER JOIN publishers pu ON pu.publisher_id = books.publisher_id
WHERE ('{genre}' = '' OR '{genre}' = genres.genre) 
ORDER BY pu.name;
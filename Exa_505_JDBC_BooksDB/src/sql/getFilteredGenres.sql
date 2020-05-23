-- Filtered Genres
SELECT DISTINCT genres.genre AS "Genres"
FROM books
 LEFT OUTER JOIN publishers ON books.publisher_id = publishers.publisher_id
 LEFT OUTER JOIN book_genres bg ON bg.book_id = books.book_id
 INNER JOIN genres ON genres.genre_id = bg.genre_id
 RIGHT OUTER JOIN book_authors ba ON books.book_id = ba.book_id
 LEFT OUTER JOIN authors ON authors.author_id = ba.author_id
WHERE ('{publisherName}' = '' OR UPPER(publishers.name) = UPPER('{publisherName}'))
    AND UPPER(books.title) LIKE UPPER('%{searchStringBookTitle}%')
    AND (UPPER(authors.last_name || ' ' || authors.first_name) LIKE UPPER('%{searchStringAuthor}%')
	 OR UPPER(authors.first_name || COALESCE(' ' || authors.middle_name, '') || COALESCE(' ' || authors.last_name, '')) LIKE UPPER('%{searchStringAuthor}%'))
ORDER BY genres.genre;
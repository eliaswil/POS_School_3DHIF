SELECT books.title AS "title", 
	   authors.first_name AS "first_name",
	   authors.middle_name AS "middle_name", 
	   authors.last_name AS "last_name",
	   books.isbn AS "isbn",
	   books.total_pages AS "total_pages",
	   genres.genre AS "genre",
	   books.rating AS "rating",
	   books.published_date AS "published_date",
	   pu.name AS "publisher_name"	   
FROM books
 INNER JOIN book_authors ba ON ba.book_id = books.book_id
 INNER JOIN authors ON authors.author_id = ba.author_id
 INNER JOIN book_genres bg ON bg.book_id = books.book_id
 INNER JOIN genres ON genres.genre_id = bg.genre_id
 INNER JOIN publishers pu ON pu.publisher_id = books.publisher_id
 
WHERE books.title = 'SCJP Sun Certified Programmer for Java 6 Study Guide';
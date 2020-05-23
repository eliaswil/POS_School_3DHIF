-- Filtered Books (Titles) (pass '' for ALL)
SELECT DISTINCT ON (bo.title) bo.title AS "Title"  --, bo.book_id, pu.name
FROM books bo
 LEFT OUTER JOIN publishers pu ON pu.publisher_id = bo.publisher_id
 LEFT OUTER JOIN book_genres bg ON bg.book_id = bo.book_id
 LEFT OUTER JOIN genres ge ON ge.genre_id = bg.genre_id
 LEFT OUTER JOIN book_authors ba ON ba.book_id = bo.book_id
 LEFT OUTER JOIN authors au ON au.author_id = ba.author_id
WHERE ('{genre}' = '' OR ge.genre = '{genre}')
	AND ('{publisherName}' = '' OR pu.name = '{publisherName}')
	AND UPPER(bo.title) LIKE UPPER('%{searchStringBookTitle}%')
 	AND (UPPER(au.last_name || ' ' || au.first_name) LIKE UPPER('%{searchStringAuthor}%')
	  		OR UPPER(au.first_name 
			   || COALESCE(' ' || au.middle_name, '') 
			   || COALESCE(' ' || au.last_name, '')) LIKE UPPER('%{searchStringAuthor}%'))
ORDER BY bo.title;
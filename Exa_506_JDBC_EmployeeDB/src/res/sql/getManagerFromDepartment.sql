SELECT e.first_name AS "first_name", 
	e.last_name AS "last_name",
	dm.from_date AS "from_date",
	dm.to_date AS "to_date"
FROM departments d
 INNER JOIN dept_manager dm ON dm.dept_no = d.dept_no
 INNER JOIN employees e ON e.emp_no = dm.emp_no
WHERE d.dept_name = '{dept_name}'
ORDER BY e.last_name, e.first_name;
SELECT e.last_name AS "last_name",
	e.first_name AS "first_name",
	e.gender AS "gender",
	e.birth_date AS "birth_date",
	e.hire_date AS "hire_date"
FROM departments d
 INNER JOIN dept_emp de ON de.dept_no = d.dept_no
 INNER JOIN employees e ON e.emp_no = de.emp_no
WHERE d.dept_name = '{dept_name}'
    AND e.birth_date < TO_DATE('{birth_date}', 'YYYY-MM-DD')
    AND ((e.gender = 'M' AND '{gender_male}') OR (e.gender = 'F' AND '{gender_female}'))
ORDER BY 
LIMIT {limit};
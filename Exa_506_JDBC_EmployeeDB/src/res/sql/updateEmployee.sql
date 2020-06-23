UPDATE employees
SET first_name = ?,
    last_name = ?,
    hire_date = ?
WHERE first_name = '{old_first_name}'
    AND last_name = '{old_last_name}'
    AND birth_date = '{birth_date}';

CREATE
OR REPLACE FUNCTION delete_by_group_admin(admin_name TEXT) RETURNS VOID AS $$
BEGIN
DELETE
FROM study_group
WHERE ctid = (SELECT ctid
              FROM study_group
              WHERE group_admin_id = (SELECT id
                                      FROM person
                                      WHERE name = admin_name
    LIMIT 1
    )
    LIMIT 1
    );
END;
$$
LANGUAGE plpgsql;

-- Сгруппировать объекты по значению поля formOfEducation, вернуть количество элементов в каждой группе.
-- todo сюда можно materialized присобачить для удобства
CREATE
OR REPLACE FUNCTION count_by_form_of_education() RETURNS TABLE(form_of_education varchar, group_count BIGINT) AS $$
BEGIN
RETURN QUERY
SELECT sg.form_of_education, COUNT(*)
FROM study_group sg
GROUP BY sg.form_of_education;
END;
$$
LANGUAGE plpgsql;

CREATE
OR REPLACE FUNCTION get_unique_average_marks() RETURNS FLOAT[] AS $$
BEGIN
RETURN QUERY
SELECT ARRAY(
           SELECT DISTINCT average_mark
            FROM study_group
        );
END;
$$
LANGUAGE plpgsql;

-- уникальные значения average mark
CREATE OR REPLACE FUNCTION get_unique_average_marks() RETURNS REAL[] AS $$
BEGIN
RETURN (
    SELECT ARRAY(
               SELECT DISTINCT average_mark
                       FROM study_group
               )
);
END;
$$ LANGUAGE plpgsql;


-- Посчитать общее число отчисленных студентов во всех группах.
CREATE OR REPLACE FUNCTION get_total_expelled_students() RETURNS BIGINT AS $$
BEGIN
RETURN (
    SELECT SUM(expelled_students)
    FROM study_group
);
END;
$$ LANGUAGE plpgsql;



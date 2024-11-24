CREATE OR REPLACE FUNCTION delete_by_group_admin(admin_name TEXT)
RETURNS TEXT AS
$$
DECLARE
group_name TEXT;
BEGIN
    -- Получаем имя группы, которую нужно удалить
SELECT name
INTO group_name
FROM study_group
WHERE group_admin_id = (
    SELECT id
    FROM person
    WHERE name = admin_name
    LIMIT 1
    )
    LIMIT 1;

-- Удаляем группу
DELETE FROM study_group
WHERE ctid = (
    SELECT ctid
    FROM study_group
    WHERE group_admin_id = (
        SELECT id
        FROM person
        WHERE name = admin_name
    LIMIT 1
    )
    LIMIT 1
    );

-- Возвращаем имя удаленной группы
RETURN group_name;
END;
$$ LANGUAGE plpgsql;


-- Сгруппировать объекты по значению поля formOfEducation, вернуть количество элементов в каждой группе.
CREATE OR REPLACE FUNCTION count_by_form_of_education()
    RETURNS TABLE
            (
                form_of_education varchar,
                group_count       BIGINT
            )
AS
$$
BEGIN
RETURN QUERY
SELECT sg.form_of_education, COUNT(*)
FROM study_group sg
GROUP BY sg.form_of_education;
END;
$$ LANGUAGE plpgsql;


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

--	Изменить groupAdmin в указанной группе.
CREATE OR REPLACE FUNCTION update_group_admin(group_id BIGINT, new_group_admin_id BIGINT) RETURNS VOID AS $$
BEGIN
UPDATE study_group
SET group_admin_id = new_group_admin_id
WHERE id = group_id;
END;
$$ LANGUAGE plpgsql;



--    не в рамках доп заданий
CREATE OR REPLACE FUNCTION delete_admin_and_groups(admin_id bigint)
    RETURNS void AS
$$
BEGIN
DELETE FROM study_group
WHERE group_admin_id = admin_id;

DELETE FROM person
WHERE id = admin_id;

END;
$$ LANGUAGE plpgsql;
CREATE TABLE developer_skills
(
    developers_id
              INT REFERENCES developers (id),
    skills_id INT REFERENCES skills (id),
    CONSTRAINT developers_skills PRIMARY KEY (developers_id, skills_id)
);



CREATE TABLE assignment (
                            assignment_id UUID PRIMARY KEY,
                            title VARCHAR(255) NOT NULL,
                            description VARCHAR(2000),
                            course_id UUID NOT NULL,
                            state VARCHAR(50) NOT NULL,
                            due_date TIMESTAMP NOT NULL,
                            total_marks INT NOT NULL,
                            lecturer_id UUID NOT NULL,
                            created_at TIMESTAMP NOT NULL,
                            updated_at TIMESTAMP NOT NULL,

                            CONSTRAINT fk_assignment_course
                                FOREIGN KEY (course_id)
                                    REFERENCES course(course_id)
                                    ON DELETE CASCADE,

                            CONSTRAINT fk_assignment_lecturer
                                FOREIGN KEY (lecturer_id)
                                    REFERENCES my_users(user_id)
                                    ON DELETE CASCADE
);


CREATE TABLE assignment_submissions (
                                        submission_id UUID PRIMARY KEY,
                                        assignment_id UUID NOT NULL,
                                        student_id UUID NOT NULL,
                                        file_path VARCHAR(1000) NOT NULL,
                                        original_file_name VARCHAR(255) NOT NULL,
                                        submitted_at TIMESTAMP NOT NULL,
                                        marks INT,
                                        feedback VARCHAR(2000),
                                        created_at TIMESTAMP NOT NULL,
                                        updated_at TIMESTAMP NOT NULL,

                                        CONSTRAINT uq_assignment_student UNIQUE (assignment_id, student_id),

                                        CONSTRAINT fk_submission_assignment
                                            FOREIGN KEY (assignment_id)
                                                REFERENCES assignment(assignment_id)
                                                ON DELETE CASCADE,

                                        CONSTRAINT fk_submission_student
                                            FOREIGN KEY (student_id)
                                                REFERENCES my_users(user_id)
                                                ON DELETE CASCADE
);






CREATE TABLE course_materials (
                                  id UUID PRIMARY KEY,
                                  title VARCHAR NOT NULL,
                                  description VARCHAR(1000),
                                  material_type VARCHAR NOT NULL,
                                  file_path VARCHAR,
                                  video_url VARCHAR,
                                  course_id UUID NOT NULL,
                                  uploaded_by UUID NOT NULL,
                                  uploaded_at TIMESTAMP WITH TIME ZONE NOT NULL,
                                  updated_at TIMESTAMP WITH TIME ZONE,
                                  is_deleted BOOLEAN NOT NULL,

                                  CONSTRAINT fk_course
                                      FOREIGN KEY(course_id)
                                          REFERENCES course(course_id),
                                  CONSTRAINT fk_uploaded_by
                                      FOREIGN KEY(uploaded_by)
                                          REFERENCES my_users(user_id)
);
CREATE TABLE identity_schema.permissions (
    permission_id VARCHAR(50) PRIMARY KEY,
    description VARCHAR(255)
);

CREATE TABLE identity_schema.roles_permissions (
    role_id INTEGER REFERENCES identity_schema.roles(id) ON DELETE CASCADE,
    permission_id VARCHAR(50) REFERENCES identity_schema.permissions(permission_id) ON DELETE CASCADE,
    PRIMARY KEY (role_id, permission_id)
);
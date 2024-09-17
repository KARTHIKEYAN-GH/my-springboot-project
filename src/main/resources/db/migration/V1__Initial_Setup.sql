CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE DEFAULT(UUID()),
    name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    email VARCHAR(50) NOT NULL,
    user_name VARCHAR(50) BINARY NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN','TRAINEE','ATTENDEE') NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_by VARCHAR(50) DEFAULT 'system' , 
    updated_by VARCHAR(50), 
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,
    serach VARCHAR(250)
    );

-- Default Admin User
INSERT INTO users (id,uuid,name, phone_number, email, user_name, password, role) VALUES
(NULL,UUID(),'Admin User', '+6383804074', 'admin@gmail.com', 'Admin', '$2a$10$sRxo7sgi4elrPdboR7jucutOC6AR3yzcDG7z2zHZmvaXr1Dablr5K', 'ADMIN');

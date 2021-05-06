# users
CREATE TABLE IF NOT EXISTS users(
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30),
    firstName VARCHAR(30),
    lastName VARCHAR(30),
    email VARCHAR(50) NOT NULL UNIQUE,
    emailVerified BOOLEAN DEFAULT FALSE,
    password VARCHAR(128),
    mobile_phone VARCHAR(15) NOT NULL UNIQUE,
    home_phone VARCHAR(15),
    picture TEXT(1000),
    provider VARCHAR(30) DEFAULT 'local',
    providerId VARCHAR(30) DEFAULT '001',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

# Contacts
CREATE TABLE IF NOT EXISTS contacts(
    contacts_id INT(10) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(30) NOT NULL,
    mobile_phone VARCHAR(15) NOT NULL,
    home_phone  VARCHAR(15),
    email VARCHAR(255) NOT NULL UNIQUE,
    website VARCHAR(255),
    category VARCHAR(255),
    picture TEXT(1000),
    user_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
USE avByParse;

CREATE TABLE producers(
    id int primary key AUTO_INCREMENT,
    name varchar(255) not null
);

CREATE TABLE models(
    id int primary key AUTO_INCREMENT,
    name varchar(255) not null,
    producerID int not null,
    FOREIGN KEY (producerID) REFERENCES producers(id)
);

CREATE TABLE cars(
    id int primary key AUTO_INCREMENT,
    cost int not null,
    released date not null,
    mileage int not null,
    comments varchar(255) not null,
    modelID int not null,
    FOREIGN KEY (modelID) REFERENCES models(id)
);
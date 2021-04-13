use speedhome;

INSERT INTO `speedhome`.`roles`
(`id`,
`name`)
VALUES
(1,'ADMIN'),(2,'LANDLORD'),(3,'USER');



INSERT INTO `speedhome`.`categories`
(`id`,
`name`)
VALUES
(1,'BUNGLOW'),(2,'FLAT');

INSERT INTO `speedhome`.`users`
(
`enabled`,
`password`,
`username`)
VALUES
(
true,
'$2a$10$ZAUnie28lONHcWRgyfZPzeAodGamNUxcHngOj4nXtG0.FMF1uGVzy',
'admin');





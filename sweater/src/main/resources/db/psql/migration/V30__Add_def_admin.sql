insert into usr (id, username, password, active)
    values(1, '${site.admin.name}', '${site.admin.pass}', true), (2, '${site.user.name}', '${site.user.pass}', true);

insert into user_role (user_id, roles)
    values(1, 'USER'), (1, 'ADMIN');

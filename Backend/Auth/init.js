db = new Mongo().getDB("auth-db");

db.createCollection('usuario', { capped: false });

db.usuario.insert([
    {
        "email": "admin@admin.com",
        "senha": "7a56551dac1cf054e7ea2ac50ddd9e694592a7f7d1a981b6cc7a94e71754db24aeaa3b036f50af3df9aa596214c2d80fe33afcea4a8cd1f0f99b7da68ffa685e",
        "cargo": "Administrador",
        "ativo": true
    },
    {
        "_id":1,
        "email": "gerente1@gerente.com",
        "senha": "1967933a7595b1b98f02a50dc7e9af5f7dcf87efc56721247690bea4b9af0bbe1f864f06429a4cce5a87ed56c73f25f6a8d83c7d359059f4698630f7db661d72",
        "cargo": "Gerente",
        "ativo": true
    },
    {
        "email": "gerente2@gerente.com",
        "senha": "2a7c1c80de47d2d185319a0bf7fbc88ce89664925f11eda61c2f120e9ca0ff59f616afd90ae61d93a4758a72fbbdf0de1fbffa1402c606140a1db3c990de7e98",
        "cargo": "Gerente",
        "ativo": true
    },
    {
        "email": "cliente1@cliente.com",
        "senha": "c581b92b8f8ae9ecf77672f502e611fc9019e979790528893939093b5300391c17a88032f11d035a5c141b83e77f46fe08b640c0a083008bdacdc2f162d380e4",
        "cargo": "Cliente",
        "ativo": true
    },
    {
        "email": "cliente2@cliente.com",
        "senha": "84c2a08b97388631100f2067263f5a6fce0e086e72b8de6c14dbf38c8076bc2eeda336cb559d853a10902f666e370e3e61744ac4daa14862e83489cb2339fb91",
        "cargo": "Cliente",
        "ativo": true
    }
]);
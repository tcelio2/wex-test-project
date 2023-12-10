//var db = connect("mongodb://admin:12345@localhost:27017/purchase");

db = db.getSiblingDB('purchase'); // we can not use "use" statement here to switch db
db.createCollection('compras');

db.createUser(
        {
            user: "user",
            pwd: "1234",
            roles: [
                {
                    role: "readWrite",
                    db: "purchase"
                }
            ]
        }
);

// Define an application user.
let user1 = {
    user: 'appuser',
    pwd: 'apppassword',
    roles: [{
        role: 'readWrite',
        db: 'appdb'
    }]
};

let user2 = {
    user: 'kuwa',
    pwd: 'kuwapassword',
    roles: [{
        role: 'readWrite',
        db: 'appdb'
    }]
};
// Execute mongodb command to create the above user.
db.createUser(user1);
db.createUser(user2);
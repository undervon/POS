db.createUser({
    user: "mongoAdmin",
    pwd: "mongoAdmin",
    roles: [{
        role: "userAdminAnyDatabase",
        db: "admin"
    },
        "readWriteAnyDatabase"
    ]
})

db.createUser({
    user: "mongoReadWrite",
    pwd: "mongoReadWrite",
    roles: [
        {
            role: "readWrite",
            db: "bookstore"
        }
    ]
})

db.createUser({
    user: "mongoOwner",
    pwd: "mongoOwner",
    roles: [
        {
            role: "dbOwner",
            db: "bookstore"
        }
    ]
})

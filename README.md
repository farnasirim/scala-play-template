# Moodvisor

- Project
 . Make sure you have JDK 8 installed
 . Using scala version 2.11.8 (You can change it in build.sbt)
 . Using sbt version 0.13.11 (You can change it in build.properties)

- Dependencies
 . ReactiveMongo, Async and Non-blocking scala driver for MongoDB
 . jBCrypt, is an implementation of OpenBSD Blowfish password hashing algorithm
 . HashIDs, small Scala library to generate YouTube-like hashes from one or many numbers

- License
 . Apache 2 license

## Create spatial indices in mongodb:
```
db.locations.createIndex({ location: "2dsphere"})
```


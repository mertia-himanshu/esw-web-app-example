# Adding Database Persistence

We will use postgres to save and use our data. We will be using Jooq dsl to write our queries.

## Update backend Implementation

Add db dependency in `Libs.scala`

Scala
: @@snip [Libs.scala](../../../../backend/project/Libs.scala) { #add-db }

Use db dependency in `build.sbt`

Scala
: @@snip [build.sbt](../../../../backend/build.sbt) { #add-db }

Add dsl context in `RaImpl.scala`

Scala
: @@snip [RaImpl.scala](../../../../backend/src/main/scala/org/tmt/sample/db/RaImpl.scala) { #add-dsl-context }

Add a query to insert data in db in `RaImpl.scala`

Scala
: @@snip [RaImpl.scala](../../../../backend/src/main/scala/org/tmt/sample/db/RaImpl.scala) { #insert-raValue-in-db }

Update `raToString` implementation to use this query in `RaImpl.scala`

Scala
: @@snip [RaImpl.scala](../../../../backend/src/main/scala/org/tmt/sample/db/RaImpl.scala) { #raToString-impl }

Add new contract to for to get ra values in service `RaService.scala`

Scala
: @@snip [RaService.scala](../../../../backend/src/main/scala/org/tmt/sample/service/RaService.scala) { #getRaValues-contract }

Implement contract by adding a new method to get these saved db values in `RaImpl.scala`

Scala
: @@snip [RaImpl.scala](../../../../backend/src/main/scala/org/tmt/sample/db/RaImpl.scala) { #get-raValues-from-db }

Add new route to get saved values in `SampleRoute.scala`

Scala
: @@snip [SampleRoute.scala](../../../../backend/src/main/scala/org/tmt/sample/http/SampleRoute.scala) { #add-get-values-route }

Update `SampleWiring.scala`

Add db setup

Scala
: @@snip [SampleWiring.scala](../../../../backend/src/main/scala/org/tmt/sample/db/SampleWiring.scala) { #db-wiring-setup }

Update implementation to use dsl context

Scala
: @@snip [SampleWiring.scala](../../../../backend/src/main/scala/org/tmt/sample/db/SampleWiring.scala) { #raImpl-db-ref }

## Database setup

The CSW Database Service needs to be running before starting the App.
Follow below instructions to run database service along with location service and authentication service:

```bash
cs install csw-services:v3.0.0-M1
csw-services start -k -d
```

Login to your postgres with your default postgres user and create new user

```bash
psql -d postgres
postgres = > CREATE USER postgres with password 'postgres'
```

This application performs fetch and insert queries on the `RAVLUES` table in the database, thus it needs to be
present.
Following command can be used to create a table

```sql
postgres = >
CREATE TABLE RAVLUES(
id TEXT             PRIMARY KEY     NOT NULL,
formattedRa TEXT                    NOT NULL
);
```

In the application, we depend on environment variables to pick up your username and password for the database, thus DB_USERNAME and
DB_PASSWORD need to be set.
To set environment variables, use the command

```bash
export DB_USERNAME=<VALUE> DB_PASSWORD=<VALUE>
```

In the application, the `database name`, `username`, `password` is picked up from the `application.conf`
Update `application.conf` , add these entries.

Scala
: @@snip [application.conf](../../../../backend/src/main/resources/application.conf) { #db-conf }

Run backend application

```sbt
sbt:backend> run start
```

Add this to your apptest.http and check the route

```http
GET http://192.168.1.4:8084/raValues
```

## Update frontend

Add fetch method to data saved ra values in `api.ts`

Typescript
: @@snip [api.ts](../../../../frontend/src/utils/api.ts) { #fetch-saved-ra-values }

Add new component `RaTable.tsx` to display ra values table

Typescript
: @@snip [RaTable.tsx](../../../../frontend/src/components/pages/RaTable.tsx) { #add-component }

Add columns for the table in this component

Typescript
: @@snip [RaTable.tsx](../../../../frontend/src/components/pages/RaTable.tsx) { #add-columns }

Use our new fetch method in this component

Typescript
: @@snip [RaTable.tsx](../../../../frontend/src/components/pages/RaTable.tsx) { #use-fetch }

Update our `Ra.tsx` component to display table also

Typescript
: @@snip [Ra.tsx](../../../../frontend/src/db/Ra.tsx) { #add-table }

Run frontend Application

```bash
npm start
```

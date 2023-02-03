Simple client-server application that allows the clients to store their data on the server in JSON format. Adding, getting and deleting data of any nesting is supported.

**Note:** 
To use this application **GSON** external library, made by Google, should be added to the project.
To get familiar with the library beforehand: see [zetcode.com]() for some explanations. Besides **JCommander** to parse cli arguments should be added as well.

Following operation to work with JSON DB supported in this application: 

• To add not nested key with value used 'set' operation with the following request format:

`{ "type": "set", "key": "Secret key", "value": "Secret value" }`

Nested key:

`{"type":"set","key":["person","rocket","launches"],"value":"88"}`

The responses is in the JSON format. Please consider the examples below.

`{ "response": "OK" }`

• The get request

`{ "type": "get", "key": "Secret key" }`

In the case of a get request with a key stored in the database:

``{ "response": "OK", "value": "Secret value" }``

In the case of a get request with a key that doesn't exist:

``{ "response": "ERROR", "reason": "No such key" }``

• The delete request

`{ "type": "delete", "key": "Key that doesn't exist" }`

The response if the key exist:
`{ "response": "OK" }`

The response if the key doesn't exist: `{ "response": "OK" }`

### Client execution:
NOTE: Server should be started first, see the next section.

 The arguments that be passed to the client when executing the app in the following format:

```` java Main -t set -k 1 -v "Hello world!" ````

**Where**

_-t_ is the type of the request \
_-k_ is the key. \
_-v_ is the value to save in the database: you only need it in case of a set request.

You can create a json file with the full request in json format

Example of testSet.json file with the set request:
```
{
   "type":"set",
   "key":"person",
   "value":{
      "name":"Elon Musk",
      "car":{
         "model":"Tesla Roadster",
         "year":"2018"
      },
      "rocket":{
         "name":"Falcon 9",
         "launches":"87"
      }
   }
}
```

Then to use this .json file as a request use following command when executing the app
```java Main -in testSet.json```

**Where** 

_-in_ - is the filename with request in json format \
**!NOTE** your file should be stored in /client/data directory

Starting the client (multiple examples):
````
java Main -t set -k 1 -v "Hello world!"
Client started!
Sent: {"type":"set","key":"1","value":"Hello world!"}
Received: {"response":"OK"}
````

````
java Main -in setFile.json
Client started!
Sent:
{
"type":"set",
"key":"person",
"value":{
"name":"Elon Musk",
"car":{
"model":"Tesla Roadster",
"year":"2018"
},
"rocket":{
"name":"Falcon 9",
"launches":"87"
}
}
}
Received: {"response":"OK"}
````

````
java Main -in getFile.json
Client started!
Sent: {"type":"get","key":["person","name"]}
Received: {"response":"OK","value":"Elon Musk"}
````


### Server Execution 

Server keeps the database on the hard drive in the db.json file and update only after setting a new value or deleting one. The JSON db file stored under the /server/data folder.

Server is able to process a lot of requests because it is parallelized using executors. Every request is parsed and handled in a separate executor's task.

Starting the server:

````
java Main
Server started!
````


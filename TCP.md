# Progetto Sistemi Distribuiti 2022-2023 - TCP

This is the documentation related to the TCP Protocol between the Database and the Server Web.

All commands are not case sensitive (but the keys and values are) and the result of each command will be a string that can be parsed into a JSON.

All commands need to be passed as a String.

There are two catergories of commands:

[Commands for the key - String database](#commands-for-the-key---string-database)

[Commands for the key - list of String database](#commands-for-the-key---list-of-string-database)

## **Commands for the key - String database**

__SET - Command to set (or replace) a value associated with key.__
```
SET key value
```
```
Example:
- Set myBeatifulNumber 42
- SeT "My Beatiful Number" 42
- SeT "Best Team" "The Synchronauts"

Returns:
- [true]
- [false, error message] if there was an error
```

__MSET - Command to set (or replace) a value associated with key for multiple keys.__
```
MSET [key value]+
```
```
Example:
mSet myBeatifulNumber 42 "My Beatiful Number" 42 mykey "no u :D"
MSET bug "An insect that lives in the dark"

Returns:
- [true]
- [false, error message] if there was an error
```

__SETIF - Command to set (or replace) a newValue associated with key only if the value at key is oldValue.__
```
SETIF key newValue oldValue
```
```
Example:
SETIF myBeatifulNumber 42 41
SeTiF "My Beatiful Number" 42 41

Returns:
- [true] if it was successfull
- [false, error message] if there was an error
```

__MSETIF - Command to set (or replace) a newValue associated with key only if the value at key is oldValue for multiple keys.__
```
MSETIF [key newValue oldValue]+
```
```
Example:
MSETIF myBeatifulNumber 42 41 "My Beatiful Number" 42 41
mSeTIf key "my value" 0 zero 0 0 

Returns:
- [true] if it was successfull
- [false, error message] if there was an error
```

__GET - Command to get the value associated to a key.__
```
GET key
```
```
Example:
gEt myBeatifulNumber
GET "My Beatiful Number"

Returns:
- [true, {key : value}] if there was a value associated with key
- [true, {key : null}] if there not
- [false, error message] if there was an error
```

__MGET - Command to get the value associated to a key for multiple keys.__
```
MGET [key]+
```
```
Example:
mgEt myBeatifulNumber "My Beatiful Number"
MGET "My Beatiful Number"

Returns:
- [true, {key : value, key2 : value2, ...}] if there was a value associated with key (value can be null)
- [false, error message] if there was an error
```

__DELETE - Command to delete the key in the HashMap.__
```
DELETE key
```
```
Example:
delete myBeatifulNumber
DELETE "My Beatiful Number"

Returns:
- [true] if there was a the key in the database
- [false, error message] if there was an error
```

## **Commands for the key - List of String database**

__ADD - Command to add into the list associated with key, a value as the last element.__
```
ADD key value
```
```
Example:
add counter 1
ADD counter 2

Returns:
- [true]
- [false, error message] if there was an error
```

__MADD - Command to add into the list associated with key, a value as the last element for multiple values.__
```
MADD key [value]+
```
```
Example:
madd counter 1 2 3 4
MADD counter 5 6

Returns:
- [true]
- [false, error message] if there was an error
```

__REMOVE - Command to remove the last value from the list associated with key.__
```
REMOVE key
```
```
Example:
REMOVE counter
REMOVE counter

Returns:
- [true]
- [false, error message] if there was an error
```

__SETL - Command to set (or replace) into the list associated with key, a value in the position index.__
```
SETL key index value
```
```
Example:
SETL counter 0 1
SETL counter 1 2

Returns:
- [true] if it was successful
- [false, error message] if there was an error
```

__MSETL - Command to set (or replace) into the list associated with key, a new value at index for multiple keys.__
```
MSETL [key index value]
```
```
Example:
MSETL counter 0 1 counter 1 2

Returns:
- [true] if it was successful
- [false, error message] if there was an error
```

__SETIFL - Command to set into the list associated with key, a value in the position index only if at that position there was the value oldValue.__
```
SETIFL key index newValue oldValue
```
```
Example:
SETIFL counter 0 1 0

Returns:
- [true] if it was successful
- [false, error message] if there was an error
```

__MSETIFL - Command to set into the list associated with key, a value in the position index only if at that position there was the value oldValue for multiple keys.__
```
MSETIFL [key index newValue oldValue]+
```
```
Example:
MSETIFL counter 0 1 0 counter 1 2 1

Returns:
- [true] if it was successful
- [false, error message] if there was an error
```

__GETL - Command to get the value at position index in the list associated with key for multiple keys.__
```
GETL key index
```
```
Example:
GETL counter 0

Returns:
- [true, {key : value}] if it was successful
- [false, error message] if there was an error
```

__GETALL - Command to get the values in the list associated with key.__
```
GETALL key
```
```
Example:
GETALL counter

Returns:
- [true, {key : array of values}] if it was successful
- [false, error message] if there was an error
```

__MGETALL - Command to get the values in the list associated with key for multiple keys.__
```
MGETALL [key]+
```
```
Example:
MGETALL counter counter2

Returns:
- [true, {key : array of values, key2 : array of values}] if it was successful
- [false, error message] if there was an error
```

__DELETEL - Command to delete the list associated with key.__
```
DELETEL key
```
```
Example:
DELETEL key

Returns:
- [true] if it was successful
- [false, error message] if there was an error
```

# DafKylo

### What is it
This is a script for enabling Kylo's feed to cluster.

### How do you install it

You need to install [node.js](https://nodejs.org/en/) to run this tool and then run:

```
npm install
npm link
```

In the working directory, create **config/default.json** file:

```javascript
{
  "username": "dladmin",
  "password": "****",
  "url": "http://localhost:8420"
}
```

### Dependencies
* [node.js](https://nodejs.org/en/)

### Version of Kylo
Tested with **0.9.1**. Should be compate onwards.

### Known Isssues
No known issues.

### Level of maturity
POC quality.

### Examples

## Usage

```
> dafKylo -h

    Usage: dafKylo <command> <type>

    Options:

      -V, --version                         output the version number
      -h, --help                            output usage information

    Commands:

      cluster <command> [id] [ifNecessary]  <get|update> <id> [ifNecessary]

```

Currently implemented:
* dafKylo cluster get all : return the data for all feeds
* dafKylo cluster get <ID> : return the data for the feed with a specific ID
* dafKylo cluster update all <ifNecessary> : update all feeds for enabling
* dafKylo cluster update <ID> <ifNecessary> : update the feed with a specific ID

N.B. For updating the if the parameter <ifNecessary> is set true the update will be done only in the case that the feed was not set in primary node.
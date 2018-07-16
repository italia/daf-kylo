#!/usr/local/bin/node --no-deprecation

var program = require('commander');

var logic = require('./scripts/logic.js');

program
    .command('cluster <command> [id] [ifNecessary]')
    .description('<get|update> <id> [ifNecessary]')
    .action(function(command, id, ifNecessary) {
        if(ifNecessary === undefined) ifNecessary = true;
        else if(ifNecessary == "true") ifNecessary = true;
        else ifNecessary = false;
	    switch(command) {
  	        case 'get': {
  	            if(id == "all") {
  	                logic.get_feeds();
  	            }
  	            else {
  	                logic.get_feed(id);
  	            }
  	            break;
       	    }
  	        case 'update': {
  	            if(id == "all") {
  	                logic.update_feeds(ifNecessary);
  	            }
  	            else {
  	                logic.update_feed(id, ifNecessary)
  	            }
  	            break;
  	        }
  	        default: console.error("Invalid object type: %s", command + what);
     	}
    });

program
    .version('0.1.0')
    .usage('<command> <type>')

program.parse(process.argv);
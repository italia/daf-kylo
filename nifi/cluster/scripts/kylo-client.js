var config = require('config');

var username = config.get('username');
var password = config.get('password');
var auth = "Basic " + new Buffer(username + ":" + password).toString("base64");
var baseurl = config.get('url') + '/api/';
var syncRequest = require('sync-request');

var request = require('request'),
	fs = require('fs'),
	AdmZip = require("adm-zip");

var _get = function(url, callback) {
	request.get({
		url : baseurl+url,
	    headers : {
	        "Authorization" : auth
	    }
	}, function(error, response, body) {
		if(error) {
	        return console.log(error);
	    }

		callback(JSON.parse(body));
	});
}

var _get_raw = function(url, callback) {
	request.get({
		url : baseurl+url,
	    headers : {
	        "Authorization" : auth
	    }
	}, function(error, response, body) {
		if(error) {
	        return console.log(error);
	    }

		callback(body);
	});
}

var _synch_get_raw = function(url) {
	var res = syncRequest('GET', baseurl+url, {
	    headers : {
	        "Authorization" : auth
	    }
	});
	return res.getBody('utf8');
}

var _synch_get = function(url) {
	var res = syncRequest('GET', baseurl+url, {
	    headers : {
	        "Authorization" : auth
	    }
	});
	return JSON.parse(res.getBody('utf8'));
}

var _synch_post = function(url, data) {
    var res = syncRequest('POST', baseurl+url, {
        json: data,
	    headers : {
	        "Authorization" : auth
	    },
    });
    return res;
}

module.exports = {
	get_feeds: function(callback) {
		return _get('v1/feedmgr/feeds?verbose=true', callback);
	},
	get_feed: function(id, callback) {
		return _get_raw('v1/feedmgr/feeds/'+ id + '?verbose=true', callback);
	},
	update_feeds: function(ifNecessary) {
	    console.log("Retrieving all feeds");
	    var feeds = _synch_get('v1/feedmgr/feeds?verbose=true');
	    var totalFeeds = feeds.recordsTotal;
	    var erroneousFeeds = [];
	    var feedsOK = 0;
	    // Update all feed
        console.log("Total feeds : " + feeds.recordsTotal)
	    for(var i=0; i<totalFeeds; i++) {
		    console.log("--------------------------------------------");
		    console.log("Update feed " + (i+1) + " of " + totalFeeds);
	        var id = feeds.data[i].id;
	        var updateOK = this.update_feed(id, ifNecessary);
	        if(updateOK) feedsOK++;
	        else erroneousFeeds.push(id);
	    }
	    // Print Statistics
	    var errorFeeds = totalFeeds - feedsOK;
	    console.log("Update Statistics");
	    if(errorFeeds == 0) {
	        console.log("It seems that every feed can work on a NIFI cluster");
	        console.log("Have a nice day");
	    }
	    else {
	        console.log("There are " + errorFeeds + " feeds with errors:");
	        for(var i=0; i<errorFeeds; i++) {
	            console.log((i+1) + ") Feed id: " + erroneousFeeds[i]);
	        }
	    }
	},
	update_feed: function(id, ifNecessary) {
	    // Getting the feed
        var updateOK = false;
	    console.log("Retrieving information for feed: " + id);
		var feed = _synch_get('v1/feedmgr/feeds/'+id);
		console.log("Update feed " + id + " for working on Primary node");
		if(ifNecessary && (feed.schedule.executionNode == "PRIMARY")) {
		    console.log("The feed " + id + " is ready for working on Primary Node");
		    return true; // The feed is primary
		}
		feed.schedule.executionNode = "PRIMARY";
		// Update Feed
        var response = _synch_post('v1/feedmgr/feeds/', feed);
        var body = JSON.parse(response.getBody('utf8'));
        if((response.statusCode == 200) && (body.success == true)) {
            console.log("Update successful for feed " + id);
            updateOK = true;
        }
        else {
            console.log("There are errors for feed " + id);
            console.log("Error Message : " + body.errorMessages);
            if((body.feedProcessGroup != null) && (body.feedProcessGroup.length != 0)) {
                for(i=0; i<body.feedProcessGroup.allErrors.length; i++) {
                    console.log(body.feedProcessGroup.allErrors[i]);
                }
            }
        }
        return updateOK;
	}
};
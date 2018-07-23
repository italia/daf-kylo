var _ = require('lodash'),
	async = require('async'),
	kylo_client = require('./kylo-client.js'),
	xpath = require('xpath'),
	dom = require('xmldom').DOMParser,
	ProgressBar = require('progress'),
	Promise = require('promise');


var _get_feeds = function() {
	kylo_client.get_feeds(function(feeds) {
		var actions = [];
        console.log("Total Feeds : " + feeds.recordsTotal)
		for(var i=0; i< feeds.recordsTotal; i++) {
			var action = function(feed) {
				console.log("--------------------------------------------");
			    console.log("Feed " + (i + 1));
				console.log("ID : " + feed.id);
				console.log("Name : " + feed.feedName);
				console.log("Category : " + feed.categoryName);
				console.log("Template : " + feed.templateName);
				console.log("Execution : " + feed.schedule.executionNode);
			}
			actions.push(action(feeds.data[i]));
		}
	});
}

var _get_feed = function(id) {
	kylo_client.get_feed(id, function(feed) {
	    console.log(feed)
	});
}

var _update_feed = function(id, ifNecessary) {
    kylo_client.update_feed(id, ifNecessary);
}

var _update_feeds = function(ifNecessary) {
    kylo_client.update_feeds(ifNecessary);
}

module.exports = {
	get_feeds: function() {
		return _get_feeds();
	},
	get_feed: function(id) {
		return _get_feed(id);
	},
	update_feeds: function(ifNecessary) {
		return _update_feeds(ifNecessary);
	},
	update_feed: function(id, ifNecessary) {
		return _update_feed(id, ifNecessary);
	}
}
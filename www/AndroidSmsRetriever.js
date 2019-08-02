
var exec = require('cordova/exec');

exports.onSmsReceived = function(success, error) {
    exec(success, error, "AndroidSmsRetriever", "onSmsReceived", [ ]);
};

exports.getAppHash = function(success, error) {
    exec(success, error, "AndroidSmsRetriever", "getAppHash", []);
};



var exec = require('cordova/exec');

exports.onSmsReceived = function(success, error) {
    exec(success, error, "WifiWizard", "onSmsReceived", [ ]);
};

exports.getAppHash = function(success, error) {
    exec(success, error, "WifiWizard", "getAppHash", []);
};


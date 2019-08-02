
var exec = require('cordova/exec');

// exports.getCurrentSSID = function(success, error, notifyWhenStarted) {
//     exec(success, error, "AndroidSmsRetriever", "onSmsReceived", [ notifyWhenStarted ]);
// };

exports.getCurrentSSID = function(success, error) {
    exec(success, error, "WifiWizard", "getConnectedSSID", []);
};


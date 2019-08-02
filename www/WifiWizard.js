
var exec = require('cordova/exec');

// exports.getCurrentSSID = function(success, error, notifyWhenStarted) {
//     exec(success, error, "AndroidSmsRetriever", "onSmsReceived", [ notifyWhenStarted ]);
// };

exports.getAppHash = function(success, error) {
    exec(success, error, "WifiWizard", "getAppHash", []);
};


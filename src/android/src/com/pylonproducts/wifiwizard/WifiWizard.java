/*
 * Copyright 2015 Rajan Modi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.pylonproducts.wifiwizard;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class WifiWizard extends CordovaPlugin {

    private static final String getAppHash = "getAppHash";

    private CallbackContext callbackContext;


    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {

        // if (action.equals("onSmsReceived")) {
        //     this.onSmsReceived(callbackContext, (args != null && args.optBoolean(0, false)));
        //     return true;
        // }

        if (action.equals("getAppHash")) {
            this.getAppHash(callbackContext);
            return true;
        }

        callbackContext.error("Android SMS Retriever Plugin: Unknown action (" + action + ")");
        return false;
    }

 
    private void getAppHash(CallbackContext callbackContext) {

        try {

            for (String hash : getAppSignatures(this.cordova.getActivity().getApplicationContext(), callbackContext)) {
                callbackContext.success("App's hash: " + hash + " - DO NOT USE THIS UTILITY IN PRODUCTION");
                return;
            }

            callbackContext.error(
                    "The app's hash could not be computed. Please refer to Google documentation for an alternative: https://developers.google.com/identity/sms-retriever/verify#computing_your_apps_hash_string");
        } catch (Exception e) {

            callbackContext.error("Something bad happened... :( Exception message: " + e.getMessage());
        }
    }
    private static final String HASH_TYPE = "SHA-256";
    public static final int NUM_HASHED_BYTES = 9;
    public static final int NUM_BASE64_CHAR = 11;

    private ArrayList<String> getAppSignatures(Context context, CallbackContext callbackContext) {
        ArrayList<String> appCodes = new ArrayList<>();

        try {
            // Get all package signatures for the current package
            String packageName = context.getPackageName();
            PackageManager packageManager = context.getPackageManager();
            Signature[] signatures = packageManager.getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES).signatures;

            // For each signature create a compatible hash
            for (Signature signature : signatures) {
                String hash = hash(packageName, signature.toCharsString(), callbackContext);
                if (hash != null) {
                    appCodes.add(String.format("%s", hash));
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            callbackContext.error("Unable to find package to obtain hash.");
        }

        return appCodes;
    }

    private static String hash(String packageName, String signature, CallbackContext callbackContext) {
        String appInfo = packageName + " " + signature;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_TYPE);
            messageDigest.update(appInfo.getBytes(StandardCharsets.UTF_8));
            byte[] hashSignature = messageDigest.digest();

            // truncated into NUM_HASHED_BYTES
            hashSignature = Arrays.copyOfRange(hashSignature, 0, NUM_HASHED_BYTES);
            // encode into Base64
            String base64Hash = Base64.encodeToString(hashSignature, Base64.NO_PADDING | Base64.NO_WRAP);
            base64Hash = base64Hash.substring(0, NUM_BASE64_CHAR);

            return base64Hash;
        } catch (NoSuchAlgorithmException e) {

            callbackContext.error("hash:NoSuchAlgorithm");
        }
        return null;
    }

}

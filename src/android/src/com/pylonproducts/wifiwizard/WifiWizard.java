/*
 * Copyright 2015 Matt Parsons
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

import org.apache.cordova.*;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiEnterpriseConfig;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.SupplicantState;
import android.content.Context;
import android.util.Log;


public class WifiWizard extends CordovaPlugin {

    private static final String GET_CONNECTED_SSID = "getConnectedSSID";

    private WifiManager wifiManager;
    private CallbackContext callbackContext;


    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext)
                            throws JSONException {

        this.callbackContext = callbackContext;
         if(action.equals(GET_CONNECTED_SSID)) {
            return this.getConnectedSSID(callbackContext);
        }
        else {
            callbackContext.error("Incorrect action parameter: " + action);
        }

        return false;
    }

    private boolean getConnectedSSID(CallbackContext callbackContext){
       
        callbackContext.success("ssid dop");
        return true;
    }

}

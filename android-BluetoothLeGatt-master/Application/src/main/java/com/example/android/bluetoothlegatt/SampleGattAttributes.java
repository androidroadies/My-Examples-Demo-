/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.bluetoothlegatt;

import java.util.HashMap;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();
    public static String HEART_RATE_MEASUREMENT = "89A8591D-BB19-485B-9F59-58492BC33E24";//00002a37-0000-1000-8000-00805f9b34fb";//89A8591D-BB19-485B-9F59-58492BC33E24
    public static String CLIENT_CHARACTERISTIC_CONFIG = "894C8042-E841-461C-A5C9-5A73D25DB08E";//00002902-0000-1000-8000-00805f9b34fb";//894C8042-E841-461C-A5C9-5A73D25DB08E

    static {
        // Sample Services.
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Service");
        attributes.put(HEART_RATE_MEASUREMENT, "Device Information Service");
        // Sample Characteristics.
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put(CLIENT_CHARACTERISTIC_CONFIG, "Manufacturer Name String");
    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}

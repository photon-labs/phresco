/*
 * ###
 * Android Maven Plugin - android-maven-plugin
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
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
 * ###
 */
/*
 * Copyright (C) 2009 Jayway AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.photon.maven.plugins.android.common;

import com.android.ddmlib.IDevice;
import org.apache.commons.lang.StringUtils;

/**
 * A bunch of helper methods for dealing with IDevice instances.
 */
public class DeviceHelper {

    private static final String MANUFACTURER_PROPERTY = "ro.product.manufacturer";
    private static final String MODEL_PROPERTY = "ro.product.model";

    /**
     * Get a device identifier string that is suitable for filenames as well as log messages.
     * This means it is human readable and contains no spaces.
     * Used for instrumentation test report file names so see more at
     * AbstractInstrumentationMojo#testCreateReport javadoc since
     * that is the public documentation.
     */
    public static String getDescriptiveName(IDevice device) {
        // if any of this logic changes update javadoc for
        // AbstractInstrumentationMojo#testCreateReport
        String SEPARATOR = "_";
        StringBuilder identfier = new StringBuilder()
                .append(device.getSerialNumber());
        if (device.getAvdName() != null) {
            identfier.append(SEPARATOR).append(device.getAvdName());
        }
        String manufacturer = getManufacturer(device);
        if (StringUtils.isNotBlank(manufacturer)) {
            identfier.append(SEPARATOR).append(manufacturer);
        }
        String model = getModel(device);
        if (StringUtils.isNotBlank(model)) {
            identfier.append(SEPARATOR).append(model);
        }

        return FileNameHelper.fixFileName(identfier.toString());
    }

    /**
     * @return  the manufacturer of the device as set in #MANUFACTURER_PROPERTY, typically "unknown" for emulators
     */
    public static String getManufacturer(IDevice device) {
            return StringUtils.deleteWhitespace(device.getProperty(MANUFACTURER_PROPERTY));
    }

    /**
     * @return  the model of the device as set in #MODEL_PROPERTY, typically "sdk" for emulators
     */
    public static String getModel(IDevice device) {
            return StringUtils.deleteWhitespace(device.getProperty(MODEL_PROPERTY));
    }

}
